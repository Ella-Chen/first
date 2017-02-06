package example.com.qiyicom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import base.BaseActivity;
import model.event.BuyEvent;

public class Buy extends BaseActivity {

    @ViewInject(R.id.txt_name)
    private TextView txt_name;
    @ViewInject(R.id.et_nowpri)
    private EditText et_nowpri;
    @ViewInject(R.id.et_buynum)
    private EditText et_buynum;
    @ViewInject(R.id.txt_buy)
    private TextView txt_buy;
    @ViewInject(R.id.txt_dt)
    private TextView txt_dt;
    @ViewInject(R.id.txt_zt)
    private TextView txt_zt;

    @ViewInject(R.id.txt_sellFivePri)
    private TextView txt_sellFivePri;
    @ViewInject(R.id.txt_sellfive)
    private TextView txt_sellfive;
    @ViewInject(R.id.txt_sellfourpri)
    private TextView txt_sellfourpri;
    @ViewInject(R.id.txt_sellfour)
    private TextView txt_sellfour;
    @ViewInject(R.id.txt_sellthreepri)
    private TextView txt_sellthreepri;
    @ViewInject(R.id.txt_sellthree)
    private TextView txt_sellthree;
    @ViewInject(R.id.txt_selltwopri)
    private TextView txt_selltwopri;
    @ViewInject(R.id.txt_selltwo)
    private TextView txt_selltwo;
    @ViewInject(R.id.txt_sellonepri)
    private TextView txt_sellonepri;
    @ViewInject(R.id.txt_sellone)
    private TextView txt_sellone;
    @ViewInject(R.id.txt_buyonepri)
    private TextView txt_buyonepri;
    @ViewInject(R.id.txt_buyone)
    private TextView txt_buyone;
    @ViewInject(R.id.txt_buytwopri)
    private TextView txt_buytwopri;
    @ViewInject(R.id.txt_buytwo)
    private TextView txt_buytwo;
    @ViewInject(R.id.txt_buythreepri)
    private TextView txt_buythreepri;
    @ViewInject(R.id.txt_buythree)
    private TextView txt_buythree;
    @ViewInject(R.id.txt_buyfourpri)
    private TextView txt_buyfourpri;
    @ViewInject(R.id.txt_buyfour)
    private TextView txt_buyfour;
    @ViewInject(R.id.txt_buyfivepri)
    private TextView txt_buyfivepri;
    @ViewInject(R.id.txt_buyfive)
    private TextView txt_buyfive;
    @ViewInject(R.id.btn_in)
    private Button btn_in;

    @ViewInject(R.id.et_gpdm)
    private EditText et_gpdm;


    private int sd11;
    public static String strGPID = "";
    public static String strCZID = "";
    public static String strGPNO = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
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

        final Intent intent = getIntent();
        strCZID = getIntent().getStringExtra("CZID");
        Log.d("strCZID111", getIntent().getStringExtra("CZID"));
        strGPNO = getIntent().getStringExtra("GPNO");
        Log.d("strGPNOmmmm", getIntent().getStringExtra("GPNO"));
        final int sd11 = Integer.parseInt(strGPNO);
        Log.d("sd11", String.valueOf(sd11));


