package rakesh.app.bustimingapp.BusRegistration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import rakesh.app.bustimingapp.Adapters.BusDetailsDataAdapter;
import rakesh.app.bustimingapp.Auth.SignInPage;
import rakesh.app.bustimingapp.Home.MainActivity;
import rakesh.app.bustimingapp.Models.BusModel;
import rakesh.app.bustimingapp.R;

public class AllBuseDetails extends AppCompatActivity {

    RecyclerView rvBusDetails;
    FirebaseFirestore firestore;

    ArrayList<BusModel> allBusDetailsData; // Bus Model because we get the all data with the form of Bus Model and we get as Array List.

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    // Edit and Delete Btns will show alert dialog box
    ImageView editBtn,deleteBtn;
    AlertDialog.Builder editBtnBuilder,deleteBtnBuilder;

    // Progress bar
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bus_details);

        firestore = FirebaseFirestore.getInstance();
        rvBusDetails = findViewById(R.id.rvAsBusDetails);

        GetBusDetailsData(); //getting all bus details from firebase

        // this is for the Side Navigation Bar
        drawerLayout = findViewById(R.id.drawableLayoutAllBusDetails);
        navigationView = findViewById(R.id.sideBarAllBusDetails);
        toolbar = findViewById(R.id.toolBarAllBusDetails);

        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenSidebar,R.string.CloseSidebar);

        DrawerArrowDrawable tog = toggle.getDrawerArrowDrawable();
        tog.setColor(Color.WHITE);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //progress dialog assigned
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching the data...");
        progressDialog.show();


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
    }

    public  void GetBusDetailsData(){
        allBusDetailsData = new ArrayList<>();
        allBusDetailsData.clear();
        firestore.collection("Buses").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Bus Number")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore error",error.getMessage());
                            return;

                        }else{

                            if(!value.isEmpty()){
                                // We are finding out the value as object in the form of bus model class.

                                for(DocumentChange documentChange : value.getDocumentChanges()){

                                    if(documentChange.getType() == DocumentChange.Type.ADDED){
                                        allBusDetailsData.clear();
                                        List<BusModel> data = value.toObjects(BusModel.class);
                                        allBusDetailsData.addAll(data);

                                        // Set data ta recycle view
                                        rvBusDetails.setLayoutManager(new LinearLayoutManager(AllBuseDetails.this));
                                        rvBusDetails.setAdapter(new BusDetailsDataAdapter(AllBuseDetails.this,allBusDetailsData));

                                    }else if(documentChange.getType() == DocumentChange.Type.MODIFIED) {
                                        allBusDetailsData.clear();
                                        List<BusModel> data = value.toObjects(BusModel.class);
                                        allBusDetailsData.addAll(data);

                                        // Set data ta recycle view
                                        rvBusDetails.setLayoutManager(new LinearLayoutManager(AllBuseDetails.this));
                                        rvBusDetails.setAdapter(new BusDetailsDataAdapter(AllBuseDetails.this,allBusDetailsData));

                                    }else if(documentChange.getType() == DocumentChange.Type.REMOVED){
                                        allBusDetailsData.clear();
                                        List<BusModel> data = value.toObjects(BusModel.class);
                                        allBusDetailsData.addAll(data);

                                        // Set data ta recycle view
                                        rvBusDetails.setLayoutManager(new LinearLayoutManager(AllBuseDetails.this));
                                        rvBusDetails.setAdapter(new BusDetailsDataAdapter(AllBuseDetails.this,allBusDetailsData));

                                    }
                                    if(progressDialog.isShowing()){
                                        progressDialog.dismiss();
                                    }

                                }

                            }else {
                                allBusDetailsData.clear();
                                List<BusModel> data = value.toObjects(BusModel.class);
                                allBusDetailsData.addAll(data);

                                // Set data ta recycle view
                                rvBusDetails.setLayoutManager(new LinearLayoutManager(AllBuseDetails.this));
                                rvBusDetails.setAdapter(new BusDetailsDataAdapter(AllBuseDetails.this,allBusDetailsData));

                                Toast.makeText(getApplicationContext(),"First Register the Bus Details.",Toast.LENGTH_SHORT).show();
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
//
                        }
                    }
                });
    }

}