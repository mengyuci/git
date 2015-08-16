package leetcode;

public class ClimbingStairs {

	int[] ji;
	public int solve(int n){
		if (n <= 1)
			return 1;
		if (ji[n] != 0)
			return ji[n];
		int ans = solve(n-1) + solve(n-2);
		ji[n] = ans;
		return ans;
	}
	public int climbStairs(int n) {
        ji = new int[n+1];
		
        return solve(n);
    }
	
	public int ss(int n){
		if (n <= 1){
			return 1;
		}
		int[] ans = new int[n+1];
		ans[0] = ans[1] = 1;
		for (int i = 2; i <= n; ++i){
			ans[i] = ans[i-1] + ans[i-2];
		}
		return ans[n];
	}
	
	public static void main(String[] args) {
		
		ClimbingStairs test = new ClimbingStairs();
		
		System.out.println(test.ss(44));
		
	}

}
