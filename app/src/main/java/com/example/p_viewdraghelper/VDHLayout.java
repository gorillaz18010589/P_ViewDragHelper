package com.example.p_viewdraghelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.customview.widget.ViewDragHelper;

public class VDHLayout extends LinearLayout {
    private ViewDragHelper mDragger;

    public VDHLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);

//        1.创建实例需要三个参数，第一个就是ViewGroup；第二个sesitivity,主要用于设置touchslop，
//        1.0f是常用值；第三个是Callback，在用户回调过程中会调用相关方法。
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback()
        {
            @Override
            public boolean tryCaptureView(View child, int pointerId)
            {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx)
            {
                Log.v("hank","left:" + left +"/dx:" + dx);
                //边界控制，横向移动控制在viewgroup内部
                int paddingLeft = getPaddingLeft();
                int rightBound = getWidth() - getPaddingRight() - child.getWidth();
                //控制左边
                left = left < paddingLeft ? paddingLeft : left;
                //控制右边
                left = left > rightBound ?
                        rightBound : left;

                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy)
            {
                return top;
            }

        });
    }
//2.将MotionEvent交给ViewDragHelper
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return  mDragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mDragger.processTouchEvent(event);
        return true;
    }


}