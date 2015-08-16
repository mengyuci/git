package leetcode;

import java.util.LinkedList;
import java.util.List;

public class MinStack {
	
	List<Integer> stack=new LinkedList<Integer>();
	List<Integer> minstack=new LinkedList<Integer>();
	
	public void push(int x) {
        stack.add(0,x);
        if (minstack.size()==0)
        	minstack.add(0,x);
        else {
	        if (minstack.get(0)>x)
	        	minstack.add(0,x);
	        else 
	        	minstack.add(0,minstack.get(0));
        }
    }

    public void pop() {
        stack.remove(0);
        minstack.remove(0);
    }

    public int top() {
        return stack.get(0);
    }

    public int getMin() {
        return minstack.get(0);
    }
	
	public static void main(String[] args) {
		MinStack test=new MinStack();
//		test.push(1);
//		test.push(2);
//		System.out.println(test.getMin());
//		System.out.println(test.top());
//		test.push(3);
//		test.push(0);
//		test.push(4);
//		System.out.println(test.getMin());
//		System.out.println(test.top());
//		test.pop();
//		System.out.println(test.top());
//		test.pop();
//		System.out.println("min:"+test.getMin());
//		System.out.println(test.top());
	
		//test.push(2),push(0),push(3),push(0),getMin,pop,getMin,pop,getMin,pop,getMin
		test.push(2);
		test.push(0);
		test.push(3);
		test.push(0);
		System.out.println(test.getMin());
		test.pop();
		test.pop();
		test.pop();
		System.out.println(test.getMin());
	}

}
