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

import adapter.BuyInfo;
import adapter.CheckAdapter;
import tools.ACache;

/**
 * Created by nico on 16/11/22.
 */
public class check2Fragment extends Fragment {
    private CheckAdapter adapter;
    private List<BuyInfo> mList = new ArrayList<BuyInfo>();
    private ListView mListView;
    private ACache mCache;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rankingone, null);
        mCache = ACache.get(this.getActivity());
        loadData();
        mListView = (ListView)view.findViewById(R.id.mListView);
        mListView.setAdapter(adapter);

        return view;
    }
    public void loadData(){
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=JYJL&URSN="+mCache.getAsString("key");
        Log.d("1234",newURl);
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("status");
                    if (strCode == 1) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            jsonObject = jsonarray.getJSONObject(i);
                            BuyInfo good = new BuyInfo();
                            good.setGPNA(jsonObject.getString("GPNA"));
                            good.setGPID(jsonObject.getString("GPID"));
                            good.setMMFX(jsonObject.getString("MMFX"));
                            good.setGPTY(jsonObject.getString("GPTY"));
                            good.setJYZE(jsonObject.getString("JYZE"));
                            good.setQSSX(jsonObject.getString("QSSX"));
                            good.setYHSE(jsonObject.getString("YHSE"));
                            good.setGHFY(jsonObject.getString("GHFY"));
                            good.setGDJG(jsonObject.getString("GDJG"));
                            good.setGPNO(jsonObject.getString("GPNO"));
                            good.setWTDT(jsonObject.getString("WTDT"));
                            good.setCJDT(jsonObject.getString("CJDT"));
                            good.setCZID(jsonObject.getString("CZID"));
                            Log.d("CZID5552333",jsonObject.getString("CZID"));
                            mList.add(good);
                        }
                        adapter = new CheckAdapter(getActivity(),(ArrayList<BuyInfo>)mList);
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

