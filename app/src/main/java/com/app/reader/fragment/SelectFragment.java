package com.app.reader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.reader.MainActivity;
import com.app.reader.R;
import com.app.reader.adapter.NovelItemAdapterUpdate;
import com.app.reader.listener.DataListener;
import com.app.reader.utils.ReaderConstants;
import com.avos.avoscloud.AVObject;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by llb on 2016/3/4.
 */
public class SelectFragment extends Fragment implements DataListener{
    @Bind(R.id.xrecyclerview)
    XRecyclerView mRecyclerView;
    private NovelItemAdapterUpdate mAdapter;
    private MainActivity parentActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_select,container,false);
        ButterKnife.bind(this, view);
        parentActivity=(MainActivity)getActivity();
        initRecycleView();
        parentActivity.controller.getInternetData().getNovelList(this);
        return view;
    }

    private void initRecycleView(){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mAdapter=new NovelItemAdapterUpdate();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                parentActivity.controller.getInternetData().getNovelList(SelectFragment.this);
            }

            @Override
            public void onLoadMore() {
            }
        });
    }

    @Override
    public void DataSuccess(List<AVObject> list, String interfaceType) {
        if(interfaceType.equals(ReaderConstants.NOVEL_LIST)) {
            mRecyclerView.refreshComplete();
            mAdapter.RefreshDataset(list);
        }
    }

    @Override
    public void DataFailture() {

    }

    @Override
    public void NetError() {

    }
}
