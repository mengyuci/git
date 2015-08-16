package leetcode;

public class MergekSortedLists {
	ListNode[] heap;
	int index=0;
	public void swap(ListNode a,ListNode b){
		ListNode t=a.next;
		int value=a.val;
		a.next=b.next;
		a.val=b.val;
		b.next=t;
		b.val=value;
	}
	
	public void insertMinHeap(ListNode node){
		if (node==null)
			return ;
		heap[index++]=node;
		int t=index-1;
		for (int i=(t-1)/2;i>=0;i=(i-1)/2){
			if (heap[i].val>heap[t].val){
				swap(heap[i],heap[t]);
				t=i;
			}else{
				break;
			}
		}
	}
	
	public ListNode getMinHeap(){
		ListNode ans=heap[0];
		//System.out.print("IN -getMinHeap: get:"+ans.val+" ： ");
		//printHeap();
		if (heap[0].next!=null){
			heap[0]=heap[0].next;
		}else {
			index--;
			heap[0]=heap[index];
		}
		int t=0;
		ListNode tt;
		int in;
		for (int i=0;i<index;){
			if (2*i+1>=index){
				break;
			}
			if (2*i+2>=index){
				if (heap[t].val>heap[2*i+1].val){
					//System.out.println("before Swap: a:"+heap[t].val+" b:"+heap[2*i+1].val);
					swap(heap[t],heap[2*i+1]);
					//System.out.println("after Swap: a:"+heap[t].val+" b:"+heap[2*i+1].val);
					t=i*2+1;
					i=2*i+1;
				}
				break;
			}
			
			if (heap[2*i+1].val<heap[2*i+2].val){
				in=2*i+1;
				tt=heap[2*i+1];
			}else{
				in=2*i+2;
				tt=heap[2*i+2];
			}
			if (heap[t].val>tt.val){
				swap(heap[t],tt);
				t=in;
				i=in;
			}else {
				break;
			}
		}
		//System.out.print("OUT-getMinHeap:");
		//printHeap();
		return ans;
	}
	

	
	public ListNode mergeKLists(ListNode[] lists) {
		if (lists==null||lists.length==0)
			return null;
		heap=lists;
		for (int i=0;i<lists.length;++i){
			insertMinHeap(lists[i]);
		}
		ListNode head,temp;
		if (index==0)
			return null;
		head=getMinHeap();
		temp=head;
		while(index!=0){
			temp.next=getMinHeap();
			temp=temp.next;
		}
		temp.next=null;
		return head;
		
	}
	
	
	public void printHeap(){
		for(int i=0;i<index;++i){
			System.out.print(heap[i].val+" ");
		}
		System.out.println();
	}
	
	public ListNode mergeKListsNN(ListNode[] lists) {
        if (lists==null)
        	return null;
        ListNode head=null,temp=null;
        boolean flag=false;
        int nowChooseIndex=-1,minValue;
        while (true){
        	nowChooseIndex=-1;
        	minValue=Integer.MAX_VALUE;
        	for (int i=0;i<lists.length;++i){
        		if (lists[i]==null)
        			continue;
        		if (minValue>lists[i].val){
        			minValue=lists[i].val;
        			nowChooseIndex=i;
        		}
        	}
        	if (minValue==Integer.MAX_VALUE)
        		break;
        	if (!flag){
        		flag=true;
        		temp=lists[nowChooseIndex];
        		lists[nowChooseIndex]=lists[nowChooseIndex].next;
        		head=temp;
        	}else{
        		temp.next=lists[nowChooseIndex];
        		temp=temp.next;
        		lists[nowChooseIndex]=lists[nowChooseIndex].next;
        	}
        }
        return head;
    }
	public static void main(String[] args) {
		MergekSortedLists test=new MergekSortedLists();
		
		ListNode a=new ListNode(1);
		ListNode b=new ListNode(2);
		ListNode c=new ListNode(2);
		ListNode d=new ListNode(5);
		ListNode e=new ListNode(1);
		ListNode f=new ListNode(1);
		ListNode g=new ListNode(2);
		ListNode h=new ListNode(1);
		ListNode i=new ListNode(2);
		
		a.next=b;
		b.next=c;
		//c.next=d;
		d.next=e;
		e.next=f;
		f.next=g;
		//g.next=h;
		h.next=i;
		ListNode[] lists={a,e};
		
		//a,d,e,h
		
		ListNode ans=test.mergeKLists(lists);
		System.out.println("Done");
		while (ans!=null){
			System.out.println(ans.val+" ");
			ans=ans.next;
		}
	}

}
