package rakesh.app.bustimingapp.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import rakesh.app.bustimingapp.Auth.SignInPage;
import rakesh.app.bustimingapp.BusRegistration.AllBuseDetails;
import rakesh.app.bustimingapp.FindYourBus.FindYourBus;
import rakesh.app.bustimingapp.R;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    EditText etStoppage, etDestination;
    ImageView btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawableLayout);
        navigationView = findViewById(R.id.sideBar);
        toolbar = findViewById(R.id.toolBar);

        etStoppage = findViewById(R.id.etStoppage);
        etDestination = findViewById(R.id.etStoppage);

        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenSidebar,R.string.CloseSidebar);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();

        btnSearch = findViewById(R.id.searchBtn);


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
                        startActivity(new Intent(getApplicationContext(),SignInPage.class));
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

        etStoppage = findViewById(R.id.etStoppage);
        etDestination = findViewById(R.id.etDestination);

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etStoppage.getText().toString())){
                    etStoppage.setError("Stoppage cannot be empty");
                    etStoppage.requestFocus();
                }else if(TextUtils.isEmpty(etDestination.getText().toString())){
                    etDestination.setError("Destination cannot be empty");
                    etDestination.requestFocus();
                }else{
                    FindYourBusPage(etStoppage.getText().toString(),etDestination.getText().toString());
                }
            }
        });

    }

    public void Clicked(View view){

        etStoppage.setText("");
        etDestination.setText("");
        Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
    }
    public void SignInPage(){
        Intent i = new Intent(getApplicationContext(), SignInPage.class);
        this.startActivity(i);
        Toast.makeText(getApplicationContext(),"SignInPage",Toast.LENGTH_SHORT).show();
    }


    public void FindYourBusPage(String busStoppage,String busDestination){
        Toast.makeText(getApplicationContext(),"Find",Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), FindYourBus.class).putExtra("BusStoppage",busStoppage).putExtra("BusDestination",busDestination));
    }
//


}