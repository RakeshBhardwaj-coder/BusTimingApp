package rakesh.app.bustimingapp.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import rakesh.app.bustimingapp.Home.MainActivity;
import rakesh.app.bustimingapp.R;
import rakesh.app.bustimingapp.TestSearching.SearchingAbilityCheck;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Hander and delayed to show the splash screen for second then open the home screen
        // show the logo then start the next activity after the 1sec
        // when you back the logo will not show.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        },1000);
    }
}