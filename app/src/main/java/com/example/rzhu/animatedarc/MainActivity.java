package com.example.rzhu.animatedarc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{

	@InjectView(R.id.active_highlight_card)
	ActiveHighlightCard mActiveHighlightCard;

	@OnClick(R.id.btn)
	void onLoadingClick()
	{
		mActiveHighlightCard.showLoadingState();
	}

	@OnClick(R.id.btn2)
	void onScoreClick()
	{
		mActiveHighlightCard.showScore(8.01);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
	}

}
