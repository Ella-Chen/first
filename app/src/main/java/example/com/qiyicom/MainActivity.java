package example.com.qiyicom;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    @ViewInject(R.id.main_bottom_tabs)
    private RadioGroup group;
    @ViewInject(R.id.main_home)
    private RadioButton main_home;
    @ViewInject(R.id.main_my)
    private RadioButton main_my;
    @ViewInject(R.id.main_tuan)
    private RadioButton main_tuan;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
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




        // 初始化FragmentManager
        fragmentManager = getSupportFragmentManager();

    Intent intent = getIntent(); //用于激活它的意图对象

    /*接受方法1*/
    int id = intent.getIntExtra("ID",-1);
    if(id > 0) {
        if(id == 1)
        {
            main_my.setChecked(true);
            group.setOnCheckedChangeListener(this);
            // 切换不同的fragment
            changeFragment(new FragmentMy(), false);
        }
        else if(id == 2)//首页
        {
            main_home.setChecked(true);
            group.setOnCheckedChangeListener(this);
            // 切换不同的fragment
            changeFragment(new FragmentHome(), false);
        }
        else if(id == 3)//首页
        {
            main_tuan.setChecked(true);
            group.setOnCheckedChangeListener(this);
            // 切换不同的fragment
            changeFragment(new FragmentHome(), false);
        }

    }
    else
    {
        // 设置默认选中
        main_home.setChecked(true);
        group.setOnCheckedChangeListener(this);
        // 切换不同的fragment
        changeFragment(new FragmentHome(), false);

    }
}


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_home:
                changeFragment(new FragmentHome(), true);
                break;
            case R.id.main_my:
                changeFragment(new FragmentMy(), true);
                break;
            case R.id.main_search:
                changeFragment(new FragmentSearch(), true);
                break;
            case R.id.main_tuan:
                changeFragment(new FragmentTuan(), true);
                break;
            default:
                break;
        }

    }
    // 切换不同的fragment
    public void changeFragment(Fragment fragment, boolean isInit)
    {
        // 开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        if (!isInit)
        {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private long mExitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
