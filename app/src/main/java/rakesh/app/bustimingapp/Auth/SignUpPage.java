package rakesh.app.bustimingapp.Auth;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import rakesh.app.bustimingapp.R;

public class SignUpPage extends AppCompatActivity {

    EditText etFullName, etEmail, etPassword;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    Toolbar signUpToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // get the white home back icon
        signUpToolbar = findViewById(R.id.signUpToolBar);
        setSupportActionBar(signUpToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // parent activity name = Main Activity in android menifest file will send to home
    }

    public void SignInPage(View view){
        Intent i = new Intent(getApplicationContext(), SignInPage.class);
        startActivity(i);
    }

    public void SignUpBtn(View view) {
        CreateUser();
    }

    private void CreateUser() {
        String fullNameStr = etFullName.getText().toString();
        String emailStr = etEmail.getText().toString();
        String passwordStr = etPassword.getText().toString();
        if(TextUtils.isEmpty(fullNameStr)){
            etFullName.setError("Name cannot be empty");
            etFullName.requestFocus();
        }else if(TextUtils.isEmpty(emailStr)){
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
        } else if(TextUtils.isEmpty(passwordStr)){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(emailStr,passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Registration Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), SignInPage.class));
                        BusOwnerDetails();

                    }else {
                        Toast.makeText(getApplicationContext(), "Registration Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }

    private void BusOwnerDetails() {

        String fullNameStr, emailStr;
        fullNameStr = etFullName.getText().toString();
        emailStr = etEmail.getText().toString();

        String currentUserId = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firestore.collection("BusOwners").document(currentUserId);
        Map<String, Object> busOwnerDetails = new HashMap<>();
        busOwnerDetails.put("Full Name",fullNameStr);
        busOwnerDetails.put("Email", emailStr);

        documentReference.set(busOwnerDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG,"Bus Details are Registered of "+currentUserId);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"Error : " + e.toString());
            }
        });
        finish();
    }
}