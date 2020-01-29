package com.wemsuser.app.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wemsuser.app.Adapter.DetailAdapter;
import com.wemsuser.app.Adapter.RelatedAdapter;
import com.wemsuser.app.Fragment.BookMarkFragment;
import com.wemsuser.app.Fragment.CarListFragment;
import com.wemsuser.app.Fragment.MapFragment;
import com.wemsuser.app.Fragment.SubscriptionFragment;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Response.ServiceProviderService;
import com.wemsuser.app.Response.SimilarServiceProvider;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements RelatedAdapter.SDetail {
    private RecyclerView recyclerView,recyclerView2;
    private LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    private DetailAdapter adapter;
    private RelatedAdapter relatedAdapter;
    private ImageView imageView,Image_share,ImageMap,Bookmark,Bannerdetail;
    private TextView textView;
    private  Integer image_List[]={R.drawable.mechanic,R.drawable.carbodywork,R.drawable.tow,R.drawable.lockout,R.drawable.fueldelivery,
            R.drawable.tire_change, R.drawable.jumper_cable,R.drawable.jump_start};
    private FragmentTransaction fragmentTransaction;
    private String backstack_name,User_Id,Service_Id,Merchant_Id,M_Latitude,M_Longitude;
    private TextView textView_call,text_provider,Merchant_name,Merchant_address,Title,feedback,ServiceRequest;
    FrameLayout frameLayout,frame_progress;
    RelativeLayout relativeLayout;
    String Type ="Detail";
    String MName,MPhone,MAddress,SimilarMerchant_Id,Car_year,Car_fuelId,UserCar_Id,User_latitude,User_longitude;
    String Shop_name,Shop_address,Shop_Phone,Business_ID,USERId,Map_MERCHANT;
    HashMap<String,String>SSDetail= new HashMap<>();
    CardView card_feedback;
    String User_vehicleId,User_CarYear,User_fuelId;
    int CallCounter=0,PaidCounter=0;
    private ImageView Detail_rating1,Detail_rating2,Detail_rating3,Detail_rating4,Detail_rating5;
    SpinKitView progressBar;
    FragmentManager mFragmentManager;
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Title=findViewById(R.id.Toolbar_text);
        Bannerdetail=findViewById(R.id.Banner_detail);
        scrollView = findViewById(R.id.Detail_scroll);

        Detail_rating1=findViewById(R.id.Detail_Rating1);
        Detail_rating2=findViewById(R.id.Detail_Rating2);
        Detail_rating3=findViewById(R.id.Detail_Rating3);
        Detail_rating4=findViewById(R.id.Detail_Rating4);
        Detail_rating5=findViewById(R.id.Detail_Rating5);

        progressBar=findViewById(R.id.spin_kit);
        frame_progress=findViewById(R.id.Frame_progress);


        Merchant_name=findViewById(R.id.Text1);
        Merchant_address=findViewById(R.id.Text_address);
        frameLayout=findViewById(R.id.fragment_Map);
        relativeLayout=findViewById(R.id.Detail_ID);
        Bookmark=findViewById(R.id.Image_bookMark);

        User_Id= PreferenceUtil.getUserId(this);
        Service_Id=PreferenceUtil.getServiceId(this);
        Merchant_Id=PreferenceUtil.getMerchant_Id(this);
        Car_fuelId=PreferenceUtil.get_Fuel_TypeID(this);
        UserCar_Id=PreferenceUtil.getUser_CarId(this);
        User_latitude=PreferenceUtil.getUserLatitude(this);
        User_longitude=PreferenceUtil.getUserLongitude(this);

        Log.e("DetailsClass",""+"UserId"+User_Id+"\n"+"ServiceId"+Service_Id);


        try {
            User_vehicleId=getIntent().getStringExtra("SelectedCar");
            User_CarYear=getIntent().getStringExtra("RegistrationYear");
            User_fuelId=getIntent().getStringExtra("FuelType_id");
            Log.e("UserRequestDetails",""+User_vehicleId+"\n"+User_CarYear);
            if (User_vehicleId!=null){
                new SendServiceRequest().execute(User_Id, Merchant_Id, Service_Id, User_latitude, User_longitude,User_vehicleId,User_fuelId, User_CarYear);

            }

        }catch (Exception e){
            e.printStackTrace();
        }



        try {
            Business_ID=getIntent().getStringExtra("BusinessId");
            Map_MERCHANT=getIntent().getStringExtra("MerchantId");
            USERId=getIntent().getStringExtra("USERID");
            if (Business_ID!=null && Map_MERCHANT!=null && Business_ID!=null){
                new DetailService_List().execute(User_Id,Business_ID,Map_MERCHANT);
                new AverageRating().execute(Map_MERCHANT);

            }



            Log.e("MapService",""+Business_ID+"\n"+Map_MERCHANT+"\n"+USERId);

        }catch (Exception e){
            e.printStackTrace();
        }


        card_feedback=findViewById(R.id.Card_Feedback);
        card_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailsActivity.this,FeedbackActivity.class);
                startActivity(intent);
            }
        });


        textView_call= findViewById(R.id.Call_service);
        text_provider= findViewById(R.id.Text_provider);



        text_provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Type.equalsIgnoreCase("Detail")) {
                    Fragment fragment = new CarListFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString( "Visible" , "Toolbar");
                    fragment.setArguments(arguments);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack(CarListFragment.class.getSimpleName());
                    ft.commit();
                }else  if (Type.equalsIgnoreCase("Similar")){
                    Fragment fragment = new CarListFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString( "Visible" , "Toolbar");
                    fragment.setArguments(arguments);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack(CarListFragment.class.getSimpleName());
                    ft.commit();

                }

