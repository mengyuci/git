package leetcode;

import java.util.ArrayList;
import java.util.List;

public class DifferentWaystoAddParentheses {

	
	public List<Integer> diffWaysToCompute(String input) {
		List<Integer> ans = new ArrayList<Integer>();
		if (input == null)
			return ans;
		int val = 0, i;
		for (i = 0;i < input.length(); ++i){
			if (!Character.isDigit(input.charAt(i))){
				break;
			}
			val = val * 10 + input.charAt(i) - '0';
		}
		if (i == input.length()){
			ans.add(val);
			return ans;
		}
		
		for (i = 0; i<input.length(); ++i){
			if (!Character.isDigit(input.charAt(i))){
				List<Integer> l = diffWaysToCompute(input.substring(0, i));
				List<Integer> r = diffWaysToCompute(input.substring(i+1));
				for (int j = 0; j < l.size(); ++j){
					for (int k = 0; k < r.size(); ++k){
						ans.add(compute(l.get(j),r.get(k),input.charAt(i)));
					}
				}
			}
		}
		return ans;
    }
	
	public int compute(int a,int b,char c){
		switch(c){
		case '+':return a+b;
		case '-':return a-b;
		case '*':return a*b;
		default: return Integer.MIN_VALUE;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DifferentWaystoAddParentheses test = new DifferentWaystoAddParentheses();
		String input = "2*3-4*5";
		System.out.println(test.diffWaysToCompute(input));
		
	}

}
