package com.wemsuser.app.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wemsuser.app.Activity.DetailsActivity;
import com.wemsuser.app.Fragment.CarListFragment;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.UserVehicleTypeList;
import com.wemsuser.app.utility.PreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.MyViewHolder> {
    ArrayList<UserVehicleTypeList>CarList;
    Context context;
    private SelectedCarDetails selectedCarDetails;
    public HashMap<String,String>cardetails=new HashMap<>();
     private Dialog dialog;
     TextView Yes,No;
     ArrayList<LinearLayout>linear_list;
     boolean profile;




    public CarListAdapter(FragmentActivity activity, ArrayList<UserVehicleTypeList> mUserCarList, boolean flag, CarListFragment fragmentActivity) {
        this.context=activity;
        this.CarList=mUserCarList;
        this.selectedCarDetails=fragmentActivity;
        linear_list=new ArrayList<LinearLayout>();
        this.profile=flag;
        Log.e("Profile_flag",""+profile);
    }

    @Override
    public CarListAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.carlistxml,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( final CarListAdapter.MyViewHolder myViewHolder, int i) {

        myViewHolder.Car.setText(CarList.get(i).getVehicleTypeName());
        myViewHolder.Fuel.setText(CarList.get(i).getFuelTypeName());
        myViewHolder.Year.setText(CarList.get(i).getRegistrationYear());
        String Vehicle_Id=CarList.get(i).getUserVehicleTypeId();
        PreferenceUtil.setUser_Vehical_Type_Id(context,Vehicle_Id);
        if (profile==true){
            myViewHolder.linearLayout.setClickable(true);
            myViewHolder.linearLayout.setId(i);
            linear_list.add(  myViewHolder.linearLayout);

            myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i=0;i<linear_list.size();i++){
                        if (v.getId()==i){
                            Log.e("selected",""+v.getId()+"\n"+i);
                            linear_list.get(i).setBackgroundColor(Color.parseColor("#FFC107"));
                            String selected_car=CarList.get(i).getVehicleTypeName();
                            String Registration_year=CarList.get(i).getRegistrationYear();
                            String Selected_fuel=CarList.get(i).getFuelTypeName();
                            String Selected_User_carId=CarList.get(i).getVehicleTypeId();
                            String Selected_fuelID=CarList.get(i).getFuelTypeId();
                            Intent intent=new Intent(context, DetailsActivity.class);
                            intent.putExtra("SelectedCar",Selected_User_carId);
                            intent.putExtra("RegistrationYear",Registration_year);
                            intent.putExtra("FuelType_id",Selected_fuelID);
                            context.startActivity(intent);
                            Log.e("selectedCarDetails",""+selected_car+"\n"+Registration_year+"\n"+Selected_fuelID+"\n"+Selected_User_carId);

                        }else {
                            linear_list.get(i).setBackgroundColor(Color.parseColor("#FFFFFF"));

                        }
                    }
//                String selected_car=CarList.get(i).getVehicleTypeName();

                }
            });
        }else {
            myViewHolder.linearLayout.setClickable(false);
            myViewHolder.linearLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return CarList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Car,Fuel,Year,Model;
        ImageView imageView,Car_delete;
        CardView cardView;
        LinearLayout linearLayout;
        public MyViewHolder( View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.Linear_car);

            cardView=itemView.findViewById(R.id.Card_carList);
            Car=itemView.findViewById(R.id.Text_car);
            Fuel=itemView.findViewById(R.id.Text_fuel);
            Year=itemView.findViewById(R.id.Text_Year);
            imageView=itemView.findViewById(R.id.Image_car);
            Car_delete=itemView.findViewById(R.id.Delete_Car);
            Car_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog=new Dialog(context);
                    dialog.setContentView(R.layout.deletealert);
                    Yes=dialog.findViewById(R.id.Delete_yes);
                    No=dialog.findViewById(R.id.Delete_no);
                    dialog.show();
                    Yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            String selectedCar=CarList.get(getLayoutPosition()).getUserVehicleTypeId();
                            cardetails.put("CarId",selectedCar);
                            selectedCarDetails.deleteCar(cardetails);
                            Log.e("DeleteCar",""+selectedCar);
                        }
                    });
                    No.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                }

            });



        }
    }
       public interface SelectedCarDetails{
        void deleteCar(HashMap<String,String>CarDetails);

    }
}