//                Toast.makeText(DetailsActivity.this,"Request has been send to provider successfully",Toast.LENGTH_LONG).show();
            }
        });


        ImageMap=findViewById(R.id.Image_map);
        ImageMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                MapFragment fragment = new MapFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_Map, fragment);
                fragmentTransaction.addToBackStack(null);
                Bundle args = new Bundle();
                args.putString("Type","Merchant");
                args.putString("ID", Merchant_Id);
                args.putString("M_Lati", M_Latitude);
                args.putString("M_Longi", M_Longitude);
                fragment.setArguments(args);
                fragmentTransaction.commit();


            }
        });


        Image_share=findViewById(R.id.Share);
        Image_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hey download this app and save your self from sudden emergency in case of your road journey"+"\n"+"http://wemsrsa.com//";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "WAMRSA");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        textView=findViewById(R.id.Text_review);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailsActivity.this,ReviewActivity.class);
                startActivity(intent);
            }
        });

        imageView=findViewById(R.id.Imageback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("FragCount",""+getFragmentManager().getBackStackEntryCount());
                if(getFragmentManager().getBackStackEntryCount()>0) {
                    clearBackstack();
                }else{
                    onBackPressed();
                }

            }
        });



        recyclerView2=findViewById(R.id.Recycler_similar);
        recyclerView2.setHasFixedSize(false);
        linearLayoutManager2=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(linearLayoutManager2);



        recyclerView=findViewById(R.id.Recycler_service);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


    }

    @Override
    protected void onResume() {
        super.onResume();
        CallAPI();
    }

    private void CallAPI() {
        if(Networkstate.isNetworkAvailable(this)){
            if (Type.equalsIgnoreCase("Detail")){

                new DetailService_List().execute(User_Id,Service_Id,Merchant_Id);
                new AverageRating().execute(Merchant_Id);
            }else if (Type.equalsIgnoreCase("Similar")){
                scrollView.scrollTo(0,Bannerdetail.getTop());
                new DetailService_List().execute(User_Id,Service_Id,SimilarMerchant_Id);
                new AverageRating().execute(SimilarMerchant_Id);

            }

        }else {
            Toast.makeText(DetailsActivity.this,"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }
    }

    public static void showToastMessage(Context context, String message){

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View layout = inflater.inflate(R.layout.customtoast,
                (ViewGroup) ((Activity) context).findViewById(R.id.custom_toast_layout));
        // set a message
        TextView text = layout.findViewById(R.id.Text_toast);
        text.setText(message);

        // Toast...
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void similar(HashMap<String,String> similarDetail) {
        SSDetail=similarDetail;
        MName=SSDetail.get("Merchant_Name");
        MAddress=SSDetail.get("M_Address");
        MPhone=SSDetail.get("M_phone");
        Type=SSDetail.get("Type");
        SimilarMerchant_Id=SSDetail.get("Merchant_Id");
        if (Type.equalsIgnoreCase("Similar")){
            Merchant_name.setText(toCamelCase(MName));
            Merchant_address.setText(toCamelCase(MAddress));
            Title.setText(toCamelCase(MName));

            final String hint=MPhone.replace(MPhone.substring(0,MPhone.length()-3),"********");
            textView_call.setText(" Call on"+" "+hint);
            textView_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + MPhone));
                    startActivity(intent);
                }
            });
            CallAPI();
            new SendServiceRequest().execute(User_Id, SimilarMerchant_Id, Service_Id, User_latitude, User_longitude,  "1000000070", "1000000002", Car_year);

        }



