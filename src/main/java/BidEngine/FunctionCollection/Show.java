package BidEngine.FunctionCollection;

import BidEngine.TimeOperation.TimeStampProcess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/27.
 */
public class Show {

    public static void Traverse_TimeList(List arrayList){
        for(int i=0; i< arrayList.size(); i++){
            String time = (String) arrayList.get(i);
            String timeFormat = TimeStampProcess.TimeStamp2Date(time);
            System.out.print(timeFormat+"("+time+")"+" , ");
        }
    }

    public static void Traverse(List arrayList){
        //遍历
        int count = 1;
        for(Iterator iter = arrayList.iterator(); iter.hasNext(); ){
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String)entry.getKey();
            //System.out.println(key+"\n\n\n");
            //String value = (String)entry.getValue();
            ArrayList<StringBuffer> value = (ArrayList<StringBuffer>) entry.getValue();
            /*for(int i=0; i<value.size(); i++){
                String value_i = value.get(i).toString();
                if(!value_i.contains("41")){
                    System.out.println(value_i);
                }
            }*/
            String time_1 = TimeStampProcess.TimeStamp2Date(key.split("!")[0].toString());
            System.out.println(time_1);
            count ++;

        }
        //System.out.print(count);

    }
}
