package testJava;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Test3 {
    int state;
    String name;



    public static void main(String[] args){
        String a = "";
        int resstr = 0, resend = 0, length = 0;
        int str = 0, end = 0;
        int testFlag = 0;

        Map<Character,Integer> m = new HashMap<>();
        for (int i=0; i<a.length(); i++){
            char aa = a.charAt(i);
            if (m.containsKey(aa) && m.get(aa) >= str) {
                int t = m.get(aa);
                end = i;
                str = t+1;
                int tlength = end-str+1;
                if (tlength > length){
                    resstr = str;
                    resend = end;
                    testFlag ++;
                }
            }
            m.put(aa, i);
            if (i-str+1 > length){
                resstr = str;
                resend = i;
            }
            length = resend - resstr + 1;
        }

        System.out.println(length);
        //System.out.println(b);
        //System.out.println(c);
    }




//aab/au/ /pwwkew/abcabcbb

}

