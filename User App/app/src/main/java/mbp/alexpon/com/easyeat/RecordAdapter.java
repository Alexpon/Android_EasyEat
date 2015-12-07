package mbp.alexpon.com.easyeat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by apple on 2015/12/7.
 */
public class RecordAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private NowRecord nowRecord;

    public RecordAdapter (Context context, NowRecord nowRecord){
        this.context = context;
        this.nowRecord = nowRecord;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return nowRecord.index;
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
        final RecordView recordView;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.listview_record, null);
            recordView = new RecordView((TextView) convertView.findViewById(R.id.store_name),
                    (TextView) convertView.findViewById(R.id.date),
                    (TextView) convertView.findViewById(R.id.money));
            convertView.setTag(recordView);
        }
        else{
            recordView = (RecordView) convertView.getTag();
        }
        recordView.store_name.setText(nowRecord.store_name[position]);
        recordView.date.setText(nowRecord.date[position]);
        recordView.money.setText(nowRecord.money[position]+"");


        return convertView;
    }


}
