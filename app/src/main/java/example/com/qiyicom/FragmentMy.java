package example.com.qiyicom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import model.event.UserFgEvent;
import tools.ACache;


/**
 * Created by Administrator on 2016/4/20.
 */
public class FragmentMy extends Fragment {
    private ACache mCache;

    @ViewInject(R.id.lblURID)
    private TextView lblURID;
    @ViewInject(R.id.imgURL)
    private CircleImageView imgURL;
    @ViewInject(R.id.txt102)
    private TextView txt102;
    @ViewInject(R.id.txt101)
    private TextView txt101;
    @ViewInject(R.id.txt103)
    private TextView txt103;


    @ViewInject(R.id.layout07)
    private RelativeLayout layout07;

    @ViewInject(R.id.layout08)
    private RelativeLayout layout08;

    @ViewInject(R.id.layout09)
    private RelativeLayout layout09;

    @ViewInject(R.id.layout10)
    private RelativeLayout layout10;

    @ViewInject(R.id.layout11)
    private RelativeLayout layout11;

    @ViewInject(R.id.layout12)
    private RelativeLayout layout12;

//    @ViewInject(R.id.layout13)
//    private RelativeLayout layout13;
    @ViewInject(R.id.Rel_set)
    private RelativeLayout Rel_set;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.my, null);
        mCache = ACache.get(this.getActivity());
        ViewUtils.inject(this, view);
        if (TextUtils.isEmpty(mCache.getAsString("ifLogin"))) {

            mCache.put("ifLogin", "false");

        }
        if (mCache.getAsString("ifLogin").equals("false")) {
            Intent myintent = new Intent(getActivity(), isLogin.class);
            startActivity(myintent);
        }
        lblURID.setText(mCache.getAsString("key"));
        EventBus.getDefault().register(this);//注册事件

        return view;
    }









    @OnClick(R.id.layout07)
    public void layout07_click(View view)
    {
        if (mCache.getAsString("ifLogin").equals("false")) {

            Intent myintent = new Intent(getActivity(), isLogin.class);
            startActivity(myintent);
        } else {
            Intent myintent = new Intent(getActivity(), MyMessage.class);
            startActivity(myintent);

        }

    }
    @OnClick(R.id.layout08)
    public void layout08_click(View view)
    {
        if (mCache.getAsString("ifLogin").equals("false")) {

            Intent myintent = new Intent(getActivity(), isLogin.class);
            startActivity(myintent);
        } else {
            Intent myintent = new Intent(getActivity(), MyTalk.class);
            startActivity(myintent);

        }
    }
    @OnClick(R.id.layout09)
    public void layout09_click(View view)
    {
        if (mCache.getAsString("ifLogin").equals("false")) {

            Intent myintent = new Intent(getActivity(), isLogin.class);
            startActivity(myintent);
        } else {
            Intent myintent = new Intent(getActivity(), MyCollection.class);
            startActivity(myintent);

        }
    }

    @OnClick(R.id.layout10)
    public void layout10_click(View view)
    {
        if (mCache.getAsString("ifLogin").equals("false")) {

            Intent myintent = new Intent(getActivity(), isLogin.class);
            startActivity(myintent);
        } else {
            Intent myintent = new Intent(getActivity(), UserInfoActivity.class);
            startActivity(myintent);

        }
    }
    @OnClick(R.id.layout11)
    public void layout11_click(View view)
    {
        if (mCache.getAsString("ifLogin").equals("false")) {

            Intent myintent = new Intent(getActivity(), isLogin.class);
            startActivity(myintent);
        } else {
            Intent myintent = new Intent(getActivity(), MyMoney.class);
            startActivity(myintent);

        }
    }
    @OnClick(R.id.layout12)
    public void layout12_click(View view)
    {
        Intent myintent = new Intent(getActivity(), SettingActivity.class);
        startActivity(myintent);
    }



    @OnClick(R.id.Rel_set)
    public void Rel_set_click(View view)
    {
        Intent myintent = new Intent(getActivity(), SettingActivity.class);
        startActivity(myintent);
    }

    @Subscribe
    public void onMessageEvent(UserFgEvent event) {//订阅事件
        if (event.getType().equals("logout")) {




            lblURID.setText("请登录");
            lblURID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myintent = new Intent(getActivity(), isLogin.class);
                    startActivity(myintent);
                }
            });
            ImageLoader imageLoader = ImageLoader.getInstance();
            String imageUri = "drawable://" + R.mipmap.icon_t; //  drawable文件
            imageLoader.displayImage(imageUri, imgURL);
//            imgURL.setVisibility(View.INVISIBLE);
            txt102.setText("0");
            txt101.setText("0");
            txt103.setText("0");



        }else {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }




}
