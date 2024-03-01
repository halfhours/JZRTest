package testJava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class test_2808 {
    public static void main(String[] args) {
        //1,0,2,3,4,0,5,6,0,7,8,0,9

        List<Integer> nums = new ArrayList<>(Arrays.asList(19,3,9,7,3,4,17,6,10,7,6,9,3));
        int minSize = nums.size();
        int key = 0;
        int count = 0;

        while (minSize != 0) {
            HashMap<Integer, Set<Integer>> hash1 = new HashMap<>();
            if(nums.stream().distinct().toList().size() == 1){
                minSize = 0;
                break;
            }

            for (int i = 0; i < nums.size(); i++) {
                int t = nums.get(i);
                int nof = i == 0 ? nums.size()-1 : i - 1;
                int nob = i == nums.size()-1 ? 0 : i + 1;
                if (!hash1.containsKey(t)) {
                    Set<Integer> ls = new HashSet<>(Arrays.asList(nof, i, nob));
                    hash1.put(t, ls);
                } else {
                    Set<Integer> ls = hash1.get(t);
                    ls.add(nof);
                    ls.add(i);
                    ls.add(nob);
                }
            }
            for (Map.Entry<Integer, Set<Integer>> entry : hash1.entrySet()) {
                int keySize = nums.size() - entry.getValue().size();
                if (keySize < minSize) {
                    minSize = keySize;
                    key = entry.getKey();
                }
                if(keySize == minSize){
                    key = getLength(nums, key)<getLength(nums, entry.getKey())? entry.getKey() :key;
                }
            }
            for(Integer a:hash1.get(key)){
                nums.set(a,key);
            }
            count ++;
        }

        System.out.println(count);
    }

    public static double getLength(List<Integer> source, int n){
        double result = 0.0;
        int count = 0;
        List<Integer> list = new ArrayList<>();
        for(int x=0; x<source.size(); x++){
            if(source.get(x) == n){
                list.add(x);
            }
        }
        for (int i=0; i< list.size()-1; i++){
            int diff = list.get(i+1)-list.get(i);
            count += diff > (double)source.size()/2? source.size()-diff:diff;
        }
        return (double) count /list.size();
    }
}
