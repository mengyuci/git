package leetcode;

import java.util.Scanner;

/**
 * @author zhaoiwei
 * DP 因为有负数，所以到i的最大值为max{nums[i],nums[i]*mul[i-1][0],nums[i]*mul[i-1][1]}，最小值类似
 * mul[i][0]代表到i位置的最大值，mul[i][1]代表到i位置的最小值
 */
public class MaximumProductSubarray {
	
	public int max(int a,int b){
		return a>b?a:b;
	}
	public int min(int a,int b){
		return a>b?b:a;
	}
	public int maxProduct(int[] nums) {
		int max=nums[0];
		int[][] mul=new int[nums.length][2];
		mul[0][0]=nums[0];
		mul[0][1]=nums[0];
		for (int i=1;i<nums.length;++i){
			mul[i][0]=max(nums[i]*mul[i-1][0],nums[i]*mul[i-1][1]);
			mul[i][0]=max(nums[i],mul[i][0]);
			mul[i][1]=min(nums[i]*mul[i-1][0],nums[i]*mul[i-1][1]);
			mul[i][1]=min(mul[i][1],nums[i]);
			if (mul[i][0]>max){
				max=mul[i][0];
			}
		}
		return max;
	}
	
	public static void main(String[] args) {
		MaximumProductSubarray test=new MaximumProductSubarray();
		Scanner in=new Scanner(System.in);
		int t=0;
		while ((t=in.nextInt())!=-1){
			int[] nums=new int[t];
			for (int i=0;i<t;++i){
				nums[i]=in.nextInt();
			}
			System.out.println(test.maxProduct(nums));
		}
		in.close();
	}

}
