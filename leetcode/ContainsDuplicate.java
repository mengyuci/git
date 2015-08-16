package leetcode;

import java.util.Arrays;

public class ContainsDuplicate {


	public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i=0;i<nums.length-1;++i){
        	if (nums[i]==nums[i+1])
        		return true;
        }
        return false;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ContainsDuplicate test=new ContainsDuplicate();
		int[] nums={1,2,3,4,5};
		System.out.println(test.containsDuplicate(nums));
	}

}
