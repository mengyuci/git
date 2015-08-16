package leetcode;

import java.util.ArrayList;
import java.util.List;

public class PascalsTriangleII {

	public List<Integer> getRow(int rowIndex) {
        List<Integer> ans=new ArrayList<Integer>();
        if (rowIndex<0)
        	return ans;
        ans.add(0,1);
        for (int i=1;i<=rowIndex;++i){
        	for (int j=i;j>0;--j){
        		if (j==i){
        			ans.add(j, 1);
        			continue;
        		}
        		ans.set(j, ans.get(j)+ans.get(j-1));
        	}
        }
        return ans;
    }
	public static void main(String[] args) {
		PascalsTriangleII test=new PascalsTriangleII();
		System.out.println(test.getRow(1));
	}

}
