package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Permutations {

	boolean[] flag;
	List<List<Integer>> ans =new ArrayList<List<Integer>>();
	
	public void solve(int[] nums,int cur,List<Integer> temp){
		
		if (cur == nums.length){
			List<Integer> add = new ArrayList<Integer>(temp);
			ans.add(add);
		}
		
		for (int i = 0; i < nums.length; ++i){
			if (!flag[i]){
				flag[i] = true;
				temp.add(cur,nums[i]);
				solve(nums,cur+1,temp);
				temp.remove(cur);
				flag[i] = false;
			}
		}
		
	}
	
	public List<List<Integer>> permute(int[] nums) {
		if (nums == null || nums.length == 0){
			return ans;
		}
		flag =new boolean[nums.length]; 
//		for (int i=0;i<nums.length;++i){
//			flag[i]=false;
//		}
		List<Integer> temp = new ArrayList<Integer>();
		solve(nums,0,temp);
		
		return ans;
    } 
	
	public static void main(String[] args) {
		Permutations test = new Permutations();
		int[] nums = {3,3,0,0,2,3,2};
		test.permute(nums);
		System.out.println(test.ans.size());
		System.out.println(test.ans);
		
	}

}
