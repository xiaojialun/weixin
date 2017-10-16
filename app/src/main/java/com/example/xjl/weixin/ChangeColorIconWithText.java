package com.example.xjl.weixin;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by xjl on 2017/10/14.
 */

public class ChangeColorIconWithText extends View {

    private int color=0xFF40C01A;
    private Bitmap iconbitmap;
    private int text_size= (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics());
    private String text="微信";

    private Canvas mCanvas;
    private Paint bitmapPaint;
    private Rect bitmapRect;
    private Rect textRect;
    private Paint textPaint;
    private float alpha;

    private Bitmap TargetBitmap;

    public ChangeColorIconWithText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ChangeColorIconWithText(Context context) {
        this(context,null);
    }

    public ChangeColorIconWithText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.ChangeColorIconWithText);
        int n=a.getIndexCount();
        for(int i=0;i<n;i++){
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.ChangeColorIconWithText_icon:
                    BitmapDrawable bitmapDrawable=(BitmapDrawable)a.getDrawable(attr);
                    iconbitmap=bitmapDrawable.getBitmap();
                    break;
                case R.styleable.ChangeColorIconWithText_color:
                    color=a.getColor(attr,0xFF40C01A);
                    break;
                case R.styleable.ChangeColorIconWithText_text:
                    text=a.getString(attr);
                    break;
                case R.styleable.ChangeColorIconWithText_text_size:
                    text_size=(int)a.getDimension(attr,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics()));
            }
        }
        a.recycle();
        textRect=new Rect();
        textPaint=new Paint();
        textPaint.setTextSize(text_size);
        textPaint.getTextBounds(text,0,text.length(),textRect);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int left,top,right,bottom;
        int iconWidth=Math.min(getMeasuredWidth()-getPaddingLeft()-getPaddingRight(),getMeasuredHeight()-getPaddingTop()-getPaddingBottom()-textRect.height());
        left=getMeasuredWidth()/2-iconWidth/2;
        top=getMeasuredHeight()/2-(iconWidth+textRect.height())/2;
        right=left+iconWidth;
        bottom=top+iconWidth;
        bitmapRect=new Rect(left,top,right,bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(iconbitmap,null,bitmapRect,null);
        setupText(canvas);

        setupTargetBitmap((int)Math.ceil(alpha*255));
        canvas.drawBitmap(TargetBitmap,0,0,null);
        setupTargetText(canvas,(int)Math.ceil(alpha*255));
    }

    private void setupText(Canvas canvas) {
        textPaint.setColor(0xff333333);
        int x=getMeasuredWidth()/2-textRect.width()/2;
        int y=bitmapRect.bottom+textRect.height();
        canvas.drawText(text,x,y,textPaint);
    }

    private void setupTargetText(Canvas canvas ,int Alpha) {

        textPaint.setColor(color);
        textPaint.setAlpha(Alpha);
        int x=getMeasuredWidth()/2-textRect.width()/2;
        int y=bitmapRect.bottom+textRect.height();
        canvas.drawText(text,x,y,textPaint);


    }

    private void setupTargetBitmap(int Alpha) {

        TargetBitmap=Bitmap.createBitmap(getMeasuredWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas =new Canvas(TargetBitmap);
        bitmapPaint=new Paint();
        bitmapPaint.setColor(color);
        bitmapPaint.setAlpha(Alpha);
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setDither(true);
        mCanvas.drawRect(bitmapRect,bitmapPaint);
        bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        bitmapPaint.setAlpha(255);
        mCanvas.drawBitmap(iconbitmap,null,bitmapRect,bitmapPaint);
    }

    public void setIconAlpha(float Alpha){
        this.alpha=Alpha;
        invalidateView();
    }

    private void invalidateView() {
        if(Looper.getMainLooper()==Looper.myLooper()){
            invalidate();
        }else {
            postInvalidate();
        }
    }

    private static final String INSTANCE_STATE="InstanceState";
    private static final String INSTANCE_ALPHA="alpha";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle=new Bundle();
        bundle.putParcelable(INSTANCE_STATE,super.onSaveInstanceState());
        bundle.putFloat(INSTANCE_ALPHA,alpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Build){
            Bundle bundle=(Bundle)state;
            alpha=bundle.getFloat(INSTANCE_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
        }else {
            super.onRestoreInstanceState(state);
        }
    }
}

