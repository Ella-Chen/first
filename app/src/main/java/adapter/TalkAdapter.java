package adapter;

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

import example.com.qiyicom.R;
import tools.ACache;

/**
 * Created by nico on 16/11/17.
 */
public class TalkAdapter extends BaseAdapter {

    private List<MyInfo> goodsDatas;
    private Context context;
    private ACache mCache;
    private LayoutInflater mInflater;


    public TalkAdapter(Context pContext, ArrayList<MyInfo> pList) {
        mInflater = LayoutInflater.from(pContext);
        if (pList != null) {
            goodsDatas = pList;
        } else {
            goodsDatas = new ArrayList<MyInfo>();
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
            convertView = mInflater.inflate(R.layout.listmy_item, null);
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (TalkAdapter.Holder) convertView.getTag();
        }
        final MyInfo info = goodsDatas.get(position);
        holder.txt_name.setText(info.getCTOW());
        holder.txt_time.setText(info.getCGTM());
        holder.txt_title.setText(info.getCTNA());
        holder.txt_text.setText(info.getCTDS());
        holder.txt_id.setText(info.getCTID());

        mCache = ACache.get(context);

        return convertView;
    }


    public class Holder {
        @ViewInject(R.id.txt_name)
        public TextView txt_name;
        @ViewInject(R.id.txt_time)
        public TextView txt_time;
        @ViewInject(R.id.txt_title)
        public TextView txt_title;
        @ViewInject(R.id.txt_text)
        public TextView txt_text;
        @ViewInject(R.id.txt_id)
        public TextView txt_id;

    }
}
