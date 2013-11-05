package com.hammer.minesweeper;

import java.util.*; 

public class Game {
	private int numVisible;
	private int numBombs;
	private int xDim, yDim;	
	private GameState[][] gameState;
	private ShowState[][] showState;
	public enum Result {WIN, HIT, VALID, INVALID};
	public enum ShowState {HIDDEN, VISIBLE, FLAGGED};
	public enum GameState {BOMB(-1), ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8);	
		private final int num;
		private GameState(int i)
		{
			if (i < -1 || i > 8) throw new IllegalArgumentException();
			num = i;
		}
		public GameState inc()
		{
			if (num < 8) return GameState.values()[num+2];
			else throw new IllegalArgumentException();
		}
		public int getNum()
		{
			return num;
		}
	}
	
	public Game()
	{
		this(9,9,10);
	}
	public Game(int xDim, int yDim, int numBombs)
	{
		if (xDim < 1 || yDim < 1 || numBombs > xDim*yDim)
			throw new IllegalArgumentException();
		this.xDim = xDim;
		this.yDim = yDim;
		this.numBombs = numBombs;
		gameState = new GameState[xDim][yDim];
		showState = new ShowState[xDim][yDim];
		setup();
	}
	public int getXDim()
	{
		return xDim;
	}
	public int getYDim()
	{
		return yDim;
	}
	public GameState getGameState(int x, int y)
	{
		return gameState[x][y];
	}
	public ShowState getShowState(int x, int y)
	{
		return showState[x][y];
	}
	public void setup()
	{
		numVisible = 0;
		for (int i = 0; i < xDim; i++)
			for (int j = 0; j < yDim; j++)
			{
				gameState[i][j] = GameState.ZERO;
				showState[i][j] = ShowState.HIDDEN;
			}
		Random rand = new Random();
		for (int i = 0; i < numBombs; i++)
		{
			int bx = rand.nextInt(xDim);
			int by = rand.nextInt(yDim);
			if (gameState[bx][by] == GameState.ZERO) gameState[bx][by] = GameState.BOMB;
			else i--;
		}
		for (int i = 0; i < xDim; i++)
			for (int j = 0; j < yDim; j++)
			{
				if (gameState[i][j] == GameState.BOMB)
				{
					if(i > 0 && j > 0 && gameState[i-1][j-1] != GameState.BOMB) gameState[i-1][j-1] = gameState[i-1][j-1].inc();
					if(j > 0 && gameState[i][j-1] != GameState.BOMB) gameState[i][j-1] = gameState[i][j-1].inc();
					if(i < xDim-1 && j > 0 && gameState[i+1][j-1] != GameState.BOMB) gameState[i+1][j-1] = gameState[i+1][j-1].inc();
					if(i > 0 && gameState[i-1][j] != GameState.BOMB) gameState[i-1][j] = gameState[i-1][j].inc();
					if(i < xDim-1 && gameState[i+1][j] != GameState.BOMB) gameState[i+1][j] = gameState[i+1][j].inc();
					if(i > 0 && j < yDim-1 && gameState[i-1][j+1] != GameState.BOMB) gameState[i-1][j+1] = gameState[i-1][j+1].inc();
					if(j < yDim-1 && gameState[i][j+1] != GameState.BOMB) gameState[i][j+1] = gameState[i][j+1].inc();
					if(i < xDim-1 && j < yDim-1 && gameState[i+1][j+1] != GameState.BOMB) gameState[i+1][j+1] = gameState[i+1][j+1].inc();
				}
			}
	}
	public Result uncover(int x, int y)
	{
		if (showState[x][y] == ShowState.HIDDEN)
		{
			showState[x][y] = ShowState.VISIBLE;
			numVisible++;
			if(gameState[x][y] == GameState.BOMB)
			{
				uncoverAll();
				return Result.HIT;
			}
			if (gameState[x][y] == GameState.ZERO) recursiveUncover(x,y);
			if (xDim*yDim - numVisible == numBombs)
				return Result.WIN;
			return Result.VALID;
		}
		return Result.INVALID;
	}
	public void uncoverAll()
	{
		for (int i = 0; i < xDim; i++)
			for(int j = 0; j < yDim; j++)
				showState[i][j] = ShowState.VISIBLE;
	}
	public void recursiveUncover(int x, int y)
	{
		if(x > 0 && y > 0) recUncover(x-1,y-1);
		if(y > 0) recUncover(x,y-1);
		if(x < xDim-1 && y > 0) recUncover(x+1,y-1);
		if(x > 0) recUncover(x-1,y);
		if(x < xDim-1) recUncover(x+1,y);
		if(x > 0 && y < yDim-1) recUncover(x-1,y+1);
		if(y < yDim-1) recUncover(x,y+1);
		if(x < xDim-1 && y < yDim-1) recUncover(x+1,y+1);
		
	}	
	private void recUncover(int x, int y)
	{
		if (showState[x][y] == ShowState.HIDDEN)
		{
			numVisible++;
			showState[x][y] = ShowState.VISIBLE;
			if (gameState[x][y] == GameState.ZERO) recursiveUncover(x,y);
		}
	}
	public Result flag(int x, int y)
	{
		if (showState[x][y] == ShowState.HIDDEN)
		{
			showState[x][y] = ShowState.FLAGGED;
			return Result.VALID;
		}
		else if (showState[x][y] == ShowState.FLAGGED)
		{
			showState[x][y] = ShowState.HIDDEN;
			return Result.VALID;
		}
		return Result.INVALID;
	}
}
