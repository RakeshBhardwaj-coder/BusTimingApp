package rakesh.app.bustimingapp.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import rakesh.app.bustimingapp.AddStops.AddStopsPage;
import rakesh.app.bustimingapp.AddStops.EditStopDetails;
import rakesh.app.bustimingapp.Models.BusStopsModel;
import rakesh.app.bustimingapp.R;

public class StopsDetailsDataAdapter extends RecyclerView.Adapter<StopsDetailsDataAdapter.StopsDetailsDataHolder> {

    public static int stopCounts;
    AddStopsPage addStopsPage;
    ArrayList<BusStopsModel> busStopsModelData;

    public StopsDetailsDataAdapter(AddStopsPage addStopsPage, ArrayList<BusStopsModel> busStopsModelData) {
        this.addStopsPage = addStopsPage;
        this.busStopsModelData = busStopsModelData;
    }

    @NonNull
    @Override
    public StopsDetailsDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StopsDetailsDataHolder(LayoutInflater.from(addStopsPage).inflate(R.layout.add_stops_data_show,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StopsDetailsDataHolder holder, int position) {
        holder.busStopIndex.setText(busStopsModelData.get(position).getStopsIndex());
        holder.busStopName.setText(busStopsModelData.get(position).getBusStopName());
        holder.busReachTime.setText(busStopsModelData.get(position).getBusReachTime());
        holder.busExitTime.setText(busStopsModelData.get(position).getBusExitTime());
        holder.busWaitingTime.setText(busStopsModelData.get(position).getBusWaitingTime());

        // Edit and Delete Button when clicked
        holder.editStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.editStopBtnBuilder.setTitle("Alert")
                        .setMessage("Do you want to edit stop.")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                addStopsPage.startActivity(new Intent(addStopsPage, EditStopDetails.class).putExtra("busStopKey",holder.busStopIndex.getText()).putExtra("busNumberKey",AddStopsPage.busNumberKey));
                                Toast.makeText(addStopsPage.getApplicationContext(), ""+holder.busStopIndex.getText(),Toast.LENGTH_SHORT).show();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });
        holder.deleteStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.deleteStopBtnBuilder.setTitle("Alert")
                        .setMessage("Do you want to delete stop.")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseFirestore.getInstance().collection("Stops").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(AddStopsPage.busNumberKey).document(holder.busStopIndex.getText().toString())
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(addStopsPage, "Stop "+holder.busStopIndex.getText()+" Deleted",Toast.LENGTH_SHORT).show();

                                                    stopCounts = stopCounts - 1 ;
                                                    AddStopsPage.busStopIndex = stopCounts;

                                                }
                                            }
                                        });

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

    @Override
    public int getItemCount() {
        stopCounts = busStopsModelData.size();
        return busStopsModelData.size();
    }

        class StopsDetailsDataHolder extends RecyclerView.ViewHolder{
        TextView busStopIndex;
        TextView busStopName,busReachTime,busExitTime,busWaitingTime;

        ImageView editStopBtn, deleteStopBtn;
        AlertDialog.Builder editStopBtnBuilder,deleteStopBtnBuilder;

        // for edit the stop data

        ImageView editBusReachTime,editBusExitTime;
        EditText editBusStopName,editBusWaitingTime;
            public StopsDetailsDataHolder(@NonNull View itemView) {
                super(itemView);
                busStopIndex = itemView.findViewById(R.id.asdsBusStopIndex);
                busStopName  = itemView.findViewById(R.id.asdsBusStopName);
                busReachTime = itemView.findViewById(R.id.asdsBusReachTime);
                busExitTime = itemView.findViewById(R.id.asdsBusExitTime);
                busWaitingTime  = itemView.findViewById(R.id.asdsBusWaitingTime);

                editStopBtn = itemView.findViewById(R.id.asdsEditStop);
                deleteStopBtn = itemView.findViewById(R.id.asdsDeleteStop);

                editStopBtnBuilder = new AlertDialog.Builder(addStopsPage); // 'addStops' is working as this key
                deleteStopBtnBuilder = new AlertDialog.Builder(addStopsPage); // 'addStops' is working as this key



            }
        }
}
