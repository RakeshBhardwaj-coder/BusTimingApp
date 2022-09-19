package rakesh.app.bustimingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import rakesh.app.bustimingapp.BusRegistration.BusRegistrationPage;

public class abc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abc);
    }
    public void Open(View view){
        Intent i = new Intent(this, BusRegistrationPage.class);
        startActivity(i);
    }
}