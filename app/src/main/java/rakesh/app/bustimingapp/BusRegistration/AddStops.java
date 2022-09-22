package rakesh.app.bustimingapp.BusRegistration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import rakesh.app.bustimingapp.Adapters.BusDetailsDataAdapter;
import rakesh.app.bustimingapp.Models.BusModel;
import rakesh.app.bustimingapp.R;

public class AddStops extends AppCompatActivity {

    RecyclerView rvBusDetails;
    FirebaseFirestore firestore;

    ArrayList<BusModel> allBusDetailsData; // Bus Model because we get the all data with the form of Bus Model and we get as Array List.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stops);

        firestore = FirebaseFirestore.getInstance();
        rvBusDetails = findViewById(R.id.rvAsBusDetails);

        GetBusDetailsData();

    }

    public  void GetBusDetailsData(){
        allBusDetailsData = new ArrayList<>();
        allBusDetailsData.clear();
        firestore.collection("Buses").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Bus Number")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error == null){

                            // We are finding out the value as object in the form of bus model class.
                            List<BusModel> data = value.toObjects(BusModel.class);
                            allBusDetailsData.addAll(data);

                            // Set data ta recycle view
                            rvBusDetails.setLayoutManager(new LinearLayoutManager(AddStops.this));
                            rvBusDetails.setAdapter(new BusDetailsDataAdapter(AddStops.this,allBusDetailsData));
                        }
                    }
                });
    }
}