        LinearLayout layoutback = (LinearLayout) findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button btn_1 = (Button) findViewById(R.id.btn_1);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_buynum.setText(sd11 + "");
            }
        });
        Button btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_buynum.setText(sd11 / 2 + "");
            }
        });
        Button btn_3 = (Button) findViewById(R.id.btn_3);
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_buynum.setText(sd11 / 3 + "");
            }
        });
        Button btn_4 = (Button) findViewById(R.id.btn_4);
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_buynum.setText(sd11 / 4 + "");
            }
        });





    }

    private void loadData() {

        strGPID = getIntent().getStringExtra("GPID");
        Log.d("mmmmmmmm", getIntent().getStringExtra("GPID"));

        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        String newURl = "http://web.juhe.cn:8080/finance/stock/hs?key=51a1ec866dddb7b623c9556dd685c2b6&gid=" + strGPID;
        mCache.put("et_gpdm", strGPID);
        Log.d("et_gpdmmmmmmmmmm", strGPID);
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo1mmmmmmm", responseInfo.result);
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
                                String name = datejsonObject.getString("name");
                                putACache("name", name);
                                Log.d("name", getACache("name"));
                                String gid = datejsonObject.getString("gid");
                                putACache("gid", gid);
                                String nowPri = datejsonObject.getString("nowPri");
                                putACache("nowPri", nowPri);
                                Log.d("nowPri", getACache("nowPri"));

//                                            String todayStartPri = datejsonObject.getString("todayStartPri");
//                                            String yestodEndPri = datejsonObject.getString("yestodEndPri");
                                String todayMax = datejsonObject.getString("todayMax");
                                String todayMin = datejsonObject.getString("todayMin");
                                String sellFivePri = datejsonObject.getString("sellFivePri");
                                String sellFive = datejsonObject.getString("sellFive");
                                String sellFourPri = datejsonObject.getString("sellFourPri");
                                String sellFour = datejsonObject.getString("sellFour");
                                String sellThreePri = datejsonObject.getString("sellThreePri");
                                String sellThree = datejsonObject.getString("sellThree");
                                String sellTwoPri = datejsonObject.getString("sellTwoPri");
                                String sellTwo = datejsonObject.getString("sellTwo");
                                String sellOnePri = datejsonObject.getString("sellOnePri");
                                String sellOne = datejsonObject.getString("sellOne");
                                String buyOnePri = datejsonObject.getString("buyOnePri");
                                String buyOne = datejsonObject.getString("buyOne");
                                String buyTwoPri = datejsonObject.getString("buyTwoPri");
                                String buyTwo = datejsonObject.getString("buyTwo");
                                String buyThreePri = datejsonObject.getString("buyThreePri");
                                String buyThree = datejsonObject.getString("buyThree");
                                String buyFourPri = datejsonObject.getString("buyFourPri");
                                String buyFour = datejsonObject.getString("buyFour");
                                String buyFivePri = datejsonObject.getString("buyFivePri");
                                String buyFive = datejsonObject.getString("buyFive");

                                Double sd = Double.parseDouble(sellFivePri);
                                Double sd1 = Double.parseDouble(sellFourPri);
                                Double sd2 = Double.parseDouble(sellThreePri);
                                Double sd3 = Double.parseDouble(sellTwoPri);
                                Double sd4 = Double.parseDouble(sellOnePri);
                                Double sd5 = Double.parseDouble(buyOnePri);
                                Double sd6 = Double.parseDouble(buyTwoPri);
                                Double sd7 = Double.parseDouble(buyThreePri);
                                Double sd8 = Double.parseDouble(buyFourPri);
                                Double sd9 = Double.parseDouble(buyFivePri);
                                Double sd0 = Double.parseDouble(nowPri);
                                Log.d("sd0", String.valueOf(sd0));

                                DecimalFormat df = new DecimalFormat("#.00");

                                txt_sellFivePri.setText(df.format(sd));
                                txt_sellfive.setText(sellFive);
                                txt_sellfourpri.setText(df.format(sd1));
                                txt_sellfour.setText(sellFour);
                                txt_sellthreepri.setText(df.format(sd2));
                                txt_sellthree.setText(sellThree);
                                txt_selltwopri.setText(df.format(sd3));
                                txt_selltwo.setText(sellTwo);
                                txt_sellonepri.setText(df.format(sd4));
                                txt_sellone.setText(sellOne);
                                txt_buyonepri.setText(df.format(sd5));
                                txt_buyone.setText(buyOne);
                                txt_buytwopri.setText(df.format(sd6));
                                txt_buytwo.setText(buyTwo);
                                txt_buythreepri.setText(df.format(sd7));
                                txt_buythree.setText(buyThree);
                                txt_buyfourpri.setText(df.format(sd8));
                                txt_buyfour.setText(buyFour);
                                txt_buyfivepri.setText(df.format(sd9));
                                txt_buyfive.setText(buyFive);

                                txt_name.setText(name);
                                et_gpdm.setText(gid);
                                et_nowpri.setText(nowPri);
                                txt_dt.setText(todayMin);
                                txt_zt.setText(todayMax);

                                txt_buy.setText("可卖" + strGPNO + "股");
                            }
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getApplicationContext(), "not found!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @OnClick(R.id.btn_in)
    public void btn_in_click(View view) {


        showDialog(Buy.this);


    }

    private void showDialog(final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(this, R.layout.alert_dialog, null);
        builder.setView(view);
        TextView tv_dialog_title = (TextView) view.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_message = (TextView) view.findViewById(R.id.tv_dialog_message);
        tv_dialog_title.setText("提示");
        tv_dialog_message.setText("您确定以[" + et_nowpri.getText().toString() + "]卖出[" + et_buynum.getText().toString() + "]股[" + getACache("name") + "]吗？\n(提醒：依据沪深交易规则，当日买入的股票须等到下个交易日才能卖出！)");
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        HttpUtils http = new HttpUtils();
                        RequestParams params = new RequestParams();
                        String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=MCGP&CZID=" + strCZID + "&URSN=" + mCache.getAsString("key") + "&GPNO=" + et_buynum.getText().toString() + "&MCJG=" + et_nowpri.getText().toString();
                        Log.d("URL123mmmmmm", newURl);
                        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {

                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                try {
                                    Log.d("resultmmmmm", responseInfo.result);
                                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                                    if (jsonObject.getString("status").equals("0")) {
                                        new AlertDialog.Builder(context)
                                                .setMessage("委托成功")
                                                .setPositiveButton("确认",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                                Intent intent = new Intent(getApplicationContext(),OlderCancel.class);
                                                                startActivity(intent);
                                                                EventBus.getDefault().post(new BuyEvent("update", "100"));
                                                                EventBus.getDefault().post(new BuyEvent("out", "100"));
                                                                finish();

                                                            }
                                                        })
                                                .show();
//                                        Toast.makeText(getApplicationContext(), "委托成功", Toast.LENGTH_SHORT).show();


                                    } else {
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
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
        builder.setNeutralButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @OnClick({R.id.btn_jian, R.id.btn_jia})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jian:
                String st = et_nowpri.getText().toString();
                Double ac = Double.parseDouble((st));
                Double ad = 0.01;
                Double dd = sub(ac, ad);
                String sd = Double.toString(dd);
                et_nowpri.setText(sd + "0");
                break;
            case R.id.btn_jia:
                String sf = et_nowpri.getText().toString();
                Double ab = Double.parseDouble((sf));
                Double aa = 0.01;
                Double bb = add(ab, aa);
                String sa = Double.toString(bb);
                et_nowpri.setText(sa + "0");
                break;
        }

    }

    public static Double add(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.add(b2).doubleValue());
    }

    public static Double sub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.subtract(b2).doubleValue());
    }
}
