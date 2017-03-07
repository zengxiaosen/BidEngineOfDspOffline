package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/6.
 */
public class TwoSum {

    public static void main(String[] args){
        int[] a = new int[]{3,2,4};
        int target = 6;
        int[] result = twoSum2(a, target);
        System.out.println(result[0]+ " " + result[1]);
    }
    public static int[] twoSum(int[] nums, int target){
        int length = nums.length;
        int index1 = 0;
        int index2 = 0;
        for(int i=0; i< length; i++){
            for(int j=i+1; j< length; j++){
                if(nums[i] + nums[j] == target){
                    index1 = i;
                    index2 = j;
                }
            }

        }
        int[] returnValue = new int[] {index1, index2};
        return returnValue;
    }

    public static int[] twoSum2(int[] nums, int target){
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i=0; i< nums.length; i++){
            int x = nums[i];
            if(map.containsKey(target - x)){
                return new int[] {map.get(target - x), i};
            }
            map.put(x, i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
