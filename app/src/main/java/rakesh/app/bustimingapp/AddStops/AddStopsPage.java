package rakesh.app.bustimingapp.AddStops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import rakesh.app.bustimingapp.Auth.SignInPage;
import rakesh.app.bustimingapp.BusRegistration.AllBuseDetails;
import rakesh.app.bustimingapp.Home.MainActivity;
import rakesh.app.bustimingapp.R;

public class AddStopsPage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    LinearLayout llAddStopBtn;
    Animation addStopBtnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stops_page);

        // this is for the Side Navigation Bar
        drawerLayout = findViewById(R.id.drawableLayoutAddBusStopsPage);
        navigationView = findViewById(R.id.sideBarAddBusStops);
        toolbar = findViewById(R.id.toolBarAddStopsPage);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenSidebar,R.string.CloseSidebar);

        DrawerArrowDrawable tog = toggle.getDrawerArrowDrawable();
        tog.setColor(Color.WHITE);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Side Options are clickable now
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_registerBusDetails:
                        startActivity(new Intent(getApplicationContext(), SignInPage.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_all_bus_details:
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if(currentUser != null){
                            startActivity(new Intent(getApplicationContext(), AllBuseDetails.class));
                        }else {
                            startActivity(new Intent(getApplicationContext(),SignInPage.class));
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });

        // add stops btn make a animation
//        addStopBtnAnim = AnimationUtils.loadAnimation(this,R.anim.add_bus_btn_anim);

//        llAddStopBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                llAddStopBtn.startAnimation(addStopBtnAnim);
//            }
//        });

        // add stops button clicked
        llAddStopBtn = findViewById(R.id.llAddStopsBtn);
        llAddStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"hii",Toast.LENGTH_SHORT).show();
            }
        });


    }

}