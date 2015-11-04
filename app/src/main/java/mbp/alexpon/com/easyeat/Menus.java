package mbp.alexpon.com.easyeat;

/**
 * Created by apple on 2015/10/14.
 */
public class Menus {
    public int index;
    public String[] food_id;
    public String[] food_name;
    public int[] money;

    public Menus(int index, String[] food_id, String[] food_name, int[] money){
        this.index = index;
        this.food_id = food_id;
        this.food_name = food_name;
        this.money = money;
    }
}
