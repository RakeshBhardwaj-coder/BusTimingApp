package rakesh.app.bustimingapp.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import rakesh.app.bustimingapp.BusRegistration.BusRegistrationPage;
import rakesh.app.bustimingapp.Home.MainActivity;
import rakesh.app.bustimingapp.R;

public class SignInPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText etEmail;
    EditText etPassword;
    Toolbar signInToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // get the white home back icon
        signInToolbar = findViewById(R.id.signInToolBar);
        setSupportActionBar(signInToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





    }


    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(), BusRegistrationPage.class));
        }
    }
    public void LoginBtn(View view){
        LoginUser();
    }

    public void SignUpPage(View view){
        Intent i = new Intent(getApplicationContext(), SignUpPage.class);
        startActivity(i);
    }
    public void ForgetPassword(View view){
        startActivity(new Intent(getApplicationContext(), ForgetPasswordPage.class));
    }
    public void LoginUser(){
        String emailStr = etEmail.getText().toString();
        String passwordStr = etPassword.getText().toString();
        if(TextUtils.isEmpty(emailStr)){
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
        }else if(TextUtils.isEmpty(passwordStr)){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(emailStr,passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Login Successfully.", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "LogIn Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }


}