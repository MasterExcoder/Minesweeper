package com.hammer.minesweeper;

import com.hammer.minesweeper.Game.Result;
import com.hammer.minesweeper.Game.ShowState;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class GameActivity extends Activity implements OnSharedPreferenceChangeListener 
{
	// Define variables
	Game game;
	GameAdapter gameAdapter;
	GridView gameGrid;
	TextView resultView;
	Button btnNewGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		//Fetch the views from xml
		gameGrid = (GridView) findViewById(R.id.gameGrid);
		resultView = (TextView) findViewById(R.id.resultView);
		btnNewGame = (Button) findViewById(R.id.btnNewGame);
		
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String pref = sharedPref.getString("pref_difficulty", "");
		
		// Initialize and set up the Game
		if (pref.equals("0")) {
			gameGrid.setNumColumns(9);
			game = new Game(9, 9, 15);
		} else if (pref.equals("1")) {
			gameGrid.setNumColumns(16);
			game = new Game(16, 16, 40);
		} else if (pref.equals("2")) {
			gameGrid.setNumColumns(24);
			game = new Game(24, 24, 86);
		} else {
			gameGrid.setNumColumns(5);
			game = new Game(5, 5, 14);
		}
		game.setup();
		gameAdapter = new GameAdapter(this, game);
		gameGrid.setAdapter(gameAdapter);
		gameAdapter.notifyDataSetChanged();

		//New Game Button, set the OnClickListener
		btnNewGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				game.setup();
				resultView.setText("");
				gameAdapter.notifyDataSetChanged();
			}
		});

		// Set the OnItemClick- and OnItemLongClick Listeners
		gameGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				game.flag(position % game.getYDim(), position / game.getXDim());
				gameAdapter.notifyDataSetChanged();
			}
		});
		gameGrid.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Result result = null;
				// Check if the clicked field is already uncovered
				if (game.getShowState(position % game.getYDim(), position
						/ game.getXDim()) != ShowState.VISIBLE) {
					result = game.uncover(position % game.getYDim(), position
							/ game.getXDim());
				}

				if (result == Result.HIT) {
					resultView.setText("You got hit!");
					game.uncoverAll();
				} else if (result == Result.WIN) {
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
	public void onSharedPreferenceChanged(SharedPreferences pref, String key) {
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);

	}

}
