package BidEngine.FunctionCollection;

import BidEngine.DataStruct.dspid_allMeanOther;
import BidEngine.DataStruct.dspid_allMeanOther_flat;
import BidEngine.DataStruct.timestamp_dataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10.
 */
public class function_collection_withP_update {
    public static void Pintellegance_dspId(List<timestamp_dataObject> timestamp_dataObjects_list, Integer dspid_test) {

        //List<dspid_allMeanOther_collection> dac_list = new ArrayList<dspid_allMeanOther_collection>();
        HashMap<Double, Double> IdeaAlltimeBidprice = new HashMap<Double, Double>();
        //所有时间片，进入11167dsp代理的<ideaid, allbidprice>
        HashMap<Double, Double> allInto = new HashMap<Double, Double>();
        //所有时间片，进入11167dsp代理的<ideaid, budget预算>
        HashMap<Double, Double> allIdeaBudget = new HashMap<Double, Double>();
        for (int i = 0; i < timestamp_dataObjects_list.size(); i++) {
            //对于一个时间片进行操作
            timestamp_dataObject tdo = timestamp_dataObjects_list.get(i);
            HashMap<String, ArrayList<StringBuffer>> datainformation_new_merge = tdo.getDatainformation_new_merge();
            List<dspid_allMeanOther> damo_list = new ArrayList<dspid_allMeanOther>();
            List<dspid_allMeanOther_flat> damof_list = new ArrayList<dspid_allMeanOther_flat>();
            int timestamp_index = i;

            for (Map.Entry<String, ArrayList<StringBuffer>> entry : datainformation_new_merge.entrySet()) {
                String dspid = entry.getKey();
                Map<Double, ArrayList<StringBuffer>> ConcernedstatisticOfIdeaid = null;
                // 只对特定的dspid做研究
                if(dspid.equals(String.valueOf(dspid_test))){
                    System.out.println("进入11167");
                    Map<Double, ArrayList<StringBuffer>> statisticOfIdeaid = new HashMap<Double, ArrayList<StringBuffer>>();
                    ConcernedstatisticOfIdeaid = new HashMap<Double, ArrayList<StringBuffer>>();
                    //元素stringbuffer里面是每次request的时候该dsp对应的idea1#idea2#idea3...
                    ArrayList<StringBuffer> result = entry.getValue();
                    /**
                     * 一个dspid的各个素材ideaid的各项数据指标
                     * 总的bidprice，平均bidprice，总的winnercost，平均winnercost，budget（待加）
                     */
                    // 解析出ideaid为key, 其他字段为result原封不动的<key, value>
                    //statisticOfIdeaid = Getdealdata(result);
                    //ShowTimeDspDealData(timestamp_index, dspid, statisticOfIdeaid);
                    //关注指标(一个时间片内）
                    ConcernedstatisticOfIdeaid = GetConcernedDealData(result);
                    //得到各个ideaid进入11167dspid代理的allbidprice
                    //得到idea和对应的总的bidprice
                    HashMap<Double, Double> IdeaAllBidpriceMap = ShowTimeDspDealConcernedData(timestamp_index, dspid, ConcernedstatisticOfIdeaid);
                    for(Map.Entry<Double, Double> temp2 : IdeaAllBidpriceMap.entrySet()){
                        Double key = temp2.getKey();
                        Double value = temp2.getValue();
                        Double l = allInto.get(key);
                        if(l == null){
                            l = value;
                        }else{
                            l += value;
                        }
                        allInto.put(key, l);
                    }
                }
            //-2
            }



            //处理由于P值影响，统计出相应指标
            /*for (Map.Entry<String, ArrayList<StringBuffer>> entry : datainformation_new_merge.entrySet()) {
                String dspid = entry.getKey();
            }*/
            //-1
        }
        //显示总的ideaid, bidprice(所有时间片）
        ShowAllIdeaBidprice(allInto);
        //把所有时间片的bidprice×100作为预算
        allIdeaBudget = getAllIdeaBudgetMap(allIdeaBudget);

        //根据<ideaid, allbidprice>重新计算： 加入策略,在每个idea出价之前给予一个random值
        /**
         * 对adxpid=56，dspid=11167的竞价数据，统计在每个ideaid出价之前事先给予一个random值。
         若这个ideaid最后赢了，并且它事先的random值大于0.5，则出价，也就是所对应的各项数据不变；
         若这个ideaid最后赢了，而且它事先的random值小于等于0.5，则不出价，那么各项指标需要更改，并且把对应的出价bidprice压入队列queue中，每个ideaid维护这样一条queue；
         若这个ideaid最后输了，如果在时间片内它之前的时间段，有这个ideaid赢了而random值小于0.5的情况，那么就把该ideaid所对应的queue取出一个bidprice，替换掉本bidprice重新参与竞价，
         在本广告位adxpid同一次请求requestid的情况下和其他dspid进行bidprice数据对比
         ， 如果它最后还是没有赢，就不改变其他状态数据，如果它最后赢了，就改变其他状态数据。
         */
        ////////////////////////////////////////////////////////////////////////////
        /*for (int i = 0; i < timestamp_dataObjects_list.size(); i++) {
            //对于一个时间片进行操作
            timestamp_dataObject tdo = timestamp_dataObjects_list.get(i);
            HashMap<String, ArrayList<StringBuffer>> datainformation_new_merge = tdo.getDatainformation_new_merge();
            int timestamp_index = i;

            for (Map.Entry<String, ArrayList<StringBuffer>> entry : datainformation_new_merge.entrySet()) {
                String dspid = entry.getKey();
                Map<Double, ArrayList<StringBuffer>> ConcernedstatisticOfIdeaid = null;
                // 只对特定的dspid做研究
                if(dspid.equals(String.valueOf(dspid_test))){
                    System.out.println("进入11167");
                    Map<Double, ArrayList<StringBuffer>> statisticOfIdeaid = new HashMap<Double, ArrayList<StringBuffer>>();
                    ConcernedstatisticOfIdeaid = new HashMap<Double, ArrayList<StringBuffer>>();

                    ArrayList<StringBuffer> result = entry.getValue();
                    *//**
                     * 一个dspid的各个素材ideaid的各项数据指标
                     * 总的bidprice，平均bidprice，总的winnercost，平均winnercost，budget（待加）
                     *//*
                    // 解析出ideaid为key, 其他字段为result原封不动的<key, value>
                    statisticOfIdeaid = Getdealdata(result);
                    //ShowTimeDspDealData(timestamp_index, dspid, statisticOfIdeaid);
                    //关注指标(一个时间片内）
                    ConcernedstatisticOfIdeaid = GetConcernedDealData(result);
                    //就是要看一个时间片内的影响
                    PtoBid(ConcernedstatisticOfIdeaid, allInto);
                    //得到各个ideaid进入11167dspid代理的allbidprice
                    HashMap<Double, Double> IdeaAllBidpriceMap = ShowTimeDspDealConcernedData(timestamp_index, dspid, ConcernedstatisticOfIdeaid);
                    for(Map.Entry<Double, Double> temp2 : IdeaAllBidpriceMap.entrySet()){
                        Double key = temp2.getKey();
                        Double value = temp2.getValue();
                        Double l = allInto.get(key);
                        if(l == null){
                            l = value;
                        }else{
                            l += value;
                        }
                        allInto.put(key, l);
                    }
                }
                //-2
            }



            //处理由于P值影响，统计出相应指标
            *//*for (Map.Entry<String, ArrayList<StringBuffer>> entry : datainformation_new_merge.entrySet()) {
                String dspid = entry.getKey();
            }*//*
            //-1
        }*/

        ////////////////////////////////////////////////////////////////////////////

    }

