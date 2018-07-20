package com.microsys.videodemo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;

public class LiveVideoDetailAdapter extends RecyclerView.Adapter<LiveVideoDetailAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<VideoInfoBean> mlist;
    private Context context;
    private View headView;
    private View footView;
    private boolean isAddFoot = false;
    private boolean isAddHead = false;
    private int headViewSize = 0;
    private int footViewSize = 0;
    private VideoInfoBean mVideoInfoBean;

    private LiveVideoDetailAdapter.OnItemClickListener mOnItemClickListener = null;

    public LiveVideoDetailAdapter(ArrayList<VideoInfoBean> list, Context context) {
        this.mlist = list;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_live_video_details, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveVideoDetailAdapter.ViewHolder holder, int position) {
        if (null != mlist) {
            mVideoInfoBean = mlist.get(position);
            String bgUrl=Constants.VIDEO_BACKGROUND_URL+mVideoInfoBean.getVideoID()+".png";
//            GlideUtils.loadImageView(context, bgUrl, holder.ivLiveVidePre);
            Glide.with(context).load(bgUrl).error(R.mipmap.img_default_video_pic).into(holder.ivLiveVidePre);
            if (StringUtil.isEmpty(mVideoInfoBean.getVideoName())) {
                holder.tvLiveVideoTitle.setText("未知");
            }else {
                holder.tvLiveVideoTitle.setText(mVideoInfoBean.getVideoName());
            }

//            holder.tvWatchingNums.setText(mVideoInfoBean.getWatchingNums());
//            holder.tvGoodNums.setText(mVideoInfoBean.getGoodNums());
//            holder.tvCommentsNums.setText(mVideoInfoBean.getCommentsNums());
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void setOnItemClickListener(LiveVideoDetailAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLiveVidePre;
        TextView tvLiveVideoTitle;
        TextView tvWatchingNums;
        TextView tvGoodNums;
        TextView tvCommentsNums;

        public ViewHolder(View itemView) {
            super(itemView);
            ivLiveVidePre = itemView.findViewById(R.id.iv_live_video_preview);
            tvLiveVideoTitle = itemView.findViewById(R.id.tv_live_video_title);
            tvWatchingNums = itemView.findViewById(R.id.tv_watching_nums);
            tvGoodNums = itemView.findViewById(R.id.tv_good_nums);
            tvCommentsNums = itemView.findViewById(R.id.tv_comments);
        }
    }
}
