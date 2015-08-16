package leetcode;

import java.util.LinkedList;
import java.util.Queue;
class TreeLinkNode {
	int val;
    TreeLinkNode left;
    TreeLinkNode right;
    TreeLinkNode next;
    TreeLinkNode(int a){
    	val=a;
    	left=right=next=null;
    }
  }

public class PopulatingNextRightPointersinEachNode {

	
	
	public void connect(TreeLinkNode root) {
		Queue<TreeLinkNode> queue=new LinkedList<TreeLinkNode>();
        TreeLinkNode pre=null,now;
        int cur=0,a=1;
        queue.offer(root);
        now=queue.poll();
        while (now!=null){
        	
        	if (now.left!=null){
        		queue.offer(now.left);
        		cur++;
        	}
        	if (now.right!=null){
        		queue.offer(now.right);
        		cur++;
        	}
        	a--;
        	if (pre!=null)
        		pre.next=now;

        	pre=now;
        	if (a==0){
        		now.next=null;
        		a=cur;
        		cur=0;
        		pre=null;
        	}
        	
        	now=queue.poll();
        }
        
    }
	
	public void print(TreeLinkNode root){
		if (root==null)
			return ;
		System.out.print(root.val+" next ");
		if (root.next!=null)
			System.out.println(root.next.val);
		else 
			System.out.println(root.next);
		print(root.left);
		print(root.right);
	}
	
	public static void main(String[] args) {
		PopulatingNextRightPointersinEachNode test=new PopulatingNextRightPointersinEachNode();
		
		TreeLinkNode a=new TreeLinkNode(1);
		TreeLinkNode b=new TreeLinkNode(2);
		TreeLinkNode c=new TreeLinkNode(3);
		TreeLinkNode d=new TreeLinkNode(4);
		TreeLinkNode e=new TreeLinkNode(5);
		TreeLinkNode f=new TreeLinkNode(6);
		TreeLinkNode g=new TreeLinkNode(7);
		a.left=b;
		a.right=c;
		b.left=d;
		b.right=e;
		c.left=f;
		c.right=g;
		
		test.connect(a);
		
		test.print(a);
		
	}

}
