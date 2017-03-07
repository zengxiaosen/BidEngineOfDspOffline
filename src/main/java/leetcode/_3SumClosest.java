package leetcode;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/3/6.
 */
public class _3SumClosest {
    public static class Solution{
        public int threeSumClosest(int[] num, int target){
            Arrays.sort(num);
            int minSum = num[0] + num[1] + num[2];
            for(int i=0; i< num.length - 2; i++){
                if(i>0 && num[i] == num[i-1]){
                    continue;
                }
                int twoSum = target - num[i];
                int begin = i + 1;
                int end = num.length - 1;
                while(begin < end){
                    int sum = num[begin] + num[end];
                    if(Math.abs(twoSum - sum) < Math.abs(target - minSum)){
                        minSum = sum + num[i];
                    }
                    if(sum < twoSum){
                        begin ++;
                    }else if(sum > twoSum){
                        end --;
                    }else{
                        return target;
                    }
                }
            }
            return minSum;
        }
    }

    public static class UnitTest{
        public static void main(String[] args){
            Solution solution = new Solution();
            int[] num = new int[]{-1, 2, 1, -4};
            int target = 1;
            int result = solution.threeSumClosest(num, target);
            System.out.println(result);
        }
    }
}
