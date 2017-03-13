package BidEngine.FunctionCollection;

import BidEngine.DataStruct.timestamp_dataObject;
import BidEngine.DataStruct.timestamp_dataObjectUpdate;

import java.util.*;

import static BidEngine.FunctionCollection.function_collection.GetMyRandom;

/**
 * Created by Administrator on 2017/3/13.
 */
public class function_collection_withP_tactics {
    /**
     * 对adxpid=56，dspid=11167的竞价数据，统计在每个ideaid出价之前事先给予一个random值。
     若这个ideaid最后赢了，并且它事先的random值大于0.5，则出价，也就是所对应的各项数据不变；
     若这个ideaid最后赢了，而且它事先的random值小于等于0.5，则不出价，那么各项指标需要更改，并且把对应的出价bidprice压入队列queue中，每个ideaid维护这样一条queue；
     若这个ideaid最后输了，如果在时间片内它之前的时间段，有这个ideaid赢了而random值小于0.5的情况，那么就把该ideaid所对应的queue取出一个bidprice，替换掉本bidprice重新参与竞价，
     在本广告位adxpid同一次请求requestid的情况下和其他dspid进行bidprice数据对比
     ， 如果它最后还是没有赢，就不改变其他状态数据，如果它最后赢了，就改变其他状态数据。
     */
    public static void Pintellegance_IdeaId(List<timestamp_dataObjectUpdate> timestamp_dataObjects_list, HashMap<Double, Double> ideaid_budget) {
        for(int i=0; i< timestamp_dataObjects_list.size(); i++){
            //每个时间片
            //每个ideaid维护一条队列Queue<String>
            HashMap<String, Queue<String>> ideaQueue = new HashMap<String, Queue<String>>();
            timestamp_dataObjectUpdate timedataObject = timestamp_dataObjects_list.get(i);
            HashMap<String, HashMap<String, ArrayList<String>>> adrequestMap = timedataObject.getDatainformation_new_merge_update();
            for(Map.Entry<String, HashMap<String, ArrayList<String>>> entry : adrequestMap.entrySet()){
                String adrequestId = entry.getKey();
                HashMap<String, ArrayList<String>> ideaMap = entry.getValue();
                //对一次请求，选出bidprice最大的那个<ideaId, List<bidprice....>>作为dsp代理要出的出价价格
                ArrayList<String> BiggestIdeaInfo = GetBiggestBidOfIdeas(ideaMap);
                System.out.println("winnercost: " + BiggestIdeaInfo.get(2) + "  iswinnerdsp: " + BiggestIdeaInfo.get(3));
                String iswinnerdsp = BiggestIdeaInfo.get(3);
                String ideaid = BiggestIdeaInfo.get(0);
                String winnercost = BiggestIdeaInfo.get(2);
                String bidprice = BiggestIdeaInfo.get(4);
                //给这个dsp端的idea素材一个random（0 ~ 1)
                Double f = GetMyRandom();
                if(f > 0.5 && iswinnerdsp.equals("1")){
                    //System.out.println("winnercost: " + winnercost + "  iswinnerdsp: " + iswinnerdsp);
                    //正常出价，什么都不管
                }else if(f < 0.5 && iswinnerdsp.equals("1")){
                    //若这个ideaid最后赢了，而且它事先的random值小于等于0.5，则不出价，那么各项指标需要更改，并且把对应的出价bidprice压入队列queue中，
                    // 每个ideaid维护这样一条queue；
                    iswinnerdsp = "0";
                    winnercost = "0";
                    Queue<String> q = ideaQueue.get(ideaid);
                    if(q == null || q.equals("")){
                        q = new LinkedList<String>();
                    }
                    q.offer(bidprice);
                    ideaQueue.put(ideaid, q);
                }else if(iswinnerdsp.equals("0")){
                    
                }


            }
        }
    }

    public static ArrayList<String> GetBiggestBidOfIdeas(HashMap<String, ArrayList<String>> ideaMap){
        Double max = 0.0;
        ArrayList<String> ideainformationOfBiggest = new ArrayList<String>();
        for(Map.Entry<String, ArrayList<String>> entry1 : ideaMap.entrySet()){
            String ideaId = entry1.getKey();
            ArrayList<String> ideaDataList = entry1.getValue();
            String resDealId = ideaDataList.get(1);
            String winnercost = ideaDataList.get(2);
            String iswinnerDsp = ideaDataList.get(3);
            String bidPrice = ideaDataList.get(4);
            Double bidprice = Double.valueOf(bidPrice);
            if(bidprice >= max){
                max = bidprice;
                ideainformationOfBiggest = new ArrayList<String>(ideaDataList);
            }
        }
        return ideainformationOfBiggest;
    }
}
