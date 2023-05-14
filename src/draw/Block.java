package draw;

import java.awt.Color;

import javax.swing.JPanel;

//Node
public class Block extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private Status status;
	private Color color;
	private int x, y;
	private int val;
	
	public Block(int x, int y) {
		setBounds(x, y, 10, 10);
		this.x = x;
		this.y = y;
		status = Status.NOT_VISITED;
		this.color = status.getColor();
		val = Integer.MAX_VALUE;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setBlockStatus(Status status) {
		this.status = status;
		color = status.getColor();
	}
	
	public Status getBlockStatus() {
		return status;
	}
	
	public void setVal(int val) {
		this.val = val;
	}
	
	public int getVal() {
		return val;
	}
}
