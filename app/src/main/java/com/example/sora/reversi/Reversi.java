package com.example.sora.reversi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Reversi extends View implements Runnable{

    Handler handler = new Handler();
    Koma koma = new Koma();
    float w = 0;
    float h = 0;
    float r = 0;
    float min = 0;
    float max = 0;
    float counter = 0;
    long INTERVAL = 1000;
    static boolean firstFlg;
    static boolean cpuFlg = false;
    static boolean touchFlg = true;
    boolean reverseFlg = false;

    public float getMin() {
        return min;
    }

    public boolean getCpuFlg(){
        return cpuFlg;
    }

    public static void setFirstFlg(boolean firstFlg) {
        Reversi.firstFlg = firstFlg;
    }

    public static void setCpuFlg(boolean cpuFlg) {
        Reversi.cpuFlg = cpuFlg;
    }

    public static void setTouchFlg(boolean touchFlg) {
        Reversi.touchFlg = touchFlg;
    }

    public static void changeCpuFlg() {
        cpuFlg = !cpuFlg;
    }

    public Reversi(Context context) {
        super(context);
    }

    public Reversi(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Reversi(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        if(w < h){
            min = w;
            max = h;
        }else{
            min = h;
            max = w;
        }
        r = min / 20 - 10;
        if(firstFlg) {
            koma.reset(min);
            firstFlg = false;
        }else{
            koma.sizeChanged(min);
        }
        invalidate();
    }

    @Override
    public void run() {
        counter += INTERVAL;
        if(counter >= 3000) {
            if (touchFlg && cpuFlg) {
                if(!koma.cpu()){
                    koma.pass(false);
                    Toast.makeText(getContext(), "pass", Toast.LENGTH_SHORT).show();
                }
                touchFlg = false;
            }
            for (int i = 0; i < koma.getKoma().length; i++) {
                for (int j = 0; j < koma.getKoma()[i].length; j++) {
                    if (koma.reversibleKoma(i, j)) {
                        reverseFlg = true;
                        break;
                    }
                }
            }
            if (!reverseFlg) {
                koma.pass(cpuFlg);
                Toast.makeText(getContext(), "pass", Toast.LENGTH_SHORT).show();
            }
            reverseFlg = false;
            counter = 0;
            invalidate();
        }
        handler.postDelayed(this, INTERVAL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!touchFlg || !cpuFlg) {
                    if (koma.moveKoma(event.getX(), event.getY(), r)) {
                        touchFlg = true;
                        counter = 0;
                    }
                }
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.GREEN);
        p.setStrokeWidth(8);

        Paint black = new Paint();
        black.setColor(Color.BLACK);
        black.setStrokeWidth(10);

        Paint white = new Paint();
        white.setColor(Color.BLACK);
        white.setStyle(Paint.Style.STROKE);
        white.setStrokeWidth(10);

        // テキストを描画する
        Paint pt = new Paint();
        pt.setColor(Color.BLACK);
        pt.setTypeface(Typeface.SANS_SERIF);
        pt.setTextSize(50);
        pt.setTextAlign(Paint.Align.LEFT);
        canvas.drawText((koma.isTurnFlg()? "黒" : "白") + "の手番", w > h? min + 30 : w / 2 - 100, w > h? min / 10 : min + 30, pt);
        canvas.drawText("黒：" + koma.getBlack() + "  白：" + koma.getWhite(), w > h? min + 30 : w / 2 - 162, w > h? min / 10 + 120 : min + 120, pt);
        canvas.drawText("CPU：" + (cpuFlg? "ON" : "OFF"), w > h? min + 30 : w / 2 - 125, w > h? min / 10 + 230 : min + 210, pt);
        // マスを描画する
        float[] ps = new float[72];
        for(int i = 0; i < 36; i += 4){
            ps[i] = min / 10;
            ps[i + 1] = (i / 4 + 1) * min / 10;
            ps[i + 2] = 9 * min / 10;
            ps[i + 3] = (i / 4 + 1) * min / 10;
        }
        for(int i = 36; i < 72; i += 4){
            ps[i] = ((i - 36) / 4 + 1) * min / 10;
            ps[i + 1] = min / 10;
            ps[i + 2] = ((i - 36) / 4 + 1) * min / 10;
            ps[i + 3] = 9 * min / 10;
        }
        canvas.drawLines(ps, p);
        // コマを描画する
        for(int i = 0; i < koma.getKoma().length; i ++){
            for(int j = 0; j < koma.getKoma()[i].length; j++){
                if(koma.getKomaColor()[i][j] == 1) {
                    canvas.drawCircle(koma.getKoma()[i][j][0], koma.getKoma()[i][j][1], r, black);
                }else if(koma.getKomaColor()[i][j] == 2){
                    canvas.drawCircle(koma.getKoma()[i][j][0], koma.getKoma()[i][j][1], r, white);
                }
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        handler.postDelayed(this, 100);
    }
}
