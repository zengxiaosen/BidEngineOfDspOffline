package leetcode;

import javax.swing.tree.TreeNode;

/**
 * Created by Administrator on 2017/3/10.
 */
public class BalancedBinaryTree {
    class Solution{
        private int getBalancedTreeHeight(TreeNode root){
            if(root == null){
                return 0;
            }
            int l = getBalancedTreeHeight(root.left);
            int r = getBalancedTreeHeight(root.right);
            if(l >= 0 && r >= 0){
                if(Math.abs(l - r) <= 1){
                    return Math.max(l, r) + 1;
                }
            }

            return -1;
        }

        public boolean isBalanced(TreeNode root){
            return getBalancedTreeHeight(root) >= 0;
        }
    }

    public static class UnitTest{

    }

    public class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x){
            val = x;
        }
    }

}
