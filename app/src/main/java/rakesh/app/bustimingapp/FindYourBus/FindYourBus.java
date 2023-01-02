package rakesh.app.bustimingapp.FindYourBus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import rakesh.app.bustimingapp.Adapters.BusFindDataAdapter;
import rakesh.app.bustimingapp.Models.BusFindModel;
import rakesh.app.bustimingapp.R;

public class FindYourBus extends AppCompatActivity {

    String findStoppageStr,findDestinationStr;
    TextView getStop,getDestination;
    TextView justShowData;
    FirebaseFirestore firestore;

    RecyclerView rvFindBusDetails;

    ArrayList<BusFindModel> allBusFindsDetailsData;


    // Progress bar
    ProgressBar progressBar;

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

//
        // Define the query

//
// Execute the query
//        query.get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot querySnapshot) {
//                        // Iterate over the documents in the query snapshot
//                        querySnapshot.forEach(new Consumer<DocumentSnapshot>() {
//                            @Override
//                            public void accept(DocumentSnapshot doc) {
//                                // doc.getData() is never null for query doc snapshots
//                                System.out.println(doc.getId() + " => " + doc.getData());
//                            }
//                        });
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        System.out.println("Error getting documents: " + e);
//                    }
//                });

//        List<DocumentSnapshot> documents = querySnapshot.getDocuments();
//        for (DocumentSnapshot doc : documents) {
//            System.out.println(doc.getId() + " => " + doc.getData());
//        }

        FindedBusDetails();



    }
    public void FindedBusDetails(){

        String searchingStoppageStr = getIntent().getStringExtra("BusStoppage");
        String searchingDestinationStr = getIntent().getStringExtra("BusDestination");

        progressBar.setVisibility(View.VISIBLE);
        allBusFindsDetailsData = new ArrayList<>();
        allBusFindsDetailsData.clear();

        justShowData.setText(getIntent().getStringExtra("BusStoppage"));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Define the query
        Query query = db.collectionGroup("1236").whereEqualTo("busStopName",searchingStoppageStr);



// Execute the query
        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {


                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {

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
//





                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);

                        System.out.println("Error getting documents: " + e);
                    }
                });



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
//





                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);

                        System.out.println("Error getting documents: " + e);
                    }
                });



    }

}
