package rakesh.app.bustimingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import rakesh.app.bustimingapp.AddStops.AddStopsPage;
import rakesh.app.bustimingapp.Models.BusStopsModel;
import rakesh.app.bustimingapp.R;

public class StopsDetailsDataAdapter extends RecyclerView.Adapter<StopsDetailsDataAdapter.StopsDetailsDataHolder> {

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
    }

    @Override
    public int getItemCount() {
        return busStopsModelData.size();
    }

        class StopsDetailsDataHolder extends RecyclerView.ViewHolder{
        TextView busStopIndex;
        TextView busStopName,busReachTime,busExitTime,busWaitingTime;

            public StopsDetailsDataHolder(@NonNull View itemView) {
                super(itemView);
                busStopIndex = itemView.findViewById(R.id.asdsBusStopIndex);
                busStopName  = itemView.findViewById(R.id.asdsBusStopName);
                busReachTime = itemView.findViewById(R.id.asdsBusReachTime);
                busExitTime = itemView.findViewById(R.id.asdsBusExitTime);
                busWaitingTime  = itemView.findViewById(R.id.asdsBusWaitingTime);

            }
        }
}
