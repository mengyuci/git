package leetcode;

public class ConstructBinaryTreefromPreorderandInorderTraversal {
	
	public TreeNode build(int[] preOrder,int pl,int pr,int[] inOrder,int il,int ir){
		if (pl>=pr||il>=ir)
			return null;
		int t=preOrder[pl],i=il;
		for (i=il;i<ir;++i){
			if (inOrder[i]==t)
				break;
		}
		TreeNode root=new TreeNode(t);
		root.left=build(preOrder,pl+1,pl+(i-il+1),inOrder,il,i);
		root.right=build(preOrder,pl+i-il+1,pr,inOrder,i+1,ir);
		return root;
	}
	
	public TreeNode buildTree(int[] preorder, int[] inorder) {
		if (preorder==null||inorder==null)
			return null;
		return build(preorder,0,preorder.length,inorder,0,inorder.length);
		
    }
	public void pre(TreeNode root){
		if (root==null)
			return ;
		System.out.print(root.val+" ");
		pre(root.left);
		pre(root.right);
		return ;
	}
	public static void main(String[] args) {
		ConstructBinaryTreefromPreorderandInorderTraversal test=new ConstructBinaryTreefromPreorderandInorderTraversal();
		int[] preOrder=new int[]{1,2,4,5,3,6};
		int[] inOrder=new int[]{4,2,5,1,3,6};
		
		TreeNode root=test.buildTree(preOrder,inOrder);
		System.out.println(root);
		test.pre(root);

	}

}
