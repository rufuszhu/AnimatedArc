package com.example.rzhu.animatedarc;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by rzhu on 7/19/2017.
 */

public class AnimateArcView extends View
{
	private static final int STROKE_WIDTH = 20;
	private Paint mDegreesPaint, mRectPaint;
	private RectF mRect;
	private int centerX, centerY, radius;

	public AnimateArcView(@NonNull Context context)
	{
		super(context);
		init();
	}

	public AnimateArcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init()
	{
		mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mRectPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
		mRectPaint.setStyle(Paint.Style.FILL);


		mDegreesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mDegreesPaint.setStyle(Paint.Style.STROKE);
		mDegreesPaint.setStrokeWidth(STROKE_WIDTH);
		mDegreesPaint.setStrokeCap(Paint.Cap.ROUND);
		mDegreesPaint.setColor(ContextCompat.getColor(getContext(), R.color.highlight_yellow));
	}

	public void startAnimation(){
		ValueAnimator valueAnimator = ValueAnimator.ofInt(STROKE_WIDTH, 100);
		valueAnimator.setDuration(1000);
		valueAnimator.setInterpolator(new DecelerateInterpolator());

		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		{
			public void onAnimationUpdate(ValueAnimator animation)
			{
				float fraction = animation.getAnimatedFraction();

				mDegreesPaint.setStrokeWidth(STROKE_WIDTH + fraction * 80);
				requestLayout();
			}
		});
	}




	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// getHeight() is not reliable, use getMeasuredHeight() on first run:
		// Note: mRect will also be null after a configuration change,
		// so in this case the new measured height and width values will be used:
		if (mRect == null)
		{
			centerX = getMeasuredWidth()/ 2;
			centerY = getMeasuredHeight()/ 2;
			radius = centerX - (2 * STROKE_WIDTH);

			mRect = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
		}

		// just to show the rectangle bounds:
//		canvas.drawRect(mRect, mRectPaint);

		// Or draw arc from degree 192 to degree 90 like this ( 258 = (360 - 192) + 90:
		// canvas.drawArc(mRect, 192, 258, false, mBasePaint);

		// draw an arc from 90 degrees to 192 degrees (102 = 192 - 90)
		// Note that these degrees are not like mathematical degrees:
		// they are mirrored along the y-axis and so incremented clockwise (zero degrees is always on the right hand side of the x-axis)
		canvas.drawArc(mRect, 180, 180, false, mDegreesPaint);

	}
}
