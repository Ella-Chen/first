package example.com.qiyicom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import adapter.MyInfo;
import tools.ACache;

/**
 * Created by nico on 16/12/13.
 */
public class ReviewsXQAdapter extends BaseAdapter {

    private List<MyInfo> goodsDatas;
    private Context context;
    private ACache mCache;
    private LayoutInflater mInflater;


    public ReviewsXQAdapter(Context pContext, ArrayList<MyInfo> pList
    ) {
        mInflater = LayoutInflater.from(pContext);
        if (pList != null) {
            goodsDatas = pList;
        } else {
            goodsDatas = new ArrayList<MyInfo>();
        }
        context = pContext;



    }

    @Override
    public int getCount() {
        return  goodsDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        System.out.println("getItemId = " + position);
        return -1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (getCount() == 0) {
            return null;
        }
        final ReviewsXQAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listxq_item, null);
            holder = new ReviewsXQAdapter.ViewHolder();
            ViewUtils.inject(holder, convertView);

            convertView.setTag(holder);
        } else {
            holder = (ReviewsXQAdapter.ViewHolder) convertView.getTag();
        }
        final MyInfo info = goodsDatas.get(position);
        holder.txt_name.setText(info.getURSN());
        holder.txt_time.setText(info.getCGTM());
        holder.txt_title.setText(info.getPLNR());

        return convertView;
    }


    public class ViewHolder {
        @ViewInject(R.id.txt_name)
        public TextView txt_name;
        @ViewInject(R.id.txt_time)
        public TextView txt_time;
        @ViewInject(R.id.txt_title)
        public TextView txt_title;

    }

}
