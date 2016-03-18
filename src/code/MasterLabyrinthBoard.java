package code;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 
 * @author Sorcerer
 * @param E
 * 
 */
public class MasterLabyrinthBoard<E> {
	
	private Tile[][] _grid;
	
	int activePlayer; //TODO work on dis
	/**
	 * @author Sal
	 */
	public MasterLabyrinthBoard(){
		_grid = new Tile[7][7];
		populate();
		//Debug Outputs
		System.out.println(_grid[2][0].tleImg + _grid[2][0].id);
		System.out.println(_grid[1][0].tleImg + _grid[1][0].id);
		System.out.println(_grid[0][0].tleImg + _grid[0][0].id);
	}
	/**
	 * @author Sal
	 */
	private void populate() {
		/*
		 * TODO:
		 * 
		 * White code to randomly fill the board 
		 * with tiles and player pieces.
		 *  >>Define Class containing Tile Blueprints
		 *
		 */
		
		//Generate a Debug "Tile"
		_grid[0][0] = new Tile("0/0", true, true, false, false, 2);
		_grid[1][0] = new Tile("1/0", true, true, false, false, 1);
		_grid[2][0] = new Tile("2/0", true, true, false, false, 3);
	}
	/**
	 * 
	 * @author Sal
	 *
	 */
	@SuppressWarnings("unused")
	private class MasterLabyrinthBoardIterator implements Iterator<E> {
		//TODO Add Iterator {Valid Moves Iterator, etc}
		
		int itx, ity; //Position of the Iterator
		
		int posOrigins;
		int posVisited;
		
		IteratorTrace[] origins;
		IteratorTrace[] visited;
		
		public MasterLabyrinthBoardIterator() {
			origins = new IteratorTrace[25];
			visited = new IteratorTrace[25];
			posOrigins = 0;
			posVisited = 0;
		}
		
		private void addAnOrigin(int itx, int ity) {
			// TODO Auto-generated method stub
			
			origins[posOrigins++] = new IteratorTrace(itx, ity);
			
		}
		
		private void makeVisited(/* Pretty sure this has inputs */) {
			visited[posVisited++] = origins[posOrigins];
			origins[posOrigins--] = null; //Set Visited Origin to null, then decrement
			
			//Move Iterator to previous origin
			this.itx = origins[posOrigins].x;
			this.ity = origins[posOrigins].y;
		}
		
		public void checkNext() {
			if ( _grid[itx][ity].hasUp && _grid[itx][ity + 1].hasDown 
			&& !( itx == origins[posOrigins].x && ( ity + 1 ) == origins[posOrigins].y )
			&& !( itx == visited[posVisited].x && ( ity + 1 ) == visited[posVisited].y ) ) {
				addAnOrigin(itx,ity++); //Add a new origin/path starting point and move the iterator to that point
				
			}
			if ( _grid[itx][ity].hasRight && _grid[itx + 1][ity].hasLeft 
			&& !( ( itx + 1 ) == origins[posOrigins].x && ity == origins[posOrigins].y ) 
			&& !( ( itx + 1 ) == visited[posVisited].x && ity == visited[posVisited].y ) ) {
				addAnOrigin(itx++,ity);//Add a new origin/path starting point and move the iterator to that point
				
			}
			if ( _grid[itx][ity].hasDown && _grid[itx][ity - 1].hasUp 
			&& !( itx == origins[posOrigins].x && ( ity - 1 ) == origins[posOrigins].y )
			&& !( itx == visited[posVisited].x && ( ity - 1 ) == visited[posVisited].y ) ) {
				addAnOrigin(itx,ity--);//Add a new origin/path starting point and move the iterator to that point
				
			}
			if ( _grid[itx][ity].hasLeft && _grid[itx - 1][ity].hasRight
			&& !( ( itx - 1 ) == origins[posOrigins].x && ity == origins[posOrigins].y ) 
			&& !( ( itx - 1 ) == visited[posVisited].x && ity == visited[posVisited].y ) ) {
				addAnOrigin(itx--,ity);//Add a new origin/path starting point and move the iterator to that point
				
			}
			else {
				makeVisited();
			}
		}
		
		@Override
		public boolean hasNext() {
			// TODO Define the Has Next Method
			return false;
		}

		@Override
		public E next() {
			// TODO Define the Next Method
			
			return null;
		}	
	}
}
