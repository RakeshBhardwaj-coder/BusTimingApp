package rakesh.app.bustimingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import rakesh.app.bustimingapp.Models.BusModel;
import rakesh.app.bustimingapp.BusRegistration.AddStops;
import rakesh.app.bustimingapp.R;

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
    }

    @Override
    public int getItemCount() {
        return allBusDetailsData.size();
    }

    class  BusDetailsDataHolder extends RecyclerView.ViewHolder{
        TextView busNumber,busName,busType,busSource,busDestination,busSourceTime,busDestinationTime;
        public BusDetailsDataHolder(@NonNull View itemView) {
            super(itemView);
            busNumber = itemView.findViewById(R.id.tvBddsBusNumber);
            busName = itemView.findViewById(R.id.tvBddsBusName);
            busType= itemView.findViewById(R.id.tvBddsBusType);
            busSource = itemView.findViewById(R.id.tvBddsSource);
            busDestination = itemView.findViewById(R.id.tvBddsDestination);
            busSourceTime = itemView.findViewById(R.id.tvBddsSourceTime);
            busDestinationTime = itemView.findViewById(R.id.tvBddsDestinationTime);
        }
    }
}
