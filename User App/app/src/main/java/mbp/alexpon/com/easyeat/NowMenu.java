package mbp.alexpon.com.easyeat;

/**
 * Created by apple on 2015/11/18.
 */
public class NowMenu {

    public String store_name[];
    public int money[];
    public int my_num[];
    public int now_num[];
   // public String meal[];
    public int index;
    public int count;

    public NowMenu(int index){
        this.index = index;
        count = 0;
        store_name = new String[index];
        money = new int[index];
        my_num = new int[index];
        now_num = new int[index];
 //       meal = new String[index];
    }

    public void add(String i_store, int i_money, int i_my_num, int i_now_num){
        store_name[count] = i_store;
        money[count] = i_money;
        my_num[count] = i_my_num;
        now_num[count] = i_now_num;
//        meal[count] = i_meal;
        count++;
    }
}
