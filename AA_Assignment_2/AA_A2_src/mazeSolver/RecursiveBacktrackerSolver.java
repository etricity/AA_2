package mazeSolver;

import maze.Cell;
import maze.Maze;

import java.util.ArrayList;


@SuppressWarnings("Duplicates")
/**
 * Implements the recursive backtracking maze solving algorithm.
 */
public class RecursiveBacktrackerSolver implements MazeSolver {

	ArrayList<Cell> path = new ArrayList<>();
	ArrayList<Cell> visited = new ArrayList<>();
	ArrayList<Cell> unvisited = new ArrayList<>();
    Cell enterence;
    Cell exit;

	@Override
	public void solveMaze(Maze maze) {

	    enterence = maze.entrance;
	    exit = maze.exit;

        //Loading all cells into unvisited
        for(int i = 0; i < maze.sizeR;i++) {
            for(int j = 0; j < maze.sizeC; j++) {
                unvisited.add(maze.map[i][j]);
            }
        }


	    Cell enterence = maze.entrance;

	    if(explore(maze, enterence, path)) {

	        for(int i = 0; i < path.size(); i++) {
                maze.drawFtPrt(path.get(i));
            }

        }

	} // end of solveMaze()


	@Override
	public boolean isSolved() {

	    if(path.get(0) == enterence && path.get(path.size() - 1) == exit) {
	        return true;
        } else {
            return false;
        }
	} // end if isSolved()


	@Override
	public int cellsExplored() {
		return visited.size();
	} // end of cellsExplored()


    public boolean explore(Maze maze, Cell cell, ArrayList<Cell> path) {

	    if(visited.contains(cell)) {
	        return false;
        }

        path.add(cell);
	    visited.add(cell);
	    unvisited.remove(cell);
	    if(isSolved()) {
	        return true;
        }

        if(cell.tunnelTo != null) {
            cell = cell.tunnelTo;
            path.add(cell);
            visited.add(cell);
            unvisited.remove(cell);
        }

	    for(Cell c: getUnivistedValidNeighbour(cell)) {

            if(explore(maze, c, path)) {
                return true;
            }

        }

        path.remove(path.size() - 1);
	    return false;
    }

    public ArrayList<Cell> getUnivistedValidNeighbour(Cell cell) {

        ArrayList<Cell> unvisitedNeighbours = new ArrayList<>();

        //neigh[0] EAST
        if(unvisited.contains(cell.neigh[0]) && !cell.wall[0].present) {
            unvisitedNeighbours.add(cell.neigh[0]);
        }

        //neigh[2] NORTH
        if(unvisited.contains(cell.neigh[2]) && !cell.wall[2].present) {
            unvisitedNeighbours.add(cell.neigh[2]);
        }

        //neigh[3] WEST
        if(unvisited.contains(cell.neigh[3]) && !cell.wall[3].present) {
            unvisitedNeighbours.add(cell.neigh[3]);
        }

        //neigh[5] SOUTH
        if(unvisited.contains(cell.neigh[5]) && !cell.wall[5].present) {
            unvisitedNeighbours.add(cell.neigh[5]);
        }

        return unvisitedNeighbours;

    }

} // end of class RecursiveBackTrackerSolver
