package leetcode;

import java.math.BigInteger;

public class MultiplyStrings {
	
	public int addE(StringBuilder ans,int ansIndex,int mid){
		if (ansIndex>=ans.length()){
			ans.append((char)(mid%10+'0'));
		}else{
			int e=ans.charAt(ansIndex)-'0';
			mid+=e;
			ans.setCharAt(ansIndex, (char)(mid%10+'0'));
		}
		return mid/10;
	}
	
	public String multiply(String num1, String num2) {
		StringBuilder a=new StringBuilder(),b=new StringBuilder(),ans=new StringBuilder();
		for (int i=num1.length()-1;i>=0;--i){
			a.append(num1.charAt(i));
		}
		for (int i=num2.length()-1;i>=0;--i){
			b.append(num2.charAt(i));
		}
		int ad,bd,add,mid,ansIndex=0;
		for (int i=0;i<b.length();++i){
			bd=b.charAt(i)-'0';
			add=0;
			ansIndex=i;
			for (int j=0;j<a.length();++j){
				ad=a.charAt(j)-'0';
				mid=ad*bd;
				mid=mid+add;
				add=addE(ans,ansIndex,mid);
				ansIndex++;
			}
			while(add!=0){
				add=addE(ans,ansIndex,add);
				ansIndex++;
			}
		}
		ans.reverse();
		int i;
		for (i=0;i<ans.length();++i){
			if (ans.charAt(i)!='0')
				break;
		}
		if (i==ans.length())
			return "0";
		return ans.substring(i);
    }
	
	public String BigIntegerMulti(String num1,String num2){
		BigInteger a=new BigInteger(num1);
		BigInteger b=new BigInteger(num2);
		BigInteger ans=a.multiply(b);
		return ans.toString();
	}
	
	public static void main(String[] args) {
		MultiplyStrings test=new MultiplyStrings();
		String num1="1344",num2="4";
		
		System.out.println(test.multiply(num1, num2));
		System.out.println(test.BigIntegerMulti(num1, num2));
	}

}
