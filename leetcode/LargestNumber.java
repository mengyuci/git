package leetcode;

import java.util.Arrays;
import java.util.Comparator;

public class LargestNumber {

	class MyComparator implements Comparator<Integer>{

		@Override
		public int compare(Integer o1, Integer o2) {
			String s1=o1.toString();
			String s2=o2.toString();
			
			return -1*(s1+s2).compareTo(s2+s1);
		}
	}
	
	public String largestNumber(int[] nums) {
		if (nums==null)
			return "0";
		Integer[] arr = new Integer[nums.length];
		for (int i=0;i<nums.length;++i){
			arr[i]=nums[i];
		}
		Arrays.sort(arr, new MyComparator());
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < arr.length; ++i){
			System.out.println(i+" "+arr[i]);
			s.append(arr[i].toString());
		}
		int i;
		for (i = 0; i < s.length(); ++i){
			if (s.charAt(i)!='0')
				break;
		}
		if (i==s.length())
			return "0";
		return s.substring(i);
    }
	
	public static void main(String[] args) {
		LargestNumber test = new LargestNumber();
		int[] nums = {0,0};
		System.out.println(test.largestNumber(nums));

	}

}
