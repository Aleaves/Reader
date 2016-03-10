package com.app.reader.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by llb on 2016/3/10.
 */

public class ReadView extends View{
    private int fontSize=30;
    private int bound=5;
    private String content;
    private Paint mPaint;

    public ReadView(Context context) {
        super(context);
    }

    public void setContent(String content){
        this.content=content;
    }

    public ReadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint=new Paint();
        mPaint.setTextSize(fontSize);
        mPaint.setAntiAlias(true);
        int a=(int)((getWidth()-getPaddingLeft()-getPaddingRight())/(fontSize));
        int b=(int)((getHeight()-getPaddingTop()-getPaddingBottom())/(fontSize+bound));

        for(int i=0;i<b;i++){
            canvas.drawText(content.substring(a*i,a*(i+1)),getPaddingLeft(),getPaddingTop()+fontSize+(fontSize+bound)*i,mPaint);
        }

        Log.i("======",a+"===="+b);
    }
}
