package rakesh.app.bustimingapp.Auth;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import rakesh.app.bustimingapp.R;

public class ForgetPasswordPage extends AppCompatActivity {

    Button resetPasswordBtn;
    EditText etEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_page);

        resetPasswordBtn = findViewById(R.id.resetBtn);
        etEmail = findViewById(R.id.etFPEmail);



        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(etEmail.getText())){
                    etEmail.setError("Email cannot be empty");
                    etEmail.requestFocus();
                }else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(etEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG,"Email Sent");
                                Toast.makeText(getApplicationContext(),"Please check your email or email spam section.",Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(),SignInPage.class));
                            }
                        }
                    });
                }

            }
        });
    }

    public void SignInPage(View view){
        startActivity(new Intent(getApplicationContext(),SignInPage.class));
    }
}