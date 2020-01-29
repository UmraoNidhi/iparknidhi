package com.wemsuser.app.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.wemsuser.app.Activity.AddVehicleActivity;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.FuelTypeDatum;
import com.wemsuser.app.Response.VehicleTypeDatum;

import java.util.ArrayList;
import java.util.HashMap;

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String>List=new ArrayList<>();
    private ArrayList<VehicleTypeDatum>Vehicle_Brand=new ArrayList<>();
    private ArrayList<FuelTypeDatum>Fuel=new ArrayList<>();
    private HashMap<String,String>SelectedCar=new HashMap<>();
    private SelectedData selectedData;
    ArrayList<String>CarType_List=new ArrayList<>();
    ArrayList<String>Car_fuel=new ArrayList<>();

    public AddAdapter(AddVehicleActivity addVehicleActivity, ArrayList<String> mList,
                      ArrayList<FuelTypeDatum> fuelList, ArrayList<VehicleTypeDatum> vehicleList,
                      AddVehicleActivity addVehicleActivity1) {

        this.Vehicle_Brand=vehicleList;
        this.Fuel=fuelList;
        this.List=mList;
        this.context=addVehicleActivity;

    }


    @Override
    public AddAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addvehicle,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int i) {

    }


    @Override
    public int getItemCount() {

        return List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        Spinner Type,Brand,Model,Year,Fuel;

        public MyViewHolder( View itemView) {
            super(itemView);


            Type= itemView.findViewById(R.id.Spinner);




        }
    }
    public interface SelectedData{
        void addData(HashMap<String,String>selectedDetail);

    }
}
