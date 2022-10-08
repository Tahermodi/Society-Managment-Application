package sgp.project.society;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ServiceCenterActivity extends AppCompatActivity {

    Button btn_plumber, btn_carpenter, btn_electrician;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_center);

        btn_plumber = findViewById(R.id.btn_plumber);
        btn_carpenter = findViewById(R.id.btn_carpenter);
        btn_electrician = findViewById(R.id.btn_electrician);

        btn_plumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call_plumber();
            }
        });

        btn_carpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call_carpenter();
            }
        });

        btn_electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call_electrician();
            }
        });



    }

    private void call_plumber() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+919756347826", null)));
    }

    private void call_electrician() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+919976835211", null)));
    }

    private void call_carpenter() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+918457214360", null)));
    }

}
