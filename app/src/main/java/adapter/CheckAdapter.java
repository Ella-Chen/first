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
public class CheckAdapter extends BaseAdapter {

    private List<BuyInfo> goodsDatas;
    private Context context;
    private LayoutInflater mInflater;


    public CheckAdapter(Context pContext, ArrayList<BuyInfo> pList) {
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
            convertView = mInflater.inflate(R.layout.listcheck_item, null);
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (CheckAdapter.Holder) convertView.getTag();
        }
        final BuyInfo goods = goodsDatas.get(position);
        holder.txt_name.setText(goods.getGPNA()+"("+goods.getGPID()+")");
        holder.txt_fx.setText(goods.getMMFX());
        holder.txt_tp.setText(goods.getGPTY());
        holder.txt_num.setText(goods.getGPNO());
        holder.txt_jye.setText(goods.getJYZE());
        holder.txt_jg.setText("委托价："+goods.getGDJG());
        holder.txt_cjjg.setText("成交价："+goods.getGDJG());
        holder.txt_qs.setText("券商手续费："+goods.getQSSX());
        holder.txt_yhs.setText("印花税："+goods.getYHSE());
        holder.txt_ghf.setText("过户费："+goods.getGHFY());
        holder.txt_time.setText("委托时间："+goods.getWTDT());
        holder.txt_cjsj.setText("成交时间："+goods.getWTDT());
//        holder.txt_hjjg.setText();

        return convertView;
    }


    public class Holder {
        @ViewInject(R.id.txt_name)
        public TextView txt_name;
        @ViewInject(R.id.txt_jg)
        public TextView txt_jg;
        @ViewInject(R.id.txt_num)
        public TextView txt_num;
        @ViewInject(R.id.txt_date)
        public TextView txt_date;
        @ViewInject(R.id.txt_time)
        public TextView txt_time;
        @ViewInject(R.id.txt_fx)
        public TextView txt_fx;
        @ViewInject(R.id.txt_tp)
        public TextView txt_tp;
        @ViewInject(R.id.txt_jye)
        public TextView txt_jye;
        @ViewInject(R.id.txt_cjjg)
        public TextView txt_cjjg;
        @ViewInject(R.id.txt_qs)
        public TextView txt_qs;
        @ViewInject(R.id.txt_yhs)
        public TextView txt_yhs;
        @ViewInject(R.id.txt_ghf)
        public TextView txt_ghf;
        @ViewInject(R.id.txt_hjjg)
        public TextView txt_hjjg;
        @ViewInject(R.id.txt_cjsj)
        public TextView txt_cjsj;


    }
}
