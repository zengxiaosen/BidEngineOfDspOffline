package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
public class _3Sum {
    public static void main(String[] args){
        int[] arrays = new int[]{-1,0,1,2,-1,-4};
        List<List<Integer>> result = threeSum(arrays);
        for(int i=0; i< result.size(); i++){
            List<Integer> a = result.get(i);
            System.out.println(a.toString());
            System.out.println();
        }
    }
    private static ArrayList<Integer> makeTriplet(int a, int b, int c) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        ans.add(a);
        ans.add(b);
        ans.add(c);
        return ans;
    }

    private static void twoSum(List<List<Integer>> ans, int a,
                               int[] num, int begin) {
        int i = begin;
        int j = num.length - 1;
        while (i < j) {
            int sum = num[i] + num[j];
            if (sum < -a) {
                i++;
            } else if (sum > -a) {
                j--;
            } else {
                ans.add(makeTriplet(a, num[i], num[j]));
                do {
                    i++;
                } while (i < j && num[i] == num[i - 1]);
                do {
                    j--;
                } while (i < j && num[j] == num[j + 1]);
            }
        }
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            twoSum(ans, nums[i], nums, i + 1);
        }
        return ans;
    }




}
