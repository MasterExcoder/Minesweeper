package com.hammer.minesweeper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class GameAdapter extends BaseAdapter {
	Game game;
	Context context;
	
	public GameAdapter(Context context, Game game)
	{
		this.context = context;
		setGame(game);
	}

	@Override
	public int getCount() {
		return game.getXDim()*game.getYDim();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ImageView imageView = new ImageView(context);
		imageView.setBackgroundColor(Color.WHITE);
		
		switch (game.getShowState(position%game.getYDim(), position/game.getXDim())) {
		case HIDDEN:
			imageView.setImageResource(R.drawable.hidden);
			imageView.setScaleType(ScaleType.CENTER_INSIDE);
			break;
			
		case VISIBLE:
			switch (game.getGameState(position%game.getYDim(), position/game.getXDim())) {
			case BOMB:
				imageView.setImageResource(R.drawable.bomb);
				break;
			case ZERO:
				imageView.setImageResource(R.drawable.empty);
				break;
			case ONE:
				imageView.setImageResource(R.drawable.icon1);
				break;
			case TWO:
				imageView.setImageResource(R.drawable.icon2);
				break;
			case THREE:
				imageView.setImageResource(R.drawable.icon3);
				break;
			case FOUR:
				imageView.setImageResource(R.drawable.icon4);
				break;
			case FIVE:
				imageView.setImageResource(R.drawable.icon5);
				break;
			case SIX:
				imageView.setImageResource(R.drawable.icon6);
				break;
			case SEVEN:
				imageView.setImageResource(R.drawable.icon7);
				break;
			case EIGHT:
				imageView.setImageResource(R.drawable.icon8);
				break;
			}
			break;
			
		case FLAGGED:
			imageView.setImageResource(R.drawable.flag);
			break;
		}
		return imageView;
	}

	public void setGame(Game game) {
		if (game != null)
				this.game = game;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}