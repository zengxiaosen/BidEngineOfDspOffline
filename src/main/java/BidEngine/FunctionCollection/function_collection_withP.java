package BidEngine.FunctionCollection;

import BidEngine.DataStruct.dspid_allMeanOther;
import BidEngine.DataStruct.dspid_allMeanOther_collection;
import BidEngine.DataStruct.dspid_allMeanOther_flat;
import BidEngine.DataStruct.timestamp_dataObject;

import java.util.*;

/**
 * Created by Administrator on 2017/2/27.
 */
public class function_collection_withP {

    public static Double Pintellegance_dspId(List<timestamp_dataObject> timestamp_dataObjects_list, Integer dspid_test, Integer n_test, Double budget) {
        /**
         * 统计所有时间片的总的bidprice_all, bidprice_mean, winnercost_all, winnercost_mean
         */
        //List<dspid_allMeanOther_collection> dac_list = new ArrayList<dspid_allMeanOther_collection>();
        HashMap<Integer, List<dspid_allMeanOther_flat>> time_daf_list = new HashMap<Integer, List<dspid_allMeanOther_flat>>();

        for(int i=0; i< timestamp_dataObjects_list.size(); i++){
            //对于一个时间片进行操作
            timestamp_dataObject tdo = timestamp_dataObjects_list.get(i);
            HashMap<String, ArrayList<StringBuffer>> datainformation_new_merge = tdo.getDatainformation_new_merge();
            List<dspid_allMeanOther> damo_list = new ArrayList<dspid_allMeanOther>();
            List<dspid_allMeanOther_flat> damof_list = new ArrayList<dspid_allMeanOther_flat>();
            int timestamp_index = i;
            for(Map.Entry<String, ArrayList<StringBuffer>> entry: datainformation_new_merge.entrySet()){
                String dspid = entry.getKey();
                ArrayList<StringBuffer> result = entry.getValue();
                /**
                 * 一个dspid的各项数据指标
                 * 总的bidprice，平均bidprice，总的winnercost，平均winnercost，budget（待加）
                 */
                Double bidprice_all = 0.0;
                Double bidprice_mean = 0.0;
                Double winnercost_all = 0.0;
                Double winnercost_mean = 0.0;
                for(int j=0; j<result.size(); j++){
                    /**
                     * 一个dspid的每次投放
                     */
                    StringBuffer sb = result.get(j);
                    Double[] bidprice_winnercost2 = new Double[4];
                    try{
                        bidprice_winnercost2 = ComputebidpriceAndWinnercost2(sb);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Double bidPrice = bidprice_winnercost2[0];
                    Double winnerCost = bidprice_winnercost2[1];
                    bidprice_all += bidPrice;
                    winnercost_all += winnerCost;
                }

                bidprice_mean = bidprice_all / result.size();
                winnercost_mean = winnercost_all / result.size();


                dspid_allMeanOther damo = new dspid_allMeanOther(dspid, bidprice_all, bidprice_mean, winnercost_all, winnercost_mean);

                damo_list.add(damo);

                for(int j=0; j< result.size(); j++){
                    /**
                     * 一个时间片内一个dspid的每次投放
                     */
                    StringBuffer sb = result.get(j);
                    Double[] bidprice_winnercost2 = new Double[4];
                    try{
                        bidprice_winnercost2 = ComputebidpriceAndWinnercost2(sb);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    Double bidPrice = bidprice_winnercost2[0];
                    Double winnerCost = bidprice_winnercost2[1];
                    Double status = bidprice_winnercost2[2];
                    Double iswinnerdsp = bidprice_winnercost2[3];

                    List<Double> bwsi = new ArrayList<Double>();
                    bwsi.add(bidPrice);
                    bwsi.add(winnerCost);
                    bwsi.add(status);
                    bwsi.add(iswinnerdsp);
                    dspid_allMeanOther_flat damo_flat = new dspid_allMeanOther_flat(dspid, bidprice_all, bidprice_mean, winnercost_all, winnercost_mean, bwsi);
                    damof_list.add(damo_flat);
                }


            }

            /*dspid_allMeanOther_collection dac = new dspid_allMeanOther_collection(damo_list);
            dac_list.add(dac);*/

            //根据damo_flat（【dspid,bidprice_all, bidprice_mean, winnercost_all, winnercost_mean,【bidprce,winnercost,status,iswinnerDsp来创建一个构建一个hashmap
            //damof_list的元素是damo_flat

            time_daf_list.put(timestamp_index, damof_list);
            //============================================================================
        }

        //Double budget = 5000.0;
        //对第n_test的时间片，dspid_test的dspid，以概率P（随机）出价,返回这个时间片内受影响之后的总扣费
        Double WinnerCostOfThat_dspId = PtoTimestampDspid(time_daf_list, n_test, dspid_test);
        //预算budget减去该dsp调整后本时间片的winnerprice
        budget -= WinnerCostOfThat_dspId;

        /**
         * 得到上一步得到的dspid对应的剩余预算budget之后，对之后的时间片只要有预算就出价，针对的那种没有了预算，但是由于上一步时间片给了P值，而又有了预算，这时候就投，
         * 返回影响之后的剩余budget
         */

        //预算budget在其他时间片都减去winnerprice后的值
        Double PeffectToTotalWinnercost = PToTotalWinnercost_Effect(WinnerCostOfThat_dspId, time_daf_list, n_test, dspid_test, budget);
        return PeffectToTotalWinnercost;
    }


    private static Double PToTotalWinnercost_Effect(Double restOfBudget_dspId, HashMap<Integer, List<dspid_allMeanOther_flat>> time_daf_list, Integer n_test, Integer dspid_test, Double budget) {


        //获取除了第n_test之外的时间片的数据
        /*for(Map.Entry<Integer, List<dspid_allMeanOther_flat>> entry: time_daf_list){
            if(entry.getKey() != n_test){
                //对其余时间片进行操作
                Integer timestamp = entry.getKey();
                List<dspid_allMeanOther_flat> flatdatalist = entry.getValue();
                //对其他每一个时间片的本dspid_test自己的总winnercost的影响，有预算就出
                Double TempEffectToBudget = PEffectOfOtherTimestampOfdspid(flatdatalist, dspid_test, budget);
                budget = TempEffectToBudget;
            }
        }*/

        Iterator<Map.Entry<Integer, List<dspid_allMeanOther_flat>>> it = time_daf_list.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer, List<dspid_allMeanOther_flat>> entry = it.next();
            if(entry.getKey() != n_test){
                //对其余时间片进行操作
                Integer timestamp = entry.getKey();
                List<dspid_allMeanOther_flat> flatdatalist = entry.getValue();
                //对其他每一个时间片的本dspid_test自己的总winnercost的影响，有预算就出
                Double TempEffectToBudget = PEffectOfOtherTimestampOfdspid(flatdatalist, dspid_test, budget);
                budget = TempEffectToBudget;
            }
        }

        return budget;
    }

    private static Double PEffectOfOtherTimestampOfdspid(List<dspid_allMeanOther_flat> flatdatalist, Integer dspid, Double budget) {
        Double nowBudget = budget;
        for(int i=0; i< flatdatalist.size(); i++){
            dspid_allMeanOther_flat daf = flatdatalist.get(i);
            Integer DSPID;
            if(daf.getDspid().equals("")){
                DSPID = 0;
            }else{
                DSPID = Integer.valueOf(daf.getDspid());
            }
            if(DSPID == dspid){
                /*
                    bwsi的结构：
                    List<Double> bwsi = new ArrayList<Double>();
                    bwsi.add(bidPrice);
                    bwsi.add(winnerCost);
                    bwsi.add(status);
                    bwsi.add(iswinnerdsp);
                */
                Double winnercost = daf.getBwsi().get(1);
                //对本时间片内的本dspid的影响
                if(nowBudget - winnercost > 0){
                    //有预算就出
                    nowBudget = nowBudget - winnercost;
                }

            }
        }
        return nowBudget;
    }

    /**
     * 返回加了概率P值之后的总扣费
     * @param time_daf_list
     * @param n_test
     * @param dspid_test
     * @return
     */
    private static Double PtoTimestampDspid(HashMap<Integer, List<dspid_allMeanOther_flat>> time_daf_list,  Integer n_test, Integer dspid_test) {
        //一个时间片的数据
        List<dspid_allMeanOther_flat> timestamp_data = time_daf_list.get(n_test);
        //获取需要取的dspid的值
        Integer dspid_temp = dspid_test;
        //dspid_allMeanOther_flat hit_Object = new dspid_allMeanOther_flat();
        List<dspid_allMeanOther_flat> timestamp_data_fordspid = new ArrayList<dspid_allMeanOther_flat>();
        for(int i=0; i< timestamp_data.size(); i++){
            dspid_allMeanOther_flat daf = timestamp_data.get(i);
            //得到目标dspid
            if(Integer.valueOf(daf.getDspid()) == dspid_test){
                //hit_Object = daf; 这种方式不行，是浅拷贝
                timestamp_data_fordspid.add(daf);//把这个时间片内所有目标dspid的数据都提取出来
            }
        }

        //Double myrandom = function_collection.GetMyRandom();
        Double[] random_queue = new Double[timestamp_data_fordspid.size()];
        Double bidpriceTotal_dspid = 0.0;
        Double winnercostTotal_dspid = 0.0;
        for(int j=0; j< timestamp_data_fordspid.size();j++){
            //对dspid的每次投放给一个概率值
            random_queue[j] = function_collection.GetMyRandom();
            //拿到每次投递的数据
            dspid_allMeanOther_flat temp = timestamp_data_fordspid.get(j);
            //解析字段:
            String dspid = temp.getDspid();
            Double bidprice_all = temp.getBidprice_all();
            Double bidprice_mean = temp.getBidprice_mean();
            Double winnercost_all = temp.getWinnercost_all();
            Double winnercost_mean = temp.getWinnercost_mean();
            List<Double> bwsi = temp.getBwsi();
            //List<Double> bwsi = new ArrayList<Double>();

            /*
            bwsi的结构：
                    List<Double> bwsi = new ArrayList<Double>();
                    bwsi.add(bidPrice);
                    bwsi.add(winnerCost);
                    bwsi.add(status);
                    bwsi.add(iswinnerdsp);
             */

            Double bidPrice = bwsi.get(0);
            Double winnercost = bwsi.get(1);
            Double status = bwsi.get(2);
            Double iswinnerdsp = bwsi.get(3);


            // 两种假设下对timestamp_data_fordspid进行操作
            if(random_queue[j] > 0.5){
                // 正常出价=======================================================================================================


            }else{
                // 不出价
                bidPrice = 0.0;
                winnercost = 0.0;

            }
            bidpriceTotal_dspid += bidPrice;
            winnercostTotal_dspid += winnercost;
        }


        //最终要1返回这个时间片这个dspid所剩下的预算budget
        return winnercostTotal_dspid;
    }

    private static Double[] ComputebidpriceAndWinnercost2(StringBuffer sb) {
        /*
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
             */
        String[] st = sb.toString().trim().split("!");

        String isWinnerDsp = st[6].toString().trim();//是否在这个位置胜出,值为0、1. 1为胜出
        String status = st[5].toString().trim();//一个位置的出价状态，包含ATM对该位置的出价状态
        System.out.println("status: "+ status);

        String bidP = st[4].toString().trim();
        String winnerC = st[7].toString().trim();
        //System.out.println(bidP);

        /*Double bidPrice = Double.valueOf(st[4].toString().trim());
        Double winnercost = Double.valueOf(st[8]);*/
        /**
         * test
         */
        Double bidPrice = 0.0;
        //System.out.println(bidP);
        if(bidP != null && !bidP.equals("null") && bidP != "null"){
            bidPrice = Double.parseDouble(bidP);
        }


        Double winnerCost = 0.0;
        if((winnerC != null) && (!winnerC.equals("null")) && (winnerC != "null")){
            winnerCost = Double.valueOf(winnerC);
        }


        Double mystatus = 0.0;
        if(status != null && !status.equals("null") && status != "null"){
            mystatus = Double.valueOf(status);
        }
        Double _status = mystatus;

        Double isWinnerDspdata = 0.0;
        if(isWinnerDsp != null && !isWinnerDsp.equals("null") && isWinnerDsp != "null"){
            isWinnerDspdata = Double.valueOf(isWinnerDsp);
        }
        Double _isWinnerDsp = isWinnerDspdata;


        System.out.println("status: "+ _status + " isWinnerDsp: " + _isWinnerDsp + " bidPrice: "+ bidPrice + "  winnerCost: " + winnerCost);

        Double[] bidPriceAndWinnerCost = new Double[4];
        bidPriceAndWinnerCost[0] = bidPrice;
        bidPriceAndWinnerCost[1] = winnerCost;
        bidPriceAndWinnerCost[2] = _status;
        bidPriceAndWinnerCost[3] = _isWinnerDsp;


        return bidPriceAndWinnerCost;
    }
}
