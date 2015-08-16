package leetcode;

public class HouseRobberII {

	public int max(int a,int b){
		return a > b ? a : b;
	}
	
	public int rob(int[] nums) {
		
		if (nums.length == 0)
			return 0;
		if (nums.length == 1)
			return nums[0];
        int[] ans = new int[nums.length];
        //第一个房子不选
        ans[0] = 0;
        ans[1] = nums[1];
        for (int i = 2; i < nums.length; ++i){
        	ans[i] = max(ans[i-1],ans[i-2]+nums[i]);
        }
        int max = ans[nums.length-1];
        ans[0] = nums[0];
        ans[1] = nums[0];
        for (int i = 2; i < nums.length - 1; ++i){
        	ans[i] = max(ans[i-1],ans[i-2]+nums[i]);
        }
        return max(max,ans[nums.length-2]);        
    }
	
	public static void main(String[] args) {
		HouseRobberII test = new HouseRobberII();
		int[] nums = {5,4,6,7,2};
		System.out.println(test.rob(nums));

	}

}
