package code;

import java.util.Iterator;

import org.junit.Ignore;

/**
 * 
 * @author Sorcerer
 * @param E
 * 
 */
public class MasterLabyrinthBoard<E> {
	
	Tile[][] _grid;
	
	Tile _extraTile;
	String extraTile;

	private Tile[] _pile;
	IteratorTrace[] validMoves;
	
	int players;
	int activePlayer = 0; //TODO Add basic player functionality
	/**
	 * @author Sal
	 */
	public MasterLabyrinthBoard(String[] args){
		long startTime = System.currentTimeMillis();
		players = args.length;
		//buildPlayers();
		_grid = new Tile[7][7];
		populate();
		playersTurn(0);
		long endTime = System.currentTimeMillis();
		System.out.println("\n\nCompleted in: " + (endTime - startTime)+" ms");
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
		MasterLabyrinthBoardIterator it = new MasterLabyrinthBoardIterator(4,3);
		
		validMoves = it.getPaths();
		
		String turnMap = "┘└┌┐";
		String interMap = "┤┴├┬";
		String strMap = "│─│─";
		/*
		String _turnMap = "╝╚╔╗";
		String _interMap = "╣╩╠╦";
		String _strMap = "║═║═";
		*/
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
					/*
					if (_grid[i][j].id == "Turn") {
						System.out.print(_turnMap.charAt(_grid[i][j].rotation));
					}
					if (_grid[i][j].id == "Intersection") {	
						System.out.print(_interMap.charAt(_grid[i][j].rotation));
					}
					if (_grid[i][j].id == "Straight") {	
						System.out.print(_strMap.charAt(_grid[i][j].rotation));
					}
					*/
					System.out.print("▒");
				}
			}
		}
	}
	/**
	 * @author Sal
	 * Adds tiles to board at the start of the game.
	 * 
	 */
	private void populate() {
		BuildSet p = new BuildSet();
		
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
		
		String turnMap = "╝╚╔╗";
		String interMap = "╣╩╠╦";
		String strMap = "║═║═";
		
		_extraTile = _pile[_pile.length - 1];
		//This is just Some basic view information. It's gonna get moved when tile insertions are added
		if (_extraTile.id == "Turn") {
			System.out.print("► " + turnMap.charAt(_extraTile.rotation) + "\n");
		}
		if (_extraTile.id == "Intersection") {	
			System.out.print("► " + interMap.charAt(_extraTile.rotation) + "\n");
		}
		if (_extraTile.id == "Straight") {	
			System.out.print("► " + strMap.charAt(_extraTile.rotation) + "\n");
		}
		
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
	 * @author Salvatore
	 * A reworked use of the Iterator interface used to
	 * find all valid paths from a players location
	 * 
	 * This implementation was written in entirety by it's author.
	 * 
	 * @category Path Iterator
	 * @version v1.0
	 */
	private class MasterLabyrinthBoardIterator implements Iterator<E> {
		
		int itx, ity; //Position of the Iterator
		int posOrigins;
		int posVisited;
		
		IteratorTrace[] paths;
		IteratorTrace[] visited;
		public MasterLabyrinthBoardIterator() {
			paths = new IteratorTrace[64];
			visited = new IteratorTrace[64];
			posOrigins = 0;
			posVisited = 0;
			addAnOrigin(0, 0);
			itx = 0;
			ity = 0;
		}
		//TODO make use of this when the time comes to add players
		public MasterLabyrinthBoardIterator(int playerX, int playerY) {
			paths = new IteratorTrace[64];
			visited = new IteratorTrace[64];
			posOrigins = 0;
			posVisited = 0;
			itx = playerX;
			ity = playerY;
			addAnOrigin(itx, ity);
		}
		
		private void addAnOrigin(int itx, int ity) {
			//System.out.println("Added Path: (" + itx + ", " + ity + ")" );
			paths[posOrigins++] = new IteratorTrace(itx, ity);
			//System.out.println(itx + "," + ity);
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
		
		@Ignore
		public boolean hasNext() {
			//Functionality taken by methods: hasNextUp(), hasNextDown(), hasNextLeft(), and hasNextRight()
			return false;
		}

		@Ignore
		public E next() {
			//Functionality taken by getPaths() method
			return null;
		}	
	}
}
