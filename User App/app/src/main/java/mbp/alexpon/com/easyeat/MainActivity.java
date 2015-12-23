package mbp.alexpon.com.easyeat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;


public class MainActivity extends Activity {

    private Intent intent;
    private TmpUserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//?
        Intent service = new Intent(this, MyHostApduService.class);
        startService(service);
//?
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(varifyCard(this)){
            Intent intent1 = new Intent(MainActivity.this, HaveCardMainPage.class);
            startActivity(intent1);
            finish();
        }
        else{
            TextView main_info = (TextView) findViewById(R.id.main_info);
            main_info.setText(R.string.infoFragment_no_card_info);
        }

        Button button = (Button) findViewById(R.id.main_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(varifyCard(getApplication())){
                    Intent intent1 = new Intent(MainActivity.this, HaveCardMainPage.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });

    }

    private boolean varifyCard(Context context) {
        userLocalStore = new TmpUserLocalStore(context);
        User user = userLocalStore.getUserData();
        return !(user.getUsername().equals("") && user.getUserID().equals(""));
    }


    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("onNewIntent", "onNewIntent");
        this.intent=intent;
    }



}