package code;

import java.util.Random;
/**
 * 
 * @author Sal
 *
 */
public class BuildSet {
	
	private int setQnty = 3; // qnty is some number of tiles variations in the set. The current number will mostlikey change
	private int pileQnty;
	
	private Tile[] tileSet;
	private Tile[] tilePile;
	/*
	 * Only tiles that can be placed will be created here
	 */
	
	/**
	 * @author Andre
	 * @author Sal
	 * 
	 */
	private void buildSet() {
		
		tileSet = new Tile[setQnty];
		
		tileSet[0] = new Tile(0, 16);
		tileSet[1] = new Tile(1, 12);
		tileSet[2] = new Tile(2, 6);
				
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
		/*
		 *TODO
		 *Make more randomization passes(Atleast 1-2)
		 * 
		 */
		while (count < pileLength) {
			typeIndex = r.nextInt(setLength);
			if (tileSet[typeIndex].qntyInSet >= 1) {
				tilePile[count++] = tileSet[typeIndex];
				tilePile[count - 1].rotateMany(r.nextInt(8));
				tileSet[typeIndex].qntyInSet--;
			}
		}
		
		//tileSet = null;
		
		/*
		for(int i = 0; i < tilePile.length; i++ )
			System.out.print("["+ i + "|" + tilePile[i].id + "]");
		*/
		
		return tilePile;
	}

}
