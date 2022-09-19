package rakesh.app.bustimingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import rakesh.app.bustimingapp.BusRegistration.BusRegistrationPage;
import rakesh.app.bustimingapp.Auth.LoginPage;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    EditText etStoppage, etDestination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawableLayout);
        navigationView = findViewById(R.id.sideBar);
        toolbar = findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenSidebar,R.string.CloseSidebar);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_home:
                        startActivity(new Intent(MainActivity.this, BusRegistrationPage.class));
//                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_registerBusDetails:
//                        RegistrationPage();
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;

                    case R.id.menu_logout:
                        Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });

        etStoppage = findViewById(R.id.etStoppage);
        etDestination = findViewById(R.id.etDestination);
    }

    public void Clicked(View view){

        etStoppage.setText("");
        etDestination.setText("");
        Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
    }
    public void RegistrationPage(){
        Intent i = new Intent(getApplicationContext(), LoginPage.class);
        this.startActivity(i);
        Toast.makeText(getApplicationContext(),"Register bus details",Toast.LENGTH_SHORT).show();
    }
}