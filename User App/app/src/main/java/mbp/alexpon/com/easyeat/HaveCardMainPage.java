package mbp.alexpon.com.easyeat;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.altbeacon.beacon.BeaconConsumer;


public class HaveCardMainPage extends FragmentActivity implements BeaconConsumer {

    private Intent intent;
    //private BeaconManager beaconManager;
    //private Region region;
    //private Boolean adFlag;


    @Override
    public void onBeaconServiceConnect() {
        //startScanning();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_have_card_main_page);
        setTag();
/*
        Intent service = new Intent(this, BackgroundSync.class);
        startService(service);
        intent = getIntent();
*/
/*
        verifyBluetooth();

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);
        region = new Region("myRangUniqueId", null, Identifier.fromInt(4660), null);
*/
    }


    private void setTag(){
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
                        .setIndicator(createCustomTab(R.string.title_history, R.drawable.menu)),
                RecordFragment.class,
                null);
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

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("onNewIntent", "onNewIntent");
        this.intent=intent;
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume","onResume");
        NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //beaconManager.unbind(this);
    }
/*
    private void startScanning() {
        adFlag = true;
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Iterator<Beacon> beaconIterator = beacons.iterator();
                    if (beaconIterator.hasNext()) {
                        do {
                            Beacon beacon = beaconIterator.next();
                            logBeaconData(beacon);
                        } while (beaconIterator.hasNext());
                    }
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
        }

    }

    private void stopScanning() {
        try {
            beaconManager.stopRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
        }
    }

    private void logBeaconData(Beacon beacon) {

        double dis = beacon.getDistance();
        if (beacon.getId2().toInt() == 4660 && dis < 1 && adFlag) {
            adFlag = false;
            Intent adIntent = new Intent();
            adIntent.setClass(HaveCardMainPage.this, AdActivity.class);
            startActivity(adIntent);
        }

    }

    private void verifyBluetooth() {
        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                        System.exit(0);
                    }
                });
                builder.show();
            }
        } catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                    System.exit(0);
                }

            });
            builder.show();

        }

    }
*/
}