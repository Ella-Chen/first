package example.com.qiyicom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import tools.ACache;

/**
 * Created by Administrator on 2016/4/20.
 */
public class FragmentTuan extends Fragment {

    @ViewInject(R.id.imgsousuo)
    private ImageView imgsousuo;

    private ACache mCache;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.zixuan, null);
        ViewUtils.inject(this, view);
        mCache = ACache.get(this.getActivity());



        return view;
    }
    @OnClick(R.id.imgsousuo)
    public void imgsousuo_click(View view) {
        Intent myintent = new Intent(getActivity(), sousuo.class);
        startActivity(myintent);
    }






}