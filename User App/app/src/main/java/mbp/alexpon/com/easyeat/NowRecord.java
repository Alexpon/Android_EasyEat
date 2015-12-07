package mbp.alexpon.com.easyeat;

/**
 * Created by apple on 2015/12/7.
 */
public class NowRecord {
    public String store_name[];
    public String date[];
    public int money[];
    public int index;
    public int count;

    public NowRecord(int index){
        this.index = index;
        count = 0;
        store_name = new String[index];
        date = new String[index];
        money = new int[index];
    }

    public void add(String i_store, String i_date, int i_money){
        store_name[count] = i_store;
        date[count] = i_date;
        money[count] = i_money;
        count++;
    }
}
