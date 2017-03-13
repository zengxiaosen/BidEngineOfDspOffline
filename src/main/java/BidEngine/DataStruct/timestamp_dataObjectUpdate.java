package BidEngine.DataStruct;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/13.
 */
public class timestamp_dataObjectUpdate {
    private HashMap<String, HashMap<String, ArrayList<String>>> datainformation_new_merge_update;

    public HashMap<String, HashMap<String, ArrayList<String>>> getDatainformation_new_merge_update() {
        return datainformation_new_merge_update;
    }

    public void setDatainformation_new_merge_update(HashMap<String, HashMap<String, ArrayList<String>>> datainformation_new_merge_update) {
        this.datainformation_new_merge_update = datainformation_new_merge_update;
    }

    public timestamp_dataObjectUpdate(HashMap<String, HashMap<String, ArrayList<String>>> datainformation_new_merge_update){
        this.datainformation_new_merge_update = datainformation_new_merge_update;
    }
}
