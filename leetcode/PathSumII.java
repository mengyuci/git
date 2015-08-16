package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PathSumII {
	List<List<Integer>> ans=new ArrayList<List<Integer>>();
	public void find(TreeNode root,int sum,int cur,LinkedList<Integer> array){
		if (root.left==null&&root.right==null){
			if (cur+root.val==sum){
				array.offerLast(root.val);
				List<Integer> t=new LinkedList<Integer>(array);
				ans.add(t);
				array.pollLast();
			}
		}
		array.offerLast(root.val);
		if (root.left!=null){
			find(root.left,sum,cur+root.val,array);
		}
		if (root.right!=null){
			find(root.right,sum,cur+root.val,array);
		}
		array.pollLast();
	}
	
	public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if (root==null)
        	return ans;
        LinkedList<Integer> array=new LinkedList<Integer>();
        find(root,sum,0,array);
        
        return ans;
    }
	
	
    public void preOrder(TreeNode root){
    	if (root==null)
    		return;
    	System.out.println(root.val);
    	preOrder(root.left);
    	preOrder(root.right);
    }
	
	public static void main(String[] args) {
		PathSumII test=new PathSumII();
		TreeNode a=new TreeNode(1);
		TreeNode b=new TreeNode(2);
		TreeNode c=new TreeNode(3);
		TreeNode d=new TreeNode(4);
		TreeNode e=new TreeNode(5);
		TreeNode f=new TreeNode(3);
		TreeNode g=new TreeNode(6);
		a.left=b;
		a.right=c;
		b.left=d;
		c.right=e;
		c.left=f;
		b.right=g;
		test.preOrder(a);
		
		List<List<Integer>> t=test.pathSum(a, 7);
		
		for (int i=0;i<t.size();++i){
			List<Integer> tt=t.get(i);
			for (int j=0;j<tt.size();++j){
				System.out.print(tt.get(j)+" ");
			}
			System.out.println();
		}
		
	}

}
