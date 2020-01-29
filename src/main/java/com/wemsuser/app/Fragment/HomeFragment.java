package com.wemsuser.app.Fragment;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.gson.Gson;
import com.wemsuser.app.Activity.HelpyouActivity;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.Activity.LoginActivity;
import com.wemsuser.app.Adapter.HomeAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.Banner;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Response.ServiceProviderDatum;
import com.wemsuser.app.Services.LoginToken;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.BannerPreferenceUtil;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.wemsuser.app.Activity.HomeActivity.textviewTitle;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private HomeAdapter adapter;
    private ArrayList<String> ShopList=new ArrayList<>();
    private ArrayList<String> mlist=new ArrayList<>();
    int[] animationList = {R.anim.recycler_anim};
    int i = 0;
    Spinner spinner,spinner_city,spinner_state;
    private LinearLayout Error_Data;
    String User_id,Service_id,Latitude,Longitude,Distance,PopSelectedId,RadioButtonId,
            service_name,New_Distance;
    SpinKitView progressBar;
    FrameLayout frameLayout;
    ArrayList<Integer>mserviceList;
    public  static TextView textView;
    ImageView home_banner,filter,banners_ads;
    LinearLayout linearLayout;
    TextView text_message;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        User_id= PreferenceUtil.getUserId(getActivity());
        Service_id=PreferenceUtil.getServiceId(getActivity());
        Latitude=PreferenceUtil.getUserLatitude(getActivity());
        Longitude=PreferenceUtil.getUserLongitude(getActivity());
        service_name=PreferenceUtil.getServiceName(getActivity());


        home_banner=view.findViewById(R.id.Banner_home);
        Error_Data=view.findViewById(R.id.Linear_Network);
        textView=view.findViewById(R.id.Text_service);
        text_message = view.findViewById(R.id.Text_Message);
        textviewTitle.setText(service_name);
        textView.setText(service_name);
        linearLayout=view.findViewById(R.id.Linear);
        banners_ads=view.findViewById(R.id.image_banner);
        HomeActivity.fab.findViewById(R.id.fab_button).setVisibility(View.VISIBLE);

//        int  resId = R.anim.recycler_anim;
//        final LayoutAnimationController controller =
//                AnimationUtils.loadLayoutAnimation(getActivity(), resId);

