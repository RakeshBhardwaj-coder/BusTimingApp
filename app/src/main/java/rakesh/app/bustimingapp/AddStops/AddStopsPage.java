package rakesh.app.bustimingapp.AddStops;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import rakesh.app.bustimingapp.Auth.SignInPage;
import rakesh.app.bustimingapp.BusRegistration.AllBuseDetails;
import rakesh.app.bustimingapp.Home.MainActivity;
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

    static int busStopIndex;
    AlertDialog.Builder addStopsBtnBuilder;

    // for stop details builder
    TextView tvBusStopIndex;
    EditText busStopName,busReachTime,busExitTime,busWaitingTime;
    String busStopNameStr,busReachTimeStr,busExitTimeStr,busWaitingTimeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stops_page);

        // this is for the Side Navigation Bar
        drawerLayout = findViewById(R.id.drawableLayoutAddBusStopsPage);
        addBusStopsToolbar = findViewById(R.id.toolBarAddStopsPage);




        // get the white home back icon
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
        String busNumberKey = getIntent().getStringExtra("BusNumberKey");
        // Get all bus key data
        GetBusDetailsData(busNumberKey);
//
//        // for stop details builder
//        busStopName = findViewById(R.id.asdsdBusStopName);
//        busReachTime = findViewById(R.id.asdsdBusReachTime);
//        busExitTime = findViewById(R.id.asdsdBusExitTime);
//        busWaitingTime = findViewById(R.id.asdsdBusWaitingTime);

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
        busStopIndex = busStopIndex + 1;

        String busNumberKey = getIntent().getStringExtra("BusNumberKey");

        addStopsBtnBuilder = new AlertDialog.Builder(this);
        addStopsBtnBuilder.setTitle("Add Bus Stop")
                .setCancelable(false);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.add_stops_data_show_design,null);
        addStopsBtnBuilder.setView(customLayout);
        busStopName = customLayout.findViewById(R.id.asdsdBusStopName);
        busReachTime = customLayout.findViewById(R.id.asdsdBusReachTime);
        busWaitingTime = customLayout.findViewById(R.id.asdsdBusWaitingTime);
        busExitTime = customLayout.findViewById(R.id.asdsdBusExitTime);





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
                                BusStopsModel busStopsModel = new BusStopsModel(""+busStopIndex,busStopNameStr,busReachTimeStr,busExitTimeStr,busWaitingTimeStr);
                                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Stops").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(busNumberKey).document(""+busStopIndex);
                                documentReference.set(busStopsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {


                                        Toast.makeText(getApplicationContext(),"Stop "+busStopIndex+busStopNameStr,Toast.LENGTH_SHORT).show();
//                                        busStopIndex = busStopIndex + 1;
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG,"Error "+ e.toString());
                                    }
                                });
                                Toast.makeText(getApplicationContext(),"Stop "+busStopIndex+busStopNameStr,Toast.LENGTH_SHORT).show();

//                                if(TextUtils.isEmpty(busStopNameStr)){
//                                    busStopName.setError("Bus Stop Name cannot be empty");
//                                    busStopName.requestFocus();
//                                }
//                                else if(TextUtils.isEmpty(busReachTimeStr)){
//                                    busReachTime.setError("Bus Name cannot be empty");
//                                    busReachTime.requestFocus();
//                                }else if(TextUtils.isEmpty(busExitTimeStr)){
//                                    busExitTime.setError("Source cannot be empty");
//                                    busExitTime.requestFocus();
//                                }else if(TextUtils.isEmpty(busWaitingTimeStr)){
//                                    busWaitingTime.setError("Bus Name cannot be empty");
//                                    busWaitingTime.requestFocus();
//                                }else{
//
//                                }



                            }
                        });

        addStopsBtnBuilder.create().show();

//        busStopIndex++;
//        BusStopsModel busStopsModelIndex = new BusStopsModel(""+busStopIndex);
//        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Stops").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Bus Stops").document(""+busStopIndex);
//
//        documentReference.set(busStopsModelIndex).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//
//                                Toast.makeText(getApplicationContext(),"hii "+busStopIndex,Toast.LENGTH_SHORT).show();
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG,"Error : " + e.toString());
//            }
//        });


    }

}