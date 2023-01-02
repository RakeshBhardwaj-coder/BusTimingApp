package rakesh.app.bustimingapp.BusRegistration;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rakesh.app.bustimingapp.Models.BusModel;
import rakesh.app.bustimingapp.R;

public class EditBusDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner busEditType;
    String busEditTypeString;
    List<String> busEditTypeList;

    TimePickerDialog picker;

    Button editBtn,cancelBtn;
    AlertDialog.Builder editBtnBuilder, cancelBtnBuilder;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    EditText busEditName,busEditSource,busEditDestination;
    TextView busEditNumber,busEditSourceTime,busEditDestinationTime;

    String busNumberKey; // getting the key from the AddStops page.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bus_details);

        //Getting busType, selecting from the spinner.
        busEditType = findViewById(R.id.sEditBusType);

        // Spinner Drop down elements
        busEditTypeList= new ArrayList<String>();
        busEditTypeList.add("Select Bus Type");
        busEditTypeList.add("City Bus");
        busEditTypeList.add("Private Bus");

        // Creating adapter for spinner
        ArrayAdapter<String> busEditTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, busEditTypeList);

        // Drop down layout style - list view with radio button
        busEditTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        busEditType.setAdapter(busEditTypeAdapter);
        busEditType.setOnItemSelectedListener(this);

        // Getting the key
        busNumberKey = getIntent().getStringExtra("BusNumberKey");

        busEditNumber = findViewById(R.id.etEditBusNum);
        busEditName = findViewById(R.id.etEditBusName);
        busEditSource = findViewById(R.id.etEditSource);
        busEditDestination = findViewById(R.id.etEditDestination);
        busEditSourceTime = findViewById(R.id.tvEditSourceTime);
        busEditDestinationTime = findViewById(R.id.tvEditDestinationTime);

        busEditNumber.setText(busNumberKey);

        // Passing the bus number key to get data from the firebase
        GetBusDetailsData(busNumberKey);




        // Edit btn click dialog box will be show
        editBtn = findViewById(R.id.btnEdit);
        editBtnBuilder = new AlertDialog.Builder(EditBusDetails.this);


        editBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"hellowEdit",Toast.LENGTH_SHORT).show();
               UpdateData();

            }
        });

        //cancel btn click dialog box will be show
        cancelBtn = findViewById(R.id.btnCancel);
        cancelBtnBuilder = new AlertDialog.Builder(EditBusDetails.this);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelBtnBuilder.setTitle("Alert")
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



    }
    // Get the data using busNum key
    public void GetBusDetailsData(String busNumKey){

        FirebaseFirestore.getInstance().collection("Buses").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Bus Number").document(busNumKey)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error == null){
                        BusModel selectedBusModel = value.toObject(BusModel.class);
                        busEditName.setText(selectedBusModel.getBusName());
                        busEditSource.setText(selectedBusModel.getBusSource());
                        busEditDestination.setText(selectedBusModel.getBusDestination());
                        busEditSourceTime.setText(selectedBusModel.getBusSourceTime());
                        busEditDestinationTime.setText(selectedBusModel.getBusDestinationTime());
                    }
                    }
                });
    }

    // Get the data using
    public void UpdateData(){
        String busEditNumberStr,busEditedTypeStr,busEditedNameStr,busEditedSourceStr,busEditedDestinationStr;
        String busEditedSourceTimeStr,busEditedDestinationTimeStr;

        if(busEditTypeString!=null){
        busEditedTypeStr = busEditTypeString;
        }else{
            busEditedTypeStr = "Other Type";
            Toast.makeText(getApplicationContext(), "Select Bus Type!!!",Toast.LENGTH_SHORT).show();
        }
        busEditNumberStr = busEditNumber.getText().toString();
        busEditedNameStr = busEditName.getText().toString();
        busEditedSourceStr = busEditSource.getText().toString();
        busEditedDestinationStr = busEditDestination.getText().toString();
        busEditedSourceTimeStr = busEditSourceTime.getText().toString();
        busEditedDestinationTimeStr = busEditDestinationTime.getText().toString();

        if(TextUtils.isEmpty(busEditedNameStr)){
            busEditName.setError("Bus Name cannot be empty");
            busEditName.requestFocus();
        }else if(TextUtils.isEmpty(busEditedSourceStr)){
            busEditSource.setError("Source cannot be empty");
            busEditSource.requestFocus();
        }else if(TextUtils.isEmpty(busEditedDestinationStr)){
            busEditDestination.setError("Bus Name cannot be empty");
            busEditDestination.requestFocus();
        }else if(TextUtils.isEmpty(busEditedSourceTimeStr)){
            busEditSourceTime.setError("Bus Name cannot be empty");
            busEditSourceTime.requestFocus();
        }else if(TextUtils.isEmpty(busEditedDestinationTimeStr)){
            busEditDestinationTime.setError("Bus Name cannot be empty");
            busEditDestinationTime.requestFocus();
        }
        else {

            editBtnBuilder.setTitle("Alert")
                    .setMessage("Do you want to Edit the Data...")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Toast.makeText(getApplicationContext(), "a"+currentUserId, Toast.LENGTH_SHORT).show();

                            FirebaseFirestore.getInstance().collection("Buses").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Bus Number").document(busNumberKey)
                                    .update("busName",busEditedNameStr,"busNumber",busEditNumberStr,"busType",busEditedTypeStr,"busDestination",busEditedDestinationStr,"busDestinationTime",busEditedDestinationTimeStr,"busSource",busEditedSourceStr,"busSourceTime",busEditedSourceTimeStr)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(),"Edit Successfully",Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
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

    public void EditTimeSource(View view){
        final Calendar cldr = Calendar.getInstance();

        // time picker dialog
        picker = new TimePickerDialog(EditBusDetails.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        cldr.set(0,0,0,sHour,sMinute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa");

                        busEditSourceTime.setText(simpleDateFormat.format(cldr.getTime()).toString());
                    }
                }, 12, 0, false);
        picker.show();
    }

    public void EditTimeDestination(View view){
        final Calendar cldr = Calendar.getInstance();
        // time picker dialog
        picker = new TimePickerDialog(EditBusDetails.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        cldr.set(0,0,0,sHour,sMinute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa");

                        busEditDestinationTime.setText(simpleDateFormat.format(cldr.getTime()).toString());
                    }
                }, 12, 0, false);
        picker.show();
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(busEditTypeList.get(i) != busEditTypeList.get(0)){
            busEditTypeString = busEditTypeList.get(i);
            Toast.makeText(getApplicationContext(), ""+ busEditTypeString,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}