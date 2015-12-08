package net.quackster.icarus.game.pathfinder;

public class Point {

	private int X;
	private int Y;
	
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
