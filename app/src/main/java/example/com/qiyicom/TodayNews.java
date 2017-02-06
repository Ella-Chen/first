package example.com.qiyicom;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import base.BaseActivity;
import tools.GoodsNewsAdapter;

/**
 * 今日要闻
 */
public class TodayNews extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener  {
    private GoodsNewsAdapter adapter;
    private List<GoodsInfo> mList = new ArrayList<GoodsInfo>();
    private ListView mListView;
    private SwipeRefreshLayout mSwipeLayout;
    public static String strTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_news);
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

        TextView txt_title = (TextView)findViewById(R.id.txt_title);

        final Intent intent = getIntent(); //用于激活它的意图对象
        strTitle = getIntent().getStringExtra("Title");
        if (strTitle.equals("股权质押")){
            txt_title.setText("股权质押");
        }else if (strTitle.equals("今日要闻")){
            txt_title.setText("今日要闻");
        }



        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(R.color.red); // 设定下拉圆圈的背景
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE); // 设置圆圈的大小


        LinearLayout layoutback = (LinearLayout)findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              finish();
                                          }
                                      }

        );

        mListView = (ListView)findViewById(R.id.mListView);
        mListView.setAdapter(adapter);
        GetShangPinLieBiao();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 此处传回来的position和mAdapter.getItemId()获取的一致;
                GoodsInfo Bean = (GoodsInfo) TodayNews.this.adapter.getItem(position);
                Intent intent = new Intent(TodayNews.this, News.class);
                intent.putExtra("GSID", Bean.getNewsID().toString());
                intent.putExtra("NewsTitle",Bean.getNewsTitle().toString());
                intent.putExtra("NewsSummary",Bean.getNewsSummary().toString());
                intent.putExtra("NewsPublishTime",Bean.getNewsPublishTime().toString());
                intent.putExtra("NewsOriginSource()",Bean.getNewsOriginSource().toString());
                intent.putExtra("Dzsl",Bean.getDzsl()+"");
                startActivity(intent);
            }
        });


        final EditText edt_sousuo = (EditText) findViewById(R.id.edt_sousuo);
        edt_sousuo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
                    Intent intent = new Intent(getApplicationContext(), SousuoData.class);
                    intent.putExtra("SSCS",edt_sousuo.getText().toString());
                    startActivity(intent);

                }
                return false;
            }
        });





    }
    private void GetShangPinLieBiao() {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//        Date curDate =new Date(System.currentTimeMillis());//获取当前时间
//        String str  = formatter.format(curDate);
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
//        params.addHeader("Authorization", "Bearer " + "d6cb4c689c30c3ee3c65ea24b00893c02060ad8450af5c40b79eea360187fe2d");
//        String newURl = "https://api.wmcloud.com/data/v1/api/subject/getNewsInfoByTime.json?field=&beginTime=08:30&endTime=08:35&newsPublishDate="+str;
        String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=XWLB";
        Log.d("xwxwxw",newURl);
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("status");
                    if (strCode == 1) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            jsonObject = jsonarray.getJSONObject(i);
                            GoodsInfo good = new GoodsInfo();
                            Log.d("accvvv","1");
                            good.setPlsl(jsonObject.getInt("plsl"));
                            Log.d("accvvv","2");
                            good.setDzsl(jsonObject.getInt("dzsl"));
                            good.setNewsID(jsonObject.getString("newsID"));
                            good.setNewsTitle(jsonObject.getString("newsTitle"));
                            good.setNewsSummary(jsonObject.getString("newsSummary"));
                            good.setNewsPublishTime(jsonObject.getString("newsPublishTime"));
                            good.setNewsOriginSource(jsonObject.getString("newsPublishSite"));
                            Log.d("accvvv","3");
                            mList.add(good);
                            Log.d("accvvv","4");

                        }
                        adapter = new GoodsNewsAdapter(getApplication(), (ArrayList<GoodsInfo>) mList);
                        Log.d("accvvv","5");
                        mListView.setAdapter(adapter);
                        Log.d("accvvv","6");


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
