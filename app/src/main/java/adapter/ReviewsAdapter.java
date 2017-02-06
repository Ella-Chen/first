package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import example.com.qiyicom.R;
import example.com.qiyicom.isLogin;
import tools.ACache;

/**
 * Created by nico on 16/12/8.
 */

public class ReviewsAdapter extends BaseAdapter {

    private List<MyInfo> goodsDatas;
    private Context context;
    private ACache mCache;
    private LayoutInflater mInflater;





    public ReviewsAdapter(Context pContext, ArrayList<MyInfo> pList
                          ) {
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listmy_item, null);
            holder = new ViewHolder();
            ViewUtils.inject(holder, convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MyInfo info = goodsDatas.get(position);
        holder.txt_name.setText(info.getCTOW());
        holder.txt_time.setText(info.getCGTM());
        holder.txt_title.setText(info.getCTNA());
        holder.txt_text.setText(info.getCTDS());
        holder.txt_id.setText(info.getCTID());
        holder.zan_num.setText(info.getdzsl() + "");
        holder.txt_plsl.setText(info.getPlsl()+"");
        mCache = ACache.get(context);


//        holder.lay_huifu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,ReviewsXQ.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });

//        // 取出bean中当记录状态是否为true，是的话则给img设置focus点赞图片
//        if (info.getdzsl()==0) {
//            holder.zan_img.setImageResource(R.mipmap.zan);
//        } else {
//            holder.zan_img.setImageResource(R.mipmap.dianzan);
//        }



        holder.zan_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCache.getAsString("key")==null||mCache.getAsString("key")=="")
                {
                    Intent intent = new Intent(context,isLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return;
                }


                // 获取上次是否已经被点击
                boolean flag = info.isZanFocus();
                // 判断当前flag是点赞还是取消赞,是的话就给bean值减1，否则就加1
                if (flag) {

                    HttpUtils http = new HttpUtils();
                    RequestParams params = new RequestParams();
                    String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=DZTZ&URSN=" + mCache.getAsString("key") + "&GSID=" + info.getCTID()+ "&GSTT=TEST&PLIT=2";
                    Log.d("newURl8888", newURl);
                    http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            try {
                                Log.d("result", responseInfo.result);
                                JSONObject jsonObject = new JSONObject(responseInfo.result);
                                if (jsonObject.getString("status").equals("0")) {
                                    Toast.makeText(context, "取消点赞!", Toast.LENGTH_SHORT).show();
                                    info.setdzsl(info.getdzsl() - 1);
                                    holder.zan_num.setTextColor(Color.parseColor("#000000"));
                                    holder.zan_num.setText(info.getdzsl() + "");
                                    holder.zan_img.setImageResource(R.mipmap.zan);




                                } else {
//                                Toast.makeText(getApplicationContext(), json.getString("msg"), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(context, "网络不可用!", Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    HttpUtils http = new HttpUtils();
                    RequestParams params = new RequestParams();
                    String newURl = "http://121.43.234.2/postjson/JsonFromDB.aspx?TP=DZTZ&URSN=" + mCache.getAsString("key") + "&GSID=" + info.getCTID() + "&GSTT=TEST&PLIT=1";
                    Log.d("newURl9999", newURl);
                    http.send(HttpRequest.HttpMethod.GET, newURl, params, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            try {
                                Log.d("result9999", responseInfo.result);
                                JSONObject jsonObject = new JSONObject(responseInfo.result);
                                if (jsonObject.getString("status").equals("0")) {
                                    Toast.makeText(context, "点赞成功!", Toast.LENGTH_SHORT).show();
                                    info.setdzsl(info.getdzsl() + 1);
                                    holder.zan_num.setTextColor(Color.parseColor("#D95555"));
                                    holder.zan_num.setText(info.getdzsl() + "");
                                    holder.zan_img.setImageResource(R.mipmap.dianzan);



                                } else {
//                                Toast.makeText(getApplicationContext(), json.getString("msg"), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(context, "网络不可用!", Toast.LENGTH_SHORT).show();
                        }
                    });



                }
                // 反向存储记录，实现取消点赞功能
                info.setZanFocus(!flag);
                AnimationTools.scale(holder.zan_img);

            }
        });



        return convertView;
    }


    public class ViewHolder {
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

        @ViewInject(R.id.lay_huifu)
        public LinearLayout lay_huifu;

        @ViewInject(R.id.img_zan)
        public ImageView zan_img;

        @ViewInject(R.id.txt_zan)
        public TextView zan_num;

        @ViewInject(R.id.txt_plsl)
        public TextView txt_plsl;

    }











}
