package mbp.alexpon.com.easyeat;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by apple on 2015/10/21.
 */
public class MyAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private Menus returnedMenu;
    private int[] order_num;

    public MyAdapter (Context context, Menus returnedMenu){
        this.context = context;
        this.returnedMenu = returnedMenu;
        inflater = LayoutInflater.from(context);
        order_num = new int[returnedMenu.index];
    }

    @Override
    public int getCount() {
        return returnedMenu.index;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyView myView;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.listview_order, null);
            myView = new MyView((TextView) convertView.findViewById(R.id.food_name),
                    (TextView) convertView.findViewById(R.id.money),
                    (EditText) convertView.findViewById(R.id.number));
            convertView.setTag(myView);
        }
        else{
            myView = (MyView) convertView.getTag();
        }
        myView.food_name.setText(returnedMenu.food_name[position]);
        myView.money.setText(returnedMenu.money[position]+"");
        myView.number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                try {
                    order_num[position] = Integer.valueOf(v.getText().toString());
                    Toast.makeText(context, position + "" + v.getText().toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    order_num[position] = 0;
                    //Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        return convertView;
    }

    public int[] getOrder_num(){
        return order_num;
    }
}