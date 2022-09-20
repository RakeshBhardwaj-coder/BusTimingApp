package rakesh.app.bustimingapp.BusRegistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rakesh.app.bustimingapp.Auth.SignInPage;
import rakesh.app.bustimingapp.MainActivity;
import rakesh.app.bustimingapp.R;

public class BusRegistrationPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner busType;
    List<String> busTypeList;

    TimePickerDialog picker;
    TextView tvSourceTime,tvDestinationTime;

    Button submitBtn,cancelBtn;
    AlertDialog.Builder submitBuilder, cancelBuilder;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_registration_page);

        busType = findViewById(R.id.sBusType);


        // Spinner Drop down elements
        busTypeList= new ArrayList<String>();
        busTypeList.add("Select Bus Type");
        busTypeList.add("City Bus");
        busTypeList.add("Private Bus");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, busTypeList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        busType.setAdapter(dataAdapter);
        busType.setOnItemSelectedListener(this);

        tvSourceTime = findViewById(R.id.tvSourceTime);
        tvDestinationTime = findViewById(R.id.tvDestinationTime);

        submitBtn = findViewById(R.id.btnSubmit);
        cancelBtn = findViewById(R.id.btnCancel);
        submitBuilder = new AlertDialog.Builder(this);
        cancelBuilder = new AlertDialog.Builder(this);

        //submit btn click dialog box will be show
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitBuilder.setTitle("Alert")
                        .setMessage("Do you want to register bus details...")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

        //cancel btn click dialog box will be show
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitBuilder.setTitle("Alert")
                        .setMessage("Do you want to Cancel.")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

        // this is for the Side Navigation Bar
        drawerLayout = findViewById(R.id.drawableLayoutRegiPage);
        navigationView = findViewById(R.id.sideBarRegiPage);
        toolbar = findViewById(R.id.toolBarRegiPage);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenSidebar,R.string.CloseSidebar);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_home:
                        HomePage();
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_registerBusDetails:
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





    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(busTypeList.get(i) != busTypeList.get(0)){

            Toast.makeText(getApplicationContext(), ""+ busTypeList.get(i),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void AddTimeSource(View view){
        final Calendar cldr = Calendar.getInstance();

        // time picker dialog
        picker = new TimePickerDialog(BusRegistrationPage.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        cldr.set(0,0,0,sHour,sMinute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa");

                        tvSourceTime.setText(simpleDateFormat.format(cldr.getTime()).toString());
                    }
                }, 12, 0, false);
        picker.show();
    }

    public void AddTimeSource2(View view){

    }
    public void AddTimeDestination(View view){
        final Calendar cldr = Calendar.getInstance();
        // time picker dialog
        picker = new TimePickerDialog(BusRegistrationPage.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        cldr.set(0,0,0,sHour,sMinute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa");

                        tvDestinationTime.setText(simpleDateFormat.format(cldr.getTime()).toString());
                    }
                }, 12, 0, false);
        picker.show();
    }

    private void HomePage(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        this.startActivity(i);
    }
}