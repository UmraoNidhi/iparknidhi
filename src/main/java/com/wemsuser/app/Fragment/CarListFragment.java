package com.wemsuser.app.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.wemsuser.app.Activity.DetailsActivity;
import com.wemsuser.app.Activity.LoginActivity;
import com.wemsuser.app.Adapter.AddAdapter;
import com.wemsuser.app.Adapter.CarListAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Response.UserVehicleTypeList;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarListFragment extends Fragment implements CarListAdapter.SelectedCarDetails {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CarListAdapter adapter;
    private ArrayList<UserVehicleTypeList>User_CarList;
    String User_Id,User_VehicalType_Id,desired_string;
    String DeleteCarData;
    LinearLayout linearLayout;
    HashMap<String,String> CarDeleteData=new HashMap<String, String>();
    private Toolbar toolbar;
    private TextView textView;
    private ImageView imageView;
    boolean flag= false ;





    public CarListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_car_list, container, false);

        User_Id= PreferenceUtil.getUserId(getContext());
        toolbar=view.findViewById(R.id.Toolbar_Card);
        textView=view.findViewById(R.id.Toolbar_text);
        imageView=view.findViewById(R.id.Imageback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), DetailsActivity.class);
                startActivity(intent);
            }
        });
        User_VehicalType_Id=PreferenceUtil.getUser_Vehical_Type_Id(getContext());
        linearLayout=view.findViewById(R.id.Linear_Network);
        recyclerView=view.findViewById(R.id.Recycler_List);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        if (Networkstate.isNetworkAvailable(getContext())){
            new UserCarList().execute(User_Id);
        }else {
            linearLayout.setVisibility(View.VISIBLE);

            Toast.makeText(getContext(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }


        try {
            Bundle arguments = getArguments();
            desired_string = arguments.getString("Visible");
            if (desired_string!=null){
                flag=true;
                if (desired_string.equalsIgnoreCase("Toolbar")){
                    toolbar.setVisibility(View.VISIBLE);
                    textView.setText("Select Car");
                }
            }

            Log.e("DataCard",""+desired_string);
        }catch (Exception e){
            e.printStackTrace();
        }



        return view;
    }

    @Override
    public void deleteCar(HashMap<String, String> CarDetails) {
        CarDeleteData=CarDetails;
        DeleteCarData=CarDeleteData.get("CarId");
        Log.e("DeleteData",""+DeleteCarData);

        if (Networkstate.isNetworkAvailable(getActivity())){
            new DeleteUserCarDetail().execute(User_Id,DeleteCarData);

        }else {
            Toast.makeText(getContext(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }





    }

    public class UserCarList extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>CarList=new ArrayList<>();
            CarList.add(new BasicNameValuePair("user_id",strings[0]));
            JSONObject jsonObject=webServiceURL.UserCar_List(CarList);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            ArrayList<UserVehicleTypeList>mUserCarList;
            super.onPostExecute(jsonObject);
            try {
                Log.e("UserCarList",""+jsonObject.toString());
                if (jsonObject!=null){
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                     mUserCarList=responseClass.getResult().getUserVehicleTypeList();
                     String Token = responseClass.getResult().getUserLoginTokenData().getLoginToken();
                     String Login_token = PreferenceUtil.getAccessTokenFromServer(getContext());
                     if (Token.equals(Login_token)){
                         adapter=new CarListAdapter(getActivity(),mUserCarList,flag,CarListFragment.this);
                         recyclerView.setAdapter(adapter);
                     }  else {
                         PreferenceUtil.clearPreferenceObject(getContext());
                         Intent intent = new Intent(getContext(), LoginActivity.class);
                         startActivity(intent);
                     }
                     }

                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public class DeleteUserCarDetail extends AsyncTask<String,String,JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>delete=new ArrayList<>();
            delete.add(new BasicNameValuePair("user_id",strings[0]));
            delete.add(new BasicNameValuePair("user_vehicle_type_id",strings[1]));
            JSONObject jsonObject=webServiceURL.Delete_carDetails(delete);
            return jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                if (jsonObject!=null){
                    Log.e("CarDetail",""+jsonObject.toString());
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        Toast.makeText(getContext(),"Car detail deleted successfully",Toast.LENGTH_LONG).show();
                        new UserCarList().execute(User_Id);
                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
