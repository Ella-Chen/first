package example.com.qiyicom;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/4/20.
 */
public class Comany extends Activity {
    @ViewInject(R.id.layoutdaz)
    private LinearLayout layoutdaz;
    @ViewInject(R.id.layoutsim)
    private LinearLayout layoutsim;
    @ViewInject(R.id.layoutdingx)
    private LinearLayout layoutdingx;
    @ViewInject(R.id.layoutguqu)
    private LinearLayout layoutguqu;
    @ViewInject(R.id.layoutzixu)
    private LinearLayout layoutzixu;
    @ViewInject(R.id.layoutpaiming)
    private LinearLayout layoutpaiming;
    @ViewInject(R.id.layoutgm)
    private LinearLayout layoutgm;
    @ViewInject(R.id.layoutjz)
    private LinearLayout layoutjz;
    @ViewInject(R.id.layoutgz)
    private LinearLayout layoutgz;
    @ViewInject(R.id.txtprice1)
    private TextView txtprice1;
    @ViewInject(R.id.txtup1)
    private TextView txtup1;
    @ViewInject(R.id.txtdown1)
    private TextView txtdown1;
    @ViewInject(R.id.txtprice2)
    private TextView txtprice2;
    @ViewInject(R.id.txtup2)
    private TextView txtup2;
    @ViewInject(R.id.txtdown2)
    private TextView txtdown2;


    @ViewInject(R.id.img_sousuo)
    private ImageView img_sousuo;


    @ViewInject(R.id.layoutback)
    private LinearLayout layoutback;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comany);

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
        GetShangPinLieBiao();
        GetShenZhi();

    }
    private void GetShangPinLieBiao() {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", "51a1ec866dddb7b623c9556dd685c2b6");
        params.addBodyParameter("type", "0");
        String newURl = "http://web.juhe.cn:8080/finance/stock/hs?type=0&key=51a1ec866dddb7b623c9556dd685c2b6";
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("error_code");
                    if (strCode == 0) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                        String highPri = jsonObject1.getString("highPri");
                        String increPer = jsonObject1.getString("increPer");
                        String increase = jsonObject1.getString("increase");
                        txtprice1.setText(highPri);
                        txtup1.setText(increase);
                        txtdown1.setText(increPer+"%");

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
    private void GetShenZhi() {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key","51a1ec866dddb7b623c9556dd685c2b6");
        params.addBodyParameter("type","1");
        String newURl = "http://web.juhe.cn:8080/finance/stock/hs?type=1&key=51a1ec866dddb7b623c9556dd685c2b6";
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("error_code");
                    if (strCode == 0) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                        String highPri = jsonObject1.getString("highPri");
                        String increPer = jsonObject1.getString("increPer");
                        String increase = jsonObject1.getString("increase");
                        txtprice2.setText(highPri);
                        txtup2.setText(increase);
                        txtdown2.setText(increPer+"%");

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

    @OnClick(R.id.layoutdaz)
    public void layoutdaz_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(), Trade.class);
        startActivity(myintent);
    }
    @OnClick(R.id.layoutsim) //
    public void layoutsim_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(), Ranking.class);
        myintent.putExtra("Title","私募基金");
        startActivity(myintent);
    }

    @OnClick(R.id.layoutdingx)
    public void layoutdingx_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(),Manager.class);
        startActivity(myintent);
    }

    @OnClick(R.id.layoutguqu)
    public void layoutguqu_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(), TodayNews.class);
        myintent.putExtra("Title","股权质押");
        startActivity(myintent);
    }
    @OnClick(R.id.layoutzixu)
    public void layoutzixu_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(), More.class);
        startActivity(myintent);
    }
    @OnClick(R.id.layoutpaiming)
    public void layoutpaiming_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(), Ranking.class);
        myintent.putExtra("Title","基金排名");
        startActivity(myintent);
    }

    @OnClick(R.id.layoutgm)//公募基金
    public void layoutgm_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(), Recome.class);
        myintent.putExtra("Title","公募基金");
        startActivity(myintent);
    }
    @OnClick(R.id.layoutjz)
    public void layoutjz_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(), Recome.class);
        myintent.putExtra("Title","基金净值");
        startActivity(myintent);
    }
    @OnClick(R.id.layoutgz)
    public void layoutgz_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(), Ranking.class);
        myintent.putExtra("Title","基金估值");
        startActivity(myintent);
    }

    @OnClick(R.id.layoutback)
    public void layoutback_click(View view)
    {
       finish();
    }
    @OnClick(R.id.img_sousuo)
    public void img_sousuo_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(), sousuo.class);
        startActivity(myintent);
    }



}
