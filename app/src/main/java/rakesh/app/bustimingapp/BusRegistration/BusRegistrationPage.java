package rakesh.app.bustimingapp.BusRegistration;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rakesh.app.bustimingapp.Auth.SignInPage;
import rakesh.app.bustimingapp.FindYourBus.FindYourBus;
import rakesh.app.bustimingapp.Models.BusModel;
import rakesh.app.bustimingapp.Home.MainActivity;
import rakesh.app.bustimingapp.R;

public class BusRegistrationPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner busType;
    String busTypeString;
    List<String> busTypeList;

    TimePickerDialog picker;
    TextView tvSourceTime,tvDestinationTime;

    Button submitBtn,cancelBtn;
    AlertDialog.Builder submitBuilder, cancelBuilder;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    EditText busNumber,busName,source,destination;
    TextView sourceTime,destinationTime;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_registration_page);

        //Getting busType, selecting from the spinner.
        busType = findViewById(R.id.sBusType);

        // Spinner Drop down elements
        busTypeList= new ArrayList<String>();
        busTypeList.add("Select Bus Type");
        busTypeList.add("City Bus");
        busTypeList.add("Private Bus");


        // Creating adapter for spinner
        ArrayAdapter<String> busTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, busTypeList);

        // Drop down layout style - list view with radio button
        busTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        busType.setAdapter(busTypeAdapter);
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

                SubmitRegistration();

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
                                finish();  //finish() will take you to the home screen.
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

        DrawerArrowDrawable tog = toggle.getDrawerArrowDrawable();
        tog.setColor(Color.WHITE);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Sidebar Options are clickable now
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


        //All Inputs Getting for Firestore
        //busType already set.
        busNumber = findViewById(R.id.etBusNum);
        busName = findViewById(R.id.etBusName);
        source = findViewById(R.id.etSource);
        destination = findViewById(R.id.etDestination);
        sourceTime = findViewById(R.id.tvSourceTime);
        destinationTime = findViewById(R.id.tvDestinationTime);

        firestore = FirebaseFirestore.getInstance();


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(busTypeList.get(i) != busTypeList.get(0)){
            busTypeString = busTypeList.get(i);
            Toast.makeText(getApplicationContext(), ""+ busTypeString,Toast.LENGTH_SHORT).show();
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

    //Sending Data to the firestore...
    public void SubmitRegistration(){
        String busTypeStr,busNumberStr,busNameStr,sourceStr,destinationStr;
        String sourceTimeStr,destinationTimeStr;

        busNumberStr =  busNumber.getText().toString();
        busNameStr = busName.getText().toString();

        if(busTypeString!=null){
            busTypeStr = busTypeString;
        }else {
            busTypeStr = "Other Type";
            Toast.makeText(getApplicationContext(), "bus type is empty!!!",Toast.LENGTH_SHORT).show();
        }
        sourceStr = source.getText().toString();
        destinationStr = destination.getText().toString();
        sourceTimeStr = sourceTime.getText().toString();
        destinationTimeStr = destinationTime.getText().toString();

        if(TextUtils.isEmpty(busNumberStr)){
            busNumber.setError("Bus Type cannot be empty");
            busNumber.requestFocus();
        }
        else if(TextUtils.isEmpty(busNameStr)){
            busName.setError("Bus Name cannot be empty");
            busName.requestFocus();
        }else if(TextUtils.isEmpty(sourceStr)){
            source.setError("Source cannot be empty");
            source.requestFocus();
        }else if(TextUtils.isEmpty(destinationStr)){
            destination.setError("Bus Name cannot be empty");
            destination.requestFocus();
        }else if(TextUtils.isEmpty(sourceTimeStr)){
            sourceTime.setError("Bus Name cannot be empty");
            sourceTime.requestFocus();
        }else if(TextUtils.isEmpty(destinationTimeStr)){
            destinationTime.setError("Bus Name cannot be empty");
            destinationTime.requestFocus();
        }
        else {

            submitBuilder.setTitle("Alert")
                    .setMessage("Do you want to register bus details...")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Toast.makeText(getApplicationContext(), "a"+currentUserId, Toast.LENGTH_SHORT).show();
//                            FindYourBus.busNumberList.add(busNumberStr);

                            //Using the model to set the data to firestore
                            FindYourBus.addBusNumber(busNumberStr);
                            BusModel busModel = new BusModel(busNumberStr,busTypeStr,busNameStr,sourceStr,destinationStr,sourceTimeStr,destinationTimeStr);

                            DocumentReference documentReference = firestore.collection("Buses").document(currentUserId).collection("Bus Number").document(busNumberStr);

//                            // This map method if you don't use any model.
//                            Map<String,Object> user = new HashMap<>();
//                            user.put("Bus Type",busTypeStr);
//
                            documentReference.set(busModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"Bus Details are Registered of "+currentUserId);
                                    startActivity(new Intent(getApplicationContext(), AllBuseDetails.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"Error : " + e.toString());

                                }
                            });

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

    }


}