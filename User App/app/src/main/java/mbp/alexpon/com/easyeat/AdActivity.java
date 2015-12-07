package mbp.alexpon.com.easyeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AdActivity extends Activity {

    private Button buy;
    private Button leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        initView();
        setListener();
    }

    public void initView() {
        buy = (Button) findViewById(R.id.buy);
        leave = (Button) findViewById(R.id.leave);
    }

    public void setListener() {
        buy.setOnClickListener(adListener);
        leave.setOnClickListener(adListener);
    }

    private Button.OnClickListener adListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buy:
                    Intent nfcIntent = new Intent();
                    nfcIntent.setClass(AdActivity.this, MainActivity.class);
                    startActivity(nfcIntent);
                    break;
                case R.id.leave:
                    finish();
                    break;
            }
        }
    };
}