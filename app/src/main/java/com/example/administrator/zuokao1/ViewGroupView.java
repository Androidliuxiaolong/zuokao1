package com.example.administrator.zuokao1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/5/14.
 */

public class ViewGroupView extends ViewGroup {
    public ViewGroupView(Context context) {
        this(context,null);
    }

    public ViewGroupView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ViewGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //所有子view加起来总的Measured Dimension高度和宽度
        int measuredWidth = 0;
        int measuredHeight = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View v = getChildAt(i);
            if (v.getVisibility() != View.GONE) {
                measureChild(v, widthMeasureSpec, heightMeasureSpec);
                measuredWidth += v.getMeasuredWidth();
                measuredWidth=Math.max(measuredWidth,v.getMeasuredWidth());
                measuredHeight += v.getMeasuredHeight();
                measuredHeight=Math.max(measuredHeight, v.getMeasuredHeight());
            }
        }
        //仔细检查！不要疏忽掉一些padding的值
        measuredWidth += getPaddingLeft() + getPaddingRight();
        measuredHeight += getPaddingTop()+50 + getPaddingBottom();
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    //Android系统在onMeasure之后调用onLayout
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //此时回调的参数l,t,r,b是上一步onMeasure计算出来的值。r是总宽度，b是总高度
        //我们在l,t,r,b这四个参数“框”出来的空间内一个一个摆放我们自己的子view

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View v = getChildAt(i);
            if (v.getVisibility() != View.GONE) {
                int childWidth = v.getMeasuredWidth();
                int childHeight = v.getMeasuredHeight();

                //开始摆放
                v.layout(l, t, l + childWidth, t + childHeight);

                //把左边的锚定位置往右移
                //如果在垂直方向继续累加l偏移量，那么显示出来的三个子view呈现阶梯状。
                l += childWidth;

                //垂直方向累计坐标量
                t += childHeight;
            }
        }
    }
}

