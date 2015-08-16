package leetcode;

public class ValidateBinarySearchTree {

	boolean flag = false;
	int pre=0;
	public boolean isValidBST(TreeNode root) {
		if (root==null)
			return true;
		if (!isValidBST(root.left))
			return false;
		if (!flag){
			pre=root.val;
			flag=true;
		}else{
			if (pre>=root.val)
				return false;
			else
				pre=root.val;
		}
		if (!isValidBST(root.right))
			return false;
		return true;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ValidateBinarySearchTree test=new ValidateBinarySearchTree();
		
		TreeNode a=new TreeNode(3);
		TreeNode b=new TreeNode(1);
		TreeNode c=new TreeNode(20);
		TreeNode d=new TreeNode(15);
		TreeNode e=new TreeNode(7);
		a.left=b;
		a.right=c;
		c.left=d;
		c.right=e;
		
		System.out.println(test.isValidBST(a));
	}

}
