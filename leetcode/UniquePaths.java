package leetcode;

public class UniquePaths {
	
	public int uniquePaths(int m, int n) {
        int map[][]=new int[m+1][n+1];
        for (int i=0;i<n+1;++i){
        	map[0][i]=0;
        	map[m][i]=0;
        }
        for (int i=0;i<m;++i){
        	map[i][0]=0;
        }
        
        for (int i=1;i<=m;++i){
        	for (int j=1;j<=n;++j){
        		if(i==1&&j==1)
        			map[i][j]=1;
        		else
        			map[i][j]=map[i-1][j]+map[i][j-1];
        	}
        }
        return map[m][n];
    }
	public static void main(String[] args) {
		UniquePaths test=new UniquePaths();
		System.out.println(test.uniquePaths(3, 3));
	}

}
