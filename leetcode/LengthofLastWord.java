package leetcode;

import java.util.Scanner;

public class LengthofLastWord {
	
	public int lengthOfLastWord(String s) {
		if (s==null)
			return 0;
		int ans=0,i=0;
        for (i=s.length()-1;i>=0;--i){
        	if (s.charAt(i)!=' '){
        		break;
        	}
        }
        if (i==-1)
        	return 0;
        for (int j=i;j>=0;--j){
        	if (s.charAt(j)!=' ')
        		ans++;
        	else break;
        }
        return ans;
    }
	
	public static void main(String[] args) {
		LengthofLastWord test=new LengthofLastWord();
		Scanner in=new Scanner(System.in);
		String s=null;
		System.out.println(test.lengthOfLastWord(s));
		while (!(s=in.nextLine()).equals("END")){
			System.out.println(test.lengthOfLastWord(s));
		}
		in.close();
	}

}
