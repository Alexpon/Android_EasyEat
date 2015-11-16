package mbp.alexpon.com.easyeat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);

        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //1
        tabHost.addTab(tabHost.newTabSpec("Tab1")
                        .setIndicator(createCustomTab(R.string.title_record, R.drawable.record)),
                InfoFragment.class,
                null);
        //2
        tabHost.addTab(tabHost.newTabSpec("Tab2")
                        .setIndicator(createCustomTab(R.string.title_order, R.drawable.order)),
                OrderFragment.class,
                null);
        //3
        tabHost.addTab(tabHost.newTabSpec("Tab3")
                        .setIndicator(createCustomTab(R.string.title_cart, R.drawable.cart)),
                CartFragment.class,
                null);
        //4
        tabHost.addTab(tabHost.newTabSpec("Tab4")
                        .setIndicator(createCustomTab(R.string.title_coupon, R.drawable.menu)),
                RecordFragment.class,
                null);
    }

    /**************************
     *
     * 給子頁籤呼叫用
     *
     **************************/
    public String getAppleData(){
        return "Apple 123";
    }
    public String getGoogleData(){
        return "Google 456";
    }
    public String getFacebookData(){
        return "Facebook 789";
    }
    public String getTwitterData(){
        return "Twitter abc";
    }


    private View createCustomTab(int label, int iconID){

        View tab = LayoutInflater.from(this).inflate(R.layout.tab_layout, null);

        LinearLayout linearLayout = (LinearLayout) tab.findViewById(R.id.linearlayout);
        TextView text =(TextView)tab.findViewById(R.id.text);
        text.setText(label);
        //tab icon
        linearLayout.setBackgroundResource(R.drawable.background_selector);
        ImageView img = (ImageView)tab.findViewById(R.id.icon);
        img.setImageResource(iconID);
        return tab;
    }


}