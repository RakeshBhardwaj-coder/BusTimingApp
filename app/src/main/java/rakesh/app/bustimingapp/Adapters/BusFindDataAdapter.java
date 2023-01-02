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
        return new BusFindDataHolder(LayoutInflater.from(allFindBusDetails).inflate(R.layout.bus_details_data_show,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull BusFindDataHolder holder, int position) {
//        holder.busName.setText(allFindBusDetailsData.get(position).getBusName());

    }

    @Override
    public int getItemCount() {
        return allFindBusDetailsData.size();
    }

     class BusFindDataHolder extends RecyclerView.ViewHolder {

         TextView busNumber,busName,busType,busSource,busDestination,busSourceTime,busDestinationTime;

        public BusFindDataHolder(@NonNull View itemView) {
            super(itemView);

//            busNumber = itemView.findViewById(R.id.tvFbdsBusName);
            busName = itemView.findViewById(R.id.tvFbdsBusName);
//            busType= itemView.findViewById(R.id.tvBddsBusType);
//            busSource = itemView.findViewById(R.id.tvBddsSource);
//            busDestination = itemView.findViewById(R.id.tvBddsDestination);
//            busSourceTime = itemView.findViewById(R.id.tvBddsSourceTime);
//            busDestinationTime = itemView.findViewById(R.id.tvBddsDestinationTime);


        }
    }
}
