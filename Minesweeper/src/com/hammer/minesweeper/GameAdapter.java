package com.hammer.minesweeper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
		
		TextView textView = new TextView(context);
		
		switch (game.getShowState(position%game.getYDim(), position/game.getXDim())) {
		case HIDDEN:
			textView.setText("X");
			break;
			
		case VISIBLE:
			switch (game.getGameState(position%game.getYDim(), position/game.getXDim())) {
			case BOMB:
				textView.setText("B");
				break;
			case ZERO:
				textView.setText("0");
				break;
			case ONE:
				textView.setText("1");
				break;
			case TWO:
				textView.setText("2");
				break;
			case THREE:
				textView.setText("3");
				break;
			case FOUR:
				textView.setText("4");
				break;
			case FIVE:
				textView.setText("5");
				break;
			case SIX:
				textView.setText("6");
				break;
			case SEVEN:
				textView.setText("7");
				break;
			case EIGHT:
				textView.setText("8");
				break;
			}
			break;
			
		case FLAGGED:
			textView.setText("F");
			break;
		}
		return textView;
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