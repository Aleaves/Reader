package com.app.reader;
import android.animation.ObjectAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.app.reader.View.DragLayout;
import com.app.reader.View.MyLinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
public class MainActivity extends BaseActivity {
    @Bind(R.id.id_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.dl)
    DragLayout mDragLayout;
    @Bind(R.id.mll)
    MyLinearLayout mLinearLayout;
    @Override
    protected void init() {
        Log.i("======","11");
        //初始化注解
        ButterKnife.bind(this);
        //左边的
        mToolbar.setNavigationIcon(R.mipmap.ab_title_menu);
        //加载toolbar
        setSupportActionBar(mToolbar);
//        toolbar.setNavigationContentDescription("aaaaa");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDragLayout.open();
            }
        });
        //点击事件
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String msg = "";
                switch (item.getItemId()) {
                    case R.id.main_menu_attention:
                        msg += "attention";
                        break;
                    case R.id.main_menu_add:
                        msg += "menu_add";
                        break;
                    case R.id.main_menu_search:
                        msg += "search";
                        break;
                }

                if (!msg.equals("")) {
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        mDragLayout.setDragStatusListener(mListener);
        mLinearLayout.setDraglayout(mDragLayout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    DragLayout.OnDragStatusChangeListener mListener=new DragLayout.OnDragStatusChangeListener() {

        @Override
        public void onOpen() {
            mToolbar.setNavigationIcon(R.mipmap.bookcase_book_pause_nor);

        }

        @Override
        public void onDraging(float percent) {
            ObjectAnimator//
                    .ofFloat(mToolbar.getChildAt(0), "rotationY", 0.0F, 180.0F)//
                    .setDuration(300)//
                    .start();
        }

        @Override
        public void onClose() {
            mToolbar.setNavigationIcon(R.mipmap.ab_title_menu);

        }
    };
}
