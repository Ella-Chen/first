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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tools.GoodsNewsAdapter;

/**
 * 基金
 */

public class fundFragment extends Fragment {
    private GoodsNewsAdapter adapter;
    private List<GoodsInfo> mList = new ArrayList<GoodsInfo>();
    private ListView mListView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_fund, null);
        mListView = (ListView)view.findViewById(R.id.mListView);
        mListView.setAdapter(adapter);
        GetShangPinLieBiao();


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 此处传回来的position和mAdapter.getItemId()获取的一致;
                GoodsInfo Bean = (GoodsInfo) fundFragment.this.adapter.getItem(position);
                Intent intent = new Intent(getActivity(), News.class);
                intent.putExtra("GSID", Bean.getNewsID().toString());
                intent.putExtra("NewsTitle", Bean.getNewsTitle().toString());
                intent.putExtra("NewsSummary", Bean.getNewsSummary().toString());
                intent.putExtra("NewsPublishTime", Bean.getNewsPublishTime().toString());
                intent.putExtra("NewsOriginSource()", Bean.getNewsOriginSource().toString());

                startActivity(intent);
            }
        });
        return view;



    }
    private void GetShangPinLieBiao() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date curDate =new Date(System.currentTimeMillis());//获取当前时间
        String str  = formatter.format(curDate);
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addHeader("Authorization", "Bearer " + "5be538da318813530efcedfb6114a7ff8f66d5d60991ff5514326b7012f733f0");
        String newURl = "https://api.wmcloud.com/data/v1/api/subject/getNewsInfoByTime.json?field=&beginTime=08:30&endTime=09:00&newsPublishDate="+str;
        http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Log.d("responseInfo", responseInfo.result);
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    int strCode = jsonObject.getInt("retCode");
                    if (strCode == 1) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            jsonObject = jsonarray.getJSONObject(i);
                            GoodsInfo good = new GoodsInfo();
                            good.setNewsID(jsonObject.getString("newsID"));
                            good.setNewsTitle(jsonObject.getString("newsTitle"));
                            good.setNewsSummary(jsonObject.getString("newsSummary"));
                            good.setNewsPublishTime(jsonObject.getString("newsPublishTime"));
                            good.setNewsOriginSource(jsonObject.getString("newsPublishSite"));
                            mList.add(good);

                        }
                        adapter = new GoodsNewsAdapter(getActivity(), (ArrayList<GoodsInfo>) mList);
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
