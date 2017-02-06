package example.com.qiyicom;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
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


/**
 * 我要点评
 */

public class Reviews extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ReviewsAdapter adapter;
    private List<MyInfo> mList = new ArrayList<MyInfo>();
    private ListView mListView;
    private SwipeRefreshLayout mSwipeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
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

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(R.color.red); // 设定下拉圆圈的背景
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE); // 设置圆圈的大小



        ViewUtils.inject(this);
        loadData();

        LinearLayout layoutback = (LinearLayout) findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout Layout_bj = (LinearLayout)findViewById(R.id.Layout_bj);
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



        mListView = (ListView)findViewById(R.id.mylistview2);

        View headerView = getLayoutInflater().inflate(R.layout.listhead, null);
        LinearLayout Lin_gst = (LinearLayout) headerView.findViewById(R.id.Lin_gst);
        LinearLayout Lin_cp = (LinearLayout) headerView.findViewById(R.id.Lin_cp);
        LinearLayout Lin_fpj = (LinearLayout) headerView.findViewById(R.id.Lin_fpj);
        LinearLayout Lin_nr = (LinearLayout) headerView.findViewById(R.id.Lin_nr);
        LinearLayout Lin_gmg = (LinearLayout) headerView.findViewById(R.id.Lin_gmg);
        LinearLayout Lin_jj = (LinearLayout) headerView.findViewById(R.id.Lin_jj);
        LinearLayout Lin_mn = (LinearLayout) headerView.findViewById(R.id.Lin_mn);
        LinearLayout Lin_cc = (LinearLayout) headerView.findViewById(R.id.Lin_cc);

        Lin_gst.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(),GuSiT.class);
                intent.putExtra("title","股市谈");
                startActivity(intent);
            }
        });
        Lin_cp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(),GuSiT.class);
                intent.putExtra("title","操盘日志");
                startActivity(intent);
            }
        });
        Lin_fpj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(),GuSiT.class);
                intent.putExtra("title","复盘家");
                startActivity(intent);
            }
        });
        Lin_nr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(),GuSiT.class);
                intent.putExtra("title","牛人访谈");
                startActivity(intent);
            }
        });
        Lin_gmg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(),GuSiT.class);
                intent.putExtra("title","港美股");
                startActivity(intent);
            }
        });
        Lin_jj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(),GuSiT.class);
                intent.putExtra("title","基金投资");
                startActivity(intent);
            }
        });
        Lin_mn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(),GuSiT.class);
                intent.putExtra("title","美女操盘");
                startActivity(intent);
            }
        });
        Lin_cc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(),GuSiT.class);
                intent.putExtra("title","炒股大赛");
                startActivity(intent);
            }
        });

        mListView.addHeaderView(headerView);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // 此处传回来的position和mAdapter.getItemId()获取的一致;
                    MyInfo info = (MyInfo) Reviews.this.adapter.getItem(position-1);
                    Intent intent = new Intent(Reviews.this, ReviewsXQ.class);
                    intent.putExtra("CTID", info.getCTID().toString());
                    intent.putExtra("CTOW", info.getCTOW().toString());
                    intent.putExtra("CGTM", info.getCGTM().toString());
                    intent.putExtra("CTNA", info.getCTNA().toString());
                    intent.putExtra("CTDS", info.getCTDS().toString());
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
    /*
    * 监听器SwipeRefreshLayout.OnRefreshListener中的方法，当下拉刷新后触发
    */
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 停止刷新
                mSwipeLayout.setRefreshing(false);
            }
        }, 5000); // 5秒后发送消息，停止刷新
    }



}
