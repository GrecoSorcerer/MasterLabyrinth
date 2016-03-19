package code;

import java.util.Iterator;

/**
 * 
 * @author Sorcerer
 * @param E
 * 
 */
public class MasterLabyrinthBoard<E> {
	
	 Tile[][] _grid;
	private Tile[] _pile;
	IteratorTrace[] validMoves;
			
	int activePlayer; //TODO work on dis
	/**
	 * @author Sal
	 */
	public MasterLabyrinthBoard(){
		long startTime = System.nanoTime()/1000000;
		_grid = new Tile[7][7];
		populate();
		playersTurn(0);
		long endTime = System.nanoTime()/1000000;
		System.out.println("\n\nCompleted in: " + (endTime - startTime)+" ms");
		//Debug Outputs
	}
	private boolean markValidMoves(int y, int x) {
		for(int i = 0; i < validMoves.length; i++) {
			if ( (validMoves[i] != null)
			&&   (x == validMoves[i].x && y == validMoves[i].y)){
				return true;
			}
		}
		return false;
	}
	private void playersTurn(int p) {
		MasterLabyrinthBoardIterator it = new MasterLabyrinthBoardIterator(0, 0);
		validMoves = it.getPaths();
		String turnMap = "┘└┌┐";
		String interMap = "┤┴├┬";
		String strMap = "│─│─";
		for(int i = 0; i < 7; i++) {
			System.out.println();
			for(int j = 0; j < 7; j++) {
				if (_grid[i][j].id == "Turn" && markValidMoves(i,j)) {
					System.out.print(turnMap.charAt(_grid[i][j].rotation));
				}
				else if (_grid[i][j].id == "Intersection" && markValidMoves(i,j)) {	
					System.out.print(interMap.charAt(_grid[i][j].rotation));
				}
				else if (_grid[i][j].id == "Straight" && markValidMoves(i,j)) {	
					System.out.print(strMap.charAt(_grid[i][j].rotation));
				}
				else {
					System.out.print("█");
				}
				
			}
		}
	}
	/**
	 * @author Sal
	 */
	private void populate() {
		BuildSet p = new BuildSet();
		
		/*
		 * TODO: Write code to randomly fill the board with
		 * tiles and player pieces.
		 *
		 */
		
		//Fill Non-moving Tiles
		//Add Fixed Turn Tiles
		_grid[0][0] = new Tile(0, null, 2);
		_grid[0][6] = new Tile(0, null, 3);
		_grid[6][0] = new Tile(0, null, 1);
		_grid[6][6] = new Tile(0, null, 0);
		
		//Add Fixed Intersection Tiles
		_grid[0][2] = new Tile(2, null, 3);
		_grid[0][4] = new Tile(2, null, 3);

		_grid[2][0] = new Tile(2, null, 2);
		_grid[2][2] = new Tile(2, null, 2);
		_grid[2][4] = new Tile(2, null, 3);
		_grid[2][6] = new Tile(2, null, 0);
		
		_grid[4][0] = new Tile(2, null, 2);
		_grid[4][2] = new Tile(2, null, 1);
		_grid[4][4] = new Tile(2, null, 0);
		_grid[4][6] = new Tile(2, null, 0);

		_grid[6][2] = new Tile(2, null, 1);
		_grid[6][4] = new Tile(2, null, 1);
		
		_pile = p.getPile();

		int x = 0, y = 0;
		for(int i = 0; i < (_pile.length - 1); i++) {

			if ( y == 7 ) {
				break;
			}
			if ( y % 2 == 0 ) {
				_grid[y][x + 1] = _pile[i];
				x += 2;
				if ( x == 6 ) {
					x = 0;
					y++;
				}
			} 
			else if(y % 2 == 1) {
				_grid[y][x] = _pile[i];
				x += 1;
				if ( x == 7 ) {
					x = 0;
					y++;
				}
			}
		}
		
		String turnMap = "┘└┌┐";
		String interMap = "┤┴├┬";
		String strMap = "│─│─";
		for(int i = 0; i < 7; i++) {
			System.out.println();
			for(int j = 0; j < 7; j++) {
				if (_grid[i][j].id == "Turn") {
					System.out.print(turnMap.charAt(_grid[i][j].rotation));
				}
				if (_grid[i][j].id == "Intersection") {	
					System.out.print(interMap.charAt(_grid[i][j].rotation));
				}
				if (_grid[i][j].id == "Straight") {	
					System.out.print(strMap.charAt(_grid[i][j].rotation));
				}
			}
		}
		System.out.println("\n");
	}
	
