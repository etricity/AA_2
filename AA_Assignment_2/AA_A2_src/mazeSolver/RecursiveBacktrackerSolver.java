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

        //If more than one neighbour exists, the algorithm with attempt to take greediest path
        if(unvisitedNeighbours.size() > 1) {
            unvisitedNeighbours = greedySort(unvisitedNeighbours);
        }

        return unvisitedNeighbours;

    }

    //Sorts array of cells in order of distance from exit
    public ArrayList<Cell> greedySort(ArrayList<Cell> unvisitedNeighbours) {
        ArrayList<Cell> sorted = new ArrayList<>();
	    Cell bestNeighbour;
	    int numNeigghtbours = unvisitedNeighbours.size();

	    while(sorted.size() != numNeigghtbours) {
            bestNeighbour = unvisitedNeighbours.get(0);
            for(int i = 1; i < unvisitedNeighbours.size(); i++) {
                if(calculateDistanceBW(exit, unvisitedNeighbours.get(i)) < calculateDistanceBW(exit, bestNeighbour)) {
                    bestNeighbour = unvisitedNeighbours.get(i);
                }
            }
            sorted.add(bestNeighbour);
            unvisitedNeighbours.remove(bestNeighbour);
        }


        return sorted;
    }

    //Calculates distance bw 2 cells (a cell & exit)
    public double calculateDistanceBW(Cell c1, Cell c2) {

	    int x = Math.abs(c1.c - c2.c);
	    int y = Math.abs(c1.r - c2.r);

	    double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	    return distance;
    }
} // end of class RecursiveBackTrackerSolver
