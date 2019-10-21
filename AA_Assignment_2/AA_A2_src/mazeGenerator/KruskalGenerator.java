package mazeGenerator;

import maze.Cell;
import maze.Maze;
import maze.Wall;

import java.util.ArrayList;
import static java.util.Collections.shuffle;

public class KruskalGenerator implements MazeGenerator {

	private Maze maze;
	private ArrayList<ArrayList<Cell>> cellTrees = new ArrayList<>();
	private ArrayList<Wall> walls = new ArrayList<>();

	@Override
	public void generateMaze(Maze maze) {
		this.maze=maze;
		//fill cellTree arrayList with arrayLists each containing one cell
		getAllCells();
		//fill walls arrayList
		getAllWalls();

		Cell cell = new Cell();
		Cell neighbourCell = new Cell();
		boolean north;
		boolean west;
		Wall wall;

		//loop until all walls have been checked
		while(!walls.isEmpty()){
			//take wall from end of arrayList
			wall = walls.get(walls.size()-1);
			north = false;
			west = false;

			//find the cells that share the wall
			for(int i = 0; i<maze.sizeR; i++){
				for(int j = 0; j<maze.sizeC; j++){
					//west wall
					if(wall.equals(maze.map[i][j].wall[3])){
						cell = maze.map[i][j];
						neighbourCell = maze.map[i][j-1];
						west = true;
						//north wall
					}else if(wall.equals(maze.map[i][j].wall[2])){
						cell = maze.map[i-1][j];
						neighbourCell = maze.map[i][j];
						north = true;
					}
				}
			}

			//if edge joins two unjoined trees, join them
			boolean remove = true;
			ArrayList<Cell> cellArrayList = new ArrayList<>();
			ArrayList<Cell> neighbourCellArrayList = new ArrayList<>();
			for(int i = 0; i<cellTrees.size(); i++) {
				for (int j = 0; j < cellTrees.get(i).size(); j++) {
					//if cell and neighbourCell are already in the same arrayList, do not remove the wall
					if(cellTrees.get(i).contains(cell)&&cellTrees.get(i).contains(neighbourCell)){
						remove = false;
					}else {
						//get arrayList containing cell
						if (cellTrees.get(i).contains(cell) && !cellTrees.get(i).contains(neighbourCell)) {
							cellArrayList = cellTrees.get(i);
						}
						//get arrayList containing neighbourCell
						if (!cellTrees.get(i).contains(cell) && cellTrees.get(i).contains(neighbourCell)) {
							neighbourCellArrayList = cellTrees.get(i);
						}
					}
				}
			}

			//if tunnel entrance/exit exists in cellArrayList and tunnel entrance/exists in neighbourArrayList, do not join
			//this avoids loops in the maze
			ArrayList<Cell> tunnelCells = new ArrayList<>();
			for(int i=0;i<cellArrayList.size();i++){
				if(cellArrayList.get(i).tunnelTo!=null){
					tunnelCells.add(cellArrayList.get(i).tunnelTo);
				}
			}
			for(int i=0;i<neighbourCellArrayList.size();i++){
				for(int j=0;j<tunnelCells.size();j++) {
					if (neighbourCellArrayList.get(i).equals(tunnelCells.get(j))) {
						remove = false;
					}
				}
			}

			if(remove == true) {
				//create arrayList to merge cell and neighbourCell arrayLists into
				ArrayList<Cell> newCellArrayList = new ArrayList<>();
				for (int i = 0; i < cellArrayList.size(); i++) {
					newCellArrayList.add(cellArrayList.get(i));
				}
				for (int i = 0; i < neighbourCellArrayList.size(); i++) {
					newCellArrayList.add(neighbourCellArrayList.get(i));
				}
				//add newCellArrayList to cellTrees ArrayList
				cellTrees.add(newCellArrayList);
				//remove relevant wall
				if(west==true) {
					cell.wall[3].present = false;
				} else if(north==true) {
					cell.wall[2].present = false;
				}
			}
			//remove wall from end of arrayList
			walls.remove(walls.size() - 1);
		}
	} // end of generateMaze()

	//fills an arrayList with arrayLists each holding one cell
	public void getAllCells(){
		for(int i = 0; i<maze.sizeR; i++){
			for(int j = 0; j<maze.sizeC; j++){
				//create new arrayList
				ArrayList<Cell> cells = new ArrayList<>();
				//add cell to arrayList
				cells.add(maze.map[i][j]);
				//add cells arrayList to cellTrees arrayList
				cellTrees.add(cells);
			}
		}
	}

	//fills an arrayList with edges
	public void getAllWalls(){
		for(int i = 0; i<maze.sizeR; i++){
			for(int j = 0; j<maze.sizeC; j++){
				if(maze.map[i][j].c!=0) {
					//add west wall
					walls.add(maze.map[i][j].wall[3]);
				}
				if(maze.map[i][j].r!=0) {
					//add north wall
					walls.add(maze.map[i][j].wall[2]);
				}
			}
		}
		shuffle(walls);
	}

} // end of class KruskalGenerator