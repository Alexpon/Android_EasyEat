package mbp.alexpon.com.easyeat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderFragment extends Fragment {
    private View v;
    private Button btn_confiem;
    private MyAdapter myAdapter;
    private String clickedStoreID;
    private TmpUserLocalStore userLocalStore;
    private User user;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_order, container, false);
        userLocalStore = new TmpUserLocalStore(getActivity());
        user = userLocalStore.getUserData();
        fetchStoreList();
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void fetchStoreList(){
        btn_confiem = (Button) v.findViewById(R.id.confirm);
        btn_confiem.setOnClickListener(myListener);
        btn_confiem.setText("請先選擇店家");
        btn_confiem.setTextColor(0xff000000);
        btn_confiem.setEnabled(false);
        ServerRequests serverRequests = new ServerRequests(getActivity());
        serverRequests.fetchStoreInBackground(new GetStoreCallBack() {
            @Override
            public void done(final Store returnedStore) {
                if (returnedStore != null) {
                    ListView listView = (ListView) v.findViewById(R.id.order_listView);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, returnedStore.store_name);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getActivity(), "你按了" + returnedStore.store_id[position] + returnedStore.store_name[position], Toast.LENGTH_SHORT).show();
                            clickedStoreID = returnedStore.store_id[position];
                            fetchMenuList(returnedStore.store_name[position]);
                        }
                    });
                }
            }
        });
    }

    public void fetchMenuList(String store_name){
        btn_confiem.setText("下單");
        btn_confiem.setEnabled(true);
        ServerRequests serverRequests = new ServerRequests(getActivity());
        serverRequests.fetchMenuInBackground(store_name, new GetMenuCallBack() {
            @Override
            public void done(final Menus returnedMenu) {
                if (returnedMenu != null) {

                    List<Map<String, String>> list = new ArrayList<Map<String,String>>();
                    for(int i=0; i<returnedMenu.index; i++){
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("food_name", returnedMenu.food_name[i]);
                        hashMap.put("money", returnedMenu.money[i]+"");
                        list.add(hashMap);
                    }
                    ListView listView = (ListView) v.findViewById(R.id.order_listView);
                    myAdapter = new MyAdapter(getActivity(), returnedMenu);
                    listView.setAdapter(myAdapter);
                }
            }
        });
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] order_num = myAdapter.getOrder_num();
            String order = getOrderString(order_num);
            update(order);
        }
    };

    public void update(String order){
        ServerRequests serverRequests = new ServerRequests(getActivity());
        serverRequests.fetchNumberInBackground(clickedStoreID, user.getCertiSerialNum(), order, new GetNumberCallBack() {
            @Override
            public void done(final String num[]) {
                if (num != null) {
                    Toast.makeText(getActivity(), num[0]+" "+num[1], Toast.LENGTH_SHORT).show();
                    showAlertDialog(num);
                }
            }
        });
    }

    public String getOrderString(int[] order_num){
        String order ="";
        for(int i=0; i<order_num.length; i++){
            if(order_num[i]/10 < 1){
                order = order + "0" + order_num[i];
            }
            else {
                order = order + order_num[i];
            }
        }
        return order;
    }

    public void showAlertDialog(String num[]){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        if(num[0] != "0"){
            dialog.setTitle("下單成功");
            dialog.setMessage("您的號碼牌： " + num[0] + "\n目前店家號碼： " + num[1]);
        }
        else{
            dialog.setTitle("下單失敗");
            dialog.setMessage("抱歉，麻煩重新操作一次");
        }
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), "我了解了",Toast.LENGTH_SHORT).show();
            }

        });
        dialog.show();
    }

}
