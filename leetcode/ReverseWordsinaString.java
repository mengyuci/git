package leetcode;

public class ReverseWordsinaString {

	public void reverse(StringBuilder s,int l,int r){
		char t;
		int j;
		for (int i=l;i<l+(r-l)/2;++i){
			t=s.charAt(i);
			j=r-(i-l+1);
			s.setCharAt(i, s.charAt(j));
			s.setCharAt(j, t);
		}
		return ;
	}
	
	public String reverseWords(String s) {
		if (s==null||s.length()==0)
			return s;
		int wordStart=0,wordEnd=s.length();
		StringBuilder sb=new StringBuilder(s);
		
		for (int i=0;i<s.length();++i){
			if (sb.charAt(i)==' '){
				System.out.println("IN");
				wordEnd=i;
				reverse(sb,wordStart,wordEnd);
				while ((++i<s.length())&&sb.charAt(i)==' ');
				wordStart=i;
			}
		}
		
		if (sb.charAt(s.length()-1)!=' '){
			reverse(sb,wordStart,s.length());
		}
		reverse(sb,0,s.length());
		
		for (int i=0;i<sb.length();++i){
			if (sb.charAt(i)==' '){
				int k=i;
				while ((++k)<sb.length()){
					if (sb.charAt(k)!=' '){
						break;
					}
				}
				if (k-i>1){
					sb.delete(i+1, k);
				}
			}
		}
		
		return sb.toString().trim();
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReverseWordsinaString test=new ReverseWordsinaString();
		String a="hi!";
		System.out.println(a+"\n"+test.reverseWords(a));
		
	}

}
