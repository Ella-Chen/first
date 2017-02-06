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
import tools.GoodsmanagerAdapter;

public class managergFragment extends Fragment {
    private GoodsmanagerAdapter adapter;
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
        String newURl = "http://121.43.234.2/PostJson/PostOF60MG.aspx?MGBJ=公募";
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
                            Goodslist good = new Goodslist();
                            good.setMGGS(jsonObject.getString("MGGS"));
                            good.setMGNO(jsonObject.getString("MGNO"));
                            good.setMGTG(jsonObject.getString("MGTG"));
                            mList.add(good);

                        }
                        adapter = new GoodsmanagerAdapter(getActivity(), (ArrayList<Goodslist>) mList);
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
