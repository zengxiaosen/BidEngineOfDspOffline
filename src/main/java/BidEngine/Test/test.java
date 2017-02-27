package BidEngine.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */
public class test {
    public static void main(String[] args){
        ArrayList<Integer> num1 = new ArrayList<Integer>();
        num1.add(1);
        num1.add(2);

        ArrayList<Integer> num2 = new ArrayList<Integer>();
        num2.add(1);
        num2.add(3);
        num2.add(4);

        /**
         * 方法一：ArrayList<Integer> num2 = new ArrayList<Integer>(num1);//利用集合自带的构造方法
           方法二：ArrayList<Integer> num2 =(ArrayList<Integer>) num1.clone();//利用克隆的方法进行赋值。
         */

        ArrayList<Integer> num3 = new ArrayList<Integer>(num1);
        //System.out.println(num3.toString());

        ArrayList<Integer> num4 =(ArrayList<Integer>) num1.clone();
        //System.out.println(num4.toString());

        List num5 = (List) num2.clone();
        //System.out.println(num5.toString());

        List num6 = new ArrayList(num2);
        num6.add(5);
        System.out.println("6 "+num6.toString());
        System.out.println("2 "+num2.toString());

    }
}
