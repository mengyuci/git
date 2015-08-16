package leetcode;

public class CompareVersionNumbers {

	public int compareVersion(String version1, String version2) {
        int a=0,b=0;
        int i1=0,i2=0;
        while (true){
        	a=0;
        	b=0;
        	while(i1<version1.length()&&version1.charAt(i1)!='.'){
        		a=a*10+(version1.charAt(i1)-'0');
        		i1++;
        	}
        	while(i2<version2.length()&&version2.charAt(i2)!='.'){
        		b=b*10+(version2.charAt(i2)-'0');
        		i2++;
        	}
        	
        	if (a!=b){
        		if (a>b)
        			return 1;
        		return -1;
        	}
        	if (i1>=version1.length()&&i2>=version2.length())
        		return 0;
        	i1++;
        	i2++;
        }
        
    }
	
	public static void main(String[] args) {
		CompareVersionNumbers test=new CompareVersionNumbers();
		String a1="01.2";
		String a2="1.10";
		System.out.println(test.compareVersion(a1, a2));
	}

}
