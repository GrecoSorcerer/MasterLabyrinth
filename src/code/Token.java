package code;

/**
 * 
 * @author Andre
 *
 */
public class Token {
	int refrenceNumber;
	String path = "";
	
	public Token(int referenceNumber){
		this.refrenceNumber = referenceNumber + 1;
		path = "token" + referenceNumber + ".png" ;
		
		
	}
	
}
