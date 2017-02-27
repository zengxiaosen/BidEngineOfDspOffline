package BidEngine.DataStruct;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/27.
 */
public class bid_log_data {
    private String log_version;
    private String log_time;//时间戳(毫秒)
    private String visitor_ip;//访问用户ip
    private String visitor_cookie;//用户cookie标识
    private String ad_request_id;//唯一竞价请求id

    private String media_site_id;//网站id
    private String media_id;//媒体id
    private String ad_dsp_info;//各家dsp出价信息，一家dspinfo的字段用"^"分割，不同家dspinfo的信息用“#”分割。不建议直接使用，建议使用ods_adx_auction_dspinfo_d视图

    public String getQz(){
        return log_time + "!" + ad_request_id;
    }


    //private bid_dspinfo_data[] dspinfodata;
    private ArrayList<bid_dspinfo_data> dspinfodata;

    public ArrayList<bid_dspinfo_data> addToDspinfodata(bid_dspinfo_data b){
        this.dspinfodata.add(b);
        return dspinfodata;
    }

    public ArrayList<bid_dspinfo_data> getDspinfodata() {
        return dspinfodata;
    }

    public void setDspinfodata(ArrayList<bid_dspinfo_data> dspinfodata) {
        this.dspinfodata = dspinfodata;
    }

    private String ad_requst_count;//一个请求adx能填充的素材位置数
    private String extension;
    private String ad_algo_info;//算法扩展字段
    private String ad_flow_mark;//流量标记，格式：实验id1:策略id1;实验id2:策略id2...

    public String getLog_version() {
        return log_version;
    }

    @Override
    public String toString() {
        return "bid_log_data{" +
                "log_version='" + log_version + '\'' +
                ", log_time='" + log_time + '\'' +
                ", visitor_ip='" + visitor_ip + '\'' +
                ", visitor_cookie='" + visitor_cookie + '\'' +
                ", ad_request_id='" + ad_request_id + '\'' +
                ", media_site_id='" + media_site_id + '\'' +
                ", media_id='" + media_id + '\'' +
                ", ad_dsp_info='" + ad_dsp_info + '\'' +
                ", ad_requst_count='" + ad_requst_count + '\'' +
                ", extension='" + extension + '\'' +
                ", ad_algo_info='" + ad_algo_info + '\'' +
                ", ad_flow_mark='" + ad_flow_mark + '\'' +
                ", ad_request_duration='" + ad_request_duration + '\'' +
                '}';
    }

    public void setLog_version(String log_version) {
        this.log_version = log_version;
    }

    public String getLog_time() {
        return log_time;
    }

    public void setLog_time(String log_time) {
        this.log_time = log_time;
    }

    public String getVisitor_ip() {
        return visitor_ip;
    }

    public void setVisitor_ip(String visitor_ip) {
        this.visitor_ip = visitor_ip;
    }

    public String getVisitor_cookie() {
        return visitor_cookie;
    }

    public void setVisitor_cookie(String visitor_cookie) {
        this.visitor_cookie = visitor_cookie;
    }

    public String getAd_request_id() {
        return ad_request_id;
    }

    public void setAd_request_id(String ad_request_id) {
        this.ad_request_id = ad_request_id;
    }

    public String getMedia_site_id() {
        return media_site_id;
    }

    public void setMedia_site_id(String media_site_id) {
        this.media_site_id = media_site_id;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getAd_dsp_info() {
        return ad_dsp_info;
    }

    public void setAd_dsp_info(String ad_dsp_info) {
        this.ad_dsp_info = ad_dsp_info;
    }

    public String getAd_requst_count() {
        return ad_requst_count;
    }

    public void setAd_requst_count(String ad_requst_count) {
        this.ad_requst_count = ad_requst_count;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAd_algo_info() {
        return ad_algo_info;
    }

    public void setAd_algo_info(String ad_algo_info) {
        this.ad_algo_info = ad_algo_info;
    }

    public String getAd_flow_mark() {
        return ad_flow_mark;
    }

    public void setAd_flow_mark(String ad_flow_mark) {
        this.ad_flow_mark = ad_flow_mark;
    }

    public String getAd_request_duration() {
        return ad_request_duration;
    }

    public void setAd_request_duration(String ad_request_duration) {
        this.ad_request_duration = ad_request_duration;
    }

    private String ad_request_duration;//一次请求，能填充的广告总时长
}
