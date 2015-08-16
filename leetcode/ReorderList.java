package leetcode;

public class ReorderList {  
	
	
	public void reorderList(ListNode head) {	
		if (head==null)
			return ;
		ListNode fast=head,slow=head,temp;
		while (fast!=null&&fast.next!=null){
			slow=slow.next;
			fast=fast.next.next;
		}
		ListNode start=slow.next,head2=start,head1=head;
		slow.next=null;

		if (start==null)
			return;
		/*反转单链表，对a->b->c
		 * pre指向a，now指向b
		 * temp=now.next
		 * now.next=pre
		 * pre=now
		 * now=temp
		 */
		ListNode pre=start;
		ListNode now=start.next;
		while (now!=null){
			temp=now.next;
			
			now.next=pre;
			pre=now;
			
			now=temp;
		}
		//原头结点为现在的尾结点，将其next设为null
		head2.next=null;
		//pre为反转之后链表的头结点
		head2=pre;
		//交叉合并两个单链表
		while (head1!=null&&head2!=null){
			temp=head1.next;
			head1.next=head2;
			pre=head2.next;
			head2.next=temp;
			head1=temp;
			head2=pre;
		}
		
		
		
	}
	
	public static void main(String[] args) {
		ReorderList test=new ReorderList();
		
		
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
		f.next=g;
		g.next=h;
		h.next=i;
		
		ListNode temp=null;
		
		test.reorderList(a);
		System.out.println("Answer");
		temp=a;
		while (temp!=null){
			System.out.println(temp.val);
			temp=temp.next;
		}
	}

}
