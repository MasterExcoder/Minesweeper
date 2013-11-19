package com.hammer.minesweeper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class GameActivity extends Activity {

	//Define variables
	Game game;
	GameAdapter gameAdapter;
	GridView gameGrid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		game = new Game();
		game.setup();
		
		gameAdapter = new GameAdapter(this, game);
		
		gameGrid = (GridView) findViewById(R.id.gameGrid);
		gameGrid.setAdapter(gameAdapter);
		gameGrid.setNumColumns(game.getXDim());
		
		gameGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				game.flag(position%game.getYDim(), position/game.getXDim());
				gameAdapter.notifyDataSetChanged();
			}
		});
		
		gameGrid.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	

}
