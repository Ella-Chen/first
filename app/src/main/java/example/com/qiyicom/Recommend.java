package example.com.qiyicom;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.BuyInfo;
import adapter.RecommendAdapter;
import base.BaseActivity;
import tools.ACache;

/**
 * 模拟炒股大赛
 */
public class Recommend extends BaseActivity {
    private ACache mCache;
    @ViewInject(R.id.txt_URMK)
    private TextView txt_URMK;
    @ViewInject(R.id.txt_user)
    private TextView txt_user;

    @ViewInject(R.id.txt_syl)
    private TextView txt_syl;

    @ViewInject(R.id.txt_pm)
    private TextView txt_pm;

    @ViewInject(R.id.txt_zc)
    private TextView txt_zc;

    @ViewInject(R.id.txt_gpsz)
    private TextView txt_gpsz;

    @ViewInject(R.id.txt_djzj)
    private TextView txt_djzj;




    private RecommendAdapter adapter;
    private List<BuyInfo> mList = new ArrayList<BuyInfo>();
    private ListView mListView;
    @ViewInject(R.id.Rel_no_data)
    private RelativeLayout Rel_no_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
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

        txt_URMK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2)
                {
                    s.delete(posDot + 3, posDot + 4);
                }

            }
        });
        txt_zc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2)
                {
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });

        mCache = ACache.get(this);
        initView();
        loadData();

        mListView = (ListView)findViewById(R.id.mListView);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 此处传回来的position和mAdapter.getItemId()获取的一致;
                BuyInfo Bean = (BuyInfo) Recommend.this.adapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), NewsXQ.class);
                intent.putExtra("GSID", Bean.getGPID().toString());


                startActivity(intent);
            }
        });



            txt_user.setText("用户名:"+mCache.getAsString("STNA"));

        LinearLayout layoutback = (LinearLayout) findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayout layoutsousuo = (LinearLayout) findViewById(R.id.layoutsousuo);
        layoutsousuo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(getApplicationContext(), sousuo.class);
                startActivity(myintent);
            }
        });
        Button btn_buy = (Button) findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BuyIn.class);
                startActivity(intent);
            }
        });
        Button btn_buyout = (Button) findViewById(R.id.btn_buyout);
        btn_buyout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BuyOut.class);
                startActivity(intent);
            }
        });
        Button btn_cancle = (Button) findViewById(R.id.btn_cancle);  //撤单
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OlderCancel.class);
                startActivity(intent);
            }
        });
        Button btn_check = (Button) findViewById(R.id.btn_check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckIn.class);
                startActivity(intent);
            }
        });



    }

    private void initView() {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=ZYSJ&URSN=" + mCache.getAsString("key");
        Log.d("cccccggggg",newURl);
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    Log.d("resultcg", responseInfo.result);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray dataJsonarray = jsonObject.getJSONArray("data");
                        Log.d("dataJsonarray", String.valueOf(dataJsonarray));
                        if(jsonObject.length()!=0){
                            Rel_no_data.setVisibility(View.INVISIBLE);
                        }

                        String ZYYL = dataJsonarray.getJSONObject(0).getString("ZSYL");
                        String ZPM = dataJsonarray.getJSONObject(0).getString("ZPM");
                        String KYZC = dataJsonarray.getJSONObject(0).getString("ZZC");

                        String URMK = dataJsonarray.getJSONObject(0).getString("KYZJ");
                        String GPSZ = dataJsonarray.getJSONObject(0).getString("GPSZ");
                        String DJZJ = dataJsonarray.getJSONObject(0).getString("DJZJ");
                        Log.d("URMK123", URMK);
                        mCache.put("URMK",URMK);
                        txt_URMK.setText(URMK+"");
                        txt_gpsz.setText(GPSZ+"");
                        txt_pm.setText(ZPM+"");
                        txt_syl.setText(ZYYL+"");
                        txt_zc.setText(KYZC+"");
                        txt_djzj.setText(DJZJ+"");


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

    private void loadData(){
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
                        adapter = new RecommendAdapter(getApplication(),(ArrayList<BuyInfo>)mList);
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
