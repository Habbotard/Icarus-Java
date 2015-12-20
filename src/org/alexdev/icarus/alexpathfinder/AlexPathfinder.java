package org.alexdev.icarus.alexpathfinder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.alexdev.icarus.game.room.model.Point;

public class AlexPathfinder {
	
	private int[][] collisionMap;
	
	private int sizeX;
	private int sizeY;

	public AlexPathfinder(int[][] collisionMap) {
		this.collisionMap = collisionMap;
		
		this.sizeX = this.collisionMap[0].length;
		this.sizeY = this.collisionMap.length;
	}
	
	public LinkedList<Point> findPath(Point start, Point goal)  {
		
		LinkedList<Point> coords = new LinkedList<Point>();
		
		// If the selected coordinates are outside map boundries then we cancel
		if (start.getX() > this.sizeX || start.getY() > this.sizeY) {
			return coords;
		}
	
		// If the goal is the same as the start, then we cancel
		if (start.sameAs(goal)) {
			return coords;
		}
		
		Point startPoint = start; {
			
			int distance = (this.sizeX * this.sizeY);
			
			while (distance > 0) {
				
				Point newPoint = this.findNearestPoint(this.getNeighbours(startPoint), goal);
				coords.add(newPoint);
				
				startPoint = newPoint;
				distance = this.getHeuristic(newPoint, goal);
			}
		};
		
		return coords;
	}
	
	public Point findNearestPoint(List<Point> points, Point goal) {
		
		int distance = (this.sizeX * this.sizeY);
		Point possiblePoint = null;
		
		for (Point point : points) {
			
			int newDistance = this.getHeuristic(point, goal);
			
			if (newDistance < distance) {
				distance = newDistance;
				possiblePoint = point;
			}
		}
		
		return possiblePoint;
	}
	
	// creates a 3x3 grid around the selected coordinate
	public List<Point> getNeighbours(Point coordinate) {
		
		List<Point> neighbours = new ArrayList<Point>();
		
		Point[] points = new Point[] {
	            new Point(-1, 0),
	            new Point(0, -1),
	            new Point(1, 0),
	            new Point(0, 1),
	            new Point(-1, -1),
	            new Point(-1, 1),
	            new Point(1, -1),
	            new Point(1, 1),
	    };

		
		for (Point neighbour : points) {
			neighbours.add(new Point(coordinate.getX() + neighbour.getX(), coordinate.getY() + neighbour.getY()));
		}
		
		return neighbours;
	}
	
	// manhattan distance
	public int getHeuristic(Point one, Point two) {
		return Math.abs(one.getX() - two.getX()) + Math.abs(one.getY() - two.getY());
	}
}
