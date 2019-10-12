package mazeGenerator;

import maze.Cell;
import maze.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@SuppressWarnings("Duplicates")
public class HuntAndKillGenerator implements MazeGenerator {

    ArrayList<Cell> unvisited = new ArrayList<>();
    ArrayList<Cell> visited = new ArrayList<>();

	@Override
	public void generateMaze(Maze maze) {


	    //Loading all cells into unvisited
	    for(int i = 0; i < maze.sizeR;i++) {
	        for(int j = 0; j < maze.sizeC; j++) {
	            unvisited.add(maze.map[i][j]);
            }
        }

        Cell current = getRandomCell(unvisited);
	    Cell next = null;


	    unvisited.remove(current);
	    visited.add(current);


	    //While unvisited cells exist
	    while(visited.size() < (maze.sizeC * maze.sizeR)) {

	        if(current.tunnelTo != null) {

	            next = current.tunnelTo;
	            current = next;
                unvisited.remove(current);
                visited.add(current);

            }

	        //IF current has unvisited neighbours
            ArrayList<Cell> unvisitedNeighbours = getUnvistedNeighbours(current);
	        if(unvisitedNeighbours.size() > 0) {

	            //Choose a random unvisited neighbour of current
	            next = getRandomCell(unvisitedNeighbours);

	            //Remove wall b/w current & next
	            addPathBetween(current, next);

	            //Update current
	            current = next;
	            unvisited.remove(current);
	            visited.add(current);

            } else {
                current = getNextStartingCell();
                    Cell visitedNeighbour = getRandomCell(getVistedNeighbours(current));
                    addPathBetween(current, visitedNeighbour);
                    unvisited.remove(current);
                    visited.add(current);
            }

        }
    }

    public Cell getRandomCell(ArrayList<Cell> cells) {

        Random random = new Random();
        int index = random.nextInt(cells.size());

	    return cells.get(index);
    }

    public ArrayList<Cell> getUnvistedNeighbours(Cell cell) {
	    ArrayList<Cell> unvisitedNeighbours = new ArrayList<>();

	    //neigh[0] EAST
	    if(unvisited.contains(cell.neigh[0])) {
	        unvisitedNeighbours.add(cell.neigh[0]);
        }

        //neigh[2] NORTH
        if(unvisited.contains(cell.neigh[2])) {
            unvisitedNeighbours.add(cell.neigh[2]);
        }

        //neigh[3] WEST
        if(unvisited.contains(cell.neigh[3])) {
            unvisitedNeighbours.add(cell.neigh[3]);
        }

        //neigh[5] SOUTH
        if(unvisited.contains(cell.neigh[5])) {
            unvisitedNeighbours.add(cell.neigh[5]);
        }

        return unvisitedNeighbours;

    }

    public ArrayList<Cell> getVistedNeighbours(Cell cell) {
        ArrayList<Cell> visitedNeighbours = new ArrayList<>();

        //neigh[0] EAST
        if(visited.contains(cell.neigh[0])) {
            visitedNeighbours.add(cell.neigh[0]);
        }

        //neigh[2] NORTH
        if(visited.contains(cell.neigh[2])) {
            visitedNeighbours.add(cell.neigh[2]);
        }

        //neigh[3] WEST
        if(visited.contains(cell.neigh[3])) {
            visitedNeighbours.add(cell.neigh[3]);
        }

        //neigh[5] SOUTH
        if(visited.contains(cell.neigh[5])) {
            visitedNeighbours.add(cell.neigh[5]);
        }

        return visitedNeighbours;

    }

    public void addPathBetween(Cell current, Cell next) {
	    int xDiff = next.c - current.c;
	    int yDiff = next.r - current.r;

      //  wall[0] EAST, wall[2] NORTH, wall[3] WEST, wall[5] SOUTH

        //current is WEST of next
	    if(xDiff == 1) {
	        current.wall[0].present = false;
	        //current is EAST of next
        } else if(xDiff == -1) {
            current.wall[3].present = false;
	        //current is NORTH of next
        } else if(yDiff == 1) {
            current.wall[2].present = false;
         //   next.wall[0].present = false;
	        //current is SOUTH of next
        } else if(yDiff == -1) {
            current.wall[5].present = false;
         //   next.wall[3].present = false;
        }
    }

    public Cell getNextStartingCell() {
	    Cell startingCell = null;
	    ArrayList<Cell> visitedNeighbours;
        Collections.shuffle(unvisited);

	    for(Cell c: unvisited) {
	        visitedNeighbours = getVistedNeighbours(c);

	        if(visitedNeighbours.size() > 0) {
	            startingCell = c;
	            break;
            }
        }
        return startingCell;

    }

} // end of class HuntAndKillGenerator


