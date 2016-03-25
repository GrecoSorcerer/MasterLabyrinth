package code;

public class Player {
	String name;
	int playerNum;
	int x, y;
	public Player(String name, int playerNum) {
		this.name = name;
		this.playerNum = playerNum;
	}
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
