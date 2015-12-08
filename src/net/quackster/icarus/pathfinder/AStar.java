package net.quackster.icarus.pathfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import net.quackster.icarus.pathfinder.heuristics.AStarHeuristic;

public class AStar {
	private AreaMap map;
	private AStarHeuristic heuristic;
	/**
	 * closedList The list of Nodes not searched yet, sorted by their distance to the goal as guessed by our heuristic.
	 */
	private LinkedList<Node> closedList;
	private SortedNodeList openList;
	private LinkedList<Point> shortestPath;

	public AStar(AreaMap map, AStarHeuristic heuristic) {
		this.map = map;
		this.heuristic = heuristic;
		closedList = new LinkedList<Node>();
		openList = new SortedNodeList();
	}

	public LinkedList<Point> calculateShortestPath(Point start, Point goal) {
		return this.calculateShortestPath(start.getX(), start.getY(), goal.getX(), goal.getY());
	}
	
	public LinkedList<Point> calculateShortestPath(int startX, int startY, int goalX, int goalY) {
		//this.startX = startX;
		//this.startY = startY;
		//this.goalX = goalX;
		//this.goalY = goalY;

		//mark start and goal node
		map.setStartLocation(startX, startY);
		map.setGoalLocation(goalX, goalY);

		//Check if the goal node is also an obstacle (if it is, it is impossible to find a path there)
		if (map.getNode(goalX, goalY).isObstacle) {
			return null;
		}

		map.getStartNode().setDistanceFromStart(0);
		closedList.clear();
		openList.clear();
		openList.add(map.getStartNode());

		//while we haven't reached the goal yet
		while(openList.size() != 0) {

			//get the first Node from non-searched Node list, sorted by lowest distance from our goal as guessed by our heuristic
			Node current = openList.getFirst();

			// check if our current Node location is the goal Node. If it is, we are done.
			if(current.getX() == map.getGoalLocationX() && current.getY() == map.getGoalLocationY()) {
				return reconstructPath(current);
			}

			//move current Node to the closed (already searched) list
			openList.remove(current);
			closedList.add(current);

			//go through all the current Nodes neighbors and calculate if one should be our next step
			for(Node neighbor : current.getNeighborList()) {
				boolean neighborIsBetter;

				//if we have already searched this Node, don't bother and continue to the next one 
				if (closedList.contains(neighbor))
					continue;

				//also just continue if the neighbor is an obstacle
				if (!neighbor.isObstacle) {

					// calculate how long the path is if we choose this neighbor as the next step in the path 
					float neighborDistanceFromStart = (current.getDistanceFromStart() + map.getDistanceBetween(current, neighbor));

					//add neighbor to the open list if it is not there
					if(!openList.contains(neighbor)) {
						openList.add(neighbor);
						neighborIsBetter = true;
						//if neighbor is closer to start it could also be better
					} else if(neighborDistanceFromStart < current.getDistanceFromStart()) {
						neighborIsBetter = true;
					} else {
						neighborIsBetter = false;
					}
					// set neighbors parameters if it is better
					if (neighborIsBetter) {
						neighbor.setPreviousNode(current);
						neighbor.setDistanceFromStart(neighborDistanceFromStart);
						neighbor.setHeuristicDistanceFromGoal(heuristic.getEstimatedDistanceToGoal(neighbor.getPoint(), map.getGoalPoint()));
					}
				}

			}
		}
		return null;
	}

	private LinkedList<Point> reconstructPath(Node node) {
		LinkedList<Point> path = new LinkedList<Point>();
		while(!(node.getPreviousNode() == null)) {
			path.add(0, node.getPoint());
			node = node.getPreviousNode();
		}
		this.shortestPath = path;
		return path;
	}

	/**
	 * @return the shortestPath
	 */
	public LinkedList<Point> getShortestPath() {
		return shortestPath;
	}

	/**
	 * @param shortestPath the shortestPath to set
	 */
	public void setShortestPath(LinkedList<Point> shortestPath) {
		this.shortestPath = shortestPath;
	}

	private class SortedNodeList {

		private ArrayList<Node> list = new ArrayList<Node>();

		public Node getFirst() {
			return list.get(0);
		}

		public void clear() {
			list.clear();
		}

		public void add(Node node) {
			list.add(node);
			Collections.sort(list);
		}

		public void remove(Node n) {
			list.remove(n);
		}

		public int size() {
			return list.size();
		}

		public boolean contains(Node n) {
			return list.contains(n);
		}
	}

}
