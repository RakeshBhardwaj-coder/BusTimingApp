package rakesh.app.bustimingapp.Adapters;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.util.ArrayList;

import rakesh.app.bustimingapp.Models.BusModel;
import rakesh.app.bustimingapp.BusRegistration.AddStops;
import rakesh.app.bustimingapp.R;
import rakesh.app.bustimingapp.Stops.EditBusDetails;

public class BusDetailsDataAdapter extends RecyclerView.Adapter<BusDetailsDataAdapter.BusDetailsDataHolder> {

    AddStops addStops;
    ArrayList<BusModel> allBusDetailsData;

    public BusDetailsDataAdapter(){

    }

    public BusDetailsDataAdapter(AddStops addStops, ArrayList<BusModel> allBusDetailsData) {
        this.allBusDetailsData = allBusDetailsData;
        this.addStops = addStops;
    }

    @NonNull
    @Override
    public BusDetailsDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BusDetailsDataHolder(LayoutInflater.from(addStops).inflate(R.layout.bus_details_data_show,parent,false));
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
                Toast.makeText(addStops.getApplicationContext(), "Edit",Toast.LENGTH_SHORT).show();
                holder.editBtnBuilder.setTitle("Alert")
                        .setMessage("Do you want to Edit.")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // passing the BusNumber key for edit the details to edit bus details.
                                addStops.startActivity(new Intent(addStops, EditBusDetails.class).putExtra("BusNumberKey",allBusDetailsData.get(holder.getAdapterPosition()).getBusNumber()));
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

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.deleteBtnBuilder.setTitle("Alert")
                        .setMessage("Do you want to Delete.")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Delete the data form the firebase of selected bus number `allBusDetailsData.get(holder.getAdapterPosition()).getBusNumber()`
                                FirebaseFirestore.getInstance().collection("Buses").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Bus Number").document(allBusDetailsData.get(holder.getAdapterPosition()).getBusNumber()).delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(addStops.getApplicationContext(), "Deleted",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG,"Error in Deletion : " + e.toString());
                                            }
                                        });


                                Toast.makeText(addStops.getApplicationContext(), "Delete",Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return allBusDetailsData.size();
    }

    class  BusDetailsDataHolder extends RecyclerView.ViewHolder{
        TextView busNumber,busName,busType,busSource,busDestination,busSourceTime,busDestinationTime;

        // Edit and Delete Button we access here
        ImageView editBtn,deleteBtn;
        AlertDialog.Builder editBtnBuilder,deleteBtnBuilder;

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
            editBtnBuilder = new AlertDialog.Builder(addStops); // 'addStops' is working as this key
            deleteBtnBuilder = new AlertDialog.Builder(addStops); // 'addStops' is working as this key
        }
    }


}
