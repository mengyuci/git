package leetcode;

public class SearchInRotatedSortedArray {

	public int binarySearch(int[] nums,int l,int r,int target){
		int a;
		if (l>r)
			return -1;
		int m=(r-l)/2+l;
		if (nums[m]==target)
			return m;
		else if(nums[m]<target) {
			if (nums[l]<nums[m]){
				return binarySearch(nums,m+1,r,target);
			}else {
				if ((a=binarySearch(nums,l,m-1,target))!=-1)
					return a;
				if ((a=binarySearch(nums,m+1,r,target))!=-1)
					return a;
				return -1;
			}
		}else {
			if (nums[l]<nums[m]){
				if (target>=nums[l])
					return binarySearch(nums,l,m-1,target);
				else 
					return binarySearch(nums,m+1,r,target);
			}else {
				if ((a=binarySearch(nums,l,m-1,target))!=-1)
					return a;
				if ((a=binarySearch(nums,m+1,r,target))!=-1)
					return a;
				return -1;
			}
		}
	}
	
	public int search(int[] nums, int target) {
		
		return binarySearch(nums,0,nums.length-1,target);
    }
	
	public static void main(String[] args) {
		SearchInRotatedSortedArray test=new SearchInRotatedSortedArray();
		int[] nums={4,5,6,7,8,0,1,3};
		int t=1;
		System.out.println(t+" "+test.search(nums, t));

	}

}
