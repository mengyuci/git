package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {

	private List<List<Integer>> ans=new ArrayList<List<Integer>>();
	private List<Integer> list=new ArrayList<Integer>();
	private int[] nums;
	public void addToAns(){
		List<Integer> temp=new ArrayList<Integer>();
		for (int i=0;i<list.size();++i){
			temp.add(list.get(i));
		}
		ans.add(temp);
	}
	
	public void solve(int cur,int in,int target){
		for (int i=in;i<nums.length;++i){
			if (cur+nums[i]<target){
				list.add(nums[i]);
				solve(cur+nums[i],i,target);
				int index=list.indexOf(nums[i]);
				list.remove(index);
			}else if (cur+nums[i]==target){
				list.add(nums[i]);
				addToAns();
				int index=list.indexOf(nums[i]);
				list.remove(index);
			}else return ;
		}
		
	}
	public int[] removeDup(int[] nums){
		int index=1;
		for (int i=1;i<nums.length;++i){
			if (nums[i]!=nums[i-1]){
				nums[index++]=nums[i];
			}
		}
		int[] ans=new int[index];
		for (int i=0;i<index;++i){
			ans[i]=nums[i];
		}
		return ans;
	}
	public List<List<Integer>> combinationSum(int[] candidates, int target) {
		Arrays.sort(candidates);
        nums=removeDup(candidates);
        solve(0,0,target);
        return ans;
    }
	public static void main(String[] args) {
		CombinationSum test=new CombinationSum();
		int[] nums=new int[]{8,7,4,3};
		int target = 11;
		test.combinationSum(nums, target);
		
		System.out.println("Answer");
		System.out.println(test.ans.size());
		for (int i=0;i<test.ans.size();++i){
			for (int j=0;j<test.ans.get(i).size();++j){
				System.out.print(test.ans.get(i).get(j)+" ");
			}
			System.out.println();
		}
	}

}
