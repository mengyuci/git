package leetcode;

public class ReorderListHistroy {
	public static ListNode first=null;//反转单链表之后的头指针
	public static ListNode back=null;//反转单链表之后的最后一个元素，方法执行完成之后设置back.next=null
	public static int count=0;
	public void resverList(ListNode head){
		if (head.next!=null){
			resverList(head.next);
			back.next=new ListNode(head.val);
			count++;
			back=back.next;
		}else{
			back=new ListNode(head.val);
			count++;
			first=back;
		}
	}
	
	public void reorderList(ListNode head) {		
		first=null;
		back=null;
		count=0;
		ListNode ans=head;
		resverList(head);
		for (int i=0;i<count/2-1;++i){
			ListNode temp=ans.next;
			ans.next=first;
			first=first.next;
			ans.next.next=temp;
			ans=temp;
		}
//		for (int i=0;i<(count/2);++i){
//			ans=tt;
//			System.out.println("AAA\n"+ans.val);
//			ans.next=first;
//			System.out.println(ans.next.val);
//			ans=first.next;
//			System.out.println(ans.val);
//			tt=tt.next;
//			System.out.println(tt.val);
//			first=first.next;
//			System.out.println(first.val);
//		}
		
		
	}
	
	public static void main(String[] args) {
		ReorderListHistroy test=new ReorderListHistroy();
		
		
		ListNode a=new ListNode(1);
		ListNode b=new ListNode(2);
		ListNode c=new ListNode(3);
		ListNode d=new ListNode(4);
		
		a.next=b;
		b.next=c;
		c.next=d;
		
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
