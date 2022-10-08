package sgp.project.society;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class RegisterActivity extends AppCompatActivity {


    TextInputLayout et_full_name;
    TextInputLayout et_email_id;
    TextInputLayout et_flat_number;
    TextInputLayout et_mobile_number;
    TextInputLayout et_no_of_twowheel;
    TextInputLayout et_no_of_fourwheel;
    TextInputLayout et_password;

    TextView tv_already_account_login;
    ImageView iv_back_to_login;

    CircularProgressButton btn_register;

    DummyClassRegisterActivity register;

    public static ProgressDialog progressDialog;


//    Firebase Database Code

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        if (LoginActivity.progressDialog == null) {
//            return;
        }
        else {
            LoginActivity.progressDialog.dismiss();
        }
        if (ForgotPasswordActivity.progressDialog == null) {
//            return;
        }
        else {
            ForgotPasswordActivity.progressDialog.dismiss();
        }


        et_full_name = findViewById(R.id.et_full_name);
        et_email_id = findViewById(R.id.et_email_id);
        et_flat_number = findViewById(R.id.et_flat_number);
        et_mobile_number = findViewById(R.id.et_mobile_number);
        et_no_of_twowheel = findViewById(R.id.et_no_of_twowheel);
        et_no_of_fourwheel = findViewById(R.id.et_no_of_fourwheel);
        et_password = findViewById(R.id.et_password);
        btn_register = findViewById(R.id.btn_register);

        tv_already_account_login = findViewById(R.id.tv_already_account_login);
        iv_back_to_login = findViewById(R.id.iv_back_to_login);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(RegisterActivity.this);

        register = new DummyClassRegisterActivity();




        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateFullName() | !validateEmail() | !validateFlatNumber() |
                        !validatePassword() | !validateMobileNumber() | !validateNumberOfFourwheel() |
                        !validateNumberOfTwowheel())
                {
                    return;
                }

                RegisterActivity.showProgressBar();

                firebaseAuth.createUserWithEmailAndPassword(et_email_id.getEditText().getText().toString(),
                        et_password.getEditText().getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    RegisterActivity.progressDialog.dismiss();

                                    storeDataIntoDatabase();
                                    Toast.makeText(RegisterActivity.this, "Successfully Registered!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }
                                else
                                {
                                    RegisterActivity.progressDialog.dismiss();

                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


        tv_already_account_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.showProgressBar();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

//        linearlayout_faculty_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RegisterActivity.showProgressBar();
//                Intent intent = new Intent(RegisterActivity.this, FacultyLoginActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
//            }
//        });

        iv_back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.showProgressBar();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

    }

    public boolean validateFullName()
    {
        String fullname = et_full_name.getEditText().getText().toString().trim().toLowerCase();

        if (fullname.isEmpty()) {
            et_full_name.setError("Field can't be empty");
            return false;
        }
        else if (fullname.length() < 3) {
            et_full_name.setError("Name too short");
            return false;
        }
        else if (fullname.length() > 30) {
            et_full_name.setError("Name too long");
            return false;
        }
        else {
            et_full_name.setError(null);
            return true;
        }
    }

    public boolean validateEmail()
    {
        String email = et_email_id.getEditText().getText().toString().trim().toLowerCase();

        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public boolean validateFlatNumber()
    {
        String flatnumber = et_flat_number.getEditText().getText().toString().trim().toLowerCase();

        if (flatnumber.isEmpty()) {
            et_flat_number.setError("Field can't be empty");
            return false;
        }
        else if (flatnumber.length() <= 3 || flatnumber.length() > 5 ) {
            et_flat_number.setError("Enter valid flat no. ex. A-302");
            return false;
        }
        else {
            et_flat_number.setError(null);
            return true;
        }
    }

    public boolean validateMobileNumber()
    {
        String mobilenumber = et_mobile_number.getEditText().getText().toString().trim().toLowerCase();

        if (mobilenumber.isEmpty()) {
            et_mobile_number.setError("Field can't be empty");
            return false;
        }
        else if (mobilenumber.length() != 10) {
            et_mobile_number.setError("Enter valid Mobile No.");
            return false;
        }
        else {
            et_mobile_number.setError(null);
            return true;
        }
    }

    public boolean validateNumberOfTwowheel()
    {
        String numberoftwowheel = et_no_of_twowheel.getEditText().getText().toString().trim().toLowerCase();

        if (numberoftwowheel.isEmpty()) {
            et_no_of_twowheel.setError("Field can't be empty");
            return false;
        }
        else if (Integer.parseInt(numberoftwowheel) < 0 || Integer.parseInt(numberoftwowheel) > 3) {
            et_no_of_twowheel.setError("Enter number between 0-3!");
            return false;
        }
        else {
            et_no_of_twowheel.setError(null);
            return true;
        }
    }

    public boolean validateNumberOfFourwheel()
    {
        String numberoffourwheel = et_no_of_fourwheel.getEditText().getText().toString().trim().toLowerCase();

        if (numberoffourwheel.isEmpty()) {
            et_no_of_fourwheel.setError("Field can't be empty");
            return false;
        }
        else if (Integer.parseInt(numberoffourwheel) < 0 || Integer.parseInt(numberoffourwheel) > 3) {
            et_no_of_fourwheel.setError("Enter number between 0-3!");
            return false;
        }
        else {
            et_no_of_fourwheel.setError(null);
            return true;
        }
    }

    public boolean validatePassword()
    {
        String password = et_password.getEditText().getText().toString().trim();

        if (password.isEmpty()) {
            et_password.setError("Field can't be empty");
            return false;
        }
        else if (password.length() < 6) {
            et_password.setError("Password too short");
            return false;
        }
        else if (!isStrongPassword(password)) {
            et_password.setError("Password must contain atleast one Numeric, Upper case, Special character value");
            return false;
        }
        else {
            et_password.setError(null);
            return true;
        }
    }
    public static boolean isStrongPassword(final String password)
    {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^" +
                "(?=.*[0-9])" +
                "(?=.*[A-Z])" +
                "(?=.*[@#$%^&+=!])" +
                "(?=\\S+$)" +
                ".{6,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public void storeDataIntoDatabase()
    {
        register.setEt_flat_no(et_flat_number.getEditText().getText().toString());
        register.setEt_full_name(et_full_name.getEditText().getText().toString());
        register.setEt_mobile_number(et_mobile_number.getEditText().getText().toString());
        register.setEt_no_of_fourwheel(et_no_of_fourwheel.getEditText().getText().toString());
        register.setEt_no_of_twowheel(et_no_of_twowheel.getEditText().getText().toString());
        register.setEt_password(et_password.getEditText().getText().toString());
        register.setNumber_of_member(0);

        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("register_data").setValue(register);
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
