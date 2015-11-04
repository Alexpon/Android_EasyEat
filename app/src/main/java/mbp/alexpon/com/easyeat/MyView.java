package mbp.alexpon.com.easyeat;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by apple on 2015/10/21.
 */
public class MyView{
    public TextView food_name;
    public TextView money;
    public EditText number;

    public MyView(TextView food_name, TextView money, EditText number){
        this.food_name = food_name;
        this.money = money;
        this.number = number;
    }
}