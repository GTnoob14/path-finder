package draw;

import java.awt.Color;

public enum Status {
	BLOCK(Color.black), NOT_VISITED(Color.white), VISITED(Color.blue), PATH(Color.red), START(Color.green), END(Color.yellow);
	
	private Color color;
	
	Status(Color color){
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
