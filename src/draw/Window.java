package draw;

import javax.swing.JFrame;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	private final GameService frontEnd;
	
	public Window(int width, int height, String name) {
		super(name);
		frontEnd = new GameService(width, height);
		setSize(width+15, height+40);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(frontEnd);
		setVisible(true);
	}
	
	public GameService getGameService() {
		return frontEnd;
	}
	
}
