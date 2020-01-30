package hu.zolta.wolforphone_ub;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnWOL;
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btnWOL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MagicPacket mp = new MagicPacket("192.168.0.255","00:22:64:56:C5:44");
                if (mp.elkuldteE()){
                    Toast.makeText(MainActivity.this, "Hibás WOL",Toast.LENGTH_SHORT);
                    tvData.setText(
                            "Ip: " + mp.getIp() + "\n" +
                                    "Mac: " + mp.getMac() + "\n" +
                                    "Port: " + mp.getPORT()
                    );
                }
                else{
                    tvData.setText("hiba hiba hiba");
                }
            }
        });
    }

    private void init(){
        btnWOL = findViewById(R.id.btnWOL);
        tvData = findViewById(R.id.tvData);
    }
}
