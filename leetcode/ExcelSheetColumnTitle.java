package leetcode;

public class ExcelSheetColumnTitle {
	
	public String convertToTitle(int n) {
		StringBuilder str=new StringBuilder();
		int a;
		while (n!=0){
			n--;//因为A——Z不存在十进制中的0，所以n--模拟出来一个0
			a=n%26;
			str.append((char)(a+'A'));
			n=n/26;
		}
		return str.reverse().toString();
	}
	
	
	public static void main(String[] args) {
		ExcelSheetColumnTitle test=new ExcelSheetColumnTitle();
		for (int i=0;i<54;++i)
		System.out.println(i+" "+test.convertToTitle(i));
		
	}

}
