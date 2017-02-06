package example.com.qiyicom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import tools.ACache;

/**
 * Created by Administrator on 2016/4/20.
 */
public class FragmentHome extends Fragment {
    @ViewInject(R.id.layouttz)//我要投资
    private ImageView layouttz;

    @ViewInject(R.id.layoutdp)//我要点评
    private ImageView layoutdp;

    @ViewInject(R.id.layouthb)//红包大战
    private ImageView layouthb;

    @ViewInject(R.id.layoutgs)//我是牛人
    private ImageView layoutgs;

    @ViewInject(R.id.layouttt)//今日要闻
    private ImageView layouttt;

    @ViewInject(R.id.layouttj)//炒股大赛
    private ImageView layouttj;




    private ACache mCache;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.home, null);
        ViewUtils.inject(this, view);
        mCache = ACache.get(this.getActivity());

        if (TextUtils.isEmpty(mCache.getAsString("ifLogin"))) {
            mCache.put("ifLogin", "false");
        }


        final EditText edt_sousuo = (EditText) view.findViewById(R.id.edt_sousuo);
        edt_sousuo.setSingleLine(true);
        edt_sousuo.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        edt_sousuo.setInputType(InputType.TYPE_CLASS_NUMBER);
        edt_sousuo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
                    Intent intent = new Intent(getActivity(), SousuoGp.class);
                    intent.putExtra("SSCS",edt_sousuo.getText().toString());
                    Log.d("SSCS",edt_sousuo.getText().toString());
                    startActivity(intent);

                }

                return false;
            }
        });



        return view;
    }
  




    @OnClick(R.id.layouttz)//我要投资
    public void layouttz_click(View view) {
        Intent myintent = new Intent(getActivity(), Comany.class);
        startActivity(myintent);
    }


    @OnClick(R.id.layoutdp)//我要点评
    public void layoutdp_click(View view) {
        Intent myintent = new Intent(getActivity(), Reviews.class);
        startActivity(myintent);
    }

    @OnClick(R.id.layoutgs)//我是牛人
    public void layoutgs_click(View view) {
        Intent myintent = new Intent(getActivity(), Master.class);
        startActivity(myintent);
    }

    @OnClick(R.id.layouthb)//红包大战
    public void layouthb_click(View view) {
        Intent myintent = new Intent(getActivity(), Redmoney.class);
        startActivity(myintent);
    }

    @OnClick(R.id.layouttt)//今日要闻
    public void layouttt_click(View view) {
        Intent myintent = new Intent(getActivity(), TodayNews.class);
        myintent.putExtra("Title","今日要闻");
        startActivity(myintent);
    }

    @OnClick(R.id.layouttj)//炒股大赛
    public void layouttj_click(View view) {

        if (mCache.getAsString("ifLogin").equals("false")) {

            Intent myintent = new Intent(getActivity(), isLogin.class);
            startActivity(myintent);
        } else {
            Intent myintent = new Intent(getActivity(), Recommend.class);
            startActivity(myintent);

        }



    }







}