import java.util.*;
import java.util.regex.*;
import java.io.*;

public class Test{
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int nn = in.nextInt();
        int m = in.nextInt();
        int n=nn/2;
        int result=0;
        int tmp=0;
        while(n%2==0){
            n=n>>1;
            tmp++;
        }
        for(int j=0;j<n;j++)
            result+=m;
        for(int i=0;i<tmp;i++){
            result=result*2;
        }
        System.out.println(result);
       // }
    }
}

