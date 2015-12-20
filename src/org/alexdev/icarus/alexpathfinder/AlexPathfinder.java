package org.alexdev.icarus.alexpathfinder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.alexdev.icarus.game.room.model.Point;
import org.alexdev.icarus.game.room.model.RoomModel;

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

		LinkedList<Point> shortestPath = new LinkedList<Point>();

		// If the selected coordinates are outside map boundries then we cancel
		if (start.getX() > this.sizeX || start.getY() > this.sizeY) {
			return shortestPath;
		}

		// If the goal is the same as the start, then we cancel
		if (start.sameAs(goal)) {
			return shortestPath;
		}

		Point startPoint = start;
		int distance = (this.sizeX * this.sizeY);

		while (distance > 0) {

			Point newPoint = this.findNearestPoint(this.getNeighbours(startPoint), goal, null);
			
			if (newPoint == null || newPoint.sameAs(startPoint)) {
				return shortestPath;
			}
			
			shortestPath.add(newPoint);
			startPoint = newPoint;
			distance = this.getHeuristic(newPoint, goal);
		}

		/*LinkedList<Point> opened = new LinkedList<Point>();
		LinkedList<Point> closed = new LinkedList<Point>();

		for (Point coord : shortestPath) {

			if (!this.isAvailable(coord)) {
				closed.add(coord);
			} else {
				opened.add(coord);
			}

			for (Point neighbour : this.getNeighbours(coord)) {

				if (!this.isAvailable(neighbour)) {
					closed.add(neighbour);
				} else {
					opened.add(neighbour);
				}
			}
		}

		LinkedList<Point> coords = new LinkedList<Point>();*/

		return shortestPath;
	}

	public boolean isAvailable(Point point) {

		try {
			return this.collisionMap[point.getX()][point.getY()] == RoomModel.OPEN;
		} catch (Exception e) {
			return false;
		}
	}

	public Point findNearestPoint(List<Point> points, Point goal, Point exclude) {

		int distance = (this.sizeX * this.sizeY);
		Point possiblePoint = null;

		for (Point point : points) {

			if (exclude != null) {
				if (point.sameAs(exclude)) {
					continue;
				}
			}

			int newDistance = this.getHeuristic(point, goal);

			if (newDistance < distance && this.isAvailable(point)) {
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
