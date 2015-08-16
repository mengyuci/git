package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubstringwithConcatenationofAllWords {

	public List<Integer> findSubstring(String s, String[] words) {
		List<Integer> ans = new ArrayList<Integer>();
		if (s == null || words.length == 0)
			return ans;
		int length = words[0].length(),nowValue = 0;
		Map<String,Integer> wordMap =new HashMap<String,Integer>();
		for (int i = 0; i < words.length; ++i){
			if (wordMap.containsKey(words[i])){
				wordMap.put(words[i], wordMap.get(words[i])+1);
			}else {
				wordMap.put(words[i], 1);
			}
		}
		int end = s.length() - words.length * length;
		String temp;
		Map<String,Integer> count = new HashMap<String,Integer>();
		for (int i = 0; i <= end; ++i){
			int j = 0;
			for (j = 0; j < words.length; ++j){
				temp = s.substring(i + j *length , i + (j + 1) * length);
				if (!wordMap.containsKey(temp)){
					break;
				}else {
					if (count.containsKey(temp)){
						nowValue = count.get(temp);
						if ((nowValue + 1) > wordMap.get(temp))
							break;
						count.put(temp, count.get(temp)+1);
					}else {
						count.put(temp, 1);
					}
				}
			}
			if (j == words.length){
				int k = 0;
				for (k = 0; k < words.length; ++k){
					if (wordMap.get(words[k]) != count.get(words[k]))
						break;
				}
				if (k == words.length){
					ans.add(i);
				}
			}
			count.clear();
		}
		
		return ans;
    }
	public static void main(String[] args) {
		SubstringwithConcatenationofAllWords test = new SubstringwithConcatenationofAllWords();
		String s = "barfoothefoobar";
		String[] words = {"foo","bar"};
		System.out.println(test.findSubstring(s, words));
	}

}
