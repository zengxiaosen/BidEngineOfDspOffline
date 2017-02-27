package BidEngine.DataLoad;

import BidEngine.DataStruct.bid_log_data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */
public class DataLoader {
    private List<bid_log_data> bid_log_data_list = new ArrayList<bid_log_data>();
    public List<bid_log_data> loadFile_bid_log_data_list(String filePath)  throws FileNotFoundException, UnsupportedEncodingException, IOException {
        File f = new File(filePath);
        FileInputStream in = new FileInputStream(f);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] items = line.trim().split("!");
            if (items.length == 0) {
                continue;
            }
            bid_log_data o = new bid_log_data();
            o.setLog_version(items[0]);
            o.setLog_time(items[1]);
            o.setVisitor_ip(items[2]);
            o.setVisitor_cookie(items[3]);
            o.setAd_request_id(items[4]);
            o.setMedia_site_id(items[5]);
            o.setMedia_id(items[6]);
            o.setAd_dsp_info(items[7]);
            o.setAd_requst_count(items[8]);
            o.setExtension(items[9]);
            o.setAd_algo_info(items[10]);
            o.setAd_flow_mark(items[11]);
            o.setAd_request_duration(items[12]);

            bid_log_data_list.add(o);
            //System.out.println(o.toString());


        }

        reader.close();
        in.close();


        return bid_log_data_list;
    }

    public static void main(String[] args) throws IOException {
        DataLoader dataLoader = new DataLoader();
        List<bid_log_data> c = dataLoader.loadFile_bid_log_data_list("C:\\Users\\Administrator\\Desktop\\yes_日志\\bid_log_out.txt");
        //List<bid_log_data> c = dataLoader.loadFile_bid_log_data_list("data\\bid_log_out.txt");
    }
}
