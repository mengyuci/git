package leetcode;

import java.util.Random;
import java.util.Scanner;

public class RandomproblemNumber {

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int tot=in.nextInt();
		Random random=new Random();
		for (int i=0;i<5;++i)
		System.out.println(random.nextInt(tot));
		
		in.close();
	}

}
