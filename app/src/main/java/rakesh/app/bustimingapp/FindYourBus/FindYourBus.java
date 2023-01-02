package rakesh.app.bustimingapp.FindYourBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import rakesh.app.bustimingapp.Adapters.BusDetailsDataAdapter;
import rakesh.app.bustimingapp.Adapters.BusFindDataAdapter;
import rakesh.app.bustimingapp.BusRegistration.AllBuseDetails;
import rakesh.app.bustimingapp.Models.BusFindModel;
import rakesh.app.bustimingapp.Models.BusModel;
import rakesh.app.bustimingapp.R;

public class FindYourBus extends AppCompatActivity {

    String findStoppageStr,findDestinationStr;
    TextView getStop,getDestination;
    FirebaseFirestore firestore;

    RecyclerView rvFindBusDetails;

    ArrayList<BusFindModel> allBusFindsDetailsData;
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




    }

    public  void FindedBusDetails(){
        allBusFindsDetailsData = new ArrayList<>();
        allBusFindsDetailsData.clear();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//
//        Query query = firestore.collectionGroup("0000")
//                .whereEqualTo("busStopName", "Raipur");
//        query.get()

        firestore.collection("Buses").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Bus Number")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot documentSnapshot:list){
                            List<BusFindModel> data = (List<BusFindModel>) documentSnapshot.toObject(BusFindModel.class);
                            allBusFindsDetailsData.addAll(data);
                        }
                    }
                });



    }

}

//
//firestore.collection().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//
//
//@Override
//public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//        for (QueryDocumentSnapshot value : queryDocumentSnapshots) {
//        // doc.getData() is never null for query doc snapshots
//        Toast.makeText(getApplicationContext(),value.getId()+" Home " + value.getData(), Toast.LENGTH_SHORT).show();
//
//        System.out.println(value.getId() + " => " + value.getData());
//        // Set data ta recycle view
//        rvFindBusDetails.setLayoutManager(new LinearLayoutManager(FindYourBus.this));
//        rvFindBusDetails.setAdapter(new BusFindDataAdapter(FindYourBus.this,allBusFindsDetailsData));
//        }
//        }
//        })
//        .addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//        System.out.println("Error getting documents: " + e);
//
//        }
//        });