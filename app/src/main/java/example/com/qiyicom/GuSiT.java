package example.com.qiyicom;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.MyInfo;
import adapter.ReviewsAdapter;
import base.BaseActivity;

public class GuSiT extends BaseActivity {
    private ReviewsAdapter adapter;
    private List<MyInfo> mList = new ArrayList<MyInfo>();
    private ListView mListView;


    public static String strTitle = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gu_si_t);

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
        loadData();

        TextView txt_title = (TextView)findViewById(R.id.txt_title);




        final Intent intent = getIntent(); //用于激活它的意图对象
        strTitle = getIntent().getStringExtra("title");
        if (strTitle.equals("股市谈")){
            txt_title.setText("股市谈");
        }else if (strTitle.equals("操盘日志")){
            txt_title.setText("操盘日志");
        }else if (strTitle.equals("复盘家")){
            txt_title.setText("复盘家");
        }else if (strTitle.equals("牛人访谈")){
            txt_title.setText("牛人访谈");
        }else if (strTitle.equals("港美股")){
            txt_title.setText("港美股");
        }else if (strTitle.equals("基金投资")){
            txt_title.setText("基金投资");
        }else if (strTitle.equals("美女操盘")){
            txt_title.setText("美女操盘");
        }else if (strTitle.equals("炒股大赛")){
            txt_title.setText("炒股大赛");
        }








        mListView = (ListView)findViewById(R.id.mListView);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 此处传回来的position和mAdapter.getItemId()获取的一致;
                MyInfo info = (MyInfo) GuSiT.this.adapter.getItem(position);
                Intent intent = new Intent(GuSiT.this, ReviewsXQ.class);
                intent.putExtra("CTID", info.getCTID().toString());
                intent.putExtra("CTOW",info.getCTOW().toString());
                intent.putExtra("CGTM",info.getCGTM().toString());
                intent.putExtra("CTNA",info.getCTNA().toString());
                intent.putExtra("CTDS",info.getCTDS().toString());
                startActivity(intent);
            }
        });

        LinearLayout layoutback = (LinearLayout) findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RelativeLayout Layout_bj = (RelativeLayout)findViewById(R.id.Layout_bj);
        Layout_bj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCache.getAsString("ifLogin").equals("false")) {
                    Intent myintent = new Intent(getApplicationContext(), isLogin.class);
                    startActivity(myintent);
                    return;
                }
                Intent intent = new Intent(getApplicationContext(),Edit.class);
                startActivity(intent);
            }
        });
    }

    public void loadData(){
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=TZLB";
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
                            info.setdzsl(jsonObject.getInt("dzsl"));
                            info.setPlsl(jsonObject.getInt("plsl"));
                            info.setCTID(jsonObject.getString("CTID"));
                            info.setCTOW(jsonObject.getString("CTOW"));
                            info.setCGTM(jsonObject.getString("CGTM"));
                            info.setCTNA(jsonObject.getString("CTNA"));
                            info.setCTDS(jsonObject.getString("CTDS"));
                            mList.add(info);

                        }
                        adapter = new ReviewsAdapter(getApplicationContext(),(ArrayList<MyInfo>)mList);
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
