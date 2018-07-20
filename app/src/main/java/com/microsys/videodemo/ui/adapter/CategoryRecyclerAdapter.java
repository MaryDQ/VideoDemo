package com.microsys.videodemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsys.videodemo.R;
import com.microsys.videodemo.ui.bean.TestBean;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> implements View.OnClickListener{

    private List<TestBean> mlist;

    private Context context;
    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOT = 2;
    private View headView;
    private View footView;
    private boolean isAddFoot = false;
    private boolean isAddHead = false;
    private int headViewSize = 0;
    private int footViewSize = 0;

    private RecycleAdapter.OnItemClickListener mOnItemClickListener = null;



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public CategoryRecyclerAdapter(List<TestBean> list, Context context){

        this.mlist = list;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_item;
        CardView cardView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            textView =  itemView.findViewById(R.id.rcv_tv_item);
            img_item =  itemView.findViewById(R.id.rcv_iv_item);
        }
    }


    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_category_item,parent,false);
        view.setOnClickListener(this);

        return new RecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleAdapter.ViewHolder holder, int position) {


        if (null!=mlist) {
            holder.img_item.setImageResource(mlist.get(position).getSrcId());
            holder.textView.setText(mlist.get(position).getContent());
            holder.itemView.setTag(position);
        }


    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(RecycleAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }



    @Override
    public int getItemCount() {

        return mlist.size();
    }
}

