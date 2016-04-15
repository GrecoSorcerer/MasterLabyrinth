	package code;

import java.util.Random;
	/**
	 * 
	 * @author Sal
	 *
	 */
	public class BuildSet {
		
		private int setQnty = 34; // qnty is some number of tiles variations in the set. The current number will mostlikey change
		
		private Token[] _tokens = new Token[21];
		private Tile[] tileSet;
		
		/*
		 * Only tiles that can be placed will be created here
		 */
		
		int size = 0;
		
		/**
		 * @author Andre
		 * @author Sal
		 * 
		 */
		private void buildSet() {
			tileSet = new Tile[setQnty];
			int turn = 16;
			int straight = 12;
			int intersection = 6;
			int count = 0;
			while (count < tileSet.length) {
				Random r = new Random();
				int sel = r.nextInt(3);
	//			System.out.println(count +"]" + sel);
	//			System.out.println(turn + " " + straight + " " + intersection);
				if (sel == 0 && turn >= 1) {
					tileSet[count] = new Tile(0, 0);
					tileSet[count].rotateMany(r.nextInt(8));
	//				System.out.println(tileSet[count].id);
					turn--;
					count++;
				}
				if (sel == 1 && straight >= 1) {
					tileSet[count] = new Tile(1, 0);
					tileSet[count].rotateMany(r.nextInt(8));
	//				System.out.println(tileSet[count].id);
					straight--;
					count++;
				}
				if (sel == 2 && intersection >= 1) {
					tileSet[count] = new Tile(2, 0);
					tileSet[count].rotateMany(r.nextInt(8));
	//				System.out.println(tileSet[count].id);
					intersection--;	
					count++;
				}
			}
		}
		
		public void buildTokens() {
			
			
		for(int i =0 ; i < _tokens.length; i++){
			if(i == _tokens.length-1){
				_tokens[20] = new Token(25);
			}
			_tokens[i] = new Token(i);
			}
		}
		
		/**
		 * @author Sal
		 * @return tilePile[]
		 */
		public Tile[] getPile() {
			
			buildSet();
			
			return tileSet;
		}
		
		public Token[] getTokens() {
			buildTokens(); 
			return _tokens;
		}
		
	}
