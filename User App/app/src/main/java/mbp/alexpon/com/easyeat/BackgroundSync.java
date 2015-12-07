package mbp.alexpon.com.easyeat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;

public class BackgroundSync extends Service {
    @Override
    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onCreate() {

        Log.i("service", "onCreate");
        IntentFilter filter =new IntentFilter("com.alexpon.easyeat");
        registerReceiver(receiver, filter);
        Timer timer =new Timer();
        timer.schedule(new BackgroundTask(this), 0, 10000); //每隔30秒抓一次資料庫
    }


    private BroadcastReceiver receiver =new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("receiver", "receiver");

            //获得notificationManager对象
            NotificationManager notificationManager =(NotificationManager)
                    getSystemService(context.NOTIFICATION_SERVICE);

            int icon =R.drawable.ic_launcher;
            CharSequence tickerText="Easy！Eat!";
            long when =System.currentTimeMillis();
            Intent intent2 =new Intent(context,MainActivity.class);

            PendingIntent pendingIntent =PendingIntent.getActivity
                    (context, 0, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
            CharSequence from ="Easy Eat";

            Notification notification = new Notification(icon, tickerText, when);
            notification.setLatestEventInfo(context, from, "您的餐點準備好囉！", pendingIntent);
            notificationManager.notify(0, notification);
        }
    };

}