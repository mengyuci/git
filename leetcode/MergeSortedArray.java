package leetcode;

public class MergeSortedArray {

	boolean flag=true;
	
	
	
	public void merge(int[] nums1, int m, int[] nums2, int n) {
		
		int t=m+n;
		m--;n--;
		for(int i=t-1;i>=0;--i){
			if (m<0){
				nums1[i]=nums2[n];
				n--;
			}else if (n<0){
				nums1[i]=nums1[m];
				m--;
			}else if (nums1[m]>nums2[n]){
				nums1[i]=nums1[m];
				m--;
			}else {
				nums1[i]=nums2[n];
				n--;
			}
		}
    }
	
	public static void main(String[] args) {
		MergeSortedArray test=new MergeSortedArray();
		int[] nums1=new int[]{1,3,5,7,0,0,0,0,0,0,0};
		int[] nums2=new int[]{2,4,6};
		int m=4,n=3;
		
		test.merge(nums1, m, nums2, n);
		
		for (int i=0;i<m+n;++i){
			System.out.print(nums1[i]+" ");
		}
		
		

	}

}
