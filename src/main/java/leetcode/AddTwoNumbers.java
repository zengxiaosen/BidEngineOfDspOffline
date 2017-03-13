package leetcode;

/**
 * Created by Administrator on 2017/3/7.
 */
public class AddTwoNumbers {


    public static class ListNode{
        int val;
        ListNode next;
        ListNode(int x){
            val = x;
        }
    }

    public static class Solution{
        public ListNode addTwoNumbers(ListNode l1, ListNode l2){
            ListNode head = null;
            ListNode prev = null;
            int carry = 0;
            while(l1 != null || l2 != null){
                int i1 = l1 == null ? 0 : l1.val;
                int i2 = l2 == null ? 0 : l2.val;
                int sum = i1 + i2 + carry;
                ListNode node = new ListNode(sum % 10);
                carry = sum / 10;
                if(prev == null){
                    head = prev = node;
                }else{
                    prev.next = node;
                    prev = node;
                }
                if(l1 != null){
                    l1 = l1.next;
                }
                if(l2 != null){
                    l2 = l2.next;
                }
            }
            if(carry > 0){
                prev.next = new ListNode(carry);
            }
            return head;
        }
    }

    public static class UnitTest{
        public static void main(String[] args){
            ListNode a = new ListNode(2);
            ListNode a1 = a;
            a.next = new ListNode(4);
            a = a.next;
            a.next = new ListNode(3);

            ListNode b = new ListNode(5);
            ListNode b1 = b;
            b.next = new ListNode(6);
            b = b.next;
            b.next = new ListNode(4);

            Solution s = new Solution();
            ListNode result = s.addTwoNumbers(a1, b1);

            while(result.next != null){
                System.out.println(result.val);
                result = result.next;
            }
        }
    }

}
