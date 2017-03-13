package BidEngine.FunctionCollection;

import java.math.BigDecimal;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/2/28.
 */
public class function_collection {

    /**
     * 随机返回0~1之间保留小数点后四位的值，比如说0.9876
     */
    public static double GetMyRandom(){
        Random r = new Random();
        double f1 = r.nextDouble();
        BigDecimal bg = new BigDecimal(f1);
        double f2 = bg.setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
        return f2;
    }

    public static void PtoBid(HashMap<Double, ArrayList<StringBuffer>> ConcernedDataOfIdeaId,
                              HashMap<Double, Double> allInto){
        //对每一次的竞价中，ideaid赋予一个概率P

    }




    public static void main(String[] args){
        Double f = GetMyRandom();
        System.out.println(f);
    }

}
