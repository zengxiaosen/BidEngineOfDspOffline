package leetcode;

/**
 * Created by Administrator on 2017/3/7.
 */
public class AddBinary {
    public static class Solution{
        public String addBinary(String a, String b){
            StringBuilder re = new StringBuilder();
            int i = a.length() - 1;
            int j = b.length() - 1;
            int carry = 0;
            while(i >= 0  || j >= 0 || carry > 0){
                int valueA = i < 0 ? 0 : a.charAt(i) - '0';
                int valueB = j < 0 ? 0 : b.charAt(j) - '0';
                re.append((valueA + valueB + carry) % 2);
                carry = (valueA + valueB + carry) / 2;
                i --;
                j --;
            }
            return re.reverse().toString();
        }
    }

    public static class UnitTest{
        public static void main(String[] args){
            Solution so = new Solution();
            String s = so.addBinary("101", "110");
            System.out.println(s);
        }
    }
}
