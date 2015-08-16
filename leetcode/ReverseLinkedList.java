package leetcode;

public class ReverseLinkedList {
	ListNode tou;
	ListNode first;
	public void digui(ListNode head){
		if (head.next==null){
			tou=head;
			first=tou;
		}else{
			digui(head.next);
			tou.next=head;
			tou=head;
		}
		
	}
	
	public ListNode three(ListNode head){
		if (head==null)
			return null;
		ListNode temp,temp2,ff=head.next;
		while (ff!=null){
			temp=ff.next;
			temp2=head.next;
			head.next=ff;
			ff.next=temp2;
			ff=temp;
		}
		//System.out.println("fdsafd "+head.val);
		//ff=head;
		temp=head.next;
		//head.next=null;
		return temp;
	}
	
	public ListNode reverseList(ListNode head) {
		if (head==null)
			return null;
        ListNode temp,pre=head,now=head.next;
        while(now!=null){
        	temp=now.next;
        	now.next=pre;
        	pre=now;
        	now=temp;
        }
        head.next=null;
        return pre;
	}
	public static void main(String[] args) {
		ReverseLinkedList test=new ReverseLinkedList();
		
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
		ListNode temp;
		//temp=test.reverseList(a);
		temp=test.three(a);
		//test.tou.next=null;
		System.out.println("Answer");
		//temp=test.first;
		while (temp!=null){
			System.out.println(temp.val);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			temp=temp.next;
		}
	}

}
