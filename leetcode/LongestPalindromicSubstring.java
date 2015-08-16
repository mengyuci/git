package leetcode;

import java.util.Scanner;

public class LongestPalindromicSubstring {

	public String mm(String s){
        int len = s.length(), max = 1, ss = 0, tt = 0;
        boolean flag[][]=new boolean[len][len];
        for (int i = 0; i < len; i++)
            for (int j = 0; j < len; j++)
                if (i >= j)
                    flag[i][j] = true;
                else flag[i][j] = false;
        for (int j = 1; j < len; j++)
            for (int i = 0; i < j; i++)
            {
                if (s.charAt(i) == s.charAt(j))
                {
                    flag[i][j] = flag[i+1][j-1];
                    if (flag[i][j] == true && j - i + 1 > max)
                    {
                        max = j - i + 1;
                        ss = i;
                        tt = j;
                    }
                }
                else flag[i][j] = false;
            }
        return s.substring(ss, tt);
			    
		
	}
	
	public String longestPalindrome(String s) {
        int n=s.length();
        int max=1,l=0,r=1;
        boolean vis[][]=new boolean[n][n];
        for (int i=0;i<n;++i){
        	for (int j=0;j<n;++j){
        		if (i>=j)
        			vis[i][j]=true;
        		else 
        			vis[i][j]=false;
        	}
        }
        for (int j=1;j<n;++j){
        	for (int i=0;i<j;++i){
        		if (s.charAt(i)==s.charAt(j)){
        			vis[i][j]=vis[i+1][j-1];
        			if (vis[i][j]){
        				if (j-i+1>max){
        					max=j-i+1;
        					l=i;
        					r=j+1;
        				}
        			}
        		}
        	}
        }
        return s.substring(l, r);
		
    }
	
	public static void main(String[] args) {
		LongestPalindromicSubstring test=new LongestPalindromicSubstring();
		Scanner in =new Scanner(System.in);
		int t=in.nextInt();
		in.nextLine();
		String s;
		while (t--!=0){
			s=in.nextLine();
			System.out.println(test.longestPalindrome(s));
			System.out.println(test.mm(s));
		}
		in.close();
	}

}
