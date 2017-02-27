package BidEngine.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/2/27.
 */
public class test1 {
    public static void main(String[] args){
        HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();
        for(int i=0; i< 10; i++){
            String is = String.valueOf(i);
            ArrayList<String> temp1 = new ArrayList<String>();
            for(int j=0; j<3; j++){
                String temp = String.valueOf(j);
                temp1.add(temp);
            }
            hm.put(is, temp1);
        }
        ArrayList<String> temp2 = hm.get(String.valueOf(4));
        temp2.add("haha");
        hm.remove(String.valueOf(4));
        hm.put(String.valueOf(4), temp2);
        System.out.println(hm);
    }
}
