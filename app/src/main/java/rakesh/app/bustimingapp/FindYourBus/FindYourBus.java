package rakesh.app.bustimingapp.FindYourBus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rakesh.app.bustimingapp.Adapters.BusFindDataAdapter;
import rakesh.app.bustimingapp.Auth.SignInPage;
import rakesh.app.bustimingapp.BusRegistration.AllBuseDetails;
import rakesh.app.bustimingapp.Home.MainActivity;
import rakesh.app.bustimingapp.Models.BusFindModel;
import rakesh.app.bustimingapp.R;

public class FindYourBus extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    String findStoppageStr,findDestinationStr;
    TextView getStop,getDestination;
    TextView justShowData;
    FirebaseFirestore firestore;

    RecyclerView rvFindBusDetails;

    ArrayList<BusFindModel> allBusFindsDetailsData;

    // Progress bar
    ProgressBar progressBar;
    LinearLayout llSearchingTxt;

    private static ArrayList<String> busNumberList = new ArrayList<>();;

    public static void addBusNumber(String number) {
        busNumberList.add(number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_your_bus);

        rvFindBusDetails = findViewById(R.id.rvFindYourBus);


        findDestinationStr = getIntent().getStringExtra("BusDestination");
        findStoppageStr = getIntent().getStringExtra("BusStoppage");

        getStop = findViewById(R.id.tvGetStoppage);
        getDestination = findViewById(R.id.tvGetDestination);

        getStop.setText(findStoppageStr);
        getDestination.setText(findDestinationStr);

        firestore = FirebaseFirestore.getInstance();


        //progress dialog assigned
        progressBar = findViewById(R.id.progress_bar);
        llSearchingTxt = findViewById(R.id.searchingText);


        drawerLayout = findViewById(R.id.afybDrawableLayout);
        navigationView = findViewById(R.id.afybSideBar);
        toolbar = findViewById(R.id.afybToolBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenSidebar,R.string.CloseSidebar);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();


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


        FindedBusDetails();



    }
    public void FindedBusDetails(){

        String searchingStoppageStr = getIntent().getStringExtra("BusStoppage");
        String searchingDestinationStr = getIntent().getStringExtra("BusDestination");
        Query query = null;


        progressBar.setVisibility(View.VISIBLE);
        llSearchingTxt.setVisibility(View.VISIBLE);

        allBusFindsDetailsData = new ArrayList<>();
        allBusFindsDetailsData.clear();

busNumberList.add("1234");
busNumberList.add("2589");
busNumberList.add("1235");
busNumberList.add("5555");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Define the queryrr


//        Query query = db.collection("Stops");
        if(busNumberList!=null){
        for (int i = 0; i < busNumberList.size(); i++) {

            String busNumber = busNumberList.get(i);
            query = firestore.collectionGroup(busNumber).whereEqualTo("busStopName",findStoppageStr);


// Execute the query
            query.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {


                        @Override
                        public void onSuccess(QuerySnapshot querySnapshot) {

                            llSearchingTxt.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);

                            List<DocumentSnapshot> documents = querySnapshot.getDocuments();

                            // Convert the document data to an instance of the object model

                            for (DocumentSnapshot doc : documents) {

                                BusFindModel busFindModel = doc.toObject(BusFindModel.class);
                                allBusFindsDetailsData.add(busFindModel);

//                            // doc.getData() is never null for query doc snapshots
//                            allBusFindsDetailsData.add((BusFindModel) doc.getData());

//                            justShowData.setText(allBusFindsDetailsData);
                                // Set data to recycle view

                                rvFindBusDetails.setLayoutManager(new LinearLayoutManager(FindYourBus.this));
                                rvFindBusDetails.setAdapter(new BusFindDataAdapter(FindYourBus.this, allBusFindsDetailsData));
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            llSearchingTxt.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);

                            System.out.println("Error getting documents: " + e);
                        }
                    });

        }
        }else {
            Toast.makeText(getApplicationContext(),"Bus Number List is empty",Toast.LENGTH_SHORT);
        }

    }


    public  void FindedBusDetailss(){
        progressBar.setVisibility(View.VISIBLE);
        allBusFindsDetailsData = new ArrayList<>();
        allBusFindsDetailsData.clear();

        justShowData.setText("doc.getData().toString()");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Define the query
        Query query = db.collectionGroup("1236");



// Execute the query
        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {


                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {

                        llSearchingTxt.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);

                        List<DocumentSnapshot> documents = querySnapshot.getDocuments();

                        // Convert the document data to an instance of the object model

                        for (DocumentSnapshot doc : documents) {

                            BusFindModel busFindModel = doc.toObject(BusFindModel.class);
                            allBusFindsDetailsData.add(busFindModel);

//                            // doc.getData() is never null for query doc snapshots
//                            allBusFindsDetailsData.add((BusFindModel) doc.getData());

//                            justShowData.setText(allBusFindsDetailsData);
                            // Set data to recycle view

                            rvFindBusDetails.setLayoutManager(new LinearLayoutManager(FindYourBus.this));
                            rvFindBusDetails.setAdapter(new BusFindDataAdapter(FindYourBus.this,allBusFindsDetailsData));
                   }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        llSearchingTxt.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);

                        System.out.println("Error getting documents: " + e);
                    }
                });



    }

}
