package leetcode;

/**
 * Created by Administrator on 2017/3/6.
 */
public class Valid_Palindrome {
    public static void main(String[] args){
        String s = "A man, a plan, a canal: Panama";
        boolean b = isPalindrome(s);
        System.out.println(b);
    }

    public static boolean isPalindrome(String s){
        int j = s.length() - 1;
        int i = 0;
        while(i<j){
            while(i<j && !Character.isLetterOrDigit(s.charAt(i))){
                i++;
            }
            while(i<j && !Character.isLetterOrDigit(s.charAt(j))){
                j--;
            }
            if(Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
