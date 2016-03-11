package tiles;

import code.Tile;

public class BuildSet {
	
	int setQnty = 10; // qnty is some number of tiles variations in the set. The current number will mostlikey change
	int pileQnty = 35;
	
	Tile[] tileSet;
	Tile[] tilePile;
	/*
	 * Only tiles that can be placed will be created here
	 */
	/**
	 * @author Andre
	 * @author Sal
	 * 
	 */
	public BuildSet() {
		tileSet = new Tile[setQnty];
		tilePile = new Tile[pileQnty];
		//tileSet[n] = new Tile("example_Tile01", bool hasUp, bool hasDown, bool hasLeft, bool hasRight, int qntyInSet);
	}
	
}
