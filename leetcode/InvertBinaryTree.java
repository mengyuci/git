package leetcode;

public class InvertBinaryTree {
	public TreeNode invertTree(TreeNode root) {
		if (root==null)
			return root;
		invertTree(root.left);
		invertTree(root.right);
		TreeNode temp=root.left;
		root.left=root.right;
		root.right=temp;
		return root;
    }
	public void preOrder(TreeNode root){
		if (root==null)
			return ;
		System.out.print(root.val);
		preOrder(root.left);
		preOrder(root.right);
		return ;
	}
	public static void main(String[] args) {
		InvertBinaryTree test=new InvertBinaryTree();
		TreeNode a=new TreeNode(4);
		TreeNode b=new TreeNode(2);
		TreeNode c=new TreeNode(1);
		TreeNode d=new TreeNode(3);
		TreeNode e=new TreeNode(7);
		TreeNode f=new TreeNode(6);
		TreeNode g=new TreeNode(9);
		a.left=b;
		b.left=c;
		b.right=d;
		a.right=e;
		e.left=f;
		e.right=g;
		
		test.preOrder(a);
		System.out.println();
		
		test.invertTree(a);
		test.preOrder(a);
		
		
	}

}
