package tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import example.com.qiyicom.R;

public class GoodslistAdapter extends BaseAdapter {
    private ArrayList<Goodslist> goodsDatas;
    private Context context;
    private ACache mCache;

    public GoodslistAdapter(Context context, ArrayList<Goodslist> goodsDatas) {
        this.context = context;
        this.goodsDatas = goodsDatas;
    }

    public int getCount() {
        return goodsDatas.size();//返回集合的长度
    }

    @Override
    public Object getItem(int position) {
        return goodsDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item2, null);
            holder.imgSC = (ImageView) convertView.findViewById(R.id.imgSC);

            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        //数据显示的处理
        final Goodslist goods = goodsDatas.get(position);
        holder.txt11.setText(goods.getNUM());
        holder.txt1.setText(goods.getSname());
        holder.txt2.setText(goods.getYesterday_nav());
        holder.txt3.setText(goods.getSymbol());
        holder.txt4.setText(goods.getNav_date());
        if (!"".equals(goods.getNav_rate())){//判断值不为空，否则报错

        Double sd = Double.parseDouble(goods.getNav_rate());

        DecimalFormat df = new DecimalFormat("##0.00");
            holder.txt5.setText(df.format(sd) + "%");
        }

        holder.imgSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               holder.imgSC.setImageResource(R.mipmap.stars);

            }
        });
        return convertView;
    }

    public class Holder {
        @ViewInject(R.id.txt1)
        public TextView txt1;
        @ViewInject(R.id.txt11)
        public TextView txt11;

        @ViewInject(R.id.txt2)
        public TextView txt2;
        @ViewInject(R.id.txt3)
        public TextView txt3;

        @ViewInject(R.id.txt4)
        public TextView txt4;
        @ViewInject(R.id.txt5)
        public TextView txt5;
        @ViewInject(R.id.imgSC)
        public ImageView imgSC;



    }
}

