package example.com.qiyicom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import tools.ACache;

public class RankingoneFragment extends Fragment {
    private GoodsAdapter adapter;
    private List<GoodsInfo> mList = new ArrayList<GoodsInfo>();
    private ListView mListView;
    private ACache mCache;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rankingone, null);
        mListView = (ListView)view.findViewById(R.id.mListView);
        mListView.setAdapter(adapter);
        GetShangPinLieBiao();

        mCache = ACache.get(this.getActivity());


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 此处传回来的position和mAdapter.getItemId()获取的一致;
                GoodsInfo Bean = (GoodsInfo) RankingoneFragment.this.adapter.getItem(position);
                Intent intent = new Intent(getActivity(),NewsXQ.class);
                intent.putExtra("GSID", Bean.getSymbol().toString());
                mCache.put("strGSID", Bean.getSymbol().toString());

                startActivity(intent);
            }
        });


        return view;
    }
    private void GetShangPinLieBiao() {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("key","51a1ec866dddb7b623c9556dd685c2b6");
        String newURl="http://web.juhe.cn:8080/finance/stock/shall?key=51a1ec866dddb7b623c9556dd685c2b6&page=1";
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("error_code");
                    if (strCode == 0) {
                        JSONObject jsonobject= jsonObject.getJSONObject("result");
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