	/**
	 * 
	 * @author Sal
	 *
	 */
	@SuppressWarnings("unused")
	private class MasterLabyrinthBoardIterator implements Iterator<E> {
		//TODO Add Iterator {Valid Moves Iterator, etc}
		
		int itx=0, ity=0; //Position of the Iterator
		int playerX, playerY;
		
		int posOrigins;
		int posVisited;
		
		boolean visiting;
		
		IteratorTrace[] paths;
		IteratorTrace[] visited;
		
		public MasterLabyrinthBoardIterator(int playerX, int playerY) {
			paths = new IteratorTrace[64];
			visited = new IteratorTrace[64];
			posOrigins = 0;
			posVisited = 0;
			addAnOrigin(0, 0);
			visiting = false;
			this.playerX = playerX;
			this.playerY = playerY;
		}
		
		private void addAnOrigin(int itx, int ity) {
			// TODO Auto-generated method stub
			//System.out.println("Added Path: (" + itx + ", " + ity + ")" );
			paths[posOrigins++] = new IteratorTrace(itx, ity);
			
			findPath();
		}
		
		private void makeVisited(/* Pretty sure this has inputs */) {
			int tmpx = paths[posOrigins-1].x;
			int tmpy = paths[posOrigins-1].y;
			visited[posVisited++] = new IteratorTrace(tmpx, tmpy);		
			//System.out.println("visited " + itx + " " + ity);
			paths[posOrigins--] = null; //Set Visited Origin to null, then decrement
				//Move Iterator to previous origin
			if (posOrigins>0) {
				this.itx = paths[posOrigins-1].x;
				this.ity = paths[posOrigins-1].y;
			}
			
			findPath();
			
		}

		public void findPath() {
			
			if ( hasNextUp()
			&& !( isPathed(0, -1) )
			&& !( isVisitedDir(0, -1) ) ) {
				//System.out.println("Up");
				ity -= 1;
				addAnOrigin(itx,ity); //Add a new origin/path starting point and move the iterator to that point
				
			}
			else if ( hasNextRight() 
			&& !( isPathed(1, 0) ) 
			&& !( isVisitedDir(1, 0) ) ) {
				//System.out.println("Right");
				itx += 1;
				addAnOrigin(itx,ity);//Add a new origin/path starting point and move the iterator to that point
				
			}
			else if ( hasNextDown() 
			&& !( isPathed(0, 1) )
			&& !( isVisitedDir(0, 1) ) ) {
				//System.out.println("Down");
				ity += 1;
				addAnOrigin(itx,ity);//Add a new origin/path starting point and move the iterator to that point
				
			}
			else if ( hasNextLeft()
			&& !( isPathed(-1,0) ) 
			&& !( isVisitedDir(-1, 0) ) ) {
				//System.out.println("Left");
				itx -= 1;
				addAnOrigin(itx,ity);//Add a new origin/path starting point and move the iterator to that point
				
			}
			if (posOrigins > 0) {
				makeVisited();
			}
		}
		
		
		private boolean isVisitedDir(int xmod, int ymod) {
			for(int i = 0; i < posVisited; i++) {
				if ((itx + xmod) == visited[i].x && ( ity + ymod ) == visited[i].y) {
					return true;
				}
			}
			return false;
		}
		
		private boolean isPathed(int xmod, int ymod) {
			
			for(int i = 0; i < posOrigins; i++) {
				if ((itx + xmod) == paths[i].x && ( ity + ymod ) == paths[i].y) {
					return true;
				}
			}
			return false;
		}
		
		public IteratorTrace[] getPaths() {
			findPath();
			
			//makeVisited();
			System.out.println("Valid Moves: " + posVisited);
			for(int i = 0; i < posVisited; i++) {
				System.out.print("(" + visited[i].x + ", " + visited[i].y + ")");
			}
			return visited;
		}
		
		private boolean hasNextUp() {
			//System.out.println("Check Up");
			if (ity == 0) { return false; }
			return _grid[ity][itx].hasUp && _grid[ity - 1][itx].hasDown;
		}
		
		private boolean hasNextDown() {
			//System.out.println("Check Down");
			if (ity == 6) { return false; }
			return _grid[ity][itx].hasDown && _grid[ity + 1][itx].hasUp;
		}
		
		private boolean hasNextRight() {
			//System.out.println("Check Right");
			if (itx == 6) { return false; }
			return  _grid[ity][itx].hasRight && _grid[ity][itx + 1].hasLeft;
		}
		
		private boolean hasNextLeft() {
			//System.out.println("Check Left");
			if (itx == 0) { return false; }
			return _grid[ity][itx].hasLeft && _grid[ity][itx - 1].hasRight;
		}
		
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public E next() {
			// TODO Define the Next Method
			
			return null;
		}	
	}
}
