package mbp.alexpon.com.easyeat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Created by apple on 2015/11/30.
 */
public class BackgroundTask extends TimerTask {
    private TmpUserLocalStore userLocalStore;
    private User user;
    private Context context;
    private NowMenu nowMenus=null;

    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://140.116.97.92/project/";

    public BackgroundTask(Context context) {
        this.context = context;
        userLocalStore = new TmpUserLocalStore(context);
        user = userLocalStore.getUserData();
    }

    @Override
    public void run() {
        Log.i("counting", "excuted");
        sync();
    }

    public void sync(){
        String result = "";

        ArrayList<NameValuePair> dataToSend = new ArrayList<>();
        dataToSend.add(new BasicNameValuePair("person", user.getCertiSerialNum()));

        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, CONNECTION_TIMEOUT);
        HttpClient client = new DefaultHttpClient(httpParams);
        HttpPost httpPost = new HttpPost(SERVER_ADDRESS + "EasyEat_GetNowMenu.php");

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(dataToSend));
            HttpResponse httpResponse = client.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
            JSONArray jsonArray = new JSONArray(result);

            if (jsonArray.length() == 0) {
                nowMenus = null;
            } else {
                nowMenus = new NowMenu(jsonArray.length());
                JSONObject stock_data;
                for(int i=0; i<jsonArray.length(); i++){
                    stock_data = jsonArray.getJSONObject(i);
                    nowMenus.add(stock_data.getString("store_name"), stock_data.getInt("money"),
                            stock_data.getInt("my_num"), stock_data.getInt("now_num"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (nowMenus != null) {
            for (int i = 0; i < nowMenus.index; i++) {
                if(nowMenus.my_num[i]<=nowMenus.now_num[i]){
                    Intent intent =new Intent ("com.alexpon.easyeat");
                    context.sendBroadcast(intent);
                    break;
                }
            }

        }
    }

}
