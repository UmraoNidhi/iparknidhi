package com.wemsuser.app.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wemsuser.app.Adapter.AddAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.FuelTypeDatum;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Response.UserVehicleTypeDatum;
import com.wemsuser.app.Response.VehicleTypeDatum;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddVehicleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button button,Confirm;
    private ImageView imageView,image_animation;
    private Dialog dialog;
    private TextView Vtype,Brand,Model,Fueltype,Register;
    private EditText Regi_year;
    String SelectedYear,User_Id,Car_Id,Fuel_Id;
    private HashMap<String,String>Details=new HashMap<>();
    ArrayList<VehicleTypeDatum>VehicleList;
    ArrayList<FuelTypeDatum>fuelList;
    ArrayList<String>Select_car=new ArrayList<>();
    ArrayList<String>Select_fuel=new ArrayList<>();
    ArrayList<String>Select_year=new ArrayList<>();
    Spinner Vehicle_spinner,Spinner_Fuel,Spinner_Year;
    String Car_type,Car_TypeId,CarFuel,CarFuelId,Selected_CarID,Selected_fuelId,Selected_car,Selected_Carfuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);


        User_Id= PreferenceUtil.getUserId(this);

        Select_car.add(0,"Select Vehicle");
        Select_fuel.add(0,"Select Fuel Type");
        Select_year.add("Select Year");
        Select_year.add("2018");
        Select_year.add("2017");
        Select_year.add("2016");
        Select_year.add("2015");
        Select_year.add("2014");
        Select_year.add("2013");
        Select_year.add("2012");
        Select_year.add("2011");
        Select_year.add("2010");
        Select_year.add("2009");
        Select_year.add("2008");
        Select_year.add("2007");
        Select_year.add("2006");
        Select_year.add("2005");





        imageView=findViewById(R.id.Image_back);
        image_animation=findViewById(R.id.shine_img1);
        Vehicle_spinner=findViewById(R.id.Spinner1);
        Spinner_Fuel=findViewById(R.id.Spinner2);
        Spinner_Year=findViewById(R.id.Spinner_year);


        Animation animation1 = new TranslateAnimation(0, 320, 0, 0);
        animation1.setDuration(850);
        animation1.setRepeatMode(Animation.RESTART);
        animation1.setFillAfter(false);
        animation1.setRepeatCount(1000);
        animation1.setInterpolator(new AccelerateDecelerateInterpolator());
        image_animation.startAnimation(animation1);



        ArrayAdapter<String> adapter_year=new ArrayAdapter<String>(AddVehicleActivity.this,android.R.layout.simple_spinner_item,Select_year);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_Year.setAdapter(adapter_year);

        Spinner_Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Year=parent.getSelectedItem()+" ";
                SelectedYear=Select_year.get(position);
                PreferenceUtil.setCarRegistration_year(AddVehicleActivity.this,SelectedYear);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner_Fuel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    String Fuel_Name=parent.getSelectedItem()+"";
                    Selected_Carfuel=fuelList.get(position-1).getFuelTypeName();
                    Selected_fuelId=fuelList.get(position-1).getFuelTypeId();
                    PreferenceUtil.set_Fuel_TypeID(AddVehicleActivity.this,Selected_fuelId);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Vehicle_spinner.setOnItemSelectedListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });





        button=findViewById(R.id.Button_Add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Spinner_Fuel.getSelectedItemPosition()!=0&&Spinner_Year.getSelectedItemPosition()!=0){
                    openReview();

                }else {
                    Toast.makeText(AddVehicleActivity.this,"Please Select Details",Toast.LENGTH_LONG).show();

                }


            }
        });


        if (Networkstate.isNetworkAvailable(this)){
            new VehicleType().execute();
            new fuelList().execute();
        }else {
            Toast.makeText(this,"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }


    }

    private void openReview() {
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.confirmalert);
        dialog.show();

        Confirm=dialog.findViewById(R.id.Button_confirm);
        Vtype=dialog.findViewById(R.id.Text_value1);
        Brand=dialog.findViewById(R.id.Text_value2);
        Model=dialog.findViewById(R.id.Text_value3);
        Fueltype=dialog.findViewById(R.id.Text_value4);
        Register=dialog.findViewById(R.id.Text_value5);
        if (Select_car==null){
           Toast.makeText(this,"Please Select Car",Toast.LENGTH_LONG ).show();
        }else {
            Vtype.setText(Selected_car);
        }
        Brand.setText(Details.get("Brand"));
        Model.setText(Details.get("Model"));
        final String ModelType=Vtype.getText().toString();
        Fueltype.setText(Selected_Carfuel);
        Register.setText(SelectedYear);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Networkstate.isNetworkAvailable(AddVehicleActivity.this)){
                    new AddVehicleList().execute(User_Id,Selected_CarID,Selected_fuelId,SelectedYear);
                }else {
                    Toast.makeText(AddVehicleActivity.this,"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
                Intent intent=new Intent(AddVehicleActivity.this,HelpyouActivity.class);
                intent.putExtra("Model",ModelType);
                startActivity(intent);
            }
        });




    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position>0){
            String Car_selected = parent.getSelectedItem() + "";
            String Car_Id=parent.getSelectedItemId()+"";
            Selected_CarID = VehicleList.get(position-1).getVehicleTypeId();
            Selected_car= VehicleList.get(position-1).getVehicleTypeName();
//
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class VehicleType extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>cardata=new ArrayList<>();
            JSONObject jsonObject=webServiceURL.VehicleList(cardata);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            super.onPostExecute(jsonObject);
            try {
                Log.e("VehicleList",""+jsonObject.toString());
                if (jsonObject!=null){
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        VehicleList=responseClass.getResult().getVehicleTypeData();
                        Select_car.clear();
                        Select_car.add(0,"Select Car");
                        for (int i=0;i<VehicleList.size();i++){
                            Car_type=VehicleList.get(i).getVehicleTypeName();
                             Car_TypeId=VehicleList.get(i).getVehicleTypeId();
                             Select_car.add(Car_type);
                             PreferenceUtil.setUser_CarId(AddVehicleActivity.this,Car_TypeId);
                        }

                        ArrayAdapter<String> adapter_spinner=new ArrayAdapter<String>(AddVehicleActivity.this,android.R.layout.simple_spinner_item,Select_car);
                        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Vehicle_spinner.setAdapter(adapter_spinner);
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class fuelList extends AsyncTask<String,String,JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>fuellist=new ArrayList<>();
            JSONObject jsonObject=webServiceURL.FuelList(fuellist);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            super.onPostExecute(jsonObject);
            try {
                if (jsonObject!=null){
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        fuelList=responseClass.getResult().getFuelTypeData();
                        Select_fuel.clear();
                        Select_fuel.add(0,"Select Fuel");
                        for (int i=0;i<fuelList.size();i++) {
                            String CarFuel = fuelList.get(i).getFuelTypeName();
                            String CarFuelId = fuelList.get(i).getFuelTypeId();
                            Select_fuel.add(CarFuel);
                        }
                        ArrayAdapter<String> adapter_spinner=new ArrayAdapter<String>(AddVehicleActivity.this,android.R.layout.simple_spinner_item,Select_fuel);
                        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Spinner_Fuel.setAdapter(adapter_spinner);


                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class AddVehicleList extends AsyncTask<String,String,JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>AddData=new ArrayList<>();
            AddData.add(new BasicNameValuePair("user_id",strings[0]));
            AddData.add(new BasicNameValuePair("vehicle_type_id",strings[1]));
            AddData.add(new BasicNameValuePair("fuel_type_id",strings[2]));
            AddData.add(new BasicNameValuePair("registration_year",strings[3]));
            JSONObject jsonObject=webServiceURL.Add_VehicleList(AddData);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            ArrayList<UserVehicleTypeDatum>mCarDetail;
            super.onPostExecute(jsonObject);
            try {
                Log.e("CarDetails",""+jsonObject.toString());
                if (jsonObject!=null){
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        String Token = responseClass.getResult().getUserLoginTokenData().getLoginToken();
                        String Login_Token = PreferenceUtil.getAccessTokenFromServer(AddVehicleActivity.this);
                        if (Token.equals(Login_Token)){
                            mCarDetail=responseClass.getResult().getUserVehicleTypeData();

                        }else {
                            PreferenceUtil.clearPreferenceObject(AddVehicleActivity.this);
                            Intent intent = new Intent(AddVehicleActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }


                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
