package example.com.qiyicom;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import base.BaseActivity;

/**
 * 今日要闻详情
 */

public class News extends BaseActivity {
    public static String strGSID = "";
    public static String NewsTitle = "";
    public static String NewsSummary = "";
    public static String NewsPublishTime = "";
    public static String NewsOriginSource = "";
    public static String Dzsl = "";


    private ReviewsXQAdapter adapter;
    private List<MyInfo> mList = new ArrayList<MyInfo>();
    private ListView mListView;
    private boolean ifFollow = false;
    private View heardView;
    private View footView;

    private TextView txtname, txttitle, txtsource, txt_dz;
    private RelativeLayout Rel_zan;
    private EditText edt_pinglun;
    private Button save;
    private ImageView img_zan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
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


        final Intent intent = getIntent(); //用于激活它的意图对象
        strGSID = getIntent().getStringExtra("GSID");
        NewsTitle = getIntent().getStringExtra("NewsTitle");
        NewsPublishTime = getIntent().getStringExtra("NewsPublishTime");
        NewsOriginSource = getIntent().getStringExtra("NewsOriginSource");
        NewsSummary = getIntent().getStringExtra("NewsSummary");
        Dzsl = getIntent().getStringExtra("Dzsl");
        loadData();
        mListView = (ListView) findViewById(R.id.mListView);
        heardView = getLayoutInflater().inflate(R.layout.newshead, null);
        findheardView();
        GetShangPinLieBiao();

        mListView.addHeaderView(heardView);
        footView=getLayoutInflater().inflate(R.layout.newsfootview, null);
        mListView.addFooterView(footView);
        findfootView();
        mListView.setAdapter(adapter);


        LinearLayout layoutback = (LinearLayout) findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private void findheardView(){
        txtname = (TextView) heardView.findViewById(R.id.txtname);
        txttitle = (TextView) heardView.findViewById(R.id.txttitle);
        txtsource = (TextView) heardView.findViewById(R.id.txtsource);
        txt_dz = (TextView) heardView.findViewById(R.id.txt_dz);
        img_zan = (ImageView)heardView.findViewById(R.id.img_zan);

        Rel_zan = (RelativeLayout)heardView.findViewById(R.id.Rel_zan);
        Rel_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(getACache("key")))
                {
                    Intent intent = new Intent();
                    intent.setClass(mContext, isLogin.class);
                    startActivity(intent);
                    return;
                }
                if(ifFollow)
                {
                    cancelfollowUser();
                }else{
                    followUser();
                }
            }
        });
    }
    private void findfootView(){
        edt_pinglun = (EditText)footView.findViewById(R.id.edt_pinglun);
        save = (Button) footView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(isEmpty(getACache("key")))
                {
                    Intent intent = new Intent();
                    intent.setClass(mContext, isLogin.class);
                    startActivity(intent);
                    return;
                }
                if (TextUtils.isEmpty(edt_pinglun.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "请填写评论!", Toast.LENGTH_SHORT).show();
                    return;     }

                HttpUtils http = new HttpUtils();
                RequestParams params = new RequestParams();
                String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=PLXW&URSN="+mCache.getAsString("key")+"&GSID="+strGSID+"&GSTT="+NewsTitle+"&PLNR="+edt_pinglun.getText().toString();
                Log.d("09090909",newURl);
                http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            Log.d("1234567", responseInfo.result);
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
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

    private void GetShangPinLieBiao() {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", "Bearer " + "d6cb4c689c30c3ee3c65ea24b00893c02060ad8450af5c40b79eea360187fe2d");
        String newURl = "https://api.wmcloud.com/data/v1/api/subject/getNewsBody.json?field=&newsID=" + strGSID;
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("retCode");
                    if (strCode == 1) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            jsonObject = (JSONObject) jsonarray.get(i);
                            if (i == 0) {
                                String newsBody = jsonObject.getString("newsBody");


                                txttitle.setText(NewsTitle);
                                txtsource.setText(NewsSummary);
                                txt_dz.setText(Dzsl);
                                txtname.setText(newsBody);
                            }
                        }
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


    private void followUser()
    {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=DZTZ&URSN=" + mCache.getAsString("key") + "&GSID=" + strGSID+ "&GSTT=TEST&PLIT=1";
        Log.d("newURldz", newURl);
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("dzdzdz", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    if (jsonObject.getString("status").equals("0")) {
                        Toast.makeText(getApplicationContext(), "点赞成功!", Toast.LENGTH_SHORT).show();
                        img_zan.setImageResource(R.mipmap.btn_dianzan);
                        txt_dz.setTextColor(Color.parseColor("#D95555"));
                        txt_dz.setText(Dzsl+1);
                        ifFollow=true;

                    } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
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

    private void cancelfollowUser()
    {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=DZTZ&URSN=" + mCache.getAsString("key") + "&GSID=" + strGSID+ "&GSTT=TEST&PLIT=2";
        Log.d("newURldz", newURl);
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("dzdzdz", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    if (jsonObject.getString("status").equals("0")) {
                        Toast.makeText(getApplicationContext(), "取消点赞!", Toast.LENGTH_SHORT).show();
                        img_zan.setImageResource(R.mipmap.btn_zan);
                        txt_dz.setTextColor(Color.parseColor("#000000"));
                        txt_dz.setText(Dzsl);
                        ifFollow=false;

                    } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
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

    private void loadData() {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=XWPLLB&XWID=" + strGSID;
        Log.d("1234999999", newURl);
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo11411", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("status");
                    if (strCode == 1) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            jsonObject = jsonarray.getJSONObject(i);
                            MyInfo info = new MyInfo();
//                            info.setPLTM(jsonObject.getString("PLTM"));
                            info.setPLNR(jsonObject.getString("PLNR"));
                            info.setCGTM(jsonObject.getString("CGTM"));
                            mList.add(info);

                        }
                        adapter = new ReviewsXQAdapter(getApplicationContext(), (ArrayList<MyInfo>) mList);
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
