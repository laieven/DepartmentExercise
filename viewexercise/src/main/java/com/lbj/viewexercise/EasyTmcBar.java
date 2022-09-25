package com.lbj.viewexercise;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.LinkedHashMap;
import java.util.Map;

public class EasyTmcBar extends View {
    public static final int TRAFFIC_STATUS_PASSED = -1;
    public static final int TRAFFIC_STATUS_UNBLOCKED = 0;
    public static final int TRAFFIC_STATUS_AMBLE = 1;
    public static final int TRAFFIC_STATUS_JAM = 2;
    // 假数据
    private Map<Integer, Integer> tmcLinkMap = new LinkedHashMap<>();

    // 画画得有画笔
    private final Paint paint;

    // 属性变量
    private Drawable carDrawable;
    private int attrsWidth;
    private int jamColor;
    private int ambleColor;
    private int unblockedColor;
    private int passedColor;
    // 车图标的宽高
    private int carWidth;
    private int carHeight;

    // tmc和车的坐标点：左上右下
    private final RectF tmcBarRect = new RectF();
    private final Rect carRect = new Rect();

    public EasyTmcBar(Context context) {
        this(context, null);
    }

    public EasyTmcBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyTmcBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 假数据
        tmcLinkMap.put(TRAFFIC_STATUS_UNBLOCKED, 200);
        tmcLinkMap.put(TRAFFIC_STATUS_AMBLE, 300);
        tmcLinkMap.put(TRAFFIC_STATUS_JAM, 500);
        tmcLinkMap.put(TRAFFIC_STATUS_PASSED, 300);

        // 获取自定义的属性
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.EasyTmcBar);

            carDrawable = ta.getDrawable(R.styleable.EasyTmcBar_tmc_car_drawable);
            attrsWidth = ta.getDimensionPixelSize(R.styleable.EasyTmcBar_tmc_width, 200);
            jamColor = ta.getColor(R.styleable.EasyTmcBar_tmc_jam_color, Color.WHITE);
            ambleColor = ta.getColor(R.styleable.EasyTmcBar_tmc_amble_color, Color.WHITE);
            unblockedColor = ta.getColor(R.styleable.EasyTmcBar_tmc_unblocked_color, Color.WHITE);
            passedColor = ta.getColor(R.styleable.EasyTmcBar_tmc_passed_color, Color.WHITE);

            // 车的宽度
            carWidth = carDrawable.getIntrinsicWidth();
            carHeight = carDrawable.getIntrinsicHeight();

            ta.recycle();
        }

        paint = new Paint();
        // 抗锯齿
        paint.setAntiAlias(true);
        /*
         * Style.FILL，实心
         * Style.FILL_AND_STROKE，同时显示实心和空心
         * Style.STROKE，空心
         */
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //进行横向计算
        if (1 == 1){
            horizontalMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }


        // 最终大小，宽度
        int finalWidth = carWidth;
        int finalHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        // 光柱图的左上右下
        tmcBarRect.left = (carWidth - attrsWidth) * 1.00f / 2;
        tmcBarRect.right = tmcBarRect.left + attrsWidth;
        tmcBarRect.top = 0;
        tmcBarRect.bottom = finalHeight;

        // 车图标的左右
        carRect.left = 0;
        carRect.right = carWidth;

        // 最终生成计算好的大小，传入super中测量
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void horizontalMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 最终大小，宽度
        int finalWidth = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        int finalHeight = carHeight;

        // 光柱图的左上右下
        tmcBarRect.left = 0;
        tmcBarRect.top = (carWidth - attrsWidth) * 1.00f / 2;
        tmcBarRect.right = finalWidth;
        tmcBarRect.bottom = tmcBarRect.top + attrsWidth;

        // 车图标的左上右下
        carRect.left = (int) (finalWidth - carWidth * 0.5);
        carRect.top = 0;
        carRect.right = (int) (finalWidth + carWidth * 0.5);
        carRect.bottom = finalHeight;

        // 最终生成计算好的大小，传入super中测量
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY);
//        widthMeasureSpec = MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressWarnings("all")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDraw(Canvas canvas) {


        if (1 == 1){
            horizontalDraw(canvas);
            return;
        }

        // 画光柱
        int sumDistance = 0; // 绿色的高度
        for (Map.Entry<Integer, Integer> entry : tmcLinkMap.entrySet()) {
            int status = entry.getKey();
            int distance = entry.getValue();

            RectF rectF = new RectF(
                    tmcBarRect.left,
                    sumDistance,
                    tmcBarRect.right,
                    sumDistance + distance
            );

            paint.setColor(getStatusColor(status));
            // 绘制矩形
            canvas.drawRect(rectF, paint);

            sumDistance += distance;
        }

        //1. 用paint给光柱图加上白色边框
        withWhiteStroke(canvas);


        //2. 调整车标位置，让它处于分割线居中
        // 此时需要调整车的底和高（只需要加上车标一半高即可）
        // 画车
        carRect.bottom = sumDistance - tmcLinkMap.getOrDefault(TRAFFIC_STATUS_PASSED, 0) + carHeight / 2;
        carRect.top = carRect.bottom - carHeight;
        carDrawable.setAlpha(80);
        // 把drawable绘制上去 // BitmapDrawable
        carDrawable.setBounds(carRect);
        // 内部实现 canvas.drawBitmap(bitmap, null, mDstRect, paint);
        carDrawable.draw(canvas);
        super.onDraw(canvas);
    }


    //3. 横向绘制，此时在绘制光柱图的时候，不是左右进行绘制了，而是上下进行绘制
    private void horizontalDraw(Canvas canvas){
        // 画进度
        int sumDistance = 0;
        for (Map.Entry<Integer, Integer> entry : tmcLinkMap.entrySet()) {
            int status = entry.getKey();
            int distance = entry.getValue();

            RectF rectF = new RectF(
                    sumDistance,
                    tmcBarRect.top,
                    sumDistance + distance,
                    tmcBarRect.bottom
            );
            paint.setColor(getStatusColor(status));
            canvas.drawRect(rectF, paint);

            sumDistance += distance;
            tmcBarRect.right = sumDistance;
        }

        //1. 用paint给光柱图加一个白色边框；
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(tmcBarRect, paint);

        //2. 调整车标位置，让它处于分割线居中
        // 此时需要调整车的底和高（只需要加上车标一半高即可）

        // 画车
        carRect.left = sumDistance - tmcLinkMap.getOrDefault(TRAFFIC_STATUS_PASSED, 0) + carHeight / 2;
        carRect.top = carRect.bottom - carHeight;
        carDrawable.setAlpha(80);
        // 把drawable绘制上去 // BitmapDrawable
        carDrawable.setBounds(carRect);
        // 内部实现 canvas.drawBitmap(bitmap, null, mDstRect, paint);
        carDrawable.draw(canvas);
        super.onDraw(canvas);
    }

    private void withWhiteStroke(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(tmcBarRect, paint);
    }


    private int getStatusColor(int status) {
        switch (status) {
            case TRAFFIC_STATUS_UNBLOCKED: {
                return unblockedColor;
            }
            case TRAFFIC_STATUS_AMBLE: {
                return ambleColor;
            }
            case TRAFFIC_STATUS_JAM: {
                return jamColor;
            }
            default: {
                return passedColor;
            }
        }
    }
}