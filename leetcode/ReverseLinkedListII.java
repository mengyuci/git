package leetcode;

public class ReverseLinkedListII {
	
	public ListNode reverse(ListNode start,ListNode end){
		ListNode a=start,b=end.next;
		end.next=null;		
		ListNode pre=a,temp;
		ListNode now=a.next;
		while (now!=null){
			temp=now.next;
			now.next=pre;
			pre=now;
			now=temp;
		}
		a.next=b;
		return pre;
	}
	
	public ListNode reverseBetween(ListNode head, int m, int n) {
		ListNode start=head,end=head,temp=head,pre=null,preStart=null;
		int count=0;
		while (temp!=null){
			count++;
			if (count==m){
				preStart=pre;
				start=temp;
			}
			if (count==n){
				end=temp;
			}
			pre=temp;
			temp=temp.next;
		}
		ListNode ans=reverse(start,end);
		if (m!=1)
			preStart.next=ans;
		else 
			head=ans;
		
		return head;
    }
	public static void main(String[] args) {
		ReverseLinkedListII test=new ReverseLinkedListII();
		
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
		
		ListNode a1=new ListNode(3);
		ListNode a2=new ListNode(5);
		a1.next=a2;
		
		
		ListNode ans=test.reverseBetween(a, 6, 9);
		
		ListNode temp=null;
		
		System.out.println("Answer");
		temp=ans;
		while (temp!=null){
			System.out.println(temp.val);
			temp=temp.next;
		}
	}

}
