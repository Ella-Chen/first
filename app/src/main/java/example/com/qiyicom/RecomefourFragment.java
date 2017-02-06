package example.com.qiyicom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

import tools.Goodslist;
import tools.GoodslistAdapter;

public class RecomefourFragment extends Fragment {
    private GoodslistAdapter adapter;
    private List<Goodslist> mList = new ArrayList<Goodslist>();
    private ListView mListView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rankingone, null);
        mListView = (ListView)view.findViewById(R.id.mListView);
        mListView.setAdapter(adapter);
        GetShangPinLieBiao();



        return view;
    }
    private void GetShangPinLieBiao() {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key","22b18d3ba5ae9f9ded152c5c82af57f2");
//        String newURl = "http://121.43.234.2/PostJson/PostPP60HD.aspx?HDTP=";
        String newURl = "http://japi.juhe.cn/jingzhi/query.from?page=1&pagesize=20&type=zhaiquan&key=22b18d3ba5ae9f9ded152c5c82af57f2";
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("error_code");
                    if (strCode == 0) {
                        JSONArray jsonarray = jsonObject.getJSONArray("result");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            jsonObject = jsonarray.getJSONObject(i);
                            Goodslist good = new Goodslist();
                            good.setSname(jsonObject.getString("sname"));
                            good.setSymbol(jsonObject.getString("symbol"));
                            good.setPer_nav(jsonObject.getString("per_nav"));
                            good.setNav_date(jsonObject.getString("nav_date"));
                            good.setNav_rate(jsonObject.getString("nav_rate"));
                            good.setNUM(i + 1 + "");
                            mList.add(good);

                        }
                        adapter = new GoodslistAdapter(getActivity(), (ArrayList<Goodslist>) mList);
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
