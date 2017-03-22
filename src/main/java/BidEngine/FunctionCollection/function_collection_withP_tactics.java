package BidEngine.FunctionCollection;

import BidEngine.DataStruct.timestamp_dataObject;
import BidEngine.DataStruct.timestamp_dataObjectUpdate;
import com.esotericsoftware.kryo.util.IdentityMap;

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
        //自定义一个概率值P，大于它就出价
        Double myP = 0.5;
        HashMap<String, Double> allIdeaBidprice = new HashMap<String, Double>();
        //每个ideaid维护一条队列Queue<String>
        HashMap<String, Queue<String>> ideaQueue = new HashMap<String, Queue<String>>();

        for(int i=0; i< timestamp_dataObjects_list.size(); i++){
            //每个时间片
            //每个ideaid维护一条队列Queue<String>
            //HashMap<String, Queue<String>> ideaQueue = new HashMap<String, Queue<String>>();
            timestamp_dataObjectUpdate timedataObject = timestamp_dataObjects_list.get(i);
            HashMap<String, HashMap<String, ArrayList<String>>> adrequestMap = timedataObject.getDatainformation_new_merge_update();
            //统计一个时间片内每个ideaid对应的概率
            HashMap<String, Double> ideaP = new HashMap<String, Double>();
            HashMap<String, Double> ideaBp = new HashMap<String, Double>();
            for(Map.Entry<String, HashMap<String, ArrayList<String>>> entry : adrequestMap.entrySet()){
                String adrequestId = entry.getKey();
                HashMap<String, ArrayList<String>> ideaMap = entry.getValue();
                for(Map.Entry<String, ArrayList<String>> entry1 : ideaMap.entrySet()){
                    String ideaTemp = entry1.getKey();
                    ArrayList<String> valTemp = entry1.getValue();
                    String bidPrice = valTemp.get(4);
                    Double myBp = Double.valueOf(bidPrice);
                    Double l = ideaBp.get(ideaTemp);
                    if(l == null){
                        l = 0.0;
                    }
                    l += myBp;
                    ideaBp.put(ideaTemp, l);
                    Double l1 = allIdeaBidprice.get(ideaTemp);
                    if(l1 == null){
                        l1 = 0.0;
                    }
                    l1 += myBp;
                    if(!ideaTemp.equals("0")){
                        allIdeaBidprice.put(ideaTemp, l1);
                    }

                }
            }
            //ideaBp得到一个时间片的<idea, allbidprice>
        }
        System.out.println("===allIdeaBidprice===");
        System.out.println(allIdeaBidprice.toString());
        //得到所有素材对应的预算
        HashMap<String, Double> IdeaBudget = new HashMap<String, Double>();
        for(Map.Entry<String, Double> entry : allIdeaBidprice.entrySet()){
            String key = entry.getKey();
            Double bidprice = entry.getValue();
            Double budget = bidprice * 100;
            if(budget != 0){
                System.out.println("currBudget: " + budget);
            }
            IdeaBudget.put(key, budget);
        }
        System.out.println("原始的预算： ");
        System.out.println(IdeaBudget.toString());

        //==============================================================================
        for(int i=0; i< timestamp_dataObjects_list.size(); i++){
            //每个时间片
            timestamp_dataObjectUpdate timedataObject = timestamp_dataObjects_list.get(i);
            HashMap<String, HashMap<String, ArrayList<String>>> adrequestMap = timedataObject.getDatainformation_new_merge_update();
            //统计一个时间片内每个ideaid对应的概率
            HashMap<String, Double> ideaP = new HashMap<String, Double>();
            HashMap<String, Double> ideaBp = new HashMap<String, Double>();

            // 给一个时间片所有的idea填充先验概率P
            for(Map.Entry<String, HashMap<String, ArrayList<String>>> entry : adrequestMap.entrySet()){
                String adrequestid = entry.getKey();
                //ArrayList temp1.add(ideaId);
                /*temp1.add(resDealId);
                temp1.add(winnerCost);
                temp1.add(isWinnerDsp);
                temp1.add(bidPrice);*/
                HashMap<String, ArrayList<String>> ideaMap = entry.getValue();
                for(Map.Entry<String, ArrayList<String>> entry1 : ideaMap.entrySet()){
                    String ideaTemp = entry1.getKey();
                    Double value1 = ideaP.get(ideaTemp);
                    if(value1 == null){
                        value1 = GetMyRandom();
                    }
                    ideaP.put(ideaTemp, value1);

                    Double value = ideaP.get(ideaTemp);
                    if(value == null){
                        value = GetMyRandom();
                    }
                    ideaP.put(ideaTemp, value);
                }
            }
            // ideaP中得到<idea, P>

            //这时ideaP就拥有了这个时间片的idea对应的概率P
            for(Map.Entry<String, HashMap<String, ArrayList<String>>> entry : adrequestMap.entrySet()){
                String adrequestId = entry.getKey();
                HashMap<String, ArrayList<String>> ideaMap = entry.getValue();
                //对一次请求，选出bidprice最大的那个<ideaId, List<bidprice....>>作为dsp代理要出的出价价格
                //ArrayList<String> BiggestIdeaInfo = GetBiggestBidOfIdeas(ideaMap);
                /*System.out.println("winnercost: " + BiggestIdeaInfo.get(2) + "  iswinnerdsp: " + BiggestIdeaInfo.get(3));
                String iswinnerdsp = BiggestIdeaInfo.get(3);
                String ideaid = BiggestIdeaInfo.get(0);
                String winnercost = BiggestIdeaInfo.get(2);
                String bidprice = BiggestIdeaInfo.get(4);*/
                double maxbidprice = 0;
                String maxideaid = null;
                for(Map.Entry<String, ArrayList<String>> entry1 : ideaMap.entrySet()) {
                    String ideaTemp = entry1.getKey();
                    ArrayList<String> valTemp = entry1.getValue();
                    String iswinnerdsp = valTemp.get(3);
                    String ideaid = valTemp.get(0);
                    String bidprice = valTemp.get(4);
                    String winnercost = valTemp.get(2);
                    if(Double.valueOf(bidprice) > maxbidprice){
                        maxbidprice = Double.valueOf(bidprice);
                        maxideaid = ideaid;
                    }
                }

                for(Map.Entry<String, ArrayList<String>> entry1 : ideaMap.entrySet()){
                    String ideaTemp = entry1.getKey();
                    ArrayList<String> valTemp = entry1.getValue();
                    String iswinnerdsp = valTemp.get(3);
                    //System.out.println("ideaid: "+ideaTemp);
                    String ideaid = valTemp.get(0);
                    String bidprice = valTemp.get(4);
                    String winnercost = valTemp.get(2);
                    //根据ideatemp取出预算budget
                    Double budget = IdeaBudget.get(ideaTemp);
                    //得到ideatemp对应的概率P
                    Double p = ideaP.get(ideaTemp);
                    //=====================
                    //接入策略
                    /**
                     * 对adxpid=56，dspid=11167的竞价数据，统计在每个ideaid出价之前事先给予一个random值。
                     若这个ideaid最后赢了，并且它事先的random值大于0.5，则出价，也就是所对应的各项数据不变；
                     若这个ideaid最后赢了，而且它事先的random值小于等于0.5，则不出价，那么各项指标需要更改，并且把对应的出价bidprice压入队列queue中，每个ideaid维护这样一条queue；
                     若这个ideaid最后输了，如果在时间片内它之前的时间段，有这个ideaid赢了而random值小于0.5的情况，那么就把该ideaid所对应的queue取出一个bidprice，替换掉本bidprice重新参与竞价，
                     在本广告位adxpid同一次请求requestid的情况下和其他dspid进行bidprice数据对比
                     ， 如果它最后还是没有赢，就不改变其他状态数据，如果它最后赢了，就改变其他状态数据。
                     */
                    //数据量不够，比来比去，bidprice就是maxbidprice
                    if(Double.valueOf(bidprice) == maxbidprice && p > myP){
                        if(Integer.valueOf(iswinnerdsp) == 1){
                            //扣预算
                            Double currbudget = IdeaBudget.get(ideaid);
                            currbudget -= Double.valueOf(winnercost);
                            IdeaBudget.put(ideaid, currbudget);
                        }else{
                            //什么都不干
                            continue;
                        }

                    }else if(Double.valueOf(bidprice) == maxbidprice && p < myP && Double.valueOf(bidprice) != 0){
                        //无论他竞价是否成功，他都不出
                        Queue<String> queue = ideaQueue.get(ideaid);
                        if(queue == null){
                            queue = new LinkedList<String>();
                        }
                        queue.offer(bidprice);
                        ideaQueue.put(ideaid, queue);
                        bidprice = "0";
                    }else if(Double.valueOf(bidprice) != maxbidprice && p < myP){
                        //什么都不干
                        continue;
                    }else if(Double.valueOf(bidprice) != maxbidprice && p > myP){
                        //去队列中取元素
                        Queue<String> queue = ideaQueue.get(ideaid);
                        String bidpricesave = "0";
                        if(queue != null){
                            bidpricesave = queue.poll();
                        }
                        //用这个bidpricesave去竞争
                        if(Double.valueOf(bidpricesave) >= maxbidprice){
                            // 用这个bidpricesave提交给dsp，并同其他dsp去进行竞价，这里作为模拟竞价成功，扣费也只扣自己的bidprice，而不是广义第二高价
                            // 在这个地方实际上是要去odps中取同一个request的其他dsp中，iswinnercost的那个dsp的素材的bidprice作为对比
                            //扣预算
                            Double currbudget = IdeaBudget.get(ideaid);
                            currbudget -= Double.valueOf(Double.valueOf(bidpricesave));
                            IdeaBudget.put(ideaid, currbudget);
                        }else{
                            //没竞价成功，不做处理
                        }
                    }else{
                        continue;
                    }

                }

            }
        }

        System.out.println("加入策略后的预算： ");
        System.out.println(IdeaBudget.toString());
    }

    public static void WithoutPintellegance_IdeaId(List<timestamp_dataObjectUpdate> timestamp_dataObjects_list, HashMap<Double, Double> ideaid_budget) {

        HashMap<String, Double> allIdeaBidprice = new HashMap<String, Double>();

        for(int i=0; i< timestamp_dataObjects_list.size(); i++){
            //每个时间片
            //每个ideaid维护一条队列Queue<String>
            //HashMap<String, Queue<String>> ideaQueue = new HashMap<String, Queue<String>>();
            timestamp_dataObjectUpdate timedataObject = timestamp_dataObjects_list.get(i);
            HashMap<String, HashMap<String, ArrayList<String>>> adrequestMap = timedataObject.getDatainformation_new_merge_update();
            //统计一个时间片内每个ideaid对应的概率
            HashMap<String, Double> ideaP = new HashMap<String, Double>();
            HashMap<String, Double> ideaBp = new HashMap<String, Double>();
            for(Map.Entry<String, HashMap<String, ArrayList<String>>> entry : adrequestMap.entrySet()){
                String adrequestId = entry.getKey();
                HashMap<String, ArrayList<String>> ideaMap = entry.getValue();
                for(Map.Entry<String, ArrayList<String>> entry1 : ideaMap.entrySet()){
                    String ideaTemp = entry1.getKey();
                    ArrayList<String> valTemp = entry1.getValue();
                    String bidPrice = valTemp.get(4);
                    Double myBp = Double.valueOf(bidPrice);
                    Double l = ideaBp.get(ideaTemp);
                    if(l == null){
                        l = 0.0;
                    }
                    l += myBp;
                    ideaBp.put(ideaTemp, l);
                    Double l1 = allIdeaBidprice.get(ideaTemp);
                    if(l1 == null){
                        l1 = 0.0;
                    }
                    l1 += myBp;
                    if(!ideaTemp.equals("0")){
                        allIdeaBidprice.put(ideaTemp, l1);
                    }

                }
            }
            //ideaBp得到一个时间片的<idea, allbidprice>
        }
        //System.out.println("===allIdeaBidprice===");
        //System.out.println(allIdeaBidprice.toString());
        //得到所有素材对应的预算
        HashMap<String, Double> IdeaBudget = new HashMap<String, Double>();
        for(Map.Entry<String, Double> entry : allIdeaBidprice.entrySet()){
            String key = entry.getKey();
            Double bidprice = entry.getValue();
            Double budget = bidprice * 100;
            if(budget != 0){
                //System.out.println("currBudget: " + budget);
            }
            IdeaBudget.put(key, budget);
        }
        //System.out.println("原始的预算： ");
        //System.out.println(IdeaBudget.toString());

        //==============================================================================
        for(int i=0; i< timestamp_dataObjects_list.size(); i++){
            //每个时间片
            timestamp_dataObjectUpdate timedataObject = timestamp_dataObjects_list.get(i);
            HashMap<String, HashMap<String, ArrayList<String>>> adrequestMap = timedataObject.getDatainformation_new_merge_update();


            for(Map.Entry<String, HashMap<String, ArrayList<String>>> entry : adrequestMap.entrySet()){
                String adrequestId = entry.getKey();
                HashMap<String, ArrayList<String>> ideaMap = entry.getValue();
                //对一次请求，选出bidprice最大的那个<ideaId, List<bidprice....>>作为dsp代理要出的出价价格
                //ArrayList<String> BiggestIdeaInfo = GetBiggestBidOfIdeas(ideaMap);
                /*System.out.println("winnercost: " + BiggestIdeaInfo.get(2) + "  iswinnerdsp: " + BiggestIdeaInfo.get(3));
                String iswinnerdsp = BiggestIdeaInfo.get(3);
                String ideaid = BiggestIdeaInfo.get(0);
                String winnercost = BiggestIdeaInfo.get(2);
                String bidprice = BiggestIdeaInfo.get(4);*/
                double maxbidprice = 0;
                String maxideaid = null;
                for(Map.Entry<String, ArrayList<String>> entry1 : ideaMap.entrySet()) {
                    String ideaTemp = entry1.getKey();
                    ArrayList<String> valTemp = entry1.getValue();
                    String iswinnerdsp = valTemp.get(3);
                    String ideaid = valTemp.get(0);
                    String bidprice = valTemp.get(4);
                    String winnercost = valTemp.get(2);
                    if(Double.valueOf(bidprice) > maxbidprice){
                        maxbidprice = Double.valueOf(bidprice);
                        maxideaid = ideaid;
                    }
                }

                for(Map.Entry<String, ArrayList<String>> entry1 : ideaMap.entrySet()){
                    String ideaTemp = entry1.getKey();
                    ArrayList<String> valTemp = entry1.getValue();
                    String iswinnerdsp = valTemp.get(3);
                    //System.out.println("ideaid: "+ideaTemp);
                    String ideaid = valTemp.get(0);
                    String bidprice = valTemp.get(4);
                    String winnercost = valTemp.get(2);
                    //根据ideatemp取出预算budget
                    Double budget = IdeaBudget.get(ideaTemp);
                    //=====================
                    //不接入策略
                    if(Double.valueOf(bidprice) == maxbidprice){
                        if(Integer.valueOf(iswinnerdsp) == 1){
                            //扣预算
                            Double currbudget = IdeaBudget.get(ideaid);
                            currbudget -= Double.valueOf(winnercost);
                            IdeaBudget.put(ideaid, currbudget);
                        }else{
                            continue;
                        }
                    }
                    else if(Double.valueOf(bidprice) != maxbidprice){
                        //什么都不干
                        continue;
                    }else{
                        continue;
                    }

                }

            }
        }

        System.out.println("不加入策略的预算： ");
        System.out.println(IdeaBudget.toString());
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
