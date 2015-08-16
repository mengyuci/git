package leetcode;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreePostorderTraversal {
	public void posOrder(TreeNode root,List<Integer> ans){
		if (root==null)
			return ;
		posOrder(root.left,ans);
		posOrder(root.right,ans);
		ans.add(root.val);
	}
	public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans=new ArrayList<Integer>();
        posOrder(root,ans);
        return ans;
    }
	public static void main(String[] args) {
		BinaryTreePostorderTraversal test=new BinaryTreePostorderTraversal();
		
		TreeNode a=new TreeNode(3);
		TreeNode b=new TreeNode(9);
		TreeNode c=new TreeNode(20);
		TreeNode d=new TreeNode(15);
		TreeNode e=new TreeNode(7);
		a.left=b;
		a.right=c;
		c.left=d;
		c.right=e;
		
		System.out.println(test.postorderTraversal(a));
	}

}
