package rakesh.app.bustimingapp.BusRegistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import rakesh.app.bustimingapp.R;

public class BusRegistrationPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner busType;
    List<String> busTypeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_registration_page);

        busType = findViewById(R.id.sBusType);


        // Spinner Drop down elements
        busTypeList= new ArrayList<String>();
        busTypeList.add("Select Bus Type");
        busTypeList.add("City Bus");
        busTypeList.add("Private Bus");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, busTypeList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        busType.setAdapter(dataAdapter);
        busType.setOnItemSelectedListener(this);



    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(busTypeList.get(i) != busTypeList.get(0)){

            Toast.makeText(getApplicationContext(), ""+ busTypeList.get(i),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}