package com.example.wuht.leloading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

/**
 * Created by wuht on 2016/9/29.
 */

public class Leloading extends View {

    private Paint
            mDefaultPaint = new Paint();
    private int mWidth, mHeight;
    private ValueAnimator mValueAnimator;
    private float alpha,radius;
    private ArrayList<PointF> mPointFs;
    private boolean isRomote = false;
    private float mProcess;

    public Leloading(Context context) {
        this(context, null);
        init();
    }

    public Leloading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public Leloading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initPaint(mDefaultPaint, Color.RED, Paint.Style.FILL);//width 8,cap Round,抗锯齿

        initValueAnimator();

        Path path = new Path();
        float delta = 100;
        RectF rectF = new RectF(-0.5f * delta, -0.5f * delta, 0.5f * delta, 0.5f * delta);
        path.addArc(rectF, -90, 359.99f);
        PathMeasure pathMeasure = new PathMeasure(path, false);

        float beta = pathMeasure.getLength() / 6f;
        mPointFs = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            float[] temp = new float[2];
            pathMeasure.getPosTan(beta * i, temp, null);
            PointF pointF = new PointF(temp[0], temp[1]);
            mPointFs.add(pointF);
        }

        radius = 15f;
    }

    private void initValueAnimator() {
        mValueAnimator = ValueAnimator.ofFloat(0, 1f).setDuration(2000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alpha = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);

        if (!isRomote) {
            int num = (int) (alpha * 6);
            switch (num) {
                default:
                case 5:
                    if (alpha < 5.5 / 6f) {
                        drawMyCirCle(canvas, "#d16a26", radius * (6 * alpha - 5), 5);
                    } else {
                        drawMyCirCle(canvas, "#d16a26", radius, 5);
                    }
                case 4:
                    if (alpha < 5 / 6f) {
                        drawMyCirCle(canvas, "#a7da1c", radius * (6 * alpha - 4), 4);
                    } else {
                        drawMyCirCle(canvas, "#a7da1c", radius, 4);
                    }
                case 3:
                    if (alpha < 4 / 6f) {
                        drawMyCirCle(canvas, "#50cf52", radius * (6 * alpha - 3), 3);
                    } else {
                        drawMyCirCle(canvas, "#50cf52", radius, 3);
                    }
                case 2:
                    if (alpha < 3 / 6f) {
                        drawMyCirCle(canvas, "#36baea", radius * (6 * alpha - 2), 2);
                    } else {
                        drawMyCirCle(canvas, "#36baea", radius, 2);
                    }
                case 1:
                    if (alpha < 2 / 6f) {
                        drawMyCirCle(canvas, "#782eb6", radius * (6 * alpha - 1), 1);
                    } else {
                        drawMyCirCle(canvas, "#782eb6", radius, 1);
                    }

                case 0:
                    if (alpha < 1 / 6f) {
                        drawMyCirCle(canvas, "#e73354", radius * (6 * alpha - 0), 0);
                    } else {
                        drawMyCirCle(canvas, "#e73354", radius, 0);
                    }
                    break;
            }
            if (alpha > 0.98) {
                isRomote = true;
                canvas.save();
            }
        } else {

            mProcess += 10;//Math.min(mProcess + 20, 360);
            canvas.rotate(mProcess, 0, 0);

            drawMyCirCle(canvas, "#d16a26", radius, 5);
            drawMyCirCle(canvas, "#a7da1c", radius, 4);
            drawMyCirCle(canvas, "#50cf52", radius, 3);
            drawMyCirCle(canvas, "#36baea", radius, 2);
            drawMyCirCle(canvas, "#782eb6", radius, 1);
            drawMyCirCle(canvas, "#e73354", radius, 0);

            if (mProcess > 360) {
                mProcess = 0;
            }
            invalidate();
        }

    }

    private void drawMyCirCle(Canvas canvas, String color, float dx, int i) {
        mDefaultPaint.setColor(Color.parseColor(color));
        canvas.drawCircle(mPointFs.get(i).x, mPointFs.get(i).y, dx, mDefaultPaint);
    }


    private void initPaint(Paint paint, int color, Paint.Style style) {
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(8);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    public void start() {
        mValueAnimator.start();
        isRomote = false;
    }

    public void stop() {
        isRomote = false;
    }
}
