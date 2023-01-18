package rakesh.app.bustimingapp.AddStops;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firestore.admin.v1.Index;
import com.google.firestore.admin.v1.IndexOrBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rakesh.app.bustimingapp.Adapters.StopsDetailsDataAdapter;
import rakesh.app.bustimingapp.Models.BusModel;
import rakesh.app.bustimingapp.Models.BusStopsModel;
import rakesh.app.bustimingapp.R;

public class AddStopsPage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar addBusStopsToolbar;

    LinearLayout llAddStopBtn;
    Animation addStopBtnAnim;

    TextView addStopsBusNumber,addStopsBusName,addStopsBusType,addStopsBusSource,addStopsBusDestination,addStopsBusSourceTime,addStopsBusDestinationTime;

    public static int busStopIndex;

    AlertDialog.Builder addStopsBtnBuilder;
    ProgressDialog progressDialog;
    // for stop details builder
    TextView busStopIndexShow; //show the index before add stops
    TextView busExitTime,busReachTime;
    EditText busStopName,busWaitingTime;
    String busName,busType,busStopNameStr,busReachTimeStr,busExitTimeStr,busWaitingTimeStr,busFinalDestination;
    RecyclerView rvBusStopsData;

    ArrayList<BusStopsModel> busStopsModelsData;  // Bus Stop Model because we get the all data with the form of Bus Model and we get as Array List.

    TimePickerDialog picker;

    public static String busNumberKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stops_page);

        // this is for the Side Navigation Bar
        drawerLayout = findViewById(R.id.drawableLayoutAddBusStopsPage);
        addBusStopsToolbar = findViewById(R.id.toolBarAddStopsPage);

        // get the white(popupTheme in xml) home back(Manifest) btn
         addBusStopsToolbar = findViewById(R.id.toolBarAddStopsPage);
        setSupportActionBar(addBusStopsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // all textviews
        addStopsBusNumber = findViewById(R.id.tvASPBusNumber);
        addStopsBusName = findViewById(R.id.tvASPBusName);
        addStopsBusType = findViewById(R.id.tvASPBusType);
        addStopsBusSource = findViewById(R.id.tvASPSource);
        addStopsBusDestination = findViewById(R.id.tvASPDestination);
        addStopsBusSourceTime = findViewById(R.id.tvASPSourceTime);
        addStopsBusDestinationTime = findViewById(R.id.tvASPDestinationTime);

        // Getting the key
        busNumberKey = getIntent().getStringExtra("BusNumberKey");
        busName = getIntent().getStringExtra("BusNameKey");
        busType = getIntent().getStringExtra("BusTypeKey");
        busFinalDestination = getIntent().getStringExtra("BusFinalDestinationKey");


        //progress dialog assigned
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching the data...");
        progressDialog.show();

        // Get all bus key data
        GetBusDetailsData(busNumberKey);

        //Get all bus stops data in recycle view
        GetAllStopsData();


        // Recycle view to show stops data
        rvBusStopsData = findViewById(R.id.rvBusStopsData);

        // add stops button clicked
        llAddStopBtn = findViewById(R.id.llAddStopsBtn);
        llAddStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Alert dialog show and add the data to the firestore stop section

                AddStopsBtn();
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

                                //getting the count of stops in firebase
                                busStopIndex = StopsDetailsDataAdapter.stopCounts;

                                addStopsBusNumber.setText(selectedBusModel.getBusNumber());

                                addStopsBusName.setText(selectedBusModel.getBusName());
                                addStopsBusType.setText(selectedBusModel.getBusType());
                                addStopsBusSource.setText(selectedBusModel.getBusSource());
                                addStopsBusDestination.setText(selectedBusModel.getBusDestination());
                                addStopsBusSourceTime.setText(selectedBusModel.getBusSourceTime());
                                addStopsBusDestinationTime.setText(selectedBusModel.getBusDestinationTime());

                            }
                        }
                    });
        }


    public void AddStopsBtn(){


        addStopsBtnBuilder = new AlertDialog.Builder(this);
        addStopsBtnBuilder
                .setCancelable(false);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.add_stops_data_show_design,null);
        addStopsBtnBuilder.setView(customLayout);

        busStopIndexShow = customLayout.findViewById(R.id.asdsdStopIndex);
        busStopIndexShow.setText(""+busStopIndex);
        busStopName = customLayout.findViewById(R.id.asdsdBusStopName);
        busReachTime = customLayout.findViewById(R.id.asdsdBusReachTime);
        busWaitingTime = customLayout.findViewById(R.id.asdsdBusWaitingTime);
        busExitTime = customLayout.findViewById(R.id.asdsdBusExitTime);
        ImageView setBusReachTimeBtn = customLayout.findViewById(R.id.asdsdSetBusReachTime);
        ImageView setBusExitTimeBtn = customLayout.findViewById(R.id.asdsdSetBusExitTime);

        setBusReachTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetBusReachTime();
            }
        });
        setBusExitTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetBusExitTime();
            }
        });
        // add a button
        addStopsBtnBuilder
                .setPositiveButton("Add",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,int which)
                            {
                                busStopNameStr = busStopName.getText().toString();
                                busReachTimeStr = busReachTime.getText().toString();
                                busWaitingTimeStr = busWaitingTime.getText().toString();
                                busExitTimeStr = busExitTime.getText().toString();
                                // send data to Firebase
                                BusStopsModel busStopsModel = new BusStopsModel(""+busStopIndex,busName,busType,busStopNameStr,busReachTimeStr,busExitTimeStr,busWaitingTimeStr,busFinalDestination);
                                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Stops").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(busNumberKey).document(""+busStopIndex);
                                documentReference.set(busStopsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                       // Get stops count and increment at same time
                                       GetStopCounts();
                                       AutoIndexing();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG,"Error "+ e.toString());
                                    }
                                });
                                Toast.makeText(getApplicationContext(),"Stop "+busStopIndex+busStopNameStr,Toast.LENGTH_SHORT).show();
                        }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        addStopsBtnBuilder.create().show();
    }

    public void AutoIndexing(){

//        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//
//        BusStopsModel busStopsModel = null;
//        busStopsModel.put("busStopName", "ASCENDING");
//
//
//        firestore.collection("__indexes__").add(BusStopsModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                // the index was created successfully
//            }
//        });


    }
    public void SetBusReachTime(){
        final Calendar cldr = Calendar.getInstance();
        // time picker dialog
        picker = new TimePickerDialog(AddStopsPage.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        cldr.set(0,0,0,sHour,sMinute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa");

                        busReachTime.setText(simpleDateFormat.format(cldr.getTime()).toString());
                    }
                }, 12, 0, false);
        picker.show();
    }
    public void SetBusReachTime(Context context, TextView tvBusReachTime){
        final Calendar cldr = Calendar.getInstance();
        // time picker dialog
        picker = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        cldr.set(0,0,0,sHour,sMinute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa");

                        tvBusReachTime.setText(simpleDateFormat.format(cldr.getTime()).toString());
                    }
                }, 12, 0, false);
        picker.show();
    }
    public void SetBusExitTime(){
        final Calendar cldr = Calendar.getInstance();
        // time picker dialog
        picker = new TimePickerDialog(AddStopsPage.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        cldr.set(0,0,0,sHour,sMinute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa");

                        busExitTime.setText(simpleDateFormat.format(cldr.getTime()).toString());
                    }
                }, 12, 0, false);
        picker.show();
    }
    public void SetBusExitTime(Context context, TextView tvBusExitTime){
        final Calendar cldr = Calendar.getInstance();
        // time picker dialog
        picker = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        cldr.set(0,0,0,sHour,sMinute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa");

                        tvBusExitTime.setText(simpleDateFormat.format(cldr.getTime()).toString());
                    }
                }, 12, 0, false);
        picker.show();
    }



    public void GetAllStopsData() {
        busStopsModelsData = new ArrayList<>();
        busStopsModelsData.clear();

        FirebaseFirestore.getInstance().collection("Stops").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(busNumberKey)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Log.e("Firestore error",error.getMessage());
                    return;

                }else {
                    if(!value.isEmpty()){
                        for (DocumentChange documentChange : value.getDocumentChanges()){
                            if(documentChange.getType() == DocumentChange.Type.ADDED){
                                busStopsModelsData.clear();
                                List<BusStopsModel> stopsData = value.toObjects(BusStopsModel.class);
                                busStopsModelsData.addAll(stopsData);

                                //setting all stop data to the recycle view
                                rvBusStopsData.setLayoutManager(new LinearLayoutManager(AddStopsPage.this));
                                rvBusStopsData.setAdapter(new StopsDetailsDataAdapter(AddStopsPage.this,busStopsModelsData));
                            }else if(documentChange.getType() == DocumentChange.Type.MODIFIED){
                                busStopsModelsData.clear();
                                List<BusStopsModel> stopsData = value.toObjects(BusStopsModel.class);
                                busStopsModelsData.addAll(stopsData);

                                //setting all stop data to the recycle view
                                rvBusStopsData.setLayoutManager(new LinearLayoutManager(AddStopsPage.this));
                                rvBusStopsData.setAdapter(new StopsDetailsDataAdapter(AddStopsPage.this,busStopsModelsData));
                            }else if(documentChange.getType() == DocumentChange.Type.REMOVED){
                                busStopsModelsData.clear();
                                List<BusStopsModel> stopsData = value.toObjects(BusStopsModel.class);
                                busStopsModelsData.addAll(stopsData);

                                //setting all stop data to the recycle view
                                rvBusStopsData.setLayoutManager(new LinearLayoutManager(AddStopsPage.this));
                                rvBusStopsData.setAdapter(new StopsDetailsDataAdapter(AddStopsPage.this,busStopsModelsData));
                            }
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }else {
                        busStopsModelsData.clear();
                        List<BusStopsModel> stopsData = value.toObjects(BusStopsModel.class);
                        busStopsModelsData.addAll(stopsData);

                        //setting all stop data to the recycle view
                        rvBusStopsData.setLayoutManager(new LinearLayoutManager(AddStopsPage.this));
                        rvBusStopsData.setAdapter(new StopsDetailsDataAdapter(AddStopsPage.this,busStopsModelsData));

                        Toast.makeText(getApplicationContext(),"Add your first stop.",Toast.LENGTH_SHORT).show();
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                }
            }
        });

    }

   void GetStopCounts(){
        Map<String, Object> busStopIndexMap = new HashMap<>();
        busStopIndexMap.put("stopCounts", busStopIndex);

        // update the bus stop count to firebase
        FirebaseFirestore.getInstance().collection("Stops").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(busNumberKey).document("--stats--")
                .set(busStopIndexMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Stop "+(busStopIndex-1)+" "+busStopNameStr,Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Error "+e.toString());
                    }
                });
        busStopIndex = busStopIndex + 1;
    }
}