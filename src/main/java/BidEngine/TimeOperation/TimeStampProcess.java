package BidEngine.TimeOperation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Administrator on 2017/2/27.
 */
public class TimeStampProcess {
    public static void main(String[] args){
        System.out.println("input a timestamp:");
        Scanner input = new Scanner(System.in);
        try{
            //int num1 = input.nextInt();
            String num2 = input.next();
            System.out.print("date times:" + TimeStamp2Date(num2));
        }catch (Exception e){
            System.out.print("Error Input");
        }
    }
    //1484668816580
    public static String TimeStamp2Date(String timestampString){
        timestampString = timestampString.substring(0, timestampString.length()-3);
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(timestamp));
        return date;
    }
}
