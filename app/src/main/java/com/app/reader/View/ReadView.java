package com.app.reader.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.app.reader.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llb on 2016/3/10.
 */

public class ReadView extends View {

    private int fontSize=30;//字体大小
    private int bound=30;//行间距
    private String content;
    private Paint mPaint;

    private int lineCount;//当页显示的行数
    private int readCount=0;
    private int width,height;

    private int pageNum=0;
    private int pageSize=0;

    private String lineStr;
    private int lineNum=0;

    private boolean isLine=false;

    private List<Integer> preList=new ArrayList<Integer>();
    private int preCount;
    private int thisCount;

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
        mPaint.setColor(Color.parseColor("#455146"));
        mPaint.setAntiAlias(true);

        width=getWidth()-getPaddingLeft()-getPaddingRight();
        height=getHeight()-getPaddingTop()-getPaddingBottom();

        lineCount=height/(fontSize+bound);

        thisCount=readCount;
        for(int i=0;i<lineCount;i++){
            while(true){
                lineNum++;
                if(content.substring(readCount+lineNum-1,readCount+lineNum).equals("#")){
                    isLine=true;
                    break;
                }
                if(mPaint.measureText(content.substring(readCount,readCount+lineNum))>width){
                    isLine=false;
                    break;
                }
            }

            canvas.drawText(content.substring(readCount,readCount+lineNum-1),getPaddingLeft(),getPaddingTop()+fontSize+(fontSize+bound)*i,mPaint);

            readCount+=lineNum;
            if(isLine) {

            }else{
                readCount--;
            }
            lineNum=0;
        }

        thisCount=readCount-thisCount;

    }


    float x1=0,x2=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1=event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2=event.getX();
                break;

        }
        if(x2!=0) {
            if (x2 - x1 < 0) {
                preList.add(thisCount);
                invalidate();
            }else if (x2 - x1 > 0){

                if(preList.size()==0){
                    T.showShort(getContext(),"已经是第一页了..");
                }else{
                    readCount -= thisCount;
                    readCount-=preList.get(preList.size()-1);
                    preList.remove(preList.size()-1);
                    invalidate();
                }

            }
            x1=0;
            x2=0;
        }
        return true;
    }
}
