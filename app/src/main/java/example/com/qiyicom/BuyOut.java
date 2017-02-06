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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.BuyInfo;
import adapter.BuyOutAdapter;
import base.BaseActivity;

public class BuyOut extends BaseActivity {
    private BuyOutAdapter adapter;
    private List<BuyInfo> mList = new ArrayList<BuyInfo>();
    private ListView mListView;
    @ViewInject(R.id.Rel_no_data)
    private RelativeLayout Rel_no_data;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_out);
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







        LinearLayout layoutback = (LinearLayout) findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    public void loadData(){
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=CCGP&URSN="+mCache.getAsString("key");
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
                            if(jsonObject.length()!=0){
                                Rel_no_data.setVisibility(View.INVISIBLE);
                            }
                            jsonObject = jsonarray.getJSONObject(i);
                            BuyInfo good = new BuyInfo();
                            good.setGPNA(jsonObject.getString("GPNA"));
                            good.setGPID(jsonObject.getString("GPID"));
                            good.setGDJG(jsonObject.getString("GDJG"));
                            good.setGPNO(jsonObject.getString("GPNO"));
                            good.setWTDT(jsonObject.getString("WTDT"));
                            good.setCZID(jsonObject.getString("CZID"));
                            Log.d("CZID5552333",jsonObject.getString("CZID"));

                            mList.add(good);
                        }
                        adapter = new BuyOutAdapter(getApplication(),(ArrayList<BuyInfo>)mList);
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
