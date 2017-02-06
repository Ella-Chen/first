package example.com.qiyicom;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import base.BaseActivity;
import model.event.UserFgEvent;

public class SettingActivity extends BaseActivity {
    @ViewInject(R.id.Rel_back)
    private RelativeLayout Rel_back;
    @ViewInject(R.id.Rel_clear)
    private RelativeLayout Rel_clear;
    @ViewInject(R.id.Rel_suggestion)
    private RelativeLayout Rel_suggestion;
    @ViewInject(R.id.Rel_about)
    private RelativeLayout Rel_about;

    @ViewInject(R.id.tv_logout)
    private TextView tv_logout;

    @ViewInject(R.id.Rel_version)
    private RelativeLayout Rel_version;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        ViewUtils.inject(this);
    }
    @OnClick(R.id.Rel_back)
    public void Rel_back_click(View view)
    {
       finish();
    }

    @OnClick(R.id.Rel_clear)
    public void Rel_clear_click(View view)
    {
//        ImageLoader.getInstance().clearDiskCache();//清除磁盘缓存
        ImageLoader.getInstance().clearMemoryCache();//清除内存缓存

        Toast.makeText(getApplicationContext(),"清理成功",Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.Rel_suggestion)
    public void Rel_suggestion_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(), AppSuggestionActivity.class);
        startActivity(myintent);
    }
    @OnClick(R.id.Rel_about)
    public void Rel_about_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(), AboutUs.class);
        startActivity(myintent);
    }

    @OnClick(R.id.Rel_version)
    public void Rel_version_click(View view)
    {
        Intent myintent = new Intent(getApplicationContext(), vErsion.class);
        startActivity(myintent);
    }
    @OnClick(R.id.tv_logout)
    public void tv_logout_click(View view)
    {
//        mPushAgent.disable();
        putACache("key", "");
        EventBus.getDefault().post(new UserFgEvent("logout",""));
        EventBus.getDefault().post(new MessageEvent("logout",""));

        finish();
    }



}
