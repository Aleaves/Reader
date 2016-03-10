package com.app.reader.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.reader.R;
import com.app.reader.entity.Novel;
import com.app.reader.listener.MyItemClickListener;
import com.app.reader.listener.MyItemLongClickListener;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/10.
 */

public class BookShelfAdapter extends RecyclerView.Adapter<BookShelfAdapter.ViewHolder>{

    private MyItemClickListener mItemClickListener;
    private MyItemLongClickListener mItemLongClickListener;
    public List<Novel> lists=new ArrayList<Novel>();
    private Context mContext;

    public BookShelfAdapter(List<Novel> lists,MyItemClickListener mItemClickListener,MyItemLongClickListener mItemLongClickListener){
        this.lists=lists;
        this.mItemClickListener=mItemClickListener;
        this.mItemLongClickListener=mItemLongClickListener;
    }


    public void setData(List<Novel> lists){
        this.lists=lists;
        notifyDataSetChanged();
    }

    public List<Novel> getLists(){
        return lists;
    }

    /**
     * TODO<删除数据，指定其位置>
     */
    public void daleteData(int position) {
        lists.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View view=View.inflate(parent.getContext(), R.layout.item_book_shelf,null);
        return new ViewHolder(view,mItemClickListener,mItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(lists.get(position).getNovelImage()!=null&&!"".equals(lists.get(position).getNovelImage())){
            Glide.with(mContext).load(lists.get(position).getNovelImage())
                    .placeholder(R.drawable.ic_empty_image)
                    .into(holder.novel_cover);
        }else{
            holder.novel_cover.setImageResource(R.drawable.ic_empty_image);
        }
        holder.novel_name.setText(lists.get(position).getNovelName());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private MyItemClickListener mItemClickListener;
        private MyItemLongClickListener mItemLongClickListener;
        private ImageView novel_cover;
        private TextView novel_name;
        public ViewHolder(View itemView,MyItemClickListener mItemClickListener,MyItemLongClickListener mItemLongClickListener) {
            super(itemView);
            novel_cover=(ImageView)itemView.findViewById(R.id.novel_cover);
            novel_name=(TextView)itemView.findViewById(R.id.novel_name);
            itemView.findViewById(R.id.ll_novel).setOnClickListener(this);
            itemView.findViewById(R.id.ll_novel).setOnLongClickListener(this);
            this.mItemClickListener=mItemClickListener;
            this.mItemLongClickListener=mItemLongClickListener;
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener!=null){
                mItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick (View v){
            if(mItemLongClickListener!=null){
                mItemLongClickListener.onItemLongClick(v,getAdapterPosition());
            }
            return false;
        }
    }
}
