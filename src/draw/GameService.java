package draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JPanel;

import logic.Game;

public class GameService extends JPanel {
	private static final long serialVersionUID = 1L;
	
	//private BeepPlayer beepPlayer = new BeepPlayer();
	private Game game;
	private final BFSFinder bfsFinder = new BFSFinder();
	private Thread t1;
	
	private final int x, y;
	public GameService(int width, int height) {
		setBounds(0, 0, width, height);
		this.x = width/10;
		this.y = height/10;
		game = new Game(width, height);
		setBackground(Color.white);
	}
	
	public void resetGame() {
		t1.stop();
		game.resetBlocks();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for(Block[] bs : game.getGame()) {
			for(Block block : bs) {
				int x, y;
				x = block.getX();
				y = block.getY();
				g.setColor(block.getColor());
				//g2d.fillRect(x+1, y+1, 9, 9);
				g2d.fillRect(x, y, 10, 10);
				g.setColor(Color.black);
				//g2d.drawRect(x, y, 10, 10);
			}
		}
		repaint();
	}
	
	public Game getGame() {
		return game;
	}

	private List<Block> getStraightConnections(Block block){
		List<Block> l = new ArrayList<>();
		
		Block left, right, up, down;
		
		
		left = game.getBlockByPosition(block.getX() - 10, block.getY());
		if(left != null)
			l.add(left);
		right = game.getBlockByPosition(block.getX() + 10, block.getY());
		if(right != null)
			l.add(right);
		up = game.getBlockByPosition(block.getX(), block.getY() - 10);
		if(up != null)
			l.add(up);
		down = game.getBlockByPosition(block.getX(), block.getY() + 10);
		if(down != null)
			l.add(down);

		return l;
	}
	
	public void shuffle() {
		for(int i = 0; i < x; i++)
			for(int j = 0; j < y; j++)
				if(Math.random() <= 0.05)
					game.markAsBlock(i*10, j*10);
	}
	
	//Finder Class to Run parallel to paintComponent Main-Thread and Finder-Thread
	public class BFSFinder implements Finder {
		@Override
		public void startPathFinding() {
			Queue<Block> q = new LinkedList<>();
			Block root = game.getStart();
			root.setVal(0);
			q.offer(root);
			double w = 0;
			outer:
			while(!q.isEmpty()) {
				root = q.poll();
				for(Block b : getStraightConnections(root)) {
					if(b.getBlockStatus() == Status.END) {
						findPath(b);
						break outer;
					}
					if(b.getBlockStatus() == Status.NOT_VISITED) {
						q.offer(b);
						game.markAsVisited(b.getX(), b.getY(), (int) w);
					}
					if(b.getBlockStatus() != Status.BLOCK)
						game.getBlockByPosition(b.getX(), b.getY()).setVal(Math.min(root.getVal() + 1, b.getVal()));
				}
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				 w += (w <= 200  ? 0.05 : 0);
			}
			
		}
		@Override
		public void findPath(Block end) {
			while(true) {
				for(Block b : getStraightConnections(end)) {
					if(end.getVal() > b.getVal()) {
						end = b;
					}
				}
				if(end.getBlockStatus() == Status.START)
					break;
				game.markAsPath(end.getX(), end.getY());
//				beepPlayer.start();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				beepPlayer.stop();
			}
		}
	}
	
	public void startPathFinding() {
		t1 = new Thread(bfsFinder);
		t1.start();
	}
}
