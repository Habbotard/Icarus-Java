package net.quackster.icarus.game.room.model;

public class Point {

	private int X;
	private int Y;
	
	public Point() {
		this(0, 0);
	}
	
	public Point(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}
	
	public boolean sameAs(Point point) {	
		return (this.X == point.getX() && this.Y == point.getY());
	}
	
}
