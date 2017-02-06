package base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lidroid.xutils.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.x;

import model.base.BaseEvent;
import tools.ACache;
import tools.AppConfig;
import tools.AppManager;

/**
 * Created by nico on 16/11/16.
 */

public class BaseActivity extends Activity {
    protected ACache mCache;
    protected BaseActivity mContext;
    protected SharedPreferences preference;
    protected KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = getSharedPreferences(AppConfig.PREFERENCE_NAME, Context.MODE_PRIVATE);
        AppManager.setPreferences(preference);
        mContext = this;
        mCache = ACache.get(this);
        x.view().inject(this);
        EventBus.getDefault().register(this);


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMessageEvent(BaseEvent event) {


    }








    public boolean isEmpty(String text) {
        if (text != null && !text.trim().equals("") && !TextUtils.isEmpty(text)) {
            return false;
        } else {
            return true;
        }
    }


    public String getIntentExtra(String key) {
        String value="";
        if(!isEmpty(getIntent().getStringExtra(key)))
        {
            value=getIntent().getStringExtra(key);
        }
        return value;
    }
    public void logPrint(String key,String value) {
        Log.d(key, value);
    }

    public String getACache(String key) {
        String value="";
        if(!isEmpty(mCache.getAsString(key)))
        {
            value=mCache.getAsString(key);
        }
        return value;
    }

    public String getACache(String key,String normal) {
        String value="";
        if(!isEmpty(mCache.getAsString(key)))
        {
            value=mCache.getAsString(key);
        }else{
            value=normal;
        }
        return value;
    }

    public void putACache(String key,String value) {
        if(!isEmpty(key))
        {
            mCache.put(key,value);
        }
    }



}


