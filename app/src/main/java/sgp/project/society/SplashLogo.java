package sgp.project.society;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashLogo extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1800;

    public static String TAG = "SplashLogo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_logo);

        //Splash Screen Code to call new Activity after some time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (FirebaseAuth.getInstance().getCurrentUser() != null)
                {
                    startActivity(new Intent(SplashLogo.this, MainActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(SplashLogo.this, LoginActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }

}
