package example.com.qiyicom;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
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

/**
 * 我要点评
 */
public class Edit extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
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




        final EditText edt_title = (EditText)findViewById(R.id.edt_title);
        final EditText edt_txt = (EditText)findViewById(R.id.edt_txt);

        LinearLayout layoutback = (LinearLayout) findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayout Layout_bj = (LinearLayout) findViewById(R.id.Layout_bj);
        Layout_bj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edt_title.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "标题不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_txt.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "内容不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpUtils http = new HttpUtils();
                RequestParams params = new RequestParams();
                String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=FT&URSN="+getACache("key")+"&CTNA="+ edt_title.getText()+"&CTTY=股票&CTDS="+edt_txt.getText()+"&CTOW="+getACache("key");
                Log.d("URL123", newURl);
                http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            Log.d("result", responseInfo.result);
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            if (jsonObject.getString("status").equals("0")) {
                                Toast.makeText(getApplicationContext(), "发帖成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Reviews.class);
                                startActivity(intent);
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

                    }
                });
            }
        });
    }
}
