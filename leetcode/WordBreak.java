package leetcode;

import java.util.HashSet;
import java.util.Set;

public class WordBreak {
	
	public boolean wordBreak(String s, Set<String> wordDict) {
		
		boolean[] vis=new boolean[s.length()];
		for (int i=0;i<s.length();++i){
			vis[i]=wordDict.contains(s.substring(0, i+1));
			if (vis[i]==true)
				continue;
			else {
				for(int j=0;j<i;++j){
					if (vis[j]){
						vis[i]=wordDict.contains(s.substring(j+1, i+1));
						if (vis[i])
							break;
					}
				}
			}
		}
		
		return vis[s.length()-1];
    }
	
	public static void main(String[] args) {
		WordBreak test=new WordBreak();
		String s="leecode";
		Set<String> wordDict=new HashSet<String>();
		wordDict.add("leet");
		wordDict.add("code");
		
		System.out.println(test.wordBreak(s, wordDict));
	}

}
