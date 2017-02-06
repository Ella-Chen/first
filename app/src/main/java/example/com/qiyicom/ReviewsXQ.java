package example.com.qiyicom;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.MyInfo;
import base.BaseActivity;

public class ReviewsXQ extends BaseActivity {

    private ReviewsXQAdapter adapter;
    private List<MyInfo> mList = new ArrayList<MyInfo>();
    private ListView mListView;

    public static String strCTID = "";
    public static String strCTOW = "";
    public static String strCGTM = "";
    public static String strCTNA = "";
    public static String strCTDS = "";

    @ViewInject(R.id.txttitle)
    private TextView txttitle;

    @ViewInject(R.id.txtname)
    private TextView txtname;

    @ViewInject(R.id.txt_time)
    private TextView txt_time;

    @ViewInject(R.id.txt_text)
    private TextView txt_text;

    @ViewInject(R.id.edt_pinglun)
    private EditText edt_pinglun;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_xq);
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


        ViewUtils.inject(this);

        loadData();


        LinearLayout layoutback = (LinearLayout)findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mListView = (ListView)findViewById(R.id.mListView);
        mListView.setAdapter(adapter);

        final Intent intent = getIntent(); //用于激活它的意图对象
        strCTID = getIntent().getStringExtra("CTID");
        strCGTM = getIntent().getStringExtra("CGTM");
        strCTNA = getIntent().getStringExtra("CTNA");
        strCTDS = getIntent().getStringExtra("CTDS");


        txttitle.setText(strCTNA);
        txt_time.setText(strCGTM);
        txt_text.setText(strCTDS);


        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCache.getAsString("ifLogin").equals("false")) {
                    Intent myintent = new Intent(getApplicationContext(), isLogin.class);
                    startActivity(myintent);
                    return;
                }


                if (TextUtils.isEmpty(edt_pinglun.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "请填写评论!", Toast.LENGTH_SHORT).show();
                    return;     }



                HttpUtils http = new HttpUtils();
                RequestParams params = new RequestParams();
                String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=PLTZ&URSN="+mCache.getAsString("key")+"&GSID="+strCTID+"&GSTT="+strCTNA+"&PLNR="+edt_pinglun.getText().toString();
                Log.d("09090909",newURl);
                http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            Log.d("123456766666", responseInfo.result);
                            if (jsonObject.getString("status").equals("0")) {

                                Toast.makeText(getApplicationContext(), "评论成功!", Toast.LENGTH_SHORT).show();
                                finish();
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







    private void loadData(){
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=TZPLLB&CTID="+strCTID;
        Log.d("1234",newURl);
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo1111", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("status");
                    if (strCode == 1) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonarray.length(); i++) {
                            jsonObject = jsonarray.getJSONObject(i);
                            MyInfo info = new MyInfo();
                            info.setURSN(jsonObject.getString("URSN"));
                            info.setPLNR(jsonObject.getString("PLNR"));
                            info.setCGTM(jsonObject.getString("CGTM"));
                            mList.add(info);

                        }
                        adapter = new ReviewsXQAdapter(getApplicationContext(),(ArrayList<MyInfo>)mList);
                        mListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

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
}
