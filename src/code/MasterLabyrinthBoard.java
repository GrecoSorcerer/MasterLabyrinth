package code;

import java.util.Iterator;
import java.util.Random;

import org.junit.Ignore;

/**
 * 
 * @author Salvatore
 *
 * @param <E> useless?
 * @version v1.5
 * 
 * 
 */
public class MasterLabyrinthBoard<E> {
	
	Tile[][] _grid;
	
	Tile _extraTile;
	String extraTile;

	private Tile[] _pile;
	IteratorTrace[] validMoves;
	
	private int maxTurn = 16;
	int turns = 0;
	
	Player[] _players = new Player[4];
	int players;
	int activePlayer = 0; 

	@SuppressWarnings("unused")
	private int[] moves; //This is used for debug movement of players each turn
	
	/**
	 * @author Salvatore
	 * @param preLoading a pre-made board-state
	 * @param extraTile the extra tile for shifting the board
	 * @param players the players
	 * @param moves the moves being made
	 * 
	 * Sets players, loads a grid, and calls playersTurn()
	 * 
	 */
	public MasterLabyrinthBoard(Tile[][] preLoading, Tile extraTile, String[] players, roundHistory[] moves) {
		this.players = players.length;
		_grid = preLoading;
		playersTurn(0); // Defaulted to zero, because games
	}
	
	/**
	 * @author Salvatore
	 * @param args used to load player and perhaps move data in the future
	 * 
	 * Makes players, builds grid, fills grid--populate()-- then calls playersTun
	 * 
	 */
	public MasterLabyrinthBoard(String[] args){
		long startTime = System.currentTimeMillis();
		buildPlayers(args);
		_grid = new Tile[7][7];
		populate();
		reloadVisuals();
		playersTurn(0);
		long endTime = System.currentTimeMillis();
		System.out.println("\n\nCompleted in: " + (endTime - startTime)+" ms");
		System.out.println(_players[0].name);
	}
	
