package mbp.alexpon.com.easyeat;

import android.widget.TextView;

/**
 * Created by apple on 2015/11/18.
 */
public class CartView {
    public TextView store_name;
    public TextView money;
    public TextView my_num;
    public TextView now_num;


    public CartView(TextView store_name, TextView money, TextView my_num, TextView now_num){
        this.store_name = store_name;
        this.money = money;
        this.my_num = my_num;
        this.now_num = now_num;
    }
}
