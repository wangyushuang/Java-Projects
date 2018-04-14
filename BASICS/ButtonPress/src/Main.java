import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
      //  while (in.hasNextInt()) {//注意while处理多个case
            String str=in.nextLine().trim();
            int count=0;
            for(int i=0;i<str.length();i++) {
            	for(int j=0;j<str.length()-i;j++) {
            	}
            }
            System.out.println(str);
      //  }
    }
    static boolean isHuiwen(String s) {
    	boolean f=true;
    	int len=s.length()-1;
    	for(int i=0;i<(len>>1);i++) {
    		if(s.charAt(i)==(s.charAt(len-i))) 
    			return false;
    	}
    	return true;
    }
}