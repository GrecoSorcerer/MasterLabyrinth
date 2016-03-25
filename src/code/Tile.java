package code;
/**
 * 
 * @author Sal
 *
 */
public class Tile {
	
	String id;
	
	int playerRegistryPos = 0; //keeps a refrence of the next avalible space in the hasPlayer array
	public int[] hasPlayer = new int[4]; //Keeps a refrence of all players on this tile
	
	//int x, y; //Use if needed for iterator
	//boolean hasPlayers = false;
	int rotation = 0;
	
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
	/**
	 * @author Sal
	 * @param id 0 = Turn, 1 = Straight, 2 = Intersection
	 * @param ignored Ignored, because constructor issues
	 * @param orientation
	 */
	public Tile(int idNum, String ignored, int orientation) {
		define(idNum, 0);
		rotateMany(orientation);
	}
	
	public Tile(int idNum, int qntyInSet) {
		define(idNum, qntyInSet);
	}
	private void define(int idNum, int qntyInSet) {
		if (idNum == 0) {
			hasUp = true;
			hasDown = false;
			hasLeft = true;
			hasRight = false;
			id = "Turn";
		}
		if (idNum == 1) {
			hasUp = true;
			hasDown = true;
			hasLeft = false;
			hasRight = false;
			id = "Straight";
		}
		if (idNum == 2) {
			hasUp = true;
			hasDown = true;
			hasLeft = true;
			hasRight = false;
			id = "Intersection";
		}
		this.qntyInSet = qntyInSet;
	}
	
	public void setPlaced() {
		//When a tile is added to or removed from the board, the boolen isPlaced will change.
	}
	
	protected void addPlayer(int player) {
		hasPlayer[playerRegistryPos++] = player;
	}
	
	public void rotateMany(int rotations) {
		for (int i = 0; i < rotations; i++) {
			rotate();
		}
	}
	public void rotate() {
		if (!isPlaced) {
			boolean tempDir = hasUp;
			hasUp = hasLeft;
			hasLeft = hasDown;
			hasDown = hasRight;
			hasRight = tempDir;
			if (rotation <= 2) {
				rotation++;
			} else {
				rotation = 0;
			}
			//rotateImage(this.Tile)
			//Add code to project rotated image when building GUIs
		}
	}

}
