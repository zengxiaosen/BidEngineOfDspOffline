package BidEngine.DataStruct;

/**
 * Created by Administrator on 2017/2/27.
 */
public class bid_dspinfo_data {
    private String dspid;//dspid ，>0 的值， =1 表示ATM
    private String biding;//dsp 对请求每个素材位置的出价信息，是个组合字段，每个字段用“,”分割,不同素材位置用“@”分割 ，具体信息见 biding 表
    private String adxPid;//adx广告位
    private String reqDealId;//请求的普通dealid 列表，可能是多个，用竖线'|'分隔。默认为0
    private String reqPdbDealId;//请求的PDB dealid 列表 ，可能是多个，用竖线'|'分隔。如果没有PDB请求记录为0 2016-05-17 增加
    private String ext;//dsp 维度的 扩展字段。 rt=xxx:DSP的响应时间，单位为毫秒; dspStatus=1或者0,1 DSP 是正式投放，0 DSP为测试投放;
    private Integer budget = 5000;//预算

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getDspid() {
        return dspid;
    }

    public void setDspid(String dspid) {
        this.dspid = dspid;
    }

    public String getBiding() {
        return biding;
    }

    public void setBiding(String biding) {
        this.biding = biding;
    }

    public String getAdxPid() {
        return adxPid;
    }

    public void setAdxPid(String adxPid) {
        this.adxPid = adxPid;
    }

    public String getReqDealId() {
        return reqDealId;
    }

    public void setReqDealId(String reqDealId) {
        this.reqDealId = reqDealId;
    }

    @Override
    public String toString() {
        return "bid_dspinfo_data{" +
                "dspid='" + dspid + '\'' +
                ", biding='" + biding + '\'' +
                ", adxPid='" + adxPid + '\'' +
                ", reqDealId='" + reqDealId + '\'' +
                ", reqPdbDealId='" + reqPdbDealId + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }

    public String getReqPdbDealId() {
        return reqPdbDealId;
    }

    public void setReqPdbDealId(String reqPdbDealId) {
        this.reqPdbDealId = reqPdbDealId;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
