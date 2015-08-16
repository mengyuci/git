package leetcode;

class TreeNode{
	int val;
	TreeNode left;
	TreeNode right;
	TreeNode(int val){
		this.val=val;
	}
}

public class PathSum {
	
	public boolean find(TreeNode root,int sum,int cur){
		if (root.left==null&&root.right==null){
			if (cur+root.val==sum)
				return true;
			return false;
		}
		if (root.left!=null){
			if (find(root.left,sum,cur+root.val))
				return true;
		}
		if (root.right!=null){
			if (find(root.right,sum,cur+root.val))
				return true;
		}
		return false;
	}
	
    public boolean hasPathSum(TreeNode root, int sum) {
    	if (root==null)
    		return false;
    	return find(root,sum,0);
    }
	
    public void preOrder(TreeNode root){
    	if (root==null)
    		return;
    	System.out.println(root.val);
    	preOrder(root.left);
    	preOrder(root.right);
    }
	
	public static void main(String[] args) {
		PathSum test=new PathSum();
		TreeNode a=new TreeNode(1);
		TreeNode b=new TreeNode(2);
		TreeNode c=new TreeNode(3);
		TreeNode d=new TreeNode(4);
		TreeNode e=new TreeNode(5);
		a.left=b;
		a.right=c;
		b.left=d;
		c.right=e;
		
		test.preOrder(a);
		
		System.out.println(test.hasPathSum(a, 3));
	}

}
