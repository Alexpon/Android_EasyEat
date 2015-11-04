package mbp.alexpon.com.easyeat;

/**
 * Created by apple on 2015/10/14.
 */
public class Store {
    public int index;
    public String[] store_id;
    public String[] store_name;

    public Store(int index, String[] store_id, String[] store_name){
        this.index = index;
        this.store_id = store_id;
        this.store_name = store_name;
    }
}
