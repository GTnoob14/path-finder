package logic;

import java.awt.Color;

import draw.Block;
import draw.Status;

public class Game {

	private final Block[][] game;
	private final int x, y;
	private Block start, end;

	public Game(int x, int y) {
		game = new Block[x / 10][y / 10];
		this.x = x;
		this.y = y;
		resetBlocks();
	}

	private int getRealVal(double val) {
		return (int) (val / 10);
	}

	public void resetBlocks() {
		for (int i = 0; i < x / 10; i++) {
			for (int j = 0; j < y / 10; j++) {
				game[i][j] = new Block(i * 10, j * 10);
			}
		}
	}

	public void markAsNotVisited(double x, double y) {
		int rx = getRealVal(x);
		int ry = getRealVal(y);
		
		try {
			game[rx][ry].setBlockStatus(Status.NOT_VISITED);
		} catch (Exception e) {}
	}

	public void markAsVisited(double x, double y, int w) {
		int rx = getRealVal(x);
		int ry = getRealVal(y);
		
		Status st = Status.VISITED;
		st.setColor(new Color(w, w, (int) (255 * Math.random() + 1)));
		try {
			game[rx][ry].setBlockStatus(st);
		} catch (Exception e) {}
	}

	public void markAsStart(double x, double y) {
		int rx = getRealVal(x);
		int ry = getRealVal(y);
		
		try {
			game[rx][ry].setBlockStatus(Status.START);
			start = game[rx][ry];
		} catch (Exception e) {}
	}

	public void markAsEnd(double x, double y) {
		int rx = getRealVal(x);
		int ry = getRealVal(y);
		
		try {
			game[rx][ry].setBlockStatus(Status.END);
			end = game[rx][ry];
		} catch (Exception e) {}
	}

	public void markAsPath(double x, double y) {
		int rx = getRealVal(x);
		int ry = getRealVal(y);
		
		try {
			game[rx][ry].setBlockStatus(Status.PATH);
		} catch (Exception e) {}
	}

	public void markAsBlock(double x, double y) {
		int rx = getRealVal(x);
		int ry = getRealVal(y);
		
		try {
			game[rx][ry].setBlockStatus(Status.BLOCK);
		} catch (Exception e) {}
	}

	public Block[][] getGame() {
		return game;
	}

	public Block getStart() {
		return start;
	}

	public Block getEnd() {
		return end;
	}

	public Block getBlockByPosition(int x, int y) {
		try {
			return game[x / 10][y / 10];
		} catch (Exception e) {
		}
		return null;
	}
}
