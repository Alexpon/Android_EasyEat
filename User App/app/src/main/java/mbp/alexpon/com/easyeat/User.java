package mbp.alexpon.com.easyeat;

/**
 * Created by apple on 2015/11/16.
 */
public class User {
    private String userID;
    private String username;
    private String issuedDate;
    private String expiredDate;
    private String certiSerialNum;
    private int money;

    public User(String userID, String username, String issuedDate, String expiredDate, String certiSerialNum, int money){
        this.userID = userID;
        this.username = username;
        this.issuedDate = issuedDate;
        this.expiredDate = expiredDate;
        this.certiSerialNum = certiSerialNum;
        this.money = money;
    }

    public String getUserID(){
        return userID;
    }

    public String getUsername(){
        return username;
    }

    public String getIssuedDate(){
        return issuedDate;
    }

    public String getExpiredDate(){
        return expiredDate;
    }

    public String getCertiSerialNum(){
        return certiSerialNum;
    }

    public int getMoney(){
        return money;
    }

}
