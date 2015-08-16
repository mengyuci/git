package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BinaryTreeLevelOrderTraversalII {

	public void addToAnswer(List<Integer> list,Stack<List<Integer>> stack){
		List<Integer> temp=new ArrayList<Integer>(list);
		stack.push(temp);
		list.clear();
	}
	public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> ans=new ArrayList<List<Integer>>();
        Stack<List<Integer>> stack=new Stack<List<Integer>>();
        if (root==null)
        	return ans;
        int nowNodeNum=0,conNum=0,pre=1;
        List<Integer> list=new ArrayList<Integer>();
        Queue<TreeNode> queue=new LinkedList<TreeNode>();
        queue.offer(root);
        TreeNode temp;
        while (!queue.isEmpty()){
        	temp=queue.poll();
        	if (temp.left!=null){
        		queue.offer(temp.left);
        		nowNodeNum++;
        	}
        	if (temp.right!=null){
        		queue.offer(temp.right);
        		nowNodeNum++;
        	}
        	list.add(temp.val);
        	conNum++;
        	if (conNum==pre){
        		addToAnswer(list,stack);
        		pre=nowNodeNum;
        		nowNodeNum=0;
        		conNum=0;
        	}
        }
        while (!stack.empty()){
        	list=stack.pop();
        	ans.add(list);
        }
        return ans;
    }
	public static void main(String[] args) {
		BinaryTreeLevelOrderTraversalII test=new BinaryTreeLevelOrderTraversalII();
		TreeNode a=new TreeNode(3);
		TreeNode b=new TreeNode(9);
		TreeNode c=new TreeNode(20);
		TreeNode d=new TreeNode(15);
		TreeNode e=new TreeNode(7);
		a.left=b;
		a.right=c;
		c.left=d;
		c.right=e;
		
		List<List<Integer>> ans=test.levelOrderBottom(a);
		
		System.out.println(ans);

	}

}
