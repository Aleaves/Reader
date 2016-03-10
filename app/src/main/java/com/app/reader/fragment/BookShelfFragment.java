package com.app.reader.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.reader.R;
import com.app.reader.ReadActivity;
import com.app.reader.adapter.BookShelfAdapter;
import com.app.reader.entity.Novel;
import com.app.reader.listener.MyItemClickListener;
import com.app.reader.listener.MyItemLongClickListener;
import com.app.reader.utils.ReaderConstants;
import org.litepal.crud.DataSupport;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by llb on 2016/3/4.
 */

public class BookShelfFragment extends Fragment implements MyItemClickListener,MyItemLongClickListener{

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    BookShelfAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_book_shelf,container,false);
        ButterKnife.bind(this, view);
        GridLayoutManager mGridLayoutManager=new GridLayoutManager(getActivity(),3);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter=new BookShelfAdapter(DataSupport.order("id desc").find(Novel.class),this,this);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    /**
     * 更新书架小说
     */
    public void updateNovel(){
        mAdapter.setData(DataSupport.order("id desc").find(Novel.class));
    }

    /**
     * 点击监听
     * @param view
     * @param postion
     */
    @Override
    public void onItemClick(View view, int postion) {
        Intent intent=new Intent(getActivity(), ReadActivity.class);
        intent.putExtra(ReaderConstants.FILE_PATH,mAdapter.getLists().get(postion).getNovelPath());
        startActivity(intent);
    }

    /**
     * 长按监听
     * @param view
     * @param postion
     */
    @Override
    public void onItemLongClick(View view, int postion) {

        DataSupport.deleteAll(Novel.class,"novelname = ?",mAdapter.getLists().get(postion).getNovelName());

        mAdapter.daleteData(postion);

    }
}
