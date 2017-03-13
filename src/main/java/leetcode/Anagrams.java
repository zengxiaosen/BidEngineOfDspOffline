package leetcode;

import java.util.*;

/**
 * Created by Administrator on 2017/3/10.
 */
public class Anagrams {
    public static class Solution{



        public static List<List<String>> groupAnagrams(String[] strs){
            Map<String, List<String>> result_m = new HashMap<String, List<String>>();
            for(String str : strs){
                char[] cs = str.toCharArray();
                Arrays.sort(cs);
                String cs1 = new String(cs);
                List<String> l = result_m.get(cs1);
                if(l == null){
                    l = new ArrayList<String>();
                }
                l.add(str);
                result_m.put(cs1, l);
            }
            List<List<String>> result1 = new ArrayList<List<String>>();
            for(Map.Entry<String, List<String>> entrySet : result_m.entrySet()){
                List<String> temp = entrySet.getValue();
                result1.add(temp);
            }
            return result1;
        }


    }

    public static class UnitTest{
        public static void main(String[] args){
            Solution s = new Solution();
            String[] strs = new String[]{"eat","tea","tan","ate","nat","bat"};
            List<List<String>> result = s.groupAnagrams(strs);
            for(int i=0; i< result.size(); i++){
                List<String> result1 = result.get(i);
                System.out.println(result1.toString());
            }
        }


    }
}
