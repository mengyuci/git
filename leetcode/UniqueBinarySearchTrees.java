package leetcode;

public class UniqueBinarySearchTrees {

	
	public int numTrees(int n) {
		if (n <= 0){
			return 0;
		}
		int[] ans = new int[n+1];
		int t;
		ans[0] = 1;
		for (int i = 1; i <= n; ++i){
			if (i < 3){
				ans[i] = i;
				continue;
			}
			t = 0;
			for (int j = 1; j <= i; ++j){
				t += ans[j-1] * ans[i-j];
			}
			ans[i] = t;
		}
		return ans[n];		
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UniqueBinarySearchTrees test = new UniqueBinarySearchTrees();
		System.out.println(test.numTrees(4));
		
	}

}
