package com.hammer.minesweeper;

import com.hammer.minesweeper.Game.Result;
import com.hammer.minesweeper.Game.ShowState;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class GameActivity extends Activity implements OnSharedPreferenceChangeListener {

	//Define variables
	Game game;
	GameAdapter gameAdapter;
	GridView gameGrid;
	TextView resultView;
	Button btnNewGame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		//Initialize and set up the Game
		game = new Game();
		game.setup();
		
		//Initialize GameAdapter
		gameAdapter = new GameAdapter(this, game);
		
		//Initialize the result TextView
		resultView = (TextView) findViewById(R.id.resultView);
		
		//Initialize the New Game Button and set the OnClickListener
		btnNewGame = (Button) findViewById(R.id.btnNewGame);
		btnNewGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				game.setup();
				resultView.setText("");
				gameAdapter.notifyDataSetChanged();
			}
		});
		
		//Initialize GameGrid and bind the gameAdapter to it
		gameGrid = (GridView) findViewById(R.id.gameGrid);
		gameGrid.setAdapter(gameAdapter);
		gameGrid.setNumColumns(game.getXDim());
		//Set the OnItemClick- and OnItemLongClick Listeners
		gameGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				game.flag(position%game.getYDim(), position/game.getXDim());
				gameAdapter.notifyDataSetChanged();
			}
		});
		gameGrid.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Result result = null;
				//Check if the clicked field is already uncovered
				if(game.getShowState(position%game.getYDim(), position/game.getXDim()) != ShowState.VISIBLE) {
					result = game.uncover(position%game.getYDim(), position/game.getXDim());
				}
				
				if(result == Result.HIT) {
					resultView.setText("You got hit!");
					game.uncoverAll();
				} 
				else if(result == Result.WIN) {
					resultView.setText("Congratulations, you won the game!");
					game.uncoverAll();
				}
				
				gameAdapter.notifyDataSetChanged();
				
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		
	}
	

}
