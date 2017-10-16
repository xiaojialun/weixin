package com.example.xjl.weixin;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private ArrayList<Fragment> pgFragment=new ArrayList<Fragment>();
    private String []mtitle =new String[]{
        "FirstFragment", "SecondFragment", "ThirdFragment", "FourthFragment"
    };
    private ChangeColorIconWithText iconWithTextOne,iconWithTextTwo,iconWithTextThree,iconWithTextFour;
    private ArrayList<ChangeColorIconWithText> iconWithTexts=new ArrayList<ChangeColorIconWithText>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initDatas();
        viewPager.setAdapter(adapter);
        initEvent();
    }

    private void initEvent() {
        viewPager.setOnPageChangeListener( this);
    }

    private void initDatas() {
        for (String title:mtitle){
            ViewpageFragment fragment=new ViewpageFragment();
            Bundle bundle=new Bundle();
            bundle.putString(ViewpageFragment.TITLE,title);
            fragment.setArguments(bundle);
            pgFragment.add(fragment);
        }
    }

    private void initView() {
        viewPager= (ViewPager) findViewById(R.id.viewpage);
        iconWithTextOne=(ChangeColorIconWithText)findViewById(R.id.iconWithTextOne);
        iconWithTexts.add(iconWithTextOne);
        iconWithTextTwo=(ChangeColorIconWithText)findViewById(R.id.iconWithTextTwo);
        iconWithTexts.add(iconWithTextTwo);
        iconWithTextThree=(ChangeColorIconWithText)findViewById(R.id.iconWithTextThree);
        iconWithTexts.add(iconWithTextThree);
        iconWithTextFour=(ChangeColorIconWithText)findViewById(R.id.iconWithTextFour);
        iconWithTexts.add(iconWithTextFour);

        iconWithTextOne.setOnClickListener(this);
        iconWithTextTwo.setOnClickListener(this);
        iconWithTextThree.setOnClickListener(this);
        iconWithTextFour.setOnClickListener(this);

        iconWithTextOne.setIconAlpha(1f);
    }

    PagerAdapter adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return pgFragment.get(position);
        }

        @Override
        public int getCount() {
            return pgFragment.size();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public void onClick(View view) {
        resetAlpha();
        switch (view.getId()){
            case R.id.iconWithTextOne:
                iconWithTextOne.setIconAlpha(1.0f);
                viewPager.setCurrentItem(0);
                break;
            case R.id.iconWithTextTwo:
                iconWithTextTwo.setIconAlpha(1.0f);
                viewPager.setCurrentItem(1);
                break;
            case R.id.iconWithTextThree:
                iconWithTextThree.setIconAlpha(1.0f);
                viewPager.setCurrentItem(2);
                break;
            case R.id.iconWithTextFour:
                iconWithTextFour.setIconAlpha(1.0f);
                viewPager.setCurrentItem(3);
                break;
        }
    }

    private void resetAlpha() {
        for(int i=0;i<iconWithTexts.size();i++){
            iconWithTexts.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(positionOffset>0){
            ChangeColorIconWithText left=iconWithTexts.get(position);
            ChangeColorIconWithText right=iconWithTexts.get(position+1);
            left.setIconAlpha(1-positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
