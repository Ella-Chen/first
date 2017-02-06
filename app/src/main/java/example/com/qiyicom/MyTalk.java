package example.com.qiyicom;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.MyInfo;
import adapter.TalkAdapter;
import base.BaseActivity;

public class MyTalk extends BaseActivity {
    @ViewInject(R.id.layoutback)
    private LinearLayout layoutback;
    @ViewInject(R.id.Rel_no_data)
    private RelativeLayout Rel_no_data;

    private TalkAdapter adapter;
    private List<MyInfo> mList = new ArrayList<MyInfo>();
    private ListView mListView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_talk);
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


        mListView = (ListView)findViewById(R.id.mListView);
        mListView.setAdapter(adapter);



    }


    public void loadData(){
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=TZLB&URSN="+mCache.getAsString("key");

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
                            if(jsonObject.length()!=0){
                                Rel_no_data.setVisibility(View.INVISIBLE);
                            }
                            MyInfo info = new MyInfo();
                            info.setCTID(jsonObject.getString("CTID"));
                            info.setCTOW(jsonObject.getString("CTOW"));
                            info.setCGTM(jsonObject.getString("CGTM"));
                            info.setCTNA(jsonObject.getString("CTNA"));
                            info.setCTDS(jsonObject.getString("CTDS"));
                            mList.add(info);

                        }
                        adapter = new TalkAdapter(getApplicationContext(),(ArrayList<MyInfo>)mList);
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




    @OnClick(R.id.layoutback)
    public void layoutback_click(View view)
    {
        finish();
    }
}
