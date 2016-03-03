package com.app.reader;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.reader.View.CircleIndicator;
import com.app.reader.utils.ReaderConstants;
import com.app.reader.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by llb on 2016/3/2.
 */

public class GuideActivity extends BaseActivity{

    private List<View> viewList;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    @Bind(R.id.tv_jump)
    TextView tv;
    @Override
    protected int getLayoutId() {
        return R.layout.avtivity_guide;
    }

    @Override
    protected void init() {
        initData();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);

        circleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);


    }

    private void initData(){
        viewList = new ArrayList<View>();
        View view1=getLayoutInflater().inflate(R.layout.activity_guide_one,null);
        View view2=getLayoutInflater().inflate(R.layout.activity_guide_two,null);
        View view3=getLayoutInflater().inflate(R.layout.activity_guide_three,null);
        View view4=getLayoutInflater().inflate(R.layout.activity_guide_four,null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
    }

    @OnClick(R.id.tv_jump)
    void jump(){
        startMain(false);
    }

    private void startMain(boolean flag){
        if(flag){
            SPUtils.put(this, ReaderConstants.IS_FIRST_IN,false);
        }
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }


    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getCount() {

            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "title";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            if(position==viewList.size()-1){
                TextView tv_start= (TextView) findViewById(R.id.tv_start);
                tv_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startMain(true);
                    }
                });
            }
            return viewList.get(position);
        }
    };

}
