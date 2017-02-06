package example.com.qiyicom;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;

import java.util.ArrayList;

import tools.MyFragmentPagerAdapter;

public class Manager extends FragmentActivity {
    Resources resources;
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private ImageView ivBottomLine;
    private TextView tvTab01, tvTab02;
    private int avg = 0;
    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one, position_two;
    public final static int num = 2;
    Fragment home1;
    Fragment home2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
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
        resources = getResources();
        InitWidth();
        InitTextView();
        InitViewPager();
        TranslateAnimation animation = new TranslateAnimation(offset, 0, 0, 0);

        animation.setFillAfter(true);
        animation.setDuration(300);
        ivBottomLine.startAnimation(animation);
        LinearLayout layoutback = (LinearLayout)findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    private void InitTextView(){
        tvTab01 = (TextView)findViewById(R.id.tv_tab_1);
        tvTab02 = (TextView)findViewById(R.id.tv_tab_2);
        tvTab01.setOnClickListener(new MyOnClickListener(0));
        tvTab02.setOnClickListener(new MyOnClickListener(1));
    }
    private void InitViewPager(){
        mPager = (ViewPager)findViewById(R.id.vPager);
        fragmentsList = new ArrayList<Fragment>();
        home1 = new managersFragment();
        home2 = new managergFragment();
        fragmentsList.add(home1);
        fragmentsList.add(home2);
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        mPager.addOnPageChangeListener(new MyOnPageChangeListener());
        mPager.setCurrentItem(0);
    }




    private void InitWidth() {
        ivBottomLine = (ImageView)findViewById(R.id.iv_bottom_line);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        Manager.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / num - bottomLineWidth) / 2);  //
        avg = (int) (screenW / num);  //每一个的宽
        position_one = avg + offset;
        position_two = avg + offset;
    }
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }



    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, offset, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        //这里是1往2滑动
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);


        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    }

}
