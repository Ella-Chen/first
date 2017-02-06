package example.com.qiyicom;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import base.BaseActivity;

public class UserInfoActivity extends BaseActivity {
    @ViewInject(R.id.Rel_back)
    private RelativeLayout Rel_back;

    @ViewInject(R.id.img_logo)
    private CircleImageView img_logo;

    @ViewInject(R.id.edt_password)
    private EditText edt_password;

    @ViewInject(R.id.img_next)
    private EditText img_next;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
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

//    @OnClick(R.id.img_logo)
//    public void img_logo_click(View view)
//    {
//        Intent intent = new Intent();
//        intent.setClass(mContext, ChangeUserNameActivity.class);
//        startActivity(intent);
//    }


}