    public static HashMap<Double, Double> getAllIdeaBudgetMap(HashMap<Double, Double> allInto){

        HashMap<Double, Double> IdeaBudgetMap = new HashMap<Double, Double>();
        for(Map.Entry<Double, Double> entry : allInto.entrySet()){
            //System.out.println("素材id: " + entry.getKey() + " 的总bidprice： " + entry.getValue());
            Double IdeaId = entry.getKey();
            Double AllBidprice = entry.getValue();
            Double Budget = AllBidprice * 100;
            IdeaBudgetMap.put(IdeaId, Budget);
        }
        return IdeaBudgetMap;
    }


    public static void ShowAllIdeaBidprice(HashMap<Double, Double> allInto){
        System.out.println("进入11167的所有idea素材各自的总bidprice：");
        for(Map.Entry<Double, Double> entry : allInto.entrySet()){
            System.out.println("素材id: " + entry.getKey() + " 的总bidprice： " + entry.getValue());
        }
    }

    public static HashMap<Double, Double>  ShowTimeDspDealConcernedData(int timestamp_index, String dspid, Map<Double, ArrayList<StringBuffer>> ConcernedstatisticOfIdeaid){

        //ArrayList<Double> IdeaAllBidpriceWinnercost = new ArrayList<Double>();
        HashMap<Double, Double> IdeaAllBidpriceWinnercostMap = new HashMap<Double, Double>();
        System.out.println("第 " + timestamp_index + " 个时间片： ");
        System.out.println("Dspid: " + dspid);
        for(Map.Entry<Double, ArrayList<StringBuffer>> entry : ConcernedstatisticOfIdeaid.entrySet()){
            Double ideaid = entry.getKey();
            System.out.println("素材id： " + ideaid);
            ArrayList<StringBuffer> val = entry.getValue();
            /*for(int j=0; j< val.size(); j++){
                System.out.println(val.get(j).toString());
            }*/

            Double BidPrice_idea = 0.0;
            Double WinnerCost_idea = 0.0;
            for(int j=0; j< val.size(); j++){
                String[] val1 = val.get(j).toString().split("#");
                System.out.println("    resDealid: " + val1[0]);
                System.out.println("    bidPrice: " + val1[1]);
                System.out.println("    winnercost: " + val1[2]);
                System.out.println("    status: " + val1[3]);
                System.out.println("    iswinnerdsp: " + val1[4]);
                System.out.println("---");
                BidPrice_idea += Double.valueOf(val1[1]);
                WinnerCost_idea += Double.valueOf(val1[2]);

            }
            System.out.println("    All Bidprice of idea: " + BidPrice_idea);
            System.out.println("    All Winnercost of idea: " + WinnerCost_idea);
            /*ArrayList<Double> temp = new ArrayList<Double>();
            temp.add(BidPrice_idea);
            temp.add(WinnerCost_idea);*/
            Double temp = BidPrice_idea;
            IdeaAllBidpriceWinnercostMap.put(ideaid, temp);

        }
        return IdeaAllBidpriceWinnercostMap;
    }

