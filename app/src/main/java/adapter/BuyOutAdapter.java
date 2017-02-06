package adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import example.com.qiyicom.Buy;
import example.com.qiyicom.R;
import tools.ACache;

/**
 * Created by nico on 16/12/16.
 */

public class BuyOutAdapter extends BaseAdapter {
    private List<BuyInfo> goodsDatas;
    private Context context;
    private ACache mCache;
    private LayoutInflater mInflater;


    public BuyOutAdapter(Context pContext, ArrayList<BuyInfo> pList) {
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
        BuyOutAdapter.Holder holder = null;
        if (convertView == null) {
            holder = new BuyOutAdapter.Holder();
            convertView = mInflater.inflate(R.layout.listbuyout_item, null);
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (BuyOutAdapter.Holder) convertView.getTag();
        }
        final BuyInfo goods = goodsDatas.get(position);
        holder.txt_name.setText(goods.getGPNA());
        holder.txt_id.setText(goods.getGPID());
        holder.txt_date.setText(goods.getGPNO());
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
                if (goods.getCZID() != null && mCache.getAsString("key") != null) {
                    Intent intent = new Intent(context, Buy.class);
                    intent.putExtra("GPID", goods.getGPID().toString());
                    Log.d("GPIDmmm",goods.getGPID().toString());
                    intent.putExtra("CZID", goods.getCZID().toString());
                    Log.d("CZIDmmm", goods.getCZID().toString());
                    intent.putExtra("GPNO",goods.getGPNO().toString());
                    Log.d("GPNOmmmm",goods.getGPNO().toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);




                }
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
