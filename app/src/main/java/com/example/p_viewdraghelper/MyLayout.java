package com.example.p_viewdraghelper;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.customview.widget.ViewDragHelper;

public class MyLayout extends LinearLayout {

    private ViewDragHelper mViewDragHelper;

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelperCallback());
    }

    private class ViewDragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int pointerId) {
            //假設我不希望紅色的子view可以被拖曳，那就加一層判斷，只要是特定的view，直接返回false，false告訴Android系統，這個子view是不允許拖曳操作的。
            if (view.getId() == R.id.decelerate)
                return false;

            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            //控制左邊的拖曳距離，不能越界。
            //當拖曳的距離超過左邊的padding值，也意味着child view越界，復位
            //默認的padding值=0
            int paddingleft = getPaddingLeft();
            if (left < paddingleft) {
                return paddingleft;
            }

            //這里是控制右邊的拖曳邊緣極限位置。
            //假設pos的值剛好是子view child右邊邊緣與父view的右邊重合的情況
            //pos值即為一個極限的最右邊位置，超過也即意味着拖曳越界：越出右邊的界限，復位。
            //可以再加一個paddingRight值，缺省的paddingRight=0，所以即便不加也在多數情況正常可以工作
            int pos = getWidth() - child.getWidth() - getPaddingRight();
            if (left > pos) {
                return pos;
            }

            //其他情況屬於在范圍內的拖曳，直接返回系統計算默認的left即可
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //控制子view拖曳不能超過最頂部
            int paddingTop = getPaddingTop();
            if (top < paddingTop) {
                return paddingTop;
            }

            //控制子view不能越出底部的邊界。
            int pos = getHeight() - child.getHeight() - getPaddingBottom();
            if (top > pos) {
                return pos;
            }

            //其他情況正常，直接返回Android系統計算的top即可。
            return top;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            /**
             switch (state) {
             case ViewDragHelper.STATE_DRAGGING:
             // 正在拖動
             break;

             case ViewDragHelper.STATE_IDLE:
             // 沒有被拖拽或者正在進行fling/snap
             break;

             case ViewDragHelper.STATE_SETTLING:
             // fling完畢后被放置到一個位置
             break;
             }
             */
            super.onViewDragStateChanged(state);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}