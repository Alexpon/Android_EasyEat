package mbp.alexpon.com.easyeat;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class InfoFragment extends Fragment {

    private TextView info;
    private TmpUserLocalStore userLocalStore;
    private User user;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Button refresh;
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        userLocalStore = new TmpUserLocalStore(getActivity());
        user = userLocalStore.getUserData();
        info = (TextView) v.findViewById(R.id.basic_info);

        info.setText("使用者ID: " + user.getUserID() + "\n\n使用者名稱: "
                + user.getUsername() + "\n\n發卡日期: "
                + user.getIssuedDate() + "\n\n使用期限: "
                + user.getExpiredDate() + "\n\n序列碼: "
                + user.getCertiSerialNum() + "\n\n餘額: " + user.getMoney());

        refresh = (Button) v.findViewById(R.id.button);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshInfo();
            }
        });
        return v;
    }

    private void refreshInfo(){
        userLocalStore = new TmpUserLocalStore(getActivity());
        user = userLocalStore.getUserData();
        info.setText("使用者ID: " + user.getUserID() + "\n\n使用者名稱: "
                + user.getUsername() + "\n\n發卡日期: "
                + user.getIssuedDate() + "\n\n使用期限: "
                + user.getExpiredDate() + "\n\n序列碼: "
                + user.getCertiSerialNum() + "\n\n餘額: " + user.getMoney());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