//        Toast.makeText(DetailsActivity.this,"Details"+MName+"\n"+MAddress,Toast.LENGTH_LONG).show();
    }




    public class DetailService_List extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>Detail_List=new ArrayList<>();
            Detail_List.add(new BasicNameValuePair("user_id",strings[0]));
            Detail_List.add(new BasicNameValuePair("service_id",strings[1]));
            Detail_List.add(new BasicNameValuePair("merchant_id",strings[2]));
            JSONObject jsonObject=webServiceURL.DetailService_ProviderList(Detail_List);
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
            ArrayList<ServiceProviderService>Provider_Service=new ArrayList<>();
            ArrayList<SimilarServiceProvider>Similar_provider= new ArrayList<>();
            super.onPostExecute(jsonObject);
            frame_progress.setVisibility(View.GONE);

            try {
                Log.e("Detail_ServiceList",""+jsonObject.toString());
                if (jsonObject!=null){
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1) {

                        String BannerDetail = responseClass.getResult().getServiceProviderDetails().getBannerImage();
                        String NewURL = BannerDetail.replace("thumb", "mobilebanner");
                        Glide.with(DetailsActivity.this).load(NewURL).placeholder(R.drawable.new2banner).into(Bannerdetail);

                        Provider_Service = responseClass.getResult().getServiceProviderServices();
                        Similar_provider = responseClass.getResult().getSimilarServiceProvider();

                        Shop_name = responseClass.getResult().getServiceProviderDetails().getUserName();
                        Shop_address = responseClass.getResult().getServiceProviderDetails().getUserAddress();
                        Shop_Phone = responseClass.getResult().getServiceProviderDetails().getUserPhone();
                        Merchant_name.setText(toCamelCase(Shop_name));
                        Title.setText(toCamelCase(Shop_name));
//                        Toast.makeText(DetailsActivity.this,"ListSimilar"+Shop_Phone,Toast.LENGTH_LONG).show();

                        Merchant_address.setText(toCamelCase(Shop_address));
                        Merchant_Id = responseClass.getResult().getServiceProviderDetails().getMerchantId();
                        M_Latitude = responseClass.getResult().getServiceProviderDetails().getCreationLat();
                        M_Longitude = responseClass.getResult().getServiceProviderDetails().getCreationLong();
                        PreferenceUtil.setMerchant_Id(DetailsActivity.this, Merchant_Id);
                        String Token = responseClass.getResult().getUserLoginTokenData().getLoginToken();
                        String Login_token = PreferenceUtil.getAccessTokenFromServer(DetailsActivity.this);
                        if (Token.equals(Login_token)){
                            adapter = new DetailAdapter(DetailsActivity.this, Provider_Service);
                            recyclerView.setAdapter(adapter);

                            relatedAdapter = new RelatedAdapter(DetailsActivity.this, Similar_provider, DetailsActivity.this);
                            recyclerView2.setAdapter(relatedAdapter);

                        }else {
                            PreferenceUtil.clearPreferenceObject(DetailsActivity.this);
                            Intent intent = new Intent(DetailsActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }



                        if (!Shop_Phone.isEmpty()) {
                            String hint = Shop_Phone.replace(Shop_Phone.substring(0, Shop_Phone.length() - 2), "********");
                            textView_call.setText("  Call on" + "  " + hint);
                            textView_call.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new UserCallCounter().execute(User_Id, Merchant_Id, Service_Id);

                                }
                            });
                        }

                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    public class SendServiceRequest extends AsyncTask<String,String,JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userDetail=new ArrayList<>();
            userDetail.add(new BasicNameValuePair("user_id",strings[0]));
            userDetail.add(new BasicNameValuePair("merchant_id",strings[1]));
            userDetail.add(new BasicNameValuePair("service_type_id",strings[2]));
            userDetail.add(new BasicNameValuePair("user_latitude",strings[3]));
            userDetail.add(new BasicNameValuePair("user_longitude",strings[4]));
            userDetail.add(new BasicNameValuePair("user_vehicle_id",strings[5]));
            userDetail.add(new BasicNameValuePair("fuel_type_id",strings[6]));
            userDetail.add(new BasicNameValuePair("registration_year",strings[7]));
            JSONObject jsonObject=webServiceURL.SendRequestForService(userDetail);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                Log.e("SendRequest",""+jsonObject.toString());
                Gson gson=new Gson();
                ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                if (responseClass.getSuccess()==1){
                    String message="Request has been sent to provider successfully & you will be contacted shortly";
                    showToastMessage(DetailsActivity.this,message);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    public class UserCallCounter extends AsyncTask<String,String,JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("user_id",strings[0]));
            userData.add(new BasicNameValuePair("merchant_id",strings[1]));
            userData.add(new BasicNameValuePair("service_type_id",strings[2]));
            JSONObject jsonObject=webServiceURL.UserCallData(userData);
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
                    Log.e("CallData",""+jsonObject.toString());

                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        CallCounter=responseClass.getResult().getCallData();
                        PaidCounter=responseClass.getResult().getPaidCallData();


                        if (CallCounter!=0){
                            if (CallCounter>=3){
                                textView_call.setClickable(false);
                                SubscriptionAlert();
                            }else {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + Shop_Phone));
                                startActivity(intent);
                            }
                        }else {
                            if (PaidCounter>=5){
                                Toast.makeText(DetailsActivity.this,"Your's today's call counter has expired",Toast.LENGTH_LONG).show();
                                textView_call.setClickable(false);
                            }else {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + Shop_Phone));
                                startActivity(intent);
                            }
                        }


                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        private void SubscriptionAlert() {
            final Dialog dialog=new Dialog(DetailsActivity.this);
            dialog.setContentView(R.layout.subscriptionalert);
            Button button=dialog.findViewById(R.id.Buy_now);
            dialog.show();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SubscriptionFragment fragment = new SubscriptionFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    dialog.dismiss();

                }
            });
            ImageView imageView=dialog.findViewById(R.id.Image_cross);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        frameLayout.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
    }

    public static String toCamelCase(final String init) {
        if (init==null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length()==init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }


    public class AverageRating extends AsyncTask<String,String,JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL = new WebServiceURL();
            List<NameValuePair>userData = new ArrayList<>();
            userData.add(new BasicNameValuePair("merchant_id",strings[0]));
            JSONObject jsonObject=webServiceURL.AvarageRating(userData);
            return jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (jsonObject!=null){
                Log.e("DetailAverage",""+jsonObject.toString());
            }
            Gson gson = new Gson();
            ResponseClass responseClass = gson.fromJson(jsonObject.toString(),ResponseClass.class);
            if (responseClass.getSuccess()==1){
                String Rating = responseClass.getResult().getTotalRating().getTotalRating();
                String Count = responseClass.getResult().getTotalRating().getTotalCount();
                try {
                    Float averageRating = (Float.parseFloat(Rating)/Float.parseFloat(Count));
                    setRatingStar(averageRating);
//
                    Log.e("AverageDetails",""+averageRating);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

    private void setRatingStar(Float count) {

        if (count == 1.00) {
            Detail_rating1.setVisibility(View.VISIBLE);
        } else if (count == 2.00) {
            Detail_rating1.setVisibility(View.VISIBLE);
            Detail_rating2.setVisibility(View.VISIBLE);
        } else if (count == 3.00) {
            Detail_rating1.setVisibility(View.VISIBLE);
            Detail_rating2.setVisibility(View.VISIBLE);
            Detail_rating3.setVisibility(View.VISIBLE);
        } else if (count == 4.00) {
            Detail_rating1.setVisibility(View.VISIBLE);
            Detail_rating2.setVisibility(View.VISIBLE);
            Detail_rating3.setVisibility(View.VISIBLE);
            Detail_rating4.setVisibility(View.VISIBLE);
        } else if (count == 5.00) {
            Detail_rating1.setVisibility(View.VISIBLE);
            Detail_rating2.setVisibility(View.VISIBLE);
            Detail_rating3.setVisibility(View.VISIBLE);
            Detail_rating4.setVisibility(View.VISIBLE);
            Detail_rating5.setVisibility(View.VISIBLE);
        } else if (count > 0.00 && count < 1.00) {
            Detail_rating1.setVisibility(View.VISIBLE);
            Detail_rating1.setImageResource(R.drawable.half_star);
        } else if (count > 1.00 && count < 2.00) {
            Detail_rating1.setVisibility(View.VISIBLE);
            Detail_rating1.setImageResource(R.drawable.rating_fill);
            Detail_rating2.setVisibility(View.VISIBLE);
            Detail_rating2.setImageResource(R.drawable.half_star);
        } else if (count > 2.00 && count < 3.00) {
            Detail_rating1.setVisibility(View.VISIBLE);
            Detail_rating2.setVisibility(View.VISIBLE);
            Detail_rating2.setImageResource(R.drawable.rating_fill);
            Detail_rating3.setVisibility(View.VISIBLE);
            Detail_rating3.setImageResource(R.drawable.half_star);
        } else if (count > 3.00 && count < 4.00) {
            Detail_rating1.setVisibility(View.VISIBLE);
            Detail_rating2.setVisibility(View.VISIBLE);
            Detail_rating3.setVisibility(View.VISIBLE);
            Detail_rating3.setImageResource(R.drawable.rating_fill);
            Detail_rating4.setVisibility(View.VISIBLE);
            Detail_rating4.setImageResource(R.drawable.half_star);
        } else if (count > 4.00 && count < 5.00) {
            Detail_rating1.setVisibility(View.VISIBLE);
            Detail_rating2.setVisibility(View.VISIBLE);
            Detail_rating3.setVisibility(View.VISIBLE);
            Detail_rating4.setVisibility(View.VISIBLE);
            Detail_rating4.setImageResource(R.drawable.rating_fill);
            Detail_rating5.setVisibility(View.VISIBLE);
            Detail_rating5.setImageResource(R.drawable.half_star);
        }
    }

    public void clearBackstack() {
        int count=getFragmentManager().getBackStackEntryCount();
        android.app.Fragment frag= getFragmentManager().findFragmentByTag(CarListFragment.class.getSimpleName());
        Log.e("FragmentName",""+getFragmentManager().getBackStackEntryAt(count-1).getName());

        if(getFragmentManager().getBackStackEntryAt(count-1).getName().equalsIgnoreCase(CarListFragment.class.getSimpleName()));
        getFragmentManager().popBackStackImmediate();
        getFragmentManager().popBackStackImmediate();

//        FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(
//                0);
//        getSupportFragmentManager().popBackStack(entry.getId(),
//                FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        getSupportFragmentManager().executePendingTransactions();

    }


}
