package example.com.qiyicom;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import base.BaseActivity;

public class AppSuggestionActivity extends BaseActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_suggestion);

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



        RelativeLayout Rel_back = (RelativeLayout)findViewById(R.id.Rel_back);
        Rel_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText et_suggestion = (EditText)findViewById(R.id.et_suggestion);
        final EditText et_contact = (EditText)findViewById(R.id.et_contact);
        RelativeLayout Rel_send = (RelativeLayout)findViewById(R.id.Rel_send);
        Rel_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_suggestion.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "请填写您的建议!", Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpUtils http = new HttpUtils();
                RequestParams params = new RequestParams();
                String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=FKYJ&URSN="+getACache("key")+"&CTDS="+et_suggestion.getText().toString();
                Log.d("newURl", newURl);
                http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            Log.d("result", responseInfo.result);
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            if (jsonObject.getString("status").equals("0")) {
                                Toast.makeText(getApplicationContext(), "意见反馈成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
//                                Toast.makeText(getApplicationContext(), json.getString("msg"), Toast.LENGTH_SHORT).show();
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
