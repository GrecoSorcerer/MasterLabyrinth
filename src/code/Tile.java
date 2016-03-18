package code;
/**
 * 
 * @author Sal
 *
 */
public class Tile {
	
	String id;
	String tleImg;
	
	int playerRegistryPos;
	Player[] playerRegistry = new Player[4];
	
	//int x, y; //Use if needed for iterator
	boolean hasPlayer = false;
	boolean hasUp;
	boolean hasDown;
	boolean hasLeft;
	boolean hasRight;
	
	int qntyInSet;

	/*
	 * Note, that placement may or may not be handled outside, 
	 * and the default of this value is subject to change
	 */
	boolean isPlaced = false; 
	
	public Tile() {
		System.out.println("!WARNING A BLANK TILE WAS CREATED!");
	}
	
	public Tile(String id, boolean hasUp, boolean hasDown, boolean hasLeft, boolean hasRight, int qntyInSet) {
		define(id, hasUp, hasDown, hasLeft, hasRight, qntyInSet);
	}
	
	private void define(String id, boolean hasUp, boolean hasDown, boolean hasLeft, boolean hasRight, int qntyInSet) {
		this.id = id;
		this.hasUp = hasUp;
		this.hasDown = hasDown;
		this.hasLeft = hasLeft;
		this.hasRight = hasRight;
		this.tleImg = id + ".png";
		this.qntyInSet = qntyInSet;
	}
	
	public void setPlaced() {
		//When a tile is added to or removed from the board, the boolen isPlaced will change.
	}
	
	public void rotateMany(int rotations) {
		for (int i = 0; i < rotations; i++) {
			rotate();
		}
	}
	public void rotate() {
		if (!isPlaced) {
			boolean tempDir = hasUp;
			hasUp = hasRight;
			hasRight = hasDown;
			hasDown = hasLeft;
			hasLeft = tempDir;
			//rotateImage(this.Tile)
			//Add code to project rotated image when builing GUIs
		}
	}

}
