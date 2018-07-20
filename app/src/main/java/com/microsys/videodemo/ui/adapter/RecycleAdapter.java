package com.microsys.videodemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.microsys.videodemo.R;
import com.microsys.videodemo.common.Constants;
import com.microsys.videodemo.ui.bean.video_info_beans.VideoInfoBean;
import com.microsys.videodemo.utils.StringUtil;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> implements View.OnClickListener {

    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOT = 2;
    private List<VideoInfoBean> mlist;
    private Context context;
    private View headView;
    private View footView;
    private boolean isAddFoot = false;
    private boolean isAddHead = false;
    private int headViewSize = 0;
    private int footViewSize = 0;
    private VideoInfoBean mVideoInfoBean;
    private String bgUrl="";

    private OnItemClickListener mOnItemClickListener = null;


    public RecycleAdapter(List<VideoInfoBean> list, Context context) {

        this.mlist = list;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycle_normal_item, parent, false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (null!=mlist) {
            mVideoInfoBean = mlist.get(position);
            if (StringUtil.isEmpty(mVideoInfoBean.getVideoName())) {
                holder.textView.setText("未知");
            }else {
                holder.textView.setText(mVideoInfoBean.getVideoName());
            }

            bgUrl= Constants.VIDEO_BACKGROUND_URL+mVideoInfoBean.getVideoID()+".png";
//            GlideUtils.loadImageView(context,bgUrl,holder.img_item);
                    Glide.with(context).load(bgUrl).error(R.mipmap.img_default_video_pic).into(holder.img_item);

        }
        holder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {

        return mlist.size();
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_item;
        CardView cardView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            textView = itemView.findViewById(R.id.rcv_tv_item);
            img_item = itemView.findViewById(R.id.rcv_iv_item);
        }
    }
}

