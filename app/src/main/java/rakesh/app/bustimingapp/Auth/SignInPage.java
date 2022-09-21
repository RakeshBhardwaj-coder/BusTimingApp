package rakesh.app.bustimingapp.Auth;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import rakesh.app.bustimingapp.BusRegistration.BusRegistrationPage;
import rakesh.app.bustimingapp.R;

public class SignInPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText etEmail;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
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
    public void CancelBtn(View view){

    }

    public void SignUpPage(View view){
        Intent i = new Intent(getApplicationContext(), SignUpPage.class);
        startActivity(i);
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
                        Toast.makeText(getApplicationContext(), "Loggin Successfully.", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), BusRegistrationPage.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "LogIn Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }


}