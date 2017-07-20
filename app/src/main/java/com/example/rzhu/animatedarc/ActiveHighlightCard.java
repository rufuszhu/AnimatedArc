package com.example.rzhu.animatedarc;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rzhu on 7/20/2017.
 *
 */

public class ActiveHighlightCard extends FrameLayout
{

	@InjectView(R.id.arc_view)
	AnimateArcView mAnimateArcView;
	@InjectView(R.id.dot_1)
	ImageView mDot1;
	@InjectView(R.id.dot_2)
	ImageView mDot2;
	@InjectView(R.id.dot_3)
	ImageView mDot3;
	@InjectView(R.id.group_loading_dots)
	LinearLayout mLoadingDots;
	@InjectView(R.id.group_highlight_score)
	LinearLayout mHighLightScore;
	@InjectView(R.id.score)
	TextView mScore;
	final Animation mDot1Animation;
	final Animation mDot2Animation;
	final Animation mDot3Animation;

	public ActiveHighlightCard(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.active_highlight_card, this, true);
		ButterKnife.inject(this);

		mDot1Animation = AnimationUtils.loadAnimation(context, R.anim.dot_animation);
		mDot2Animation = AnimationUtils.loadAnimation(context, R.anim.dot_animation);
		mDot3Animation = AnimationUtils.loadAnimation(context, R.anim.dot_animation);

		mDot1Animation.setAnimationListener(new Animation.AnimationListener()
		{
			@Override
			public void onAnimationStart(Animation animation)
			{}

			@Override
			public void onAnimationEnd(Animation animation)
			{
				mDot2.startAnimation(mDot2Animation);
			}

			@Override
			public void onAnimationRepeat(Animation animation)
			{}
		});

		mDot2Animation.setAnimationListener(new Animation.AnimationListener()
		{
			@Override
			public void onAnimationStart(Animation animation)
			{}

			@Override
			public void onAnimationEnd(Animation animation)
			{
				mDot3.startAnimation(mDot3Animation);
			}

			@Override
			public void onAnimationRepeat(Animation animation)
			{
			}
		});

		mDot3Animation.setAnimationListener(new Animation.AnimationListener()
		{
			@Override
			public void onAnimationStart(Animation animation)
			{}

			@Override
			public void onAnimationEnd(Animation animation)
			{
				mDot1Animation.setStartOffset(getResources().getInteger(R.integer.dot_animation_loop_offset_time));
				mDot1.startAnimation(mDot1Animation);
			}

			@Override
			public void onAnimationRepeat(Animation animation)
			{
			}
		});

		showLoadingState();
	}

	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		mAnimateArcView.startAnimation();
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		mAnimateArcView.stopAnimation();
	}

	public void showLoadingState()
	{
		mLoadingDots.setVisibility(VISIBLE);
		mHighLightScore.setVisibility(GONE);
		startLoadingDotAnimation();
	}

	public void showScore(double score)
	{
		mLoadingDots.setVisibility(GONE);
		mHighLightScore.setVisibility(VISIBLE);
		stopLoadingDotAnimation();
		mScore.setText(score + "x");
	}

	private void startLoadingDotAnimation()
	{
		stopLoadingDotAnimation();
		mDot1.startAnimation(mDot1Animation);
	}

	private void stopLoadingDotAnimation()
	{
		mDot1Animation.cancel();
		mDot2Animation.cancel();
		mDot3Animation.cancel();
	}

}
