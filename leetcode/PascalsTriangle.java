package leetcode;

import java.util.ArrayList;
import java.util.List;

public class PascalsTriangle {

	public List<List<Integer>> generate(int numRows) {
		List<List<Integer>> ans=new ArrayList<List<Integer>>();
		if (numRows==0)
			return ans;
		List<Integer> one=new ArrayList<Integer>();
		one.add(1);
		ans.add(one);
		for (int i=2;i<=numRows;++i){
			List<Integer> t=new ArrayList<Integer>();
			List<Integer> pre=ans.get(i-2);
			for (int j=0;j<i;++j){
				if(j==0||j==i-1){
					t.add(1);
					continue;
				}
				t.add(pre.get(j-1)+pre.get(j));
			}
			ans.add(t);
		}
		return ans;
    }
	public static void main(String[] args) {
		PascalsTriangle test=new PascalsTriangle();
		List<List<Integer>> ans=test.generate(10);
		for (int i=0;i<ans.size();++i){
			System.out.println(ans.get(i));
		}
	}

}
