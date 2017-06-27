package com.yingde.gaydcj.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by tanghao on 2017/2/3.
 */

public class MyToolbar extends Toolbar {
    public MyToolbar(Context context) {
        super(context);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getChildCount() > 0)
            ((TextView) getChildAt(0)).setText(title);
        else{
            super.setTitle(title);
        }
    }

    @Override
    public void setNavigationOnClickListener(OnClickListener listener) {
//        super.setNavigationOnClickListener(listener);
        if (getChildCount() > 1)
            ((Button) getChildAt(1)).setOnClickListener(listener);


    }
    public void showChangeBtn(OnClickListener onClickListener){
        if (getChildCount() > 2) {
            ((Button) getChildAt(2)).setVisibility(VISIBLE);
            ((Button) getChildAt(2)).setOnClickListener(onClickListener);
        }
    }

    public void dismissTitle(){
        if (getChildCount() > 0){
            getChildAt(0).setVisibility(GONE);
        }
    }

}
