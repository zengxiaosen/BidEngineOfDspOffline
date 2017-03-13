package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
public class _4Sum {
    public static class Solution{
        private ArrayList<Integer> list(int... ns){
            ArrayList<Integer> l = new ArrayList<Integer>();
            for(int n : ns){
                l.add(n);
            }
            return l;
        }

        private void twoSum(int[] num, int a, int b, int start, int target, List<List<Integer>> quadruplets){
            int low = start;
            int high = num.length - 1;
            while(low < high){
                int sum = num[low] + num[high];
                if(sum < target){
                    low ++;
                }else if(sum > target){
                    high --;
                }else{
                    quadruplets.add(list(a, b, num[low], num[high]));
                    do{
                        low ++;
                    }while(low < high && num[low] == num[low - 1]);
                    do{
                        high --;
                    }while(low < high && num[high] == num[high + 1]);
                }
            }
        }

        public List<List<Integer>> fourSum(int[] num, int target){
            List<List<Integer>> quadruplets = new ArrayList<List<Integer>>();
            Arrays.sort(num);
            for(int i=0; i< num.length - 3; i++){
                if(i != 0 && num[i] == num[i-1]){
                    continue;
                }
                for(int j=i+1; j< num.length - 2; j++){
                    if(j != i+1 && num[j] == num[j-1]){
                        continue;
                    }
                    twoSum(num, num[i], num[j], j+1, target - num[i] - num[j], quadruplets);
                }
            }
            return quadruplets;
        }
    }

    public static class UnitTest{
        public static void main(String[] args){
            Solution solution = new Solution();
            int[] num = new int[]{1,0,-1,0,-2,2};
            int target = 0;
            List<List<Integer>> result = solution.fourSum(num, target);
            System.out.println(result.toString());
        }
    }
}
