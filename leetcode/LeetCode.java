package leetcode;

public class LeetCode {

	class node{
		int val;
		node left,right;
		node(){
			left=right=null;
		}
		node(int val){
			this.val=val;
			left=right=null;
		}
	}
	
	public node root;
	
	public void preOrder(node root){
		if (root==null)
			return;
		System.out.println(root.val);
		preOrder(root.left);
		preOrder(root.right);
	}
	public void inOrder(node root){
		if (root==null)
			return;
		preOrder(root.left);
		System.out.println(root.val);
		preOrder(root.right);
	}
	public void postOrder(node root){
		if (root==null)
			return;
		preOrder(root.left);
		System.out.println(root.val);
		preOrder(root.right);
	}
	public int getDepth(node root){
		if (root==null)
			return 0;
		int depthleft=getDepth(root.left);
		int depthright=getDepth(root.right);
		return depthleft>depthright?depthleft+1:depthright+1;
	}
	
	public static void main(String[] args) {
		
	}

}
