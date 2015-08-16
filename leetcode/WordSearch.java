package leetcode;


public class WordSearch {
	
	public boolean find(char [][]board,String word,int index,int x,int y){
		
		if (index==word.length()-1&&board[x][y]==word.charAt(index))
			return true;
		if (board[x][y]!=word.charAt(index))
			return false;
		char ch=board[x][y];
		board[x][y]='~';
		if (x-1>=0&&board[x-1][y]!='~'){
			if (find(board,word,index+1,x-1,y)){
				board[x][y]=ch;
				return true;
			}
		}
		if (y-1>=0&&board[x][y-1]!='~'){
			if (find(board,word,index+1,x,y-1)){
				board[x][y]=ch;
				return true;
			}
		}
		if (x+1<board.length&&board[x+1][y]!='~'){
			if (find(board,word,index+1,x+1,y)){
				board[x][y]=ch;
				return true;
			}
		}
		if (y+1<board[x].length&&board[x][y+1]!='~'){
			if (find(board,word,index+1,x,y+1)){
				board[x][y]=ch;
				return true;
			}
		}
		board[x][y]=ch;
		return false;
	}
	
	public boolean exist(char[][] board, String word) {
		
		for (int i=0;i<board.length;++i){
			for (int j=0;j<board[i].length;++j){
				if (board[i][j]==word.charAt(0)){
					if (find(board,word,0,i,j))
						return true;
				}
			}
		}
		return false;
    }
	
	public static void main(String[] args) {
		
		WordSearch test=new WordSearch();
		
		char[][] board={{'a','b','c','e'},{'s','f','c','s'},{'a','d','e','e'}};
		
		String word="abfda";
		
		System.out.println(test.exist(board, word));
		
		
		for (int i=0;i<board.length;++i){
			for (int j=0;j<board[i].length;++j)
				System.out.print(board[i][j]);
			System.out.println();
		}
	}

}
