package leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ContainsDuplicateII {

	class Com{
		int index,val;
		Com(int index, int cal){
			this.index = index;
			this.val = cal;
		}
	}
	class MyComprator implements Comparator<Object> {
	    public int compare(Object arg0, Object arg1) {
	        Com t1=(Com)arg0;
	        Com t2=(Com)arg1;
	        if(t1.val != t2.val)
	            return t1.val>t2.val? 1:-1;
	        else
	            return 0;
	    }
	}
	
	public boolean containsNearbyDuplicate1(int[] nums, int k) {
		Com[] array=new Com[nums.length];
		for (int i = 0; i < nums.length; ++i){
			array[i]=new Com(i,nums[i]);
		}
		Arrays.sort(array,new MyComprator());
		
		for (int i = 0;i < array.length-1; ++i){
			if (array[i].val == array[i+1].val 
					&& Math.abs(array[i].index - array[i+1].index)<=k){
				return true;
			}
		}		
		return false;
    }
	
	public boolean containsNearbyDuplicate(int[] nums, int k) {
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		
		for (int i = 0;i < nums.length; ++i){
			if (map.containsKey(nums[i])){
				if (Math.abs(i - map.get(nums[i]))<=k){
					return true;
				}
			}
			map.put(nums[i], i);	
		}
		
		return false;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ContainsDuplicateII test = new ContainsDuplicateII();
		int[] nums = {0, 1, 3, 2, 5, 2};
		System.out.println(test.containsNearbyDuplicate(nums, 2));
	}

}
