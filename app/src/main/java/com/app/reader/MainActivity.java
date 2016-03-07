package com.app.reader;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.app.reader.View.DragLayout;
import com.app.reader.View.MyLinearLayout;
import com.app.reader.fragment.BookShelfFragment;
import com.app.reader.fragment.FindFragment;
import com.app.reader.fragment.SelectFragment;
import com.app.reader.fragment.StackFragment;
import butterknife.Bind;
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    @Bind(R.id.id_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.dl)
    DragLayout mDragLayout;
    @Bind(R.id.mll)
    MyLinearLayout mLinearLayout;
    @Bind(R.id.rg_bottom)
    RadioGroup mRgBottom;
    @Bind(R.id.rb_book_shelf)
    RadioButton mBookShelf;
    BookShelfFragment mBookShelfFragment;
    FindFragment mFindFragment;
    SelectFragment mSelectFragment;
    StackFragment mStackFragment;
    Fragment selectFragment;
    FragmentManager mFragmentManager;
    FragmentTransaction mTransaction;
    @Override
    protected void init() {
        //左边的
        mToolbar.setNavigationIcon(R.mipmap.ab_title_menu);
        mToolbar.setTitle("书架");
        mToolbar.setTitleTextColor(Color.WHITE);
        //加载toolbar
        setSupportActionBar(mToolbar);
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
                switch (item.getItemId()) {
                    /*case R.id.main_menu_attention:
                        msg += "attention";
                        break;*/
                    case R.id.main_menu_add:
                        Intent intent=new Intent(MainActivity.this,FileChooser.class);
                        startActivityForResult(intent,1);
                        break;
                    case R.id.main_menu_search:
                        break;
                }
                return true;
            }
        });
        mLinearLayout.setDraglayout(mDragLayout);
        mFragmentManager=getSupportFragmentManager();
        mRgBottom.setOnCheckedChangeListener(this);
        mBookShelf.setChecked(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(null==data)
            return;
        String filename = data.getStringExtra("filename");
        Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();
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

    //底部导航监听
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_book_shelf:
                if(mBookShelfFragment==null){
                    mBookShelfFragment=new BookShelfFragment();
                }
                mToolbar.setTitle("书架");
                select(mBookShelfFragment);
                break;
            case R.id.rb_select:
                if(mSelectFragment==null){
                    mSelectFragment=new SelectFragment();
                }
                mToolbar.setTitle("精选");
                select(mSelectFragment);
                break;
            case R.id.rb_stack:
                if(mStackFragment==null){
                    mStackFragment=new StackFragment();
                }
                mToolbar.setTitle("书库");
                select(mStackFragment);
                break;
            case R.id.rb_find:
                if(mFindFragment==null){
                    mFindFragment=new FindFragment();
                }
                mToolbar.setTitle("发现");
                select(mFindFragment);
                break;
        }
    }

    /**
     * 提取公共方法
     * @param fragment
     */

    private void select(Fragment fragment){
        mTransaction=mFragmentManager.beginTransaction();
        if(selectFragment==null){
            selectFragment=new Fragment();
        }
        if(selectFragment!=fragment){
            if(fragment.isAdded()){
                mTransaction.hide(selectFragment).show(fragment);
            }else{
                mTransaction.hide(selectFragment).add(R.id.rl_content,fragment);
            }
            selectFragment=fragment;
        }
        mTransaction.commitAllowingStateLoss();
    }
}
