package mbp.alexpon.com.easyeat;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * Created by apple on 2015/11/18.
 */
public class CartAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private NowMenu nowMenu;

    public CartAdapter (Context context, NowMenu nowMenu){
        this.context = context;
        this.nowMenu = nowMenu;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return nowMenu.index;
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
        final CartView cartView;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.listview_cart, null);
            cartView = new CartView((TextView) convertView.findViewById(R.id.store_name),
                    (TextView) convertView.findViewById(R.id.money),
                    (TextView) convertView.findViewById(R.id.my_num),
                    (TextView) convertView.findViewById(R.id.now_num));
            convertView.setTag(cartView);
        }
        else{
            cartView = (CartView) convertView.getTag();
        }
        cartView.store_name.setText(nowMenu.store_name[position]);
        cartView.money.setText(nowMenu.money[position]+"");
        cartView.my_num.setText(nowMenu.my_num[position]+"");
        cartView.now_num.setText(nowMenu.now_num[position]+"");


        return convertView;
    }


}
