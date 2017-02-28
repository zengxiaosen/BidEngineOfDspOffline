package BidEngine.DataStruct;

/**
 * Created by Administrator on 2017/2/28.
 */
public class dspid_allMeanOther {
    private String dspid;
    private Double bidprice_all;
    private Double bidprice_mean;
    private Double winnercost_all;
    private Double winnercost_mean;

    public dspid_allMeanOther(String dspid, Double bidprice_all, Double bidprice_mean, Double winnercost_all, Double winnercost_mean){
        this.dspid = dspid;
        this.bidprice_all = bidprice_all;
        this.bidprice_mean = bidprice_mean;
        this.winnercost_all = winnercost_all;
        this.winnercost_mean = winnercost_mean;
    }

    public String getDspid() {
        return dspid;
    }

    public void setDspid(String dspid) {
        this.dspid = dspid;
    }

    public Double getBidprice_all() {
        return bidprice_all;
    }

    public void setBidprice_all(Double bidprice_all) {
        this.bidprice_all = bidprice_all;
    }

    public Double getBidprice_mean() {
        return bidprice_mean;
    }

    public void setBidprice_mean(Double bidprice_mean) {
        this.bidprice_mean = bidprice_mean;
    }

    public Double getWinnercost_all() {
        return winnercost_all;
    }

    public void setWinnercost_all(Double winnercost_all) {
        this.winnercost_all = winnercost_all;
    }

    public Double getWinnercost_mean() {
        return winnercost_mean;
    }

    public void setWinnercost_mean(Double winnercost_mean) {
        this.winnercost_mean = winnercost_mean;
    }
}
