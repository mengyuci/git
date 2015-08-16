package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermutationsII {

	List<List<Integer>> ans = new ArrayList<List<Integer>>();
	
	public void solve(int[] nums,int cur){
		if (cur == nums.length){			
			List<Integer> add = new ArrayList<Integer>();
			for (int i = 0; i < nums.length; ++i){
				add.add(nums[i]);
			}
			ans.add(add);
		}
		
		for (int i = cur; i < nums.length; ++i){
			if (contained(nums, cur, i)){
				swap(nums,i,cur);
				solve(nums,cur+1);
				swap(nums,i,cur);
			}
		}
		
	}
	
	public boolean contained(int[] nums,int l,int r){
		for (int i=l;i<r;++i){
			if (nums[i]==nums[r])
				return false;
		}
		return true;
	}
	
	public void swap(int[] nums,int a,int b){
		int t= nums[a];
		nums[a] = nums[b];
		nums[b] = t;
	}
	
	public List<List<Integer>> permuteUnique(int[] nums) {
        
		Arrays.sort(nums);
		solve(nums,0);
		
		return ans;
    }
	
	public static void main(String[] args) {
		PermutationsII test = new PermutationsII();
		int[] nums = {3,3,0,0,2,3,2};
		
//		test.swap(nums, 1, 2);
//		System.out.println(nums[1]+" "+nums[2]);
		test.permuteUnique(nums);
		System.out.println(test.ans.size());
		System.out.println(test.ans);
	}

}
