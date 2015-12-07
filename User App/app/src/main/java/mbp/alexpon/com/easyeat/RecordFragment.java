package mbp.alexpon.com.easyeat;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecordFragment extends Fragment {

    private View v;
    private Button btn_refresh;
    private TmpUserLocalStore tmpUserLocalStore;
    private User user;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_record, container, false);
        tmpUserLocalStore = new TmpUserLocalStore(getActivity());
        user = tmpUserLocalStore.getUserData();
        btn_refresh = (Button) v.findViewById(R.id.refresh);
        btn_refresh.setOnClickListener(myListener);
        btn_refresh.setTextColor(0xff000000);
        fetchRecord();
        return v;
    }

    public void fetchRecord(){
        ServerRequests serverRequests = new ServerRequests(getActivity());
        serverRequests.fetchUserRecord(user.getCertiSerialNum(), new GetRecordCallBack() {
            @Override
            public void done(final NowRecord nowRecord) {
                if (nowRecord != null) {

                    List<Map<String, String>> list = new ArrayList<>();
                    for (int i = 0; i < nowRecord.index; i++) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("store_name", nowRecord.store_name[i]);
                        hashMap.put("date", nowRecord.date[i]);
                        hashMap.put("money", nowRecord.money[i] + "");
                        list.add(hashMap);
                    }
                    ListView listView = (ListView) v.findViewById(R.id.cart_listView);
                    RecordAdapter recordAdapter = new RecordAdapter(getActivity(), nowRecord);
                    listView.setAdapter(recordAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getActivity(), nowRecord.money[position] + "", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fetchRecord();
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
