package com.example.xjl.weixin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by xjl on 2017/10/14.
 */

public class ViewpageFragment extends Fragment {
    TextView textView;
    private String title="test";
    public static final String TITLE="title";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        textView=new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(-1,-1,-1,-1);//4个参数按顺序分别是左上右下
        textView.setLayoutParams(layoutParams);
        if (getArguments()!=null){
            title=getArguments().getString(TITLE);
        }
        textView.setGravity(Gravity.CENTER);
        textView.setText(title);
        return textView;
    }
}
