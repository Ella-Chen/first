package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.qiyicom.R;
import tools.ACache;

/**
 * Created by nico on 16/11/17.
 */
public class CancelAdapter extends BaseAdapter {

    private List<BuyInfo> goodsDatas;
    private Context context;
    private ACache mCache;
    private LayoutInflater mInflater;

    public CancelAdapter(Context pContext, ArrayList<BuyInfo> pList) {
        mInflater = LayoutInflater.from(pContext);
        if (pList != null) {
            goodsDatas = pList;
        } else {
            goodsDatas = new ArrayList<BuyInfo>();
        }
        context = pContext;

    }

    @Override
    public Object getItem(int position) {
        return goodsDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        System.out.println("getItemId = " + position);
        return position;
    }

    @Override
    public int getCount() {
        return goodsDatas.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (getCount() == 0) {
            return null;
        }
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.listbuy_item, null);
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (CancelAdapter.Holder) convertView.getTag();
        }
        final BuyInfo goods = goodsDatas.get(position);
        holder.txt_name.setText(goods.getGPNA());
        holder.txt_id.setText(goods.getGPID());
        holder.txt_gs.setText(goods.getGPNO());
        holder.txt_jg.setText(goods.getGDJG());
        holder.txt_CZID.setText(goods.getCZID());
        Log.d("CZID", goods.getCZID());


//        String time = goods.getWTDT();
//        String[] times = time.split("");
//        holder.txt_date.setText(times[0]);
//        holder.txt_time.setText(times[1]);
        mCache = ACache.get(context);





        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    HttpUtils http = new HttpUtils();
                    RequestParams params = new RequestParams();
                    String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=CD&CZID="+goods.getCZID()+"&URSN="+mCache.getAsString("key");
                    Log.d("URL1234", newURl);
                    http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            try {
                                Log.d("result1111", responseInfo.result);
                                JSONObject jsonObject = new JSONObject(responseInfo.result);
                                if (jsonObject.getString("status").equals("0")) {
                                Toast.makeText(context, "撤单成功", Toast.LENGTH_SHORT).show();
                                    goodsDatas.remove(position);
                                    notifyDataSetChanged();

                                } else {
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


        return convertView;
    }

    private static void notifyDatasetChanged() {

    }




    public class Holder {
        @ViewInject(R.id.txt_name)
        public TextView txt_name;
        @ViewInject(R.id.txt_id)
        public TextView txt_id;
        @ViewInject(R.id.txt_jg)
        public TextView txt_jg;
        @ViewInject(R.id.txt_gs)
        public TextView txt_gs;
        @ViewInject(R.id.txt_date)
        public TextView txt_date;
        @ViewInject(R.id.txt_time)
        public TextView txt_time;
        @ViewInject(R.id.btn_cancel)
        public Button btn_cancel;
        @ViewInject(R.id.txt_CZID)
        public TextView txt_CZID;

    }
}
