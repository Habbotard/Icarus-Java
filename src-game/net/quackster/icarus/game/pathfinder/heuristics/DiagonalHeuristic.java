package net.quackster.icarus.game.pathfinder.heuristics;

import net.quackster.icarus.game.room.models.Point;

/**
 * Calculate the diagonal distance to goal when 
 * a straight step costs 1 and diagonal step costs sqrt(2).
 */
public class DiagonalHeuristic implements AStarHeuristic {

	public float getEstimatedDistanceToGoal(Point start, Point goal) {		
		
		float h_diagonal = (float) Math.min(Math.abs(start.getX() - goal.getX()), Math.abs(start.getY() - goal.getY()));
		float h_straight = (float) (Math.abs(start.getX() - goal.getX()) + Math.abs(start.getY() - goal.getY()));
		float h_result = (float) (Math.sqrt(2) * h_diagonal + (h_straight - 2*h_diagonal));
		
		/**
		 * Breaking ties: Adding a small value to the heuristic to avoid A* fully searching all equal length paths
		 * We only want 1 shortest path, not all of them.
		 * 
		 * @param p The small value we add to the heuristic. Should be p < (minimum cost of taking one step) / (expected maximum path length) to avoid 
		 */
		
		float p = (1/10000);
		h_result *= (1.0 + p);
		
		return h_result;
	}

}
