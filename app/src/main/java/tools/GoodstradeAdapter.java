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

public class GoodstradeAdapter extends BaseAdapter {
    private ArrayList<Goodslist> goodsDatas;
    private Context context;
    private int index;
    private ACache mCache;

    public  GoodstradeAdapter(Context context, ArrayList<Goodslist> goodsDatas) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, null);
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        //数据显示的处理
        final Goodslist goods = goodsDatas.get(position);
        holder.txt11.setText(goods.getNUM());
        holder.txt1.setText(goods.getGPNA());
        holder.txt2.setText(goods.getCJJG());
        holder.txt3.setText(goods.getGPDM());
        holder.txt4.setText(goods.getGPDT());
        holder.txt5.setText(goods.getCJSL());




        return convertView;
    }

    public class Holder {
        @ViewInject(R.id.txt11)
        public TextView txt11;

        @ViewInject(R.id.txt1)
        public TextView txt1;

        @ViewInject(R.id.txt2)
        public TextView txt2;
        @ViewInject(R.id.txt3)
        public TextView txt3;

        @ViewInject(R.id.txt4)
        public TextView txt4;
        @ViewInject(R.id.txt5)
        public TextView txt5;




    }
}
