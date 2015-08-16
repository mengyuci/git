package leetcode;

public class InsertionSortList {

	public void print(ListNode head){
		ListNode head1=head;
		System.out.print("test: ");
		while (head1!=null){
			System.out.print(head1.val);
			head1=head1.next;
		}
		System.out.println();
	}
	
	public ListNode insertionSortList(ListNode head) {
		if (head==null)
			return head;
        ListNode s=head.next;
        ListNode f=head;
        f.next=null;
        while (s!=null){
        	ListNode pre=null,temp=s.next;
        	f=head;
        	boolean flag=false;
        	while (f!=null){
        		if (f.val>s.val){
        			s.next=f;
        			if (pre!=null){
        				pre.next=s;
        			}else {
        				head=s;
        			}
        			flag=true;
        			break;
        		}
        		pre=f;
        		f=f.next;
        	}
        	if (!flag){
        		pre.next=s;
        		s.next=null;
        	}
        	s=temp;
        }
        return head;
    }
	public static void main(String[] args) {
		InsertionSortList test=new InsertionSortList();
		
		ListNode a=new ListNode(8);
		ListNode b=new ListNode(9);
		ListNode c=new ListNode(3);
		ListNode d=new ListNode(5);
		ListNode e=new ListNode(4);
		ListNode f=new ListNode(6);
		ListNode g=new ListNode(7);
		ListNode h=new ListNode(1);
		ListNode i=new ListNode(2);
		
		a.next=b;
		b.next=c;
		c.next=d;
		d.next=e;
		e.next=f;
		f.next=g;
		g.next=h;
		h.next=i;
		
		ListNode ans=test.insertionSortList(a);
		
		while (ans!=null){
			System.out.print(ans.val+" ");
			ans=ans.next;
		}

	}

}