//        String SelectedId=getArguments().getString("Selected_Position");





        LoadBannerImage();

        mlist.clear();
        mlist.add("Select Kms");
        mlist.add("0-5 Kms");
        mlist.add("5-10 Kms");
        mlist.add("10-15 Kms");
        mlist.add("15-20 Kms");


        mserviceList= new ArrayList<Integer>(HomeActivity.Selected_Service.keySet());
        PopSelectedId=String.valueOf(HomeActivity.position);
        RadioButtonId=String.valueOf(HomeActivity.selectedId);


        Log.e("SelectedId",""+PopSelectedId);







        banners_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebook= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wemsrsa.com/"));
                startActivity(facebook);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });

        progressBar=view.findViewById(R.id.spin_kit);
        frameLayout=view.findViewById(R.id.Frame_progress);


        spinner=view.findViewById(R.id.Spinner_home);
        ArrayAdapter<String> adapter_spinner=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,mlist);
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selected_posi1 = spinner.getSelectedItemPosition();
                Distance = "0-5";
                Distance = spinner.getItemAtPosition(selected_posi1).toString();
                Log.e("SelectedItem", "" + Distance);

                try {
                    if (getArguments()!=null) {
                    String newLatitude=getArguments().getString("NewLatitude");
                    String newLongitude=getArguments().getString("NewLongitude");
                    Log.e("NewLatLong",""+newLatitude+"\n"+newLongitude);

                    new ServiceProviderList().execute(User_id,Service_id,newLatitude,newLongitude,New_Distance);

                } else {
                if (!Distance.equalsIgnoreCase("Select Kilometers")) {
                    if (Distance.equals("0-5 KM")) {
                        int Distance = 5;
                        New_Distance = String.valueOf(Distance);
                        Log.e("SelectedDistance", "" + New_Distance);
                        PreferenceUtil.setDistance(getContext(), New_Distance);
                        if (Latitude.equals("0.0") && Longitude.equals("0.0")) {
                            new ServiceProviderList().execute(User_id, Service_id, "6.5244", "3.3792", New_Distance);
                        } else {
                            new ServiceProviderList().execute(User_id, Service_id, Latitude, Longitude, New_Distance);

                        }
                    } else if (Distance.equals("5-10 KM")) {
                        int Distance = 10;
                        New_Distance = String.valueOf(Distance);
                        PreferenceUtil.setDistance(getContext(), New_Distance);
                        Log.e("SelectedDistance", "" + New_Distance);
                        if (Latitude.equals("0.0") && Longitude.equals("0.0")) {
                            new ServiceProviderList().execute(User_id, Service_id, "6.5244", "3.3792", New_Distance);
                        } else {
                            new ServiceProviderList().execute(User_id, Service_id, Latitude, Longitude, New_Distance);

                        }
                    } else if (Distance.equals("10-15 KM")) {
                        int Distance = 15;
                        New_Distance = String.valueOf(Distance);
                        PreferenceUtil.setDistance(getContext(), New_Distance);
                        Log.e("SelectedDistance", "" + New_Distance);
                        if (Latitude.equals("0.0") && Longitude.equals("0.0")) {
                            new ServiceProviderList().execute(User_id, Service_id, "6.5244", "3.3792", New_Distance);
                        } else {
                            new ServiceProviderList().execute(User_id, Service_id, Latitude, Longitude, New_Distance);

                        }
                    } else if (Distance.equals("15-20 KM")) {
                        int Distance = 20;
                        New_Distance = String.valueOf(Distance);
                        PreferenceUtil.setDistance(getContext(), New_Distance);
                        Log.e("SelectedDistance", "" + New_Distance);
                        if (Latitude.equals("0.0") && Longitude.equals("0.0")) {
                            new ServiceProviderList().execute(User_id, Service_id, "6.5244", "3.3792", New_Distance);
                        } else {
                            new ServiceProviderList().execute(User_id, Service_id, Latitude, Longitude, New_Distance);

                        }
                    }
                } else {
                    if (Latitude.equals("0.0") && Longitude.equals("0.0")) {
                        new ServiceProviderList().execute(User_id, Service_id, "6.5244", "3.3792", "50+");
                    } else {
                        new ServiceProviderList().execute(User_id, Service_id, Latitude, Longitude, "50+");

                    }
                }
            }

                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView=view.findViewById(R.id.Recycler_home);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;



    }

    private void LoadBannerImage() {
        if (service_name.equalsIgnoreCase("Tires")){
            String Tire_url= BannerPreferenceUtil.gettireHomeBanner(getContext());
            Glide.with(getActivity()).load(Tire_url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(home_banner);
        }else if (service_name.equalsIgnoreCase("Lockout")){
            String Lock_url = BannerPreferenceUtil.getLOCKHomeBanner(getContext());
            Glide.with(getActivity()).load(Lock_url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(home_banner);
        }else if (service_name.equalsIgnoreCase("Mechanics")){
            String Mechanics_url = BannerPreferenceUtil.getMechanicsHomeBanner(getContext());
            Glide.with(getActivity()).load(Mechanics_url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(home_banner);
        }else if (service_name.equalsIgnoreCase("Electrical/Battery")){
            String Battery_url = BannerPreferenceUtil.getBatteryHomeBanner(getContext());
            Glide.with(getActivity()).load(Battery_url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(home_banner);
        }else if (service_name.equalsIgnoreCase("Tow Truck")){
            String Tow_url = BannerPreferenceUtil.getTowingHomeBanner(getContext());
            Glide.with(getActivity()).load(Tow_url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(home_banner);
        }else if (service_name.equalsIgnoreCase("Car Body Work")){
            String Car_url = BannerPreferenceUtil.getCarBodyHomeBanner(getContext());
            Glide.with(getActivity()).load(Car_url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(home_banner);
        }
    }


    public class ServiceProviderList extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("user_id",strings[0]));
            userData.add(new BasicNameValuePair("service_id",strings[1]));
            userData.add(new BasicNameValuePair("latitude",strings[2]));
            userData.add(new BasicNameValuePair("longitude",strings[3]));
            userData.add(new BasicNameValuePair("distance",strings[4]));
            JSONObject jsonObject=webServiceURL.Service_providerList(userData);
            return jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Sprite circle = new Circle();
            progressBar.setIndeterminateDrawable(circle);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            ArrayList<ServiceProviderDatum>ServiceProvider;
            super.onPostExecute(jsonObject);
            frameLayout.setVisibility(View.GONE);
            try {
                if (jsonObject!=null) {
                    Gson gson = new Gson();
                    ResponseClass responseClass = gson.fromJson(jsonObject.toString(), ResponseClass.class);
                    if (responseClass.getSuccess() == 1) {
                        String Message = responseClass.getMessage();
                        Log.e("ServiceListMessage",""+Message);
                        if (Message.equalsIgnoreCase("Sorry! No RSA available in this area.")){
                            recyclerView.setVisibility(View.GONE);
                            text_message.setText(Message);
                            Error_Data.setVisibility(View.VISIBLE);
                            if (service_name.equalsIgnoreCase("Tires")){
                                home_banner.setBackgroundResource(R.mipmap.tier_banner);
                            }else if(service_name.equalsIgnoreCase("Lockout")) {
                                home_banner.setBackgroundResource(R.mipmap.key_banner);
                            }else if (service_name.equalsIgnoreCase("Mechanics")){
                                home_banner.setBackgroundResource(R.mipmap.mechanics_banner);
                            }else if (service_name.equalsIgnoreCase("Electrical/Battery")){
                                home_banner.setBackgroundResource(R.mipmap.battery_banner);
                            }else if (service_name.equalsIgnoreCase("Tow Truck")){
                                home_banner.setBackgroundResource(R.mipmap.towing_banner);

                            }else if (service_name.equalsIgnoreCase("Car Body Work")){
                                home_banner.setBackgroundResource(R.mipmap.car_body_banner);

                            }
                        }else {
                            String Token = responseClass.getResult().getUserLoginTokenData().getLoginToken();
                            String Login_token = PreferenceUtil.getAccessTokenFromServer(getContext());
                            Log.e("HomeLoginToken", "" + Login_token);

                            ServiceProvider = responseClass.getResult().getServiceProviderData();
                            String BannerURL = responseClass.getResult().getBanner().getBannerImage();
                            new LoginToken(getContext(),User_id,Login_token);

                            String NewURL = BannerURL.replace("thumb", "mobilebanner");
                            Glide.with(getActivity()).load(NewURL).diskCacheStrategy(DiskCacheStrategy.ALL).into(home_banner);
                            Log.e("ImageURl", "" + NewURL);

                            if (Token.equals(Login_token)){
                                adapter = new HomeAdapter(getContext(), ServiceProvider);
                                recyclerView.setAdapter(adapter);
                            }else {
                                PreferenceUtil.clearPreferenceObject(getContext());
                                Intent intent = new Intent(getContext(),LoginActivity.class);
                                startActivity(intent);
                            }


                            if (service_name.equalsIgnoreCase("Tires")){
                                BannerPreferenceUtil.settireHomeBanner(getContext(),NewURL);

                            }else if (service_name.equalsIgnoreCase("Lockout")){
                                BannerPreferenceUtil.setLOCKHomeBanner(getContext(),NewURL);
                            }else if (service_name.equalsIgnoreCase("Mechanics")){
                                BannerPreferenceUtil.setMechanicsHomeBanner(getContext(),NewURL);
                            }else if (service_name.equalsIgnoreCase("Electrical/Battery")){
                                BannerPreferenceUtil.setBatteryHomeBanner(getContext(),NewURL);
                            }else if (service_name.equalsIgnoreCase("Tow Truck")){
                                BannerPreferenceUtil.setTowingHomeBanner(getContext(),NewURL);
                            }else if (service_name.equalsIgnoreCase("Car Body Work")){
                                BannerPreferenceUtil.setCarBodyHomeBanner(getContext(),NewURL);
                            }

                        }



                    } else {

                        text_message.setText("Please Check Your Network Connection");
                        recyclerView.setVisibility(View.GONE);
                        Error_Data.setVisibility(View.VISIBLE);

                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (Latitude.equals("0.0") && Longitude.equals("0.0")) {
            new ServiceProviderList().execute(User_id, Service_id, "6.5244", "3.3792", "5");
        } else {
            new ServiceProviderList().execute(User_id, Service_id, Latitude, Longitude, "5");

        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    Intent intent=new Intent(getContext(), HelpyouActivity.class);
                    startActivity(intent);

                    // handle back button

                    return true;

                }

                return false;
            }
        });
    }

    private void openLogoutPopup() {
        final Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.logoutxml);
        TextView text_yes=dialog.findViewById(R.id.Text_yes);
        TextView text_No=dialog.findViewById(R.id.Text_no);
        text_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.clearPreferenceObject(getContext());
                dialog.dismiss();
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        text_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
