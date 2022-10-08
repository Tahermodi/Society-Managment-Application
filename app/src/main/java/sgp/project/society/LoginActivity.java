package sgp.project.society;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class LoginActivity extends AppCompatActivity {

    CircularProgressButton btn_login;

    TextInputLayout et_emailid, et_password;

    TextView tv_register, tv_forgotpassword;

    ImageView iv_register;

    FirebaseAuth firebaseAuth;

    public static ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);



        if (ForgotPasswordActivity.progressDialog == null) {
//            return;
        }
        else {
            ForgotPasswordActivity.progressDialog.dismiss();
        }
        if (RegisterActivity.progressDialog == null) {
//            return;
        }
        else {
            RegisterActivity.progressDialog.dismiss();
        }


        btn_login = findViewById(R.id.btn_login);
        et_emailid = findViewById(R.id.et_emailid);
        et_password = findViewById(R.id.et_password);
        tv_register = findViewById(R.id.tv_register);
        tv_forgotpassword = findViewById(R.id.tv_forgotpassword);

        iv_register = findViewById(R.id.iv_register);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(LoginActivity.this);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateEmail() | !validatePassword())
                {
                    return;
                }

                LoginActivity.showProgressBar();

                firebaseAuth.signInWithEmailAndPassword(et_emailid.getEditText().getText().toString().trim().toLowerCase(), et_password.getEditText().getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    LoginActivity.progressDialog.dismiss();
                                    et_emailid.getEditText().getText().clear();
                                    et_password.getEditText().getText().clear();
                                    Toast.makeText(LoginActivity.this, "Login Successfully...", Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();

                                } else {
                                    LoginActivity.progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.showProgressBar();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            }
        });

        iv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.showProgressBar();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            }
        });

        tv_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.showProgressBar();
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

    }

    public boolean validatePassword()
    {
        String password = et_password.getEditText().getText().toString().trim();

        if (password.isEmpty()) {
            et_password.setError("Field can't be empty");
            return false;
        }
        else {
            et_password.setError(null);
            return true;
        }
    }

    public boolean validateEmail()
    {
        String email = et_emailid.getEditText().getText().toString().trim().toLowerCase();

        if (email.isEmpty()) {
            et_emailid.setError("Field can't be empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_emailid.setError("Please enter valid email address");
            return false;
        }
        else {
            et_emailid.setError(null);
            return true;
        }
    }

    public static void showProgressBar() {
        progressDialog.show();

        progressDialog.setContentView(R.layout.progress_dialog);

        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

    // added for exit app when user click back button 1 time and give choice yes/no
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Confirm exit..!!");
        alertDialogBuilder.setIcon(R.drawable.ic_exit_app);
        alertDialogBuilder.setMessage("Are you sure want to exit?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
