package leetcode;

import java.util.Scanner;

public class Sqrtx {

	public int mySqrt(int x) {
		long l=0,r=1<<16,m=0;
        while (l<r){
        	m=(l+r)/2;
        	if (m*m==x)
        		return (int)m;
        	else if (m*m<x){
        		l=m+1;
        	}else 
        		r=m;
        }
        return (int)l-1;
        
    }
	public static void main(String[] args) {
		Sqrtx test=new Sqrtx();
		Scanner in =new Scanner(System.in);
		int num;
		while ((num=in.nextInt())>=0){
			System.out.print((int)Math.sqrt(num));
			System.out.println(" "+test.mySqrt(num));
			System.out.println("please in:");
		}
		in.close();
	}

}
