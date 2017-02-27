package BidEngine.DataStruct;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/2/27.
 */
public class timestamp_dataObject {
    private HashMap<String, ArrayList<StringBuffer>> datainformation_new_merge;

    public HashMap<String, ArrayList<StringBuffer>> getDatainformation_new_merge() {
        return datainformation_new_merge;
    }

    public void setDatainformation_new_merge(HashMap<String, ArrayList<StringBuffer>> datainformation_new_merge) {
        this.datainformation_new_merge = datainformation_new_merge;
    }

    public timestamp_dataObject(HashMap<String, ArrayList<StringBuffer>> datainformation_new_merge1){
        this.datainformation_new_merge = datainformation_new_merge1;
    }
}
