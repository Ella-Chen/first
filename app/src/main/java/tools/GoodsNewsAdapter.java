package tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import example.com.qiyicom.GoodsInfo;
import example.com.qiyicom.R;

public class GoodsNewsAdapter extends BaseAdapter {
    private ArrayList<GoodsInfo> goodsDatas;
    private Context context;
    private int index;
    private ACache mCache;

    public GoodsNewsAdapter(Context context, ArrayList<GoodsInfo> goodsDatas) {
        this.context = context;
        this.goodsDatas = goodsDatas;
    }

    @Override
    public int getCount() {
        return goodsDatas.size();//返回集合的长度
    }

    @Override
    public Object getItem(int position) {
        return goodsDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_itemnews, null);
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        //数据显示的处理
        final GoodsInfo goods = goodsDatas.get(position);
        holder.txt1.setText(goods.getNewsTitle());
        holder.txt_dz.setText(goods.getDzsl()+"");
        holder.txt_pl.setText(goods.getPlsl()+"评论");
//        holder.txt2.setText(goods.getNewsSummary());



        return convertView;
    }

    public class Holder {
        @ViewInject(R.id.txt1)
        public TextView txt1;

        @ViewInject(R.id.txt2)
        public TextView txt2;

        @ViewInject(R.id.txt_dz)
        public TextView txt_dz;


        @ViewInject(R.id.txt_pl)
        public TextView txt_pl;







    }
}
