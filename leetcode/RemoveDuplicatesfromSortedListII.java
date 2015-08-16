package leetcode;

public class RemoveDuplicatesfromSortedListII {

	public ListNode deleteDuplicates1(ListNode head) {
		if (head==null)
			return null;
		boolean flag=true;
		ListNode p1=head,nextNode=null,first=null;
		while (p1!=null){
			ListNode p2=p1.next;
			while (p2!=null){
				if (p2.val!=p1.val)
					break;
				else flag=false;
				p2=p2.next;
			}
			if (flag==true){
				if (first==null){
					first=p1;
					nextNode = p1;
				}else {
					nextNode.next=p1;
					nextNode = p1;
				}
			}
			p1=p2;
			flag=true;
		}
		if (first!=null)
			nextNode.next=null;
		return first;
	}
	
	public ListNode deleteDuplicates(ListNode head) {
		if (head==null)
			return head;
		boolean flag=true;
		int con=0;
        ListNode first=null,nextNode=head,pre=head;
        ListNode temp=head.next;
        while (temp!=null){
        	if (temp.val!=pre.val){
        		if (flag==true){
        			if (con==0){
        				first=pre;
        				nextNode=first;
        			}else {
        				nextNode.next=pre;
        				nextNode=pre;
        			}
            		con++;
        		}
        		flag=true;
        	}else {
        		flag=false;
        	}
        	pre=temp;
        	temp=temp.next;
        }
		if (flag==true){
			if (first==null){
				first=pre;
				nextNode=first;
			}else{
				nextNode.next=pre;
				nextNode=pre;
			}
		}
		nextNode.next=null;
		return first;
    }
	public static void main(String[] args) {
		RemoveDuplicatesfromSortedListII test=new RemoveDuplicatesfromSortedListII();
		ListNode a=new ListNode (1);
		ListNode b=new ListNode (1);
		ListNode c=new ListNode (3);
		ListNode d=new ListNode (3);
		ListNode e=new ListNode (4);
		a.next=b;
		//b.next=c;
		c.next=d;
		d.next=e;
		
		ListNode ans=test.deleteDuplicates1(a);
		System.out.println(ans);
		while(ans!=null){
			System.out.print(ans.val+" ");
			ans=ans.next;
		}
	}

}