	private void buildPlayers(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].contains("Name=") && players < 4){
				String temp = args[i].replace("Name=", "");
				System.out.println(temp + " joined the game!");
				_players[i] = new Player(temp, players++);
			}
			if (args[i].contains("maxTurns=")) {
				String temp = args[i].replace("maxTurns=", "");
				maxTurn = Integer.parseInt(temp);
			}
		}
		
		if (players >= 1) {
			_players[0].setPos(0, 0);
		}
		if (players >= 2) {
			_players[1].setPos(6, 0);
		}
		if (players >= 3) {
			_players[2].setPos(0, 6); 
		}
		if (players == 4) {
			_players[3].setPos(6, 6);
		}
	}

	/**
	 * @author Salvatore
	 * 
	 * A debug/visual output method to print paths to console
	 *
	 */
	private boolean markValidMoves(int y, int x) {
		if (validMoves != null) {
			for(int i = 0; i < validMoves.length; i++) {
				if ( (validMoves[i] != null)
				&&   (x == validMoves[i].x && y == validMoves[i].y)){
					return true;
				}
			}
		return false;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @author Salvatore
	 * @param p player 0, 1, 2, or 3
	 * 
	 * This method handles all players turns
	 * 
	 * For the purpose of allowing the user story to run for Stage 1, 
	 * I've implemented temporary maxTurns and turns variables
	 * 
	 */
	private void playersTurn(int p) {
		Random r = new Random();
		
		String insert = ""; // USed as a random insert for userstory
		insert += (char) ('A' + r.nextInt(11));
		insert += r.nextInt(8);
		//System.out.println("Inserting at: " + insert);
		shiftBoard(insert);
		
		int x = _players[activePlayer].x, y = _players[activePlayer].y;
		MasterLabyrinthBoardIterator it = new MasterLabyrinthBoardIterator(x,y);
		
		validMoves = it.getPaths();
		
		int validMoveSel = r.nextInt(it.validSize);
		_players[activePlayer].x = validMoves[validMoveSel].x;
		_players[activePlayer].y = validMoves[validMoveSel].y;
		System.out.println(_players[activePlayer].name + " to: " + "("+ _players[activePlayer].x +","+ _players[activePlayer].y +")");
		reloadVisuals();
		
		while (turns <= maxTurn) { 
			turns++;
			if (activePlayer < players-1) { activePlayer++; } 
			else if (activePlayer == players-1) { activePlayer = 0; }
			playersTurn(activePlayer); 
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

		_extraTile = _pile[_pile.length - 1];
		
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
	}
	
	/**
	 * @author Salvatore
	 * 
	 * prints board to console
	 * 
	 */
	private void reloadVisuals() {
		
		System.out.println("\n");
		
		String playersMap = "♠♥♣♦";
		
		String turnMap = "╝╚╔╗";
		String interMap = "╣╩╠╦";
		String strMap = "║═║═";
		
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
		
		turnMap = "┘└┌┐";
		interMap = "┤┴├┬";
		strMap = "│─│─";
		
		String _turnMap = "╝╚╔╗";
		String _interMap = "╣╩╠╦";
		String _strMap = "║═║═";

		for(int i = 0; i < 7; i++) {
			System.out.println();
			for(int j = 0; j < 7; j++) {
				if (_players[activePlayer].x == j && _players[activePlayer].y == i) {
					System.out.print(playersMap.charAt(activePlayer));
				}
				else {
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
						
						if (_grid[i][j].id == "Turn") {
							System.out.print(_turnMap.charAt(_grid[i][j].rotation));
						}
						if (_grid[i][j].id == "Intersection") {	
							System.out.print(_interMap.charAt(_grid[i][j].rotation));
						}
						if (_grid[i][j].id == "Straight") {	
							System.out.print(_strMap.charAt(_grid[i][j].rotation));
						}
						
						//System.out.print("▒");
					}
				}
			}
		}
		System.out.println("\n");
	}
	
	/**
	 * @author Ryan
	 * @author Salvatore
	 * @param pos
	 */
	private void shiftBoard(String pos) {
		String _pos = "" + pos.charAt(0);
		String _rotate = "" + pos.charAt(1);
//		int opposite = 0;
		
//		int setIncrement = 0;
		int x = 0, y = 0;
		
		int rotate = Integer.parseInt(_rotate);
		_extraTile.rotateMany(rotate);
		
		_pos.toUpperCase();
		
		Tile temp = null;
		
		//This Switch statement establishes parameters for the generic shifter loops
		switch(_pos) {
			
			case "A":	x = 1;
						temp = shiftTopDown(x, y);
			break;
			
			case "B":	x = 3;
						temp = shiftTopDown(x, y);			
			break;
				
			case "C":	x = 5;
					 	temp = shiftTopDown(x, y);
			break;
			case "I":	x = 1;
						y = 6;
						temp = shiftBottomUp(x, y);
		  	break;
		  	//Pattern continues
		  	case "H":	x = 3;
		  				y = 6;
		  				temp = shiftBottomUp(x, y);
			break;
	
			case "G":	x = 5;
						y = 6;
						temp = shiftBottomUp(x, y);
			break;

			case "L":	y = 1;
						temp = shiftLeftRight(x, y);
			break;
			
			case "K":	y = 3;
						temp = shiftLeftRight(x, y);
			break;
				
			case "J":	y = 5;
						temp = shiftLeftRight(x, y);
			break;
			case "D":	y = 1;
						x = 6;
						temp = shiftRigtLeft(x, y);
			break;
			
			case "E":	y = 3;
						x = 6;
						temp = shiftRigtLeft(x, y);
			break;
			
			case "F":	y = 5;
						x = 6;
						temp = shiftRigtLeft(x, y);
			break;
		}
		_grid[y][x] = _extraTile;
		_extraTile = temp;
	}
	/**
	 * @author Ryan
	 * @author Salvatore
	 * @param x
	 * @param y
	 * @return
	 */
	private Tile shiftRigtLeft(int x, int y) {
		Tile temp = _grid[y][6];
		//System.out.println("\nCopy:" + temp.id + ", " + temp.rotation);
		for (int i = 5; i >= 0; i -= 1) {
			//System.out.println("Move: " + (i+1) + "]" + _grid[i+1][x].id + ", " + _grid[i+1][x].rotation + " << " + (i) + "]" + _grid[i][x].id + ", " + _grid[i][x].rotation);
			_grid[y][i+1] = _grid[y][i];
		}
		return temp;
	}
	/**
	 * @author Ryan
	 * @author Salvatore
	 * @param x
	 * @param y
	 * @return
	 */
	private Tile shiftLeftRight(int x, int y) {
		Tile temp = _grid[y][6];
		//System.out.println("\nCopy:" + temp.id + ", " + temp.rotation);
		for (int i = x-1; i >= 0; i -= -1) {
			//System.out.println("Move: " + (i+1) + "]" + _grid[i+1][x].id + ", " + _grid[i+1][x].rotation + " << " + (i) + "]" + _grid[i][x].id + ", " + _grid[i][x].rotation);
			_grid[y][i+1] = _grid[y][i];
		}
		return temp;
		
	}
	/**
	 * @author Ryan
	 * @author Salvatore
	 * @param x
	 * @param y
	 * @return
	 */
	private Tile shiftBottomUp(int x, int y) {
		Tile temp = _grid[6][x];
		//System.out.println("\nCopy:" + temp.id + ", " + temp.rotation);
		for (int i = y-1; i >= 0; i += -1) {
			//System.out.println("Move: " + (i+1) + "]" + _grid[i+1][x].id + ", " + _grid[i+1][x].rotation + " << " + (i) + "]" + _grid[i][x].id + ", " + _grid[i][x].rotation);
			_grid[i+1][x] = _grid[i][x];
		}
		return temp;
	}
	/**
	 * @author Ryan
	 * @author Salvatore
	 * @param x
	 * @param y
	 * @return
	 */
	private Tile shiftTopDown(int x, int y) {
		Tile temp = _grid[6][x];
		//System.out.println("\nCopy:" + temp.id + ", " + temp.rotation);
		for (int i = 5; i >= 0; i -= 1) {
			//System.out.println("Move: " + (i+1) + "]" + _grid[i+1][x].id + ", " + _grid[i+1][x].rotation + " << " + (i) + "]" + _grid[i][x].id + ", " + _grid[i][x].rotation);
			_grid[i+1][x] = _grid[i][x];
		}
		return temp;
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
		int validSize = 0;
		
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
		/**
		 * 
		 * @param playerX
		 * @param playerY
		 */
		public MasterLabyrinthBoardIterator(int playerX, int playerY) {
			System.out.println(_players[activePlayer].name+": (" + playerX + "," + playerY + ")");
			paths = new IteratorTrace[49];
			visited = new IteratorTrace[49];
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
			validSize++;
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
			System.out.println("=================================\n"+ "Turn:" + turns + "\n" + "Valid Moves: " + posVisited);
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
