package application;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import draw.Window;

public class App {

	private int width = 1200, height = 400;
	private Mode mode = Mode.BLOCK;
	private final String name = "PathFinder";
	
	private enum Mode {
		BLOCK, MARK_START, MARK_END, FIND, FINISHED;
		
		@Override
		public String toString() {
			return " [" + this.name() + ']';
		}
	}

	public void run() {
		final Window window = new Window(width, height, name);
		window.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					switch (mode) {
					case BLOCK:
						mode = Mode.MARK_START;
						break;
					case FIND:
					case FINISHED:
						mode = Mode.BLOCK;
						window.getGameService().resetGame();
						break;
					default:
						break;
					}
				}else {
					if(e.getKeyCode() == KeyEvent.VK_SPACE && mode == Mode.BLOCK) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								window.getGameService().shuffle();						
							}
						}).start();
					}else {
						if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && mode == Mode.BLOCK) {
							window.getGameService().resetGame();
						}
					}
				}
				window.setTitle(name + mode.toString());
			}
		});
		window.getGameService().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				final int x = e.getX();
				final int y = e.getY();
				
				switch (mode) {
				case BLOCK:
					if(isLeftMouseButton(e)) {
						window.getGameService().getGame().markAsBlock(x, y);
					}else {
						if(isRightMouseButton(e)) {
							window.getGameService().getGame().markAsNotVisited(x, y);
						}
					}
					break;
				default:
					break;
				}
				window.setTitle(name + mode.toString());
			}
		});
		window.getGameService().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				final int x = e.getX();
				final int y = e.getY();
				
				switch(mode) {
				case MARK_START:
					if(e.getButton() == MouseEvent.BUTTON1) {
						window.getGameService().getGame().markAsStart(x, y);
					}else {
						if(e.getButton() == MouseEvent.BUTTON3) {
								window.getGameService().getGame().markAsNotVisited(x, y);
						}
					}
					mode = Mode.MARK_END;
					break;
				case MARK_END:
					if(e.getButton() == MouseEvent.BUTTON1) {
						window.getGameService().getGame().markAsEnd(x, y);
					}else {
						if(e.getButton() == MouseEvent.BUTTON3) {
								window.getGameService().getGame().markAsNotVisited(x, y);
						}
					}
					window.getGameService().startPathFinding();
					mode = Mode.FINISHED;
					break;
				default:
					break;
				}
			}
		});
	}
}
