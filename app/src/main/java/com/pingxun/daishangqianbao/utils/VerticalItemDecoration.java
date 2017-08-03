package com.pingxun.daishangqianbao.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.pingxun.daishangqianbao.R;


/**
 * Author:Cow Shed<p/>
 * Created time:2017-03-09 01:53<p/>
 * Description:Custom the recycle item decoration value
 */
public class VerticalItemDecoration extends RecyclerView.ItemDecoration{

    private int mDividerHeight;
    private Paint mPaint;

    public VerticalItemDecoration(Context context, int space){
        mDividerHeight= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,space,context.getResources().getDisplayMetrics());
        mPaint=new Paint();
        mPaint.setColor(ContextCompat.getColor(context, R.color.blue));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mDividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + mDividerHeight;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }
}
