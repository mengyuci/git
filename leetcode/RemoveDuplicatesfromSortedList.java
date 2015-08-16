package leetcode;

public class RemoveDuplicatesfromSortedList {

	public ListNode deleteDuplicates(ListNode head) {
        if (head==null)
        	return null;
		ListNode nowNode=head;
		int nowValue=head.val;
		ListNode temp=head.next;
		while (temp!=null){
			if (temp.val!=nowValue){
				nowNode.next=temp;
				nowNode =temp;
				nowValue=temp.val;
			}
			temp=temp.next;
		}
		nowNode.next=null;
		return head;
    }
	public static void main(String[] args) {
		RemoveDuplicatesfromSortedList test=new RemoveDuplicatesfromSortedList();
		ListNode a=new ListNode (1);
		ListNode b=new ListNode (1);
		ListNode c=new ListNode (2);
		ListNode d=new ListNode (2);
		ListNode e=new ListNode (2);
		a.next=b;
		b.next=c;
		c.next=d;
		d.next=e;
		
		ListNode ans=test.deleteDuplicates(a);
		while(ans!=null){
			System.out.print(ans.val+" ");
			ans=ans.next;
		}
	}

}
