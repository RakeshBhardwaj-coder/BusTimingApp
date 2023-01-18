package rakesh.app.bustimingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import rakesh.app.bustimingapp.FindYourBus.FindYourBus;
import rakesh.app.bustimingapp.Models.BusFindModel;
import rakesh.app.bustimingapp.R;

public class BusFindDataAdapter extends RecyclerView.Adapter<BusFindDataAdapter.BusFindDataHolder> {

    FindYourBus allFindBusDetails;
    ArrayList<BusFindModel> allFindBusDetailsData;

    public BusFindDataAdapter(){

    }

    public BusFindDataAdapter(FindYourBus allFindBusDetails, ArrayList<BusFindModel> allFindBusDetailsData) {
        this.allFindBusDetails = allFindBusDetails;
        this.allFindBusDetailsData = allFindBusDetailsData;
    }

    @NonNull
    @Override
    public BusFindDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BusFindDataHolder(LayoutInflater.from(allFindBusDetails).inflate(R.layout.find_bus_data_show,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull BusFindDataHolder holder, int position) {
        holder.busName.setText(allFindBusDetailsData.get(position).getBusName());
        holder.busType.setText(allFindBusDetailsData.get(position).getBusType());
        holder.busSource.setText(allFindBusDetailsData.get(position).getBusStopName());
        holder.busReachTime.setText(allFindBusDetailsData.get(position).getBusReachTime());
        holder.busExitTime.setText(allFindBusDetailsData.get(position).getBusExitTime());
        holder.busWaitingTime.setText(allFindBusDetailsData.get(position).getBusWaitingTime());
        holder.busDestination.setText(allFindBusDetailsData.get(position).getBusFinalDestination());

    }

    @Override
    public int getItemCount() {
        return allFindBusDetailsData.size();
    }

     class BusFindDataHolder extends RecyclerView.ViewHolder {

         TextView busName,busType,busSource,busDestination,busReachTime,busExitTime,busWaitingTime;

        public BusFindDataHolder(@NonNull View itemView) {
            super(itemView);

            busName = itemView.findViewById(R.id.tvBfdaBusName);
            busType= itemView.findViewById(R.id.tvBfdaBusType);
            busSource = itemView.findViewById(R.id.tvBfdaSource);
            busDestination = itemView.findViewById(R.id.tvBfdaDestination);
            busReachTime = itemView.findViewById(R.id.tvBfdaBusReachTime);
            busExitTime = itemView.findViewById(R.id.tvBfdaBusExitTime);
            busWaitingTime = itemView.findViewById(R.id.tvBfdaBusWaitingTime);

        }
    }
}
