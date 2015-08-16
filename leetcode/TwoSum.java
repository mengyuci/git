package leetcode;

public class TwoSum {	
	
	void print(int[] num){
		for (int i : num){
			System.out.print(i);
		}
		System.out.println();
	}
	
	public void rotate(int[] nums, int k) {
        int t=nums[0],index=0,ii=0,l=nums.length;
		if (k%l==0)return;
        boolean vis[]=new boolean[l];
        for (int i=0;i<l-1;++i)
        	vis[i]=false;
        vis[0]=true;
        for (int i=0;i<l;++i){
        	int p=(index+k)%l;
        	int temp=nums[p];
        	nums[p]=t;
        	if (vis[p]){
        		t=nums[++ii];
        		index=ii;
        	}else{
        		t=temp;
        		index=p;
        	}
        	vis[index]=true;
        }
    }
	
	public static void main(String[] args){
		TwoSum a=new TwoSum();
		int[] num=new int[]{1,2,3,4,5,6,7};
		for (int i : num){
			System.out.print(i);
		}
		System.out.println();
		a.rotate(num,3);
		for (int i : num){
			System.out.print(i);
		}
		System.out.println();
	}

}
