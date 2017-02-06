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

import example.com.qiyicom.R;

public class GoodsmanagerAdapter extends BaseAdapter {
    private ArrayList<Goodslist> goodsDatas;
    private Context context;
    private int index;
    private ACache mCache;

    public GoodsmanagerAdapter(Context context, ArrayList<Goodslist> goodsDatas) {
        this.context = context;
        this.goodsDatas = goodsDatas;
    }

    @Override
    public int getCount() {
        return goodsDatas.size();//返回集合的长度
    }

    @Override
    public Object getItem(int position) {
        return goodsDatas.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item3, null);
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        //数据显示的处理
        final Goodslist goods = goodsDatas.get(position);
        holder.txt1.setText(goods.getMGGS());
        holder.txt2.setText(goods.getMGNO());
        holder.txt3.setText(goods.getMGTG());


        return convertView;
    }

    public class Holder {
        @ViewInject(R.id.txt1)
        public TextView txt1;

        @ViewInject(R.id.txt2)
        public TextView txt2;
        @ViewInject(R.id.txt3)
        public TextView txt3;




    }
}