    public static HashMap<Double, ArrayList<StringBuffer>> GetConcernedDealData(ArrayList<StringBuffer> result){
        HashMap<Double, ArrayList<StringBuffer>> ConcernedstatisticOfIdeaid = new HashMap<Double, ArrayList<StringBuffer>>();
        for(int j=0; j< result.size(); j++){
            //不同次request，各次request都存放着各个idea的信息
            //一次request，各个idea的信息以#隔开
            StringBuffer sb = result.get(j);
            String[] sb_do = sb.toString().split("#");
            for(int z=0; z< sb_do.length; z++){
                String temp = sb_do[z];
                Double[] bidprice_winnercost2 = new Double[6];
                try {
                    bidprice_winnercost2 = ComputebidpriceAndWinnercostAndIdeaIdAndresDealId(temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Double ideaId = bidprice_winnercost2[0];
                Double resdealid = bidprice_winnercost2[1];
                Double bidPrice = bidprice_winnercost2[2];
                Double winnerCost = bidprice_winnercost2[3];
                Double status = bidprice_winnercost2[4];
                Double iswinnerdsp = bidprice_winnercost2[5];
                StringBuffer sb1 = new StringBuffer();
                sb1.append(resdealid).append("#").append(bidPrice).append("#").append(winnerCost).append("#").append(status).append("#").append(iswinnerdsp);
                ArrayList<StringBuffer> l = ConcernedstatisticOfIdeaid.get(ideaId);
                if(l == null){
                    l = new ArrayList<StringBuffer>();
                }
                l.add(sb1);
                ConcernedstatisticOfIdeaid.put(ideaId, l);
            }
        }
        return ConcernedstatisticOfIdeaid;
    }

    public static void ShowTimeDspDealData(int timestamp_index, String dspid, Map<Double, ArrayList<StringBuffer>> statisticOfIdeaid){
        System.out.println("第 " + timestamp_index + " 个时间片： ");
        System.out.println("Dspid: " + dspid);
        for(Map.Entry<Double, ArrayList<StringBuffer>> entry : statisticOfIdeaid.entrySet()){
            Double ideaid = entry.getKey();
            System.out.println("素材id： " + ideaid);
            ArrayList<StringBuffer> val = entry.getValue();
            for(int j=0; j< val.size(); j++){
                System.out.println(val.get(j).toString());
            }
            System.out.println("---");
        }
    }


    /*public static HashMap<Double, ArrayList<StringBuffer>> Getdealdata(ArrayList<StringBuffer> result){
        HashMap<Double, ArrayList<StringBuffer>> statisticOfIdeaid = new HashMap<Double, ArrayList<StringBuffer>>();
        for(int j=0; j< result.size(); j++){
            StringBuffer sb = result.get(j);
            Double[] bidprice_winnercost2 = new Double[6];
            try {
                bidprice_winnercost2 = ComputebidpriceAndWinnercostAndIdeaIdAndresDealId(sb);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Double ideaId = bidprice_winnercost2[0];
            Double resdealid = bidprice_winnercost2[1];
            Double bidPrice = bidprice_winnercost2[2];
            Double winnerCost = bidprice_winnercost2[3];
            Double status = bidprice_winnercost2[4];
            Double iswinnerdsp = bidprice_winnercost2[5];
            ArrayList<StringBuffer> l = statisticOfIdeaid.get(ideaId);
            if(l == null){
                l = new ArrayList<StringBuffer>();

            }
            l.add(sb);
            statisticOfIdeaid.put(ideaId, l);
        }
        return statisticOfIdeaid;
    }*/

    private static Double[] ComputebidpriceAndWinnercostAndIdeaIdAndresDealId(String sb) {

        String[] st = sb.trim().split("!");

        String ideaId = st[5].toString().trim();
        System.out.println("ideaId: " + ideaId);

        String resDealId = st[7].toString().trim();
        System.out.println("resDealId: " + resDealId);

        String isWinnerDsp = st[2].toString().trim();//是否在这个位置胜出,值为0、1. 1为胜出
        String status = st[1].toString().trim();//一个位置的出价状态，包含ATM对该位置的出价状态
        System.out.println("status: "+ status);

        String bidP = st[0].toString().trim();
        String winnerC = st[3].toString().trim();
        //System.out.println(bidP);

        /*Double bidPrice = Double.valueOf(st[4].toString().trim());
        Double winnercost = Double.valueOf(st[8]);*/
        /**
         * test
         */
        Double bidPrice = 0.0;
        //System.out.println(bidP);
        if(bidP != null && !bidP.equals("")){
            bidPrice = Double.parseDouble(bidP);
        }

        Double ideaid = 0.0;
        if(ideaId != null && !ideaId.equals("")){
            ideaid = Double.valueOf(ideaId);
        }

        Double resdealid = 0.0;
        if(resDealId != null && !resDealId.equals("")){
            resdealid = Double.valueOf(resDealId);
        }


        Double winnerCost = 0.0;
        if((winnerC != null) && (!winnerC.equals(""))){
            winnerCost = Double.valueOf(winnerC);
        }


        Double mystatus = 0.0;
        if(status != null && !status.equals("")){
            mystatus = Double.valueOf(status);
        }
        Double _status = mystatus;

        Double isWinnerDspdata = 0.0;
        if(isWinnerDsp != null && !isWinnerDsp.equals("")){
            isWinnerDspdata = Double.valueOf(isWinnerDsp);
        }
        Double _isWinnerDsp = isWinnerDspdata;


        System.out.println("ideaId: " + ideaid + "resDealId: " + resdealid + "status: "+ _status + " isWinnerDsp: " + _isWinnerDsp + " bidPrice: "+ bidPrice + "  winnerCost: " + winnerCost);

        Double[] bidPriceAndWinnerCost = new Double[6];
        bidPriceAndWinnerCost[0] = ideaid;
        bidPriceAndWinnerCost[1] = resdealid;
        bidPriceAndWinnerCost[2] = bidPrice;
        bidPriceAndWinnerCost[3] = winnerCost;
        bidPriceAndWinnerCost[4] = _status;
        bidPriceAndWinnerCost[5] = _isWinnerDsp;


        return bidPriceAndWinnerCost;
    }

}
