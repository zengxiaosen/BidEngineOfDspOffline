package BidEngine.DataStruct;

/**
 * Created by Administrator on 2017/2/27.
 */
public class bid_dspinfo_biding_data {
    //dsp 对请求每个素材位置的出价信息，是个组合字段，每个字段用“,”分割,不同素材位置用“@”分割

    private String bidPrice;//对一个位置的出价
    private String status;//一个位置的出价状态，包含ATM对该位置的出价状态
    private String isWinnerDsp;//是否在这个位置胜出,值为0、1. 1为胜出
    private String winnerCost;//胜出的花费
    private String advertiserid;//广告主id 。 原来这个位置是是否保量出价(废弃) 2015-08-25 更改
    private String ideaId;//该DSP在该位置的返回素材
    private String adposition;//ATM 响应的广告位id
    private String resDealId;//响应dealid ，一个位置只能响应一个dealid，如果没有响应记0
    private String ideaType;//素材类型 0：普通 1：trueview 2015-08-25 更改
    private String ideaLength;//素材时长 秒 2015-08-25 更改
    private String ideaBottomPrice;//这个素材参与竞价时使用的底板价。2015-10-16增加 15是使用15秒底板价，30秒使用30秒底板价，45秒使用45秒底板价，以此类推
    private String orderid;//ATM 参与竞价时的素材所对应的订单id 2015-11-30增加
    private String castid;//ATM 参与竞价时的素材所对应的投放id 2015-11-30增加
    private String pdbDealid;//如果一个PDB请求，响应的是普通的deal。记录这个普通的deal对应的pdb的dealid 2016-05-17增加
    private String cardId;//返回广告素材所使用的广告刊例id ，不同行业刊例不同 （20160517 6月上线）
    private String positionCount;//素材占用标准广告位个数，普通素材根据时长折算，特殊素材计算实际占用，（2016-07-19上线）
}
