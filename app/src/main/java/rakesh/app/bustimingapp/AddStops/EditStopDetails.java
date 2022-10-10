package rakesh.app.bustimingapp.AddStops;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import rakesh.app.bustimingapp.Models.BusStopsModel;
import rakesh.app.bustimingapp.R;

public class EditStopDetails extends AppCompatActivity {

    String busStopKey;
    String busNumberKey;

    EditText editBusStopName,editBusWaitingTime;
    TextView editingBusStopIndex,editBusReachTime,editBusExitTime;
    ImageView setReachTime,setExitTime;

    Button editBtn;
    Button editCancelBtn;
    AlertDialog.Builder editBtnBuilder,editCancelBtnBuilder;

    Toolbar editBusStopsDetailsToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stop_details);

        busStopKey = getIntent().getStringExtra("busStopKey");
        busNumberKey = getIntent().getStringExtra("busNumberKey");

        editingBusStopIndex = findViewById(R.id.esdStopIndex);
        editBusExitTime = findViewById(R.id.esdBusExitTime);
        editBusReachTime = findViewById(R.id.esdBusReachTime);
        editBusStopName = findViewById(R.id.esdBusStopName);
        editBusWaitingTime = findViewById(R.id.esdBusWaitingTime);

        editCancelBtn = findViewById(R.id.esdCancelBtn);
        editBtn = findViewById(R.id.esdEditBtn);

        editingBusStopIndex.setText(busStopKey);

        // getting the stop data form the firebase
        GetStopData();

        editCancelBtnBuilder = new AlertDialog.Builder(this);
        editBtnBuilder = new AlertDialog.Builder(this);

        // when click on clock image got the method of addstopspage and set time to given parameter
        setReachTime = findViewById(R.id.esdSetBusReachTime);
        setExitTime = findViewById(R.id.esdSetBusExitTime);

        // set white(popup theme in xml) back(manifest file) button
        editBusStopsDetailsToolbar = findViewById(R.id.toolBarEditStopsDetails);
        setSupportActionBar(editBusStopsDetailsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setReachTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddStopsPage().SetBusReachTime(EditStopDetails.this,editBusReachTime);
            }
        });
        setExitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddStopsPage().SetBusExitTime(EditStopDetails.this,editBusExitTime);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBtnBuilder.setTitle("Alert")
                        .setMessage("Do you want to cancel edit")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                UpdateStopData();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });
        editCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editCancelBtnBuilder.setTitle("Alert")
                                .setMessage("Do you want to cancel edit")
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });


    }
    void GetStopData(){
        // getting the bus number and stop key from the stop adapter and fetching for edit the data
        FirebaseFirestore.getInstance().collection("Stops").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(busNumberKey).document(busStopKey)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error == null){
                            BusStopsModel stopData = value.toObject(BusStopsModel.class);

                            editBusStopName.setText(stopData.getBusStopName());
                            editBusReachTime.setText(stopData.getBusReachTime());
                            editBusExitTime.setText(stopData.getBusExitTime());
                            editBusWaitingTime.setText(stopData.getBusWaitingTime());
                        }
                    }
                });
    }
    void UpdateStopData(){

        String editBusStopNameStr,editBusReachTimeStr,editBusExitTimeStr,editBusWaitingTimeStr;
        editBusStopNameStr = editBusStopName.getText().toString();
        editBusReachTimeStr = editBusReachTime.getText().toString();
        editBusExitTimeStr = editBusExitTime.getText().toString();
        editBusWaitingTimeStr = editBusWaitingTime.getText().toString();

        FirebaseFirestore.getInstance().collection("Stops").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(busNumberKey).document(busStopKey)
                .update("busStopName",editBusStopNameStr,"busReachTime",editBusReachTimeStr,"busExitTime",editBusExitTimeStr,"busWaitingTime",editBusWaitingTimeStr)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Edit Successfully",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Error : " + e.toString());

                    }
                });
        finish();

    }

}