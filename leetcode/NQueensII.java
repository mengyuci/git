package leetcode;

import java.util.ArrayList;
import java.util.List;

public class NQueensII {

	 int ans=0;
	    
	    public boolean check(List<StringBuilder> str,int x,int y,int n){
	    	for (int i=0;i<n;++i){
	    		if (str.get(i).charAt(y)=='Q')
	    			return false;
	    	}
	    	
	    	for (int i=0;;++i){
	    		if (x-i<0||y-i<0)
	    			break;
	    		if (str.get(x-i).charAt(y-i)=='Q')
	    			return false;
	    	}
	    	for (int i=0;;++i){
	    		if (x-i<0||y+i>=n)
	    			break;
	    		if (str.get(x-i).charAt(y+i)=='Q')
	    			return false;
	    	}
	    	
	    	return true;
	    }
	    
		public void find(List<StringBuilder> str,int cur,int n){
			if (cur==n)
			{
				ans++;
				return ;
			}for (int i=0;i<n;++i){
				if (check(str,cur,i,n)){
					str.get(cur).setCharAt(i, 'Q');
					
					find(str,cur+1,n);
					
					str.get(cur).setCharAt(i, '.');
				}
			}
			
		}
		
		public int totalNQueens(int n) {
			if (n<=0)
				return 0;
	        List<StringBuilder> str=new ArrayList<StringBuilder>();
	        StringBuilder a=new StringBuilder();
	        for (int i=0;i<n;++i)
	        	a.append('.');
	        for (int i=0;i<n;++i)
	        	str.add(new StringBuilder(a));
	        find(str,0,n);
	        return ans;
	        
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		NQueensII test=new NQueensII();
		System.out.println(test.totalNQueens(4));
		
	}

}
