package example.com.qiyicom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tools.MyFragmentPagerAdapter;
/**
 * 私募排名
 */
public class Ranking extends FragmentActivity {
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentList;
    private TextView view1, view2, view3, view4, view5;
    private int currIndex;// 当前页卡编号
    private Context mContext;
    public static String strTitle = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        ActivityWidth = wm.getDefaultDisplay().getWidth();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
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

        TextView txt_title = (TextView)findViewById(R.id.txt_title);

        final Intent intent = getIntent(); //用于激活它的意图对象
        strTitle = getIntent().getStringExtra("Title");
        if (strTitle.equals("私募基金")){
            txt_title.setText("私募基金");
        }else if (strTitle.equals("基金排名")){
            txt_title.setText("基金排名");
        }else if (strTitle.equals("基金估值")){
            txt_title.setText("基金估值");
        }




        mContext = Ranking.this;
        InitButtonView();
        InitViewPager();
        LinearLayout layoutback = (LinearLayout)findViewById(R.id.layoutback);
        layoutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public void InitButtonView() {
        view1 = (TextView) findViewById(R.id.tv_guid1);
        view2 = (TextView) findViewById(R.id.tv_guid2);
        view3 = (TextView) findViewById(R.id.tv_guid3);
        view4 = (TextView) findViewById(R.id.tv_guid4);
        view5 = (TextView) findViewById(R.id.tv_guid5);
        view1.setTextColor(Color.RED);
        view2.setTextColor(Color.GRAY);
        view3.setTextColor(Color.GRAY);
        view4.setTextColor(Color.GRAY);
        view5.setTextColor(Color.GRAY);

        view1.setOnClickListener(new txListener(0));
        view2.setOnClickListener(new txListener(1));
        view3.setOnClickListener(new txListener(2));
        view4.setOnClickListener(new txListener(3));
        view5.setOnClickListener(new txListener(4));
    }
    public class txListener implements View.OnClickListener {
        private int index = 0;
        public txListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }
    // 当前屏幕宽度
    private int ActivityWidth;


    /*
	 * 初始化ViewPager
	 */
    public void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        fragmentList = new ArrayList<Fragment>();
        Fragment oneFragm = new RankingoneFragment();
        Fragment twoFragm = new RankingoneFragment();
        Fragment thrFragm = new RankingoneFragment();
        Fragment fourFragm = new RankingoneFragment();
        Fragment fiveFragm = new RankingoneFragment();
        fragmentList.add(oneFragm);
        fragmentList.add(twoFragm);
        fragmentList.add(thrFragm);
        fragmentList.add(fourFragm);
        fragmentList.add(fiveFragm);

        // 给ViewPager设置适配器
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mPager.setCurrentItem(0);// 设置当前显示标签页为第一页
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                currIndex = position;
                if(position == 0){
                    view1.setTextColor(Color.RED);
                    view2.setTextColor(Color.GRAY);
                    view3.setTextColor(Color.GRAY);
                    view4.setTextColor(Color.GRAY);
                    view5.setTextColor(Color.GRAY);
                }else if(position == 1)
                {
                    view1.setTextColor(Color.GRAY);
                    view2.setTextColor(Color.RED);
                    view3.setTextColor(Color.GRAY);
                    view4.setTextColor(Color.GRAY);
                    view5.setTextColor(Color.GRAY);
                }else if(position == 2)
                {
                    view1.setTextColor(Color.GRAY);
                    view2.setTextColor(Color.GRAY);
                    view3.setTextColor(Color.RED);
                    view4.setTextColor(Color.GRAY);
                    view5.setTextColor(Color.GRAY);
                }else if(position == 3)
                {
                    view1.setTextColor(Color.GRAY);
                    view2.setTextColor(Color.GRAY);
                    view3.setTextColor(Color.GRAY);
                    view4.setTextColor(Color.RED);
                    view5.setTextColor(Color.GRAY);
                }else if(position == 4)
                {
                    view1.setTextColor(Color.GRAY);
                    view2.setTextColor(Color.GRAY);
                    view3.setTextColor(Color.GRAY);
                    view4.setTextColor(Color.GRAY);
                    view5.setTextColor(Color.RED);
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

}
