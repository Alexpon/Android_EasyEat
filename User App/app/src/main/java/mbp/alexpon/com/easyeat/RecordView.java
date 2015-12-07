package mbp.alexpon.com.easyeat;

import android.widget.TextView;

/**
 * Created by apple on 2015/12/7.
 */
public class RecordView {

    public TextView store_name;
    public TextView date;
    public TextView money;


    public RecordView(TextView store_name, TextView date, TextView money){
        this.store_name = store_name;
        this.date = date;
        this.money = money;
    }
}
