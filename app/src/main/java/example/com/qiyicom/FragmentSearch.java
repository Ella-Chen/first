package example.com.qiyicom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

import tools.ACache;

/**
 * Created by Administrator on 2016/4/20.
 */
public class FragmentSearch extends Fragment {
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
    private GoodsAdapter adapter;
    private List<GoodsInfo> mList = new ArrayList<GoodsInfo>();
    private ListView mListView;
    private ACache mCache;

    @ViewInject(R.id.imgsousuo)
    private ImageView imgsousuo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.hangqing, null);
        ViewUtils.inject(this, view);
        mListView = (ListView) view.findViewById(R.id.mListView);
        mListView.setAdapter(adapter);
        mCache = ACache.get(this.getActivity());

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 此处传回来的position和mAdapter.getItemId()获取的一致;
                GoodsInfo Bean = (GoodsInfo) FragmentSearch.this.adapter.getItem(position);
                Intent intent = new Intent(getActivity(), NewsXQ.class);
                intent.putExtra("GSID", Bean.getSymbol().toString());
//                mCache.put("strGSID", Bean.getSymbol().toString());

                startActivity(intent);
            }
        });

        GetShangPinLieBiao();
        GetShenZhi();
        loadData();



        return view;
}


    @OnClick(R.id.imgsousuo)
    public void imgsousuo_click(View view) {
        Intent myintent = new Intent(getActivity(), sousuo.class);
        startActivity(myintent);
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
                        txtdown1.setText(increPer + "%");

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
        params.addBodyParameter("key", "51a1ec866dddb7b623c9556dd685c2b6");
        params.addBodyParameter("type", "1");
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
                        txtdown2.setText(increPer + "%");

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

    private void loadData() {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", "51a1ec866dddb7b623c9556dd685c2b6");
        String newURl = "http://web.juhe.cn:8080/finance/stock/shall?key=51a1ec866dddb7b623c9556dd685c2b6&page=1";
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("error_code");
                    if (strCode == 0) {
                        JSONObject jsonobject = jsonObject.getJSONObject("result");
                        JSONArray jsonarray = jsonobject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            jsonObject = jsonarray.getJSONObject(i);
                            GoodsInfo good = new GoodsInfo();
                            good.setSymbol(jsonObject.getString("symbol"));
                            good.setName(jsonObject.getString("name"));
                            good.setTrade(jsonObject.getString("trade"));
                            mList.add(good);

                        }
                        adapter = new GoodsAdapter(getActivity(), (ArrayList<GoodsInfo>) mList);
                        mListView.setAdapter(adapter);


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
