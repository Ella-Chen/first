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

import base.BaseActivity;
import tools.GoodsNewsAdapter;

public class SousuoData extends BaseActivity {
    public static String strSSCS = "";
    private GoodsNewsAdapter adapter;
    private List<GoodsInfo> mList = new ArrayList<GoodsInfo>();
    private ListView mListView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo_data);
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

        LinearLayout layoutback = (LinearLayout)findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Intent intent = getIntent(); //用于激活它的意图对象
        strSSCS = getIntent().getStringExtra("SSCS");


        mListView = (ListView)findViewById(R.id.mListView);
        mListView.setAdapter(adapter);

        GetShangPinLieBiao();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 此处传回来的position和mAdapter.getItemId()获取的一致;
                GoodsInfo Bean = (GoodsInfo) SousuoData.this.adapter.getItem(position);
                Intent intent = new Intent(SousuoData.this, News.class);
                intent.putExtra("GSID", Bean.getNewsID().toString());
                intent.putExtra("NewsTitle",Bean.getNewsTitle().toString());
                intent.putExtra("NewsSummary",Bean.getNewsSummary().toString());
                intent.putExtra("NewsPublishTime",Bean.getNewsPublishTime().toString());
                intent.putExtra("NewsOriginSource()",Bean.getNewsOriginSource().toString());
                startActivity(intent);
            }
        });
    }


    private void GetShangPinLieBiao() {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=SSXW&SSCS="+strSSCS;
        Log.d("sssssss",newURl);
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfosss", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("status");
                    if (strCode == 1) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            jsonObject = jsonarray.getJSONObject(i);
                            GoodsInfo good = new GoodsInfo();
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
}
