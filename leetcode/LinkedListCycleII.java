package leetcode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LinkedListCycleII {

	public ListNode hasCycle(ListNode head){
		ListNode ans=null;
		ListNode fast=head,slow=head;
		while (fast!=null&&fast.next!=null){
			fast=fast.next.next;
			slow=slow.next;
			if (slow==fast)
				return fast;
		}
		return ans;
	}
	
	public ListNode detectCycle(ListNode head) {
		if (head==null)
			return null;
		ListNode cycleElement=hasCycle(head);
		if (cycleElement==null)
			return null;
		ListNode head2=cycleElement.next;
		cycleElement.next=null;
		
		List<ListNode> a1=new LinkedList<ListNode>();
		List<ListNode> a2=new LinkedList<ListNode>();
		
		while (head!=null){
			a1.add(0, head);
			head=head.next;
		}
		
		while (head2!=null){
			a2.add(0, head2);
			head2=head2.next;
		}
		
		ListNode pre=head;
		
		Iterator<ListNode> i1=a1.iterator();
		Iterator<ListNode> i2=a2.iterator();
		
		while (i1.hasNext()&&i2.hasNext()){
			ListNode t1=i1.next();
			ListNode t2=i2.next();
			if (t1!=t2)
				break;
			pre=t1;
		}
		
		return pre;
    }
	public static void main(String[] args) {
		
		LinkedListCycleII test=new LinkedListCycleII();
		
		ListNode a=new ListNode(1);
		ListNode b=new ListNode(2);
		ListNode c=new ListNode(3);
		ListNode d=new ListNode(4);
		ListNode e=new ListNode(5);
		ListNode f=new ListNode(6);
		ListNode g=new ListNode(7);
		ListNode h=new ListNode(8);
		ListNode i=new ListNode(9);
		
		a.next=b;
		b.next=c;
		c.next=d;
		d.next=e;
		e.next=f;
		f.next=b;
		g.next=h;
		h.next=i;
		
		System.out.println(test.detectCycle(a).val);
	}

}
