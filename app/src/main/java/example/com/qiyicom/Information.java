package example.com.qiyicom;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import base.BaseActivity;

public class Information extends BaseActivity {
    private boolean isHidden = true;
    private EditText passwordEditText;
    private EditText password2EditText;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);



        ImageView img_clear = (ImageView) findViewById(R.id.img_clear);
        img_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone.setText("");
            }
        });

        final ImageView img_eye = (ImageView) findViewById(R.id.img_eye);
        img_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHidden) {
                    //设置EditText文本为可见的
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                passwordEditText.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = passwordEditText.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }

            }
        });

        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        password2EditText = (EditText) findViewById(R.id.password2EditText);
        phone = (EditText) findViewById(R.id.phone);

        Button saveCord = (Button) findViewById(R.id.savecord);
        saveCord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "请填写手机号码!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordEditText.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "请填写密码!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password2EditText.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "请确认密码!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.getText().toString().trim().length()<11){
                    Toast.makeText(getApplicationContext(), "请输入完整电话号码!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordEditText.getText().toString().trim().length() < 6) {
                    Toast.makeText(getApplicationContext(), "密码长度不能小于6位!", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpUtils http = new HttpUtils();
                RequestParams params = new RequestParams();
                String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=ZC&PHNO="+phone.getText().toString()+"&PSWD="+passwordEditText.getText().toString()+"&PSWDC="+password2EditText.getText().toString();
                Log.d("newURl",newURl);
                http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            Log.d("result",responseInfo.result);
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            if (jsonObject.getString("status").equals("1")) {
                                JSONArray dataJsonarray = jsonObject.getJSONArray("data");
                                String key = dataJsonarray.getJSONObject(0).getString("KEY");
                                String pswd = dataJsonarray.getJSONObject(0).getString("PSWD");
                                String STNA = dataJsonarray.getJSONObject(0).getString("STNA");

                                Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Information.this, isLogin.class);
                                Information.this.startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("data").toString(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(getApplicationContext(), "网络不可用!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}
