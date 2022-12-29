package testJava;

public class Test4 {

    public static void main (String[] args){
        int[] ls1 = {1,3,5,6,7,44};
        int[] ls2 = {2,3,4,4,4,5,6,22,55,131,552};

        int[] sl, ll;

        sl = (ls1.length<=ls2.length)?ls1:ls2;
        ll = (ls1.length<=ls2.length)?ls2:ls1;

        int resNo = 0, size = ls1.length+ls2.length;
        double res = 0.0;
        int aim = size/2;

        if ((sl[0] >= ll[ll.length-1]) || (sl[sl.length-1] <= ll[0])){
            resNo = (sl[0] >= ll[ll.length-1])?aim:(sl[sl.length-1] <= ll[0])?(aim-sl.length):0;
            if (size%2 == 0){
                res = ((double)ll[resNo]+(double)ll[resNo+1])/2;
            }else {
                res = ll[resNo];
            }
        }else{
            int count = 0, lastCount = 0;
            for (int i=0; i<=sl.length; i++){
                count = dichotomy(sl[i],ll)+i+1;
                if (count > aim){
                    if (size%2 == 0) {
                        if (aim == resNo + 1) {
                            resNo = lastCount +1;
                            res = ((double)sl[i-1]+(double)ll[resNo])/2;
                        }else{
                            resNo = aim-resNo+lastCount;
                        }
                    }
                }else if (count == aim){

                }else{
                    resNo = count;
                    lastCount = count-i-1;
                }

            }
        }



        System.out.println();
    }

    public static int dichotomy (int m, int[] ls){
        int z = ls.length/2;
        int str=0, end=ls.length-1;

        for (int i=0;i<=z+1;i++){
            int num = ls[z];
            if (num <= m){
                str = z;

            }else {
                end = z;
            }

            if (end-str == 1){
                break;
            }


            z = (end-str)/2+str;

        }

        return str;
    }
}
