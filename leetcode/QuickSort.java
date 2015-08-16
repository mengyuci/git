package leetcode;

import java.util.Random;
import java.util.Scanner;

public class QuickSort {
	
	public int[] quickSort(int[] numbers,int l,int r){
		if (l<r){
			int q=PARTITION(numbers,l,r);
			quickSort(numbers,l,q);
			quickSort(numbers,q+1,r);
		}
		return numbers;
	}
	public int PARTITION(int[] numbers,int l,int r){
		int index=randomPartition(l, r);
		int temp=numbers[index];
		numbers[index]=numbers[l];
		numbers[l]=temp;
		int i=l+1,j=r-1;
		while(i<r&&j>=i){
			while (i<r&&numbers[i]<temp)
				i++;
			while (j>l&&numbers[j]>=temp)
				j--;
			if (i>=j){
				break;
			}
			int t=numbers[i];
			numbers[i]=numbers[j];
			numbers[j]=t;
		}
		numbers[l]=numbers[j];
		numbers[j]=temp;
		return j;
	}
	public int randomPartition(int l,int r){
		Random random=new Random();
		int ansRandom=random.nextInt(r-l)+l;
		return ansRandom;
	}
	
	void qsort(int[] num,int l,int r){
		if (l>=r)return;
		int q=partition(num,l,r);
		qsort(num,l,q);
		qsort(num,q+1,r);
		
	}
	int partition(int[] num,int l,int r){
		int index=randPosition(l, r);
		int temp=num[index];
		num[index]=num[l];
		num[l]=temp;
		int i=l,j=r;
		while (l<r){
			while (i<r&&num[++i]<temp)
				;
			while (j>l&&num[--j]>=temp)
				;
			if (i>j)
				break;
			int t=num[i];
			num[i]=num[j];
			num[j]=t;
		}
		num[l]=num[j];
		num[j]=temp;
		return j;
	}
	int randPosition(int l,int r){
		Random rand=new Random();
		return rand.nextInt(r-l)+l;
	}
	
	public static void main(String[] args) {
		int num[]=new int[10010],n;
		Scanner in=new Scanner(System.in);
		while (in.hasNextInt()){
			n=in.nextInt();
			for (int i=0;i<n;++i){
				num[i]=in.nextInt();
			}
			QuickSort test=new QuickSort();
			//test.qsort(num, 0, n);
			test.quickSort(num, 0, n);
			System.out.println(num[n/2]);
		}
		in.close();
	}

}
