package com.example.rzhu.animatedarc;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by rzhu on 7/19/2017.
 */

public class AnimateArcView extends View
{
	private Paint mFirstRipplePaint, mSecondRipplePaint, mFixArcPaint;
	private RectF mRect;
	private ValueAnimator mFirstRippleAnimator;
	private ValueAnimator mSecondRippleAnimator;
	private int mStrokeWidth;

	public AnimateArcView(@NonNull Context context)
	{
		super(context);
		init();
	}

	public AnimateArcView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		mStrokeWidth = getResources().getDimensionPixelOffset(R.dimen.highlight_arc_stroke_width);
		int pulseWidth = getResources().getDimensionPixelOffset(R.dimen.highlight_arc_pulse_width);

		Interpolator interpolator = new DecelerateInterpolator();
		mFirstRipplePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mFirstRipplePaint.setStyle(Paint.Style.STROKE);
		mFirstRipplePaint.setStrokeWidth(mStrokeWidth);
		mFirstRipplePaint.setStrokeCap(Paint.Cap.ROUND);
		mFirstRipplePaint.setColor(ContextCompat.getColor(getContext(), R.color.highlight_gold));

		mSecondRipplePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mSecondRipplePaint.setStyle(Paint.Style.STROKE);
		mSecondRipplePaint.setStrokeWidth(mStrokeWidth);
		mSecondRipplePaint.setStrokeCap(Paint.Cap.ROUND);
		mSecondRipplePaint.setColor(ContextCompat.getColor(getContext(), R.color.highlight_gold));

		mFixArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mFixArcPaint.setStyle(Paint.Style.STROKE);
		mFixArcPaint.setStrokeWidth(mStrokeWidth);
		mFixArcPaint.setStrokeCap(Paint.Cap.ROUND);
		mFixArcPaint.setColor(ContextCompat.getColor(getContext(), R.color.highlight_gold));

		int animationLength = getResources().getInteger(R.integer.arc_pulse_animation_time);

		mFirstRippleAnimator = ValueAnimator.ofFloat(mStrokeWidth, pulseWidth);
		mFirstRippleAnimator.setDuration(animationLength);
		mFirstRippleAnimator.setInterpolator(interpolator);
		mFirstRippleAnimator.setRepeatMode(ValueAnimator.RESTART);
		mFirstRippleAnimator.setRepeatCount(ValueAnimator.INFINITE);
		mFirstRippleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		{
			public void onAnimationUpdate(ValueAnimator animation)
			{
				mFirstRipplePaint.setStrokeWidth((Float) animation.getAnimatedValue());
				mFirstRipplePaint.setAlpha((int) ((1f - animation.getAnimatedFraction()) * 256));

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
				{
					postInvalidateOnAnimation();
				}
				else
				{
					postInvalidate();
				}
			}
		});

		mSecondRippleAnimator = ValueAnimator.ofFloat(mStrokeWidth, pulseWidth);
		mSecondRippleAnimator.setDuration(animationLength);
		mSecondRippleAnimator.setInterpolator(interpolator);
		mSecondRippleAnimator.setRepeatMode(ValueAnimator.RESTART);
		mSecondRippleAnimator.setRepeatCount(ValueAnimator.INFINITE);
		mSecondRippleAnimator.setStartDelay(animationLength / 5 * 2);
		mSecondRippleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		{
			public void onAnimationUpdate(ValueAnimator animation)
			{
				mSecondRipplePaint.setStrokeWidth((Float) animation.getAnimatedValue());
				mSecondRipplePaint.setAlpha((int) ((1f - animation.getAnimatedFraction()) * 256));
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
				{
					postInvalidateOnAnimation();
				}
				else
				{
					postInvalidate();
				}
			}
		});
	}

	public void startAnimation()
	{
		stopAnimation();
		mFirstRippleAnimator.start();
		mSecondRippleAnimator.start();
	}

	public void stopAnimation()
	{
		mFirstRippleAnimator.cancel();
		mSecondRippleAnimator.cancel();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if (mRect == null)
		{
			int centerX = getMeasuredWidth() / 2;
			int centerY = getMeasuredHeight() - mStrokeWidth * 2;
			int radius = getResources().getDimensionPixelOffset(R.dimen.highlight_arc_radius);

			mRect = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
		}

		canvas.drawArc(mRect, 180, 180, false, mFirstRipplePaint);
		canvas.drawArc(mRect, 180, 180, false, mSecondRipplePaint);
		canvas.drawArc(mRect, 180, 180, false, mFixArcPaint);
	}
}
