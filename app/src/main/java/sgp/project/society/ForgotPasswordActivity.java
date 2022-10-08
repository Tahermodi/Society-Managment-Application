package sgp.project.society;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputLayout et_resetemail;
    Button btn_forgot_password_reset;
    TextView tv_forgot_password_register;
    ImageView iv_close_forgot_password;

    FirebaseAuth firebaseAuth;

    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        if (LoginActivity.progressDialog == null) {
//            return;
        }
        else {
            LoginActivity.progressDialog.dismiss();
        }

        et_resetemail = findViewById(R.id.et_resetemail);
        btn_forgot_password_reset = findViewById(R.id.btn_forgot_password_reset);
        tv_forgot_password_register = findViewById(R.id.tv_forgot_password_register);
        iv_close_forgot_password = findViewById(R.id.iv_close_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);

        btn_forgot_password_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateEmail()) {
                    return;
                }

                ForgotPasswordActivity.showProgressBar();

                firebaseAuth.sendPasswordResetEmail(et_resetemail.getEditText().getText().toString().trim().toLowerCase())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    ForgotPasswordActivity.progressDialog.dismiss();
                                    et_resetemail.getEditText().getText().clear();
                                    Toast.makeText(ForgotPasswordActivity.this,
                                            "Password reset link is sent to your registered Email id", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ForgotPasswordActivity.progressDialog.dismiss();
                                    Toast.makeText(ForgotPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        tv_forgot_password_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPasswordActivity.showProgressBar();
                Intent intent = new Intent(ForgotPasswordActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

        iv_close_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPasswordActivity.showProgressBar();
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });


    }

    public boolean validateEmail()
    {
        String email = et_resetemail.getEditText().getText().toString().trim().toLowerCase();

        if (email.isEmpty()) {
            et_resetemail.setError("Field can't be empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_resetemail.setError("Please enter valid email address");
            return false;
        }
        else {
            et_resetemail.setError(null);
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

}
