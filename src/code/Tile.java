package code;
/**
 * 
 * @author Sal
 *
 */
public class Tile {
	
	String id;
	
	boolean hasPlayerOne = false;
	boolean hasPlayerTwo = false;
	boolean hasPlayerThree = false;
	boolean hasPlayerFour = false;
	//int x, y; //Use if needed for iterator
	//boolean hasPlayers = false;
	int rotation = 0;
	
	boolean hasUp;
	boolean hasDown;
	boolean hasLeft;
	boolean hasRight;
	
	int qntyInSet;
	
	boolean hasToken = false;
	Token storage; //Holds a Token Object
	
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
	
	public String getTexture(){
		return id + rotation + ".png";
	}
	
	public void setPlaced() {
		//When a tile is added to or removed from the board, the boolen isPlaced will change.
	}
	protected void addPlayer(int p) {
		if (p == 1) {
			hasPlayerOne = true;
		}
		else if (p == 2) {
			hasPlayerTwo = true;
		}
		else if (p == 3) {
			hasPlayerThree = true;
		}
		else if (p == 4) {
			hasPlayerFour = true;
		}
	}
	
	protected void removePlayer(int p) {
		if (p == 1) {
			hasPlayerOne = false;
		}
		else if (p == 2) {
			hasPlayerTwo = false;
		}
		else if (p == 3) {
			hasPlayerThree = false;
		}
		else if (p == 4) {
			hasPlayerFour = false;
		}
	}
	
	public void placeToken(Token t) {
		storage = t;
		hasToken = true;
	}
	
	public void takeToken() {
		
	}
	
	public void rotateMany(int rotations) {
		for (int i = 0; i < rotations; i++) {
			rotateRight();
		}
	}
	
	public void rotateRight() {
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
	public void rotateLeft() {
		if (!isPlaced) {
			boolean tempDir = hasUp;
			hasUp = hasRight;
			hasRight = hasDown;
			hasDown = hasLeft;
			hasLeft = tempDir;
			if (rotation > 0 && rotation <=3) {
				rotation--;
			} else {
				rotation = 3;
			}
			//rotateImage(this.Tile)
			//Add code to project rotated image when building GUIs
		}
	}
	public boolean hasToken() {
		return storage != null;
	}
}
