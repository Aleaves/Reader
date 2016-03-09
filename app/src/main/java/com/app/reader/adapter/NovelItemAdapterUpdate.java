package com.app.reader.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.reader.R;
import com.app.reader.entity.NovelItemInfoUpdate;
import com.app.reader.listener.MyDeleteClickListener;
import com.app.reader.listener.MyItemClickListener;
import com.app.reader.listener.MyItemLongClickListener;
import com.avos.avoscloud.AVObject;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/9.
 */

public class NovelItemAdapterUpdate extends RecyclerView.Adapter<NovelItemAdapterUpdate.ViewHolder>{

    private MyItemClickListener mItemClickListener;
    private MyDeleteClickListener mMyDeleteClickListener;
    private MyItemLongClickListener mItemLongClickListener;
    private List<AVObject> mDataset;
    private Context mContext;
    public NovelItemAdapterUpdate() {
        mDataset = new ArrayList<>();
    }

    public NovelItemAdapterUpdate(List<AVObject> dataset) {
        super();
        mDataset = null;
        mDataset = dataset;
    }

    public void RefreshDataset(List<AVObject> dataset) {
        mDataset = dataset;
        notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View view=View.inflate(parent.getContext(), R.layout.item_view_novel,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.novel_author.setText(mDataset.get(position).getString("author"));
        holder.novel_title.setText(mDataset.get(position).getString("bookName"));
        holder.novel_intro.setText(mDataset.get(position).getString("desc"));
        holder.novel_status.setText(mDataset.get(position).getString("status"));
        holder.novel_update.setText(mDataset.get(position).getString("updateDate"));
        Glide.with(mContext).load(mDataset.get(position).getString("imageUrl"))
                .placeholder(R.drawable.ic_empty_image)
                .into(holder.novel_cover);
    }

    public List<AVObject> getDataset() {
        return mDataset;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public void setOnDeleteClickListener(MyDeleteClickListener listener) {
        this.mMyDeleteClickListener = listener;
    }

    public void setOnItemLongClickListener(MyItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView novel_cover;
        private TextView novel_title;
        private TextView novel_author;
        private TextView novel_status;
        private TextView novel_update;
        private TextView novel_intro;
        public ViewHolder(View itemView) {
            super(itemView);
            novel_cover= (ImageView) itemView.findViewById(R.id.novel_cover);
            novel_title=(TextView)itemView.findViewById(R.id.novel_title);
            novel_author=(TextView)itemView.findViewById(R.id.novel_author);
            novel_status=(TextView)itemView.findViewById(R.id.novel_status);
            novel_update=(TextView)itemView.findViewById(R.id.novel_update);
            novel_intro=(TextView)itemView.findViewById(R.id.novel_intro);
        }
    }
}
