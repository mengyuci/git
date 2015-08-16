package leetcode;

public class PlusOne {

	public int[] plusOne(int[] digits) {
        int t=1,sum=0;
        for (int i=digits.length-1;i>=0;--i){
        	sum=digits[i]+t;
        	t=sum/10;
        	digits[i]=sum%10;
        	if (t==0)
        		break;
        }
        if (t!=0){
        	int[] ans=new int[digits.length+1];
        	ans[0]=t;
        	for(int i=0;i<digits.length;++i){
        		ans[i+1]=digits[i];
        	}
        	return ans;
        }else{
        	return digits;
        }
        
    }
	
	public static void main(String[] args) {
		PlusOne test=new PlusOne();
		int[] digits={8,9,9,9};
		int[] ans=test.plusOne(digits);
		for (int i=0;i<ans.length;++i)
			System.out.print(ans[i]);
	}

}
