package leetcode;

import java.util.Scanner;


public class Main {
	static int[] pre=new int[1010];
	static int[] bac=new int[1010];
	static int[] ans=new int[1010];
	static int cur=0;
	static int n;
	static class node{
		int val;
		node left,right;
	}
	public static node root;
	
	static node buildtree(int l,int r,int ll,int rr){
		if (l>=r||ll>=rr) return null;
		//System.out.println(l+"  "+r);
		node now=new node();
		int mid=pre[l],i;
		now.val=mid;
		for (i=ll;i<rr;++i)
			if (bac[i]==mid)
				break;
		int temp=i-ll;
		now.left=buildtree(l+1,l+temp+1,ll,i);
		now.right=buildtree(l+temp+1,r,i+1,rr);
		return now;
	}
	
	static void bacOrder(node now){
		if (now==null)
			return ;
		bacOrder(now.left);
		bacOrder(now.right);
		cur++;
		System.out.print(now.val);
		if (cur!=n)
			System.out.print(" ");
	}
	
	static void preOrder(node now){
		if(now==null) 
			return;
		System.out.print(now.val+" ");
		preOrder(now.left);
		preOrder(now.right);
	}
	
	static void inOrder(node now){
		if(now==null) 
			return;
		inOrder(now.left);
		System.out.print(now.val+" ");
		inOrder(now.right);
	}
	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		while (in.hasNextInt()){
			n=in.nextInt();
			for (int i=0;i<n;++i)
				pre[i]=in.nextInt();
			for (int i=0;i<n;++i)
				bac[i]=in.nextInt();
			root=buildtree(0,n,0,n);
			bacOrder(root);
			System.out.println();
			
		}
		in.close();
	}

}
