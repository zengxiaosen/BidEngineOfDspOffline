package BidEngine.BidStrategy;

import BidEngine.DataLoad.DataLoader;
import BidEngine.DataStruct.bid_log_data;
import BidEngine.DataStruct.timestamp_dataObject;
import BidEngine.FunctionCollection.Show;
import BidEngine.FunctionCollection.function_collection_withP;
import BidEngine.TimeOperation.TimeStampProcess;
import javafx.scene.chart.PieChart;
import jdk.nashorn.internal.runtime.regexp.joni.constants.Traverse;

import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2017/2/27.
 */
public class bid_driver {
    public static void main(String[] args) throws IOException{
        //数据加载和预处理
        DataLoader dataLoader = new DataLoader();
        List<bid_log_data> c = dataLoader.loadFile_bid_log_data_list("C:\\Users\\Administrator\\Desktop\\yes_日志\\bid_log.txt");
        //按时间戳进行排好序的Time_Request,other数据结构
        List arrayList = GetSortedTRandOthers(c);
        //遍历TimeSorted的arrayList数据
        Show.Traverse(arrayList);
        //得到MeanTotal数据结构
        ArrayList<HashMap<String, ArrayList<StringBuffer>>> MeanTotal = GetMeanTotal(arrayList);
        //dataObjectList是一个含有所有时间片的list，每个元素是一个时间片内的数据
        List<timestamp_dataObject> timestamp_dataObjects_list = GetTDObjectList(MeanTotal);
        //假设dspid=11214,n为第几个时间片,p为给第n个时间片的概率值
        Integer dspid_test = 11214;
        Integer n_test = 3;
        //返回还剩预算
        Double TotalBudget_EffectOfP = function_collection_withP.Pintellegance_dspId(timestamp_dataObjects_list, dspid_test, n_test);


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     *
     * @param MeanTotal
     * @return
     */
    private static List<timestamp_dataObject> GetTDObjectList(ArrayList<HashMap<String, ArrayList<StringBuffer>>> MeanTotal) {
        List<timestamp_dataObject> timestamp_dataObjects_list1 = new ArrayList<timestamp_dataObject>();
        //分时间片进行处理
        for(int i=0; i< MeanTotal.size(); i++){
            HashMap<String, ArrayList<StringBuffer>> Data_t = MeanTotal.get(i);
            //datainformation_new_merge得到一个时间片内重新格式化的信息，以dspid为key
            HashMap<String, ArrayList<StringBuffer>> datainformation_new_merge = GetDatainformationMerge(Data_t);
            //以dspid为key进行排序，后续可能用到
            List arrayList_new_merge = SortByDspid(datainformation_new_merge);
            timestamp_dataObject tdo = new timestamp_dataObject(datainformation_new_merge);
            timestamp_dataObjects_list1.add(tdo);
        }
        return timestamp_dataObjects_list1;
    }

    private static ArrayList<HashMap<String,ArrayList<StringBuffer>>> GetMeanTotal(List arrayList) {
        //ArrayList<Integer> num3 = new ArrayList<Integer>(num1);
        List arrayList1 = new ArrayList(arrayList);
        ArrayList<HashMap<String, ArrayList<StringBuffer>>> MeanTotal = new ArrayList<HashMap<String, ArrayList<StringBuffer>>>();
        Long time_interval = Long.valueOf(1000);//毫秒级别时间间隔

        for(int i=0; i< arrayList.size(); i++){
            ArrayList<String> timeTemp = new ArrayList<String>();
            HashMap<String, ArrayList<StringBuffer>> temp = new HashMap<String, ArrayList<StringBuffer>>();
            Map.Entry entry = (Map.Entry)arrayList.get(i);
            String key = entry.getKey().toString();
            ArrayList<StringBuffer> val = (ArrayList<StringBuffer>) entry.getValue();
            String time = key.split("!")[0].toString();
            String time_c = TimeStampProcess.TimeStamp2Date(time);
            String ad_request_id = key.split("!")[1].toString();

            int j=0;
            for(j=i; j<arrayList1.size(); j++){
                Map.Entry entry1 = (Map.Entry)arrayList1.get(j);
                String key1 = entry1.getKey().toString();
                ArrayList<StringBuffer> val1 = (ArrayList<StringBuffer>) entry1.getValue();
                String time1 = key1.split("!")[0].toString();
                if(time1.toString().trim().equals("1484668838571")){
                    for(int j1=0; j1< val.size(); j1++){
                        String t = val.get(j1).toString();
                        //System.out.println(t);
                    }
                }
                if(Long.valueOf(time1) - Long.valueOf(time) < time_interval){
                    temp.put(key1,val1);
                    timeTemp.add(time1);
                }else{
                    break;
                }
            }
            MeanTotal.add(temp);
            //System.out.println(timeTemp.toString());
            Show.Traverse_TimeList(timeTemp);
            System.out.println();
            System.out.println("============= split of timestamp ===============");

            i = j;

        }

        return MeanTotal;
    }

    private static List SortByDspid(HashMap<String, ArrayList<StringBuffer>> datainformation_new_merge) {
        List arrayList_new_merge = new ArrayList(datainformation_new_merge.entrySet());
        Collections.sort(arrayList_new_merge, new Comparator() {
            public int compare(Object o1, Object o2) {
                Map.Entry obj1 = (Map.Entry) o1;
                Map.Entry obj2 = (Map.Entry) o2;
                return(obj1.getKey()).toString().compareTo((obj2.getKey()).toString());
            }
        });
        return arrayList_new_merge;
    }

    /**
     * 解析为：这一个时间片内以dspid为key的数据结构
     * @param Data_t
     * @return
     */
    private static HashMap<String,ArrayList<StringBuffer>> GetDatainformationMerge(HashMap<String, ArrayList<StringBuffer>> Data_t) {
        HashMap<String, ArrayList<StringBuffer>> datainformation_new_merge = new HashMap<String, ArrayList<StringBuffer>>();
        //一个时间片内有很多次投递行为
        for(Map.Entry<String, ArrayList<StringBuffer>> entry: Data_t.entrySet()){
            //每次投递行为有很多dsp竞争
            String key = entry.getKey();
            String time = key.split("!")[0];
            String ad_request_id = key.split("!")[1];
            //ArrayList包含很多家dsp信息，StringBuffer里面是一家dsp的各个字段
            ArrayList<StringBuffer> val = entry.getValue();

            HashMap<String,StringBuffer> datainformation_new = new HashMap<String, StringBuffer>();
            //解析出val属性
            for(int j=0; j< val.size(); j++){

                //每次是一家dsp的信息
                StringBuffer temp = val.get(j);
                String[] split_data = temp.toString().split("\\!");
                String ad_request_id_new = ad_request_id;

                String dspid = null;
                String biding = null;
                String adxPid = null;
                String reqDealId = null;
                String reqPdbDealId = null;
                String ext = null;

                String bidPrice = null;
                String status = null;
                String isWinnerDsp = null;
                String winnerCost = null;
                String advertiserid = null;
                String ideaId = null;
                String adposition = null;
                String resDealId = null;
                String ideaType = null;
                String ideaLength = null;
                String ideaBottomPrice = null;
                String orderid = null;
                String castid = null;
                String pdbDealid = null;
                String cardId = null;
                String positionCount = null;
                int count = 0;
                for (String s : split_data) {
                    if(count == 0){
                        dspid = s;//dspid ，>0 的值， =1 表示ATM
                        count ++;
                    }else if(count == 1){
                        biding = s;//dsp 对请求每个素材位置的出价信息，是个组合字段，每个字段用“,”分割,不同素材位置用“@”分割 ，具体信息见 biding 表
                        count ++;
                        //切割biding字段
                        String[] split_biding = biding.toString().trim().replace(",","#").toString().split("#");
                        bidPrice = split_biding[0];
                        status = split_biding[1];
                        isWinnerDsp = split_biding[2];
                        winnerCost = split_biding[3];
                        advertiserid = split_biding[4];
                        ideaId = split_biding[5];
                        adposition = split_biding[6];
                        resDealId = split_biding[7];
                        ideaType = split_biding[8];
                        ideaLength = split_biding[9];
                        ideaBottomPrice = split_biding[10];
                        orderid = split_biding[11];
                        castid = split_biding[12];
                        pdbDealid = split_biding[13];
                        cardId = split_biding[14];
                        positionCount = split_biding[15];
                    }else if( count == 2){
                        adxPid = s;//adx广告位
                        count ++;
                    }else if( count == 3){
                        reqDealId = s;//请求的普通dealid 列表，可能是多个，用竖线'|'分隔。默认为0
                        count ++;
                    }else if( count == 4){
                        reqPdbDealId = s;//请求的PDB dealid 列表 ，可能是多个，用竖线'|'分隔。如果没有PDB请求记录为0 2016-05-17 增加
                        count ++;
                    }else if(count == 5){
                        ext = s;//dsp 维度的 扩展字段。 rt=xxx:DSP的响应时间，单位为毫秒; dspStatus=1或者0,1 DSP 是正式投放，0 DSP为测试投放;
                    }
                }

                String key_new = dspid;
                StringBuffer val_new = new StringBuffer();
                val_new.append(adxPid).append("!").
                        append(reqDealId).append("!").
                        append(reqPdbDealId).append("!").
                        append(ext).append("!").
                        append(bidPrice).append("!").
                        append(status).append("!").
                        append(isWinnerDsp).append("!").
                        append(winnerCost).append("!").
                        append(advertiserid).append("!").
                        append(ideaId).append("!").
                        append(adposition).append("!").
                        append(resDealId).append("!").
                        append(ideaType).append("!").
                        append(ideaLength).append("!").
                        append(ideaBottomPrice).append("!").
                        append(orderid).append("!").
                        append(castid).append("!").
                        append(pdbDealid).append("!").
                        append(cardId).append("!").
                        append(positionCount).append("!").
                        append(ad_request_id_new);

                if(!datainformation_new_merge.containsKey(key_new)){
                    ArrayList<StringBuffer> l1 = new ArrayList<StringBuffer>();
                    l1.add(val_new);
                    datainformation_new_merge.put(key_new,  l1);
                }else if(datainformation_new_merge.containsKey(key_new)){
                    //datainformation_new_merge.get(key_new).add(val_new);

                    ArrayList<StringBuffer> ll1 = datainformation_new_merge.get(key_new);
                    String key_new1 = key_new;
                    ll1.add(val_new);
                    datainformation_new_merge.remove(key_new);
                    datainformation_new_merge.put(key_new1, ll1);
                }

            }


        }

        //datainformation_new_merge得到一个时间片内重新格式化的信息，以dspid为key


        return datainformation_new_merge;
    }


    private static List GetSortedTRandOthers(List<bid_log_data> c) {
        HashMap<String, ArrayList<StringBuffer>> Qz_bid = new HashMap<String, ArrayList<StringBuffer>>();
        ArrayList<StringBuffer> asb = null;

        for(int i=0; i< c.size(); i++){
            //把ad_dsp_info字段数据load到dspinfo_data
            String Qz = c.get(i).getQz();
            String[] dspitems = c.get(i).getAd_dsp_info().trim().split("#");

            if(dspitems.length == 0){
                continue;
            }
            asb = new ArrayList<StringBuffer>();

            for(int j=0; j< dspitems.length; j++){
                System.out.println();
                String replaceStr = dspitems[j].trim().replace('^','!');
                String[] dspObjects = replaceStr.trim().split("!");//每家dsp的属性
                /*String adxPid_z = dspObjects[2].toString();
                if(Integer.valueOf(adxPid_z) != 41) {
                    System.out.println("广告位id：" + adxPid_z);
                }*/
                StringBuffer sb = new StringBuffer();
                int k=0;

                for(k=0; k< dspObjects.length-1; k++){
                    sb.append(dspObjects[k]);
                    sb.append("!");
                }
                sb.append(dspObjects[k]);
                //System.out.println(sb.toString());
                asb.add(sb);
            }

            //System.out.println(Qz);
            Qz_bid.put(Qz, asb);
            //System.out.println(asb.toString());
        }

        //按照时间戳进行排序
        List arrayList = new ArrayList(Qz_bid.entrySet());
        Collections.sort(arrayList, new Comparator() {
            public int compare(Object o1, Object o2) {
                Map.Entry obj1 = (Map.Entry) o1;
                Map.Entry obj2 = (Map.Entry) o2;
                return (obj1.getKey().toString().split("!")[0]).compareTo((String) obj2.getKey().toString().split("!")[0]);
            }
        });

        return arrayList;
    }
}
