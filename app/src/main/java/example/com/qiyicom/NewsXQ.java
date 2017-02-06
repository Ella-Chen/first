package example.com.qiyicom;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import base.BaseActivity;
import tools.SlideShowView;

public class NewsXQ extends BaseActivity {
    public static String strGSID = "";
    @ViewInject(R.id.txtname)
    private TextView txtname;
    @ViewInject(R.id.txtcode)
    private TextView txtcode;
    @ViewInject(R.id.txtnumber)
    private TextView txtnumber;
    @ViewInject(R.id.txtup)
    private TextView txtup;
    @ViewInject(R.id.txtdown)
    private TextView txtdown;
    @ViewInject(R.id.txt1)
    private TextView txt1;
    @ViewInject(R.id.txt2)
    private TextView txt2;
    @ViewInject(R.id.txt3)
    private TextView txt3;
    @ViewInject(R.id.txt4)
    private TextView txt4;
    @ViewInject(R.id.txt5)
    private TextView txt5;
    @ViewInject(R.id.txt6)
    private TextView txt6;
    @ViewInject(R.id.txt7)
    private TextView txt7;
    @ViewInject(R.id.txt8)
    private TextView txt8;
    @ViewInject(R.id.txt9)
    private TextView txt9;
    @ViewInject(R.id.txt10)
    private TextView txt10;
    @ViewInject(R.id.txt11)
    private TextView txt11;
    @ViewInject(R.id.txt12)
    private TextView txt12;
    @ViewInject(R.id.txt13)
    private TextView txt13;
    @ViewInject(R.id.imgpic)
    private ImageView imgpic;
    @ViewInject(R.id.layoutback)
    private LinearLayout layoutback;


    @ViewInject(R.id.slideshowView)
    private SlideShowView slideshowView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_xq);
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
        TextView txtname = (TextView) findViewById(R.id.txtname);
        final Intent intent = getIntent(); //用于激活它的意图对象
         strGSID = getIntent().getStringExtra("GSID");


        GetShangPinLieBiao();

    }
    @OnClick(R.id.layoutback)
    public void layoutback_click(View view)
    {
        finish();
    }

    private void GetShangPinLieBiao() {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        String newURl = "http://web.juhe.cn:8080/finance/stock/hs?key=51a1ec866dddb7b623c9556dd685c2b6&gid="+strGSID;
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo1", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("resultcode");
                    if (strCode == 200) {
                        JSONArray jsonarray = jsonObject.getJSONArray("result");
                        Log.d("result", String.valueOf(jsonarray));
                        for (int i = 0; i < jsonarray.length(); i++) {
                            jsonObject = (JSONObject) jsonarray.get(i);
                            if (i == 0) {
                                Log.d("jsonObject", String.valueOf(jsonObject));
                                JSONObject datejsonObject = jsonObject.getJSONObject("data");
                                Log.d("datejsonObject", String.valueOf(datejsonObject));
//                              JSONObject dapandatajsonObject = jsonObject.getJSONObject("dapandatason");
//                              JSONObject gopicturejsonObject = jsonObject.getJSONObject("gopicture");
                                String name = datejsonObject.getString("name");
                                String gid = datejsonObject.getString("gid");
                                String date = datejsonObject.getString("date");
                                String time = datejsonObject.getString("time");
                                String nowPri = datejsonObject.getString("nowPri");
                                String increase = datejsonObject.getString("increase");
                                String increPer = datejsonObject.getString("increPer");
                                String todayStartPri = datejsonObject.getString("todayStartPri");
                                String yestodEndPri = datejsonObject.getString("yestodEndPri");
                                String traNumber = datejsonObject.getString("traNumber");
                                String todayMax = datejsonObject.getString("todayMax");
                                String todayMin = datejsonObject.getString("todayMin");
                                String traAmount = datejsonObject.getString("traAmount");
                                Double sd = Double.parseDouble(traAmount);
                                DecimalFormat df = new DecimalFormat("#.00");



                                txtname.setText(name + "(" + gid + ")");
                                txtcode.setText("已收盘" + date + " " + time);
                                txtnumber.setText(nowPri);
                                txtup.setText(increase);
                                txtdown.setText(increPer + "%");
                                txt1.setText(todayStartPri);
                                txt3.setText(yestodEndPri);
                                txt2.setText(traNumber);
                                txt5.setText(todayMax);
                                txt8.setText(todayMin);
                                txt11.setText(df.format(sd / 100000000) + "亿");

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
}
