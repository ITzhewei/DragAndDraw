package com.example.john.draganddraw.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.john.draganddraw.bean.Box;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2016/10/3.
 */

public class BoxDrawingView extends View {

    private static final String TAG = "BoxDrawingView";

    private Box mCurrentBox;
    private List<Box> mBoxList = new ArrayList<>();

    private Paint mBoxPaint;
    private Paint mBackGroundPaint;

    public BoxDrawingView(Context context) {
        this(context, null);
    }

    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);
        mBackGroundPaint = new Paint();
        mBackGroundPaint.setColor(0xfff8efe0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        PointF current = new PointF(event.getX(), event.getY());
        String action = "";

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://手指刚触摸到屏幕
                action = "down";
                mCurrentBox = new Box(current);
                mBoxList.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE://手指在屏幕上移动
                action = "move";
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(current);
                    invalidate();//该方法会强制重新绘制当前view
                }
                break;
            case MotionEvent.ACTION_UP://手指离开屏幕
                action = "up";
                break;
            case MotionEvent.ACTION_CANCEL://父视图拦截了触摸事件
                action = "cancel";
                break;
        }
//        Log.i(TAG, "onTouchEvent: " + action + "at" + "(" + current.x + "," + current.y + ")");
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //填充背景颜色
        canvas.drawPaint(mBackGroundPaint);

        for (Box box : mBoxList) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("draw", (Parcelable) mBoxList);
        bundle.putParcelable("view",super.onSaveInstanceState());
        Log.i(TAG, "onSaveInstanceState: 啊大大说大飒飒大大");
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle= (Bundle) state;
//        mBoxList = bundle.getParcelable("draw");
        Log.i(TAG, "onRestoreInstanceState: 哈哈哈哈哈哈啊");
        super.onRestoreInstanceState(bundle.getParcelable("view"));
    }
}
