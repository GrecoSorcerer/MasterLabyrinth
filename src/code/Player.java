package code;

public class Player {
	String name;
	int age;
	int x, y;
	
	public Player(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
