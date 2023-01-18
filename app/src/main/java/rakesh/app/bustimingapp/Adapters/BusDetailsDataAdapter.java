package rakesh.app.bustimingapp.Adapters;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import rakesh.app.bustimingapp.AddStops.AddStopsPage;
import rakesh.app.bustimingapp.Models.BusModel;
import rakesh.app.bustimingapp.BusRegistration.AllBuseDetails;
import rakesh.app.bustimingapp.R;
import rakesh.app.bustimingapp.BusRegistration.EditBusDetails;

public class BusDetailsDataAdapter extends RecyclerView.Adapter<BusDetailsDataAdapter.BusDetailsDataHolder> {

    AllBuseDetails allBuseDetails;
    ArrayList<BusModel> allBusDetailsData;

    public BusDetailsDataAdapter(){

    }

    public BusDetailsDataAdapter(AllBuseDetails allBuseDetails, ArrayList<BusModel> allBusDetailsData) {
        this.allBusDetailsData = allBusDetailsData;
        this.allBuseDetails = allBuseDetails;
    }

    @NonNull
    @Override
    public BusDetailsDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new BusDetailsDataHolder(LayoutInflater.from(allBuseDetails).inflate(R.layout.bus_details_data_show,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BusDetailsDataHolder holder, int position) {

        holder.busNumber.setText(allBusDetailsData.get(position).getBusNumber());
        holder.busName.setText(allBusDetailsData.get(position).getBusName());
        holder.busType.setText(allBusDetailsData.get(position).getBusType());
        holder.busSource.setText(allBusDetailsData.get(position).getBusSource());
        holder.busSourceTime.setText(allBusDetailsData.get(position).getBusSourceTime());
        holder.busDestination.setText(allBusDetailsData.get(position).getBusDestination());
        holder.busDestinationTime.setText(allBusDetailsData.get(position).getBusDestinationTime());

        // when Edit and delete button clicked
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(allBuseDetails.getApplicationContext(), "Edit",Toast.LENGTH_SHORT).show();

                holder.editBtnBuilder.setTitle("Alert")
                        .setMessage("Do you want to Edit.")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // passing the BusNumber key for edit the details to edit bus details.

                                allBuseDetails.startActivity(new Intent(allBuseDetails, EditBusDetails.class).putExtra("BusNumberKey",allBusDetailsData.get(holder.getAdapterPosition()).getBusNumber()));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

//                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.deleteBtnBuilder.setTitle("Alert")
                        .setMessage("Do you want to Delete.")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

final String busNumberStr = allBusDetailsData.get(holder.getAdapterPosition()).getBusNumber();



                                // Delete All Stops as well as delete the bus

                                FirebaseFirestore.getInstance().collection("Stops").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(busNumberStr).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                                    FirebaseFirestore.getInstance().collection("Stops").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(busNumberStr).document(queryDocumentSnapshot.getId())
                                                            .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        Toast.makeText(allBuseDetails.getApplicationContext(), "All done!",Toast.LENGTH_SHORT).show();
                                                                        //  Delete the data form the firebase of selected bus number `allBusDetailsData.get(holder.getAdapterPosition()).getBusNumber()`
                                                                        FirebaseFirestore.getInstance().collection("Buses").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Bus Number").document(busNumberStr)
                                                                                .delete()
                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if(task.isSuccessful()){
                                                                                            Toast.makeText(allBuseDetails.getApplicationContext(), "Deleted",Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    }
                                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                                    @Override
                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                        Log.d(TAG,"Error in Deletion : " + e.toString());
                                                                                    }
                                                                                });

                                                                    }
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.d(TAG,"Error "+e.toString());
                                                                }
                                                            });

                                                }

                                            }
                                        });




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

        // when click on every Add stops button then open the add stops page
        holder.addStopsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                allBuseDetails.startActivity(new Intent(allBuseDetails, AddStopsPage.class).putExtra("BusNumberKey",allBusDetailsData.get(holder.getAdapterPosition()).getBusNumber()).putExtra("BusNameKey",allBusDetailsData.get(holder.getAdapterPosition()).getBusName()).putExtra("BusTypeKey",allBusDetailsData.get(holder.getAdapterPosition()).getBusType()).putExtra("BusFinalDestinationKey",allBusDetailsData.get(holder.getAdapterPosition()).getBusDestination()));
            }
        });
    }

    private void DeleteAllTheStops(String busNumber) {

    }

    @Override
    public int getItemCount() {
        return allBusDetailsData.size();
    }

    class  BusDetailsDataHolder extends RecyclerView.ViewHolder{

        TextView busNumber,busName,busType,busSource,busDestination,busSourceTime,busDestinationTime;

        // Edit and Delete Button we access here
        ImageView editBtn,deleteBtn;
        AlertDialog.Builder editBtnBuilder,deleteBtnBuilder;

        Button addStopsBtn;

        View customLayout;
        Dialog editStopBuilder1; // editable form will show to edit data by this builder


        public BusDetailsDataHolder(@NonNull View itemView) {
            super(itemView);
            busNumber = itemView.findViewById(R.id.tvBddsBusNumber);
            busName = itemView.findViewById(R.id.tvBddsBusName);
            busType= itemView.findViewById(R.id.tvBddsBusType);
            busSource = itemView.findViewById(R.id.tvBddsSource);
            busDestination = itemView.findViewById(R.id.tvBddsDestination);
            busSourceTime = itemView.findViewById(R.id.tvBddsSourceTime);
            busDestinationTime = itemView.findViewById(R.id.tvBddsDestinationTime);

            editBtn = itemView.findViewById(R.id.ivBddsEdit);
            deleteBtn = itemView.findViewById(R.id.ivBddsDelete);
            editBtnBuilder = new AlertDialog.Builder(allBuseDetails); // 'addStops' is working as this key
            deleteBtnBuilder = new AlertDialog.Builder(allBuseDetails); // 'addStops' is working as this key

            addStopsBtn = itemView.findViewById(R.id.btnBddsAddStops);

        }
    }


}
