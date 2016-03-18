package code;

import java.util.Random;
/**
 * 
 * @author Sal
 *
 */
public class BuildSet {
	
	int setQnty = 3; // qnty is some number of tiles variations in the set. The current number will mostlikey change
	int pileQnty;
	
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
	public void buildSet() {
		
		tileSet = new Tile[setQnty];
		
		//tileSet[n] = new Tile("example_Tile01", bool hasUp, bool hasDown, bool hasLeft, bool hasRight, int qntyInSet);
		tileSet[0] = new Tile("Turn", true, false, true, false, 15);//This is a temp int
		tileSet[1] = new Tile("Streight", true, true, false, false, 15);//This is a temp int
		tileSet[2] = new Tile("Intersection", true, true, false, true, 15);//This is a temp int
				
		pileQnty =  takeSum();
	}
	
	/**
	 * @author Sal
	 * @return out sumOfTilesInSet
	 */
	private int takeSum() {
		int out = 0;
		for ( int i = 0; i < tileSet.length; i++ ) {
			out += tileSet[i].qntyInSet;
		}
		return out;
	}
	
	/**
	 * @author Sal
	 * @return tilePile[]
	 */
	public Tile[] getPile() {
		
		buildSet();
		
		tilePile = new Tile[pileQnty];

		int pileLength = tilePile.length;
		
		Random r = new Random(); //initialize Random to r
		int typeIndex; //initialize int for _pileSet random number 1-x,
					   //where x is the number of tiles in set
		int setLength = tileSet.length;

		int count = 0;

		//While loop will continue until all tiles in set are shuffled to the Pile
		
		while (count < pileLength) {
			typeIndex = r.nextInt(setLength);
			if (tileSet[typeIndex].qntyInSet >= 1) {
				tilePile[count++] = tileSet[typeIndex];
				tilePile[count - 1].rotateMany(r.nextInt(8));
				tileSet[typeIndex].qntyInSet--;
			}
		}
		
		for(int i = 0; i < tilePile.length; i++ )
			System.out.print("["+ i + "|" + tilePile[i].id + "]");
		
		return tilePile;

	}

}
