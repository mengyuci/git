package leetcode;

import java.util.ArrayList;
import java.util.List;

public class UniquePathsII {

	public int uniquePathsWithObstacles(int[][] obstacleGrid) {
		List<Integer[]> ans=new ArrayList<Integer[]>();
		for(int i=0;i<obstacleGrid.length;++i){
			Integer[] temp=new Integer[obstacleGrid[i].length];
			if (i==0){
				int n=1;
				for (int j=0;j<obstacleGrid[i].length;++j){
					if (obstacleGrid[0][j]==1)
						n=0;
					temp[j]=n;
				}
			}else{
				Integer[] pre=ans.get(i-1);
				for (int j=0;j<obstacleGrid[i].length;++j){
					if (obstacleGrid[i][j]==0){
						if (j!=0)
							temp[j]=temp[j-1]+pre[j];
						else 
							temp[j]=pre[j];
					}else temp[j]=0;
				}
			}
			ans.add(temp);
		}
		Integer[] t=ans.get(obstacleGrid.length-1);
		return t[obstacleGrid[obstacleGrid.length-1].length-1];
    }

	public static void main(String[] args) {
		UniquePathsII test=new UniquePathsII();
		int[][] obstacleGrid={{0,0,0},{0,0,0},{0,0,0}};
		System.out.println(test.uniquePathsWithObstacles(obstacleGrid));
	}

}
