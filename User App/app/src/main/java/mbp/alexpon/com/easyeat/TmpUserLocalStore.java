package mbp.alexpon.com.easyeat;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by apple on 2015/11/16.
 */
public class TmpUserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public TmpUserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("userID", user.getUserID());
        spEditor.putString("username", user.getUsername());
        spEditor.putString("issuedDate", user.getIssuedDate());
        spEditor.putString("expiredDate", user.getExpiredDate());
        spEditor.putString("certiSerialNum", user.getCertiSerialNum());
        spEditor.putInt("money", user.getMoney());
        spEditor.commit();
    }

    public User getUserData(){
        String userID = userLocalDatabase.getString("userID", "");
        String username = userLocalDatabase.getString("username", "");
        String issuedDate = userLocalDatabase.getString("issuedDate", "");
        String expiredDate = userLocalDatabase.getString("expiredDate", "");
        String certiSerialNum = userLocalDatabase.getString("certiSerialNum", "");
        int money = userLocalDatabase.getInt("money", 0);

        User storeUser = new User(userID, username, issuedDate, expiredDate, certiSerialNum, money);

        return storeUser;
    }

    public void modifyMoney(int money){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putInt("money", money);
        spEditor.commit();
    }


}
