package com.wemsuser.app.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.wemsuser.app.Activity.DetailsActivity;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.Activity.LoginActivity;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Response.ServiceProviderDatum;
import com.wemsuser.app.Response.ServiceProviderMapDatum;
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
public class MapFragment extends Fragment implements LocationListener {
    HashMap<String, String> map = new HashMap<>();
    public ArrayList<ServiceProviderMapDatum> serviceList;
    String servicename, S_Latitude, S_longitude, Icon;
    String UserId, ServiceId, Latitude, Longitude, distance,MerchantId,MLatitude,MLongitude,Merchant
            ,MERCHANT_ID,New_Distance;
    GoogleMap MarkergoogleMap;
    String M_ServiceName;
    String Type="Service";
    private LatLngBounds.Builder builder = new LatLngBounds.Builder();
    ArrayList<LatLng>points = new ArrayList<LatLng>();
    Polyline line;





//    private static final LatLngBounds PACIFIC = new LatLngBounds(
//            new LatLng(-6.5244 , 3.3792), new LatLng(6.5244, -3.3792));


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivity.fab.findViewById(R.id.fab_button).setVisibility(View.GONE);
        HomeActivity.mMenu.findItem(R.id.Refresh).setVisible(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

//        nearBtVehiclesLatLng.add("11.833333","13.150000");



        UserId = PreferenceUtil.getUserId(getContext());
        ServiceId = PreferenceUtil.getServiceId(getContext());
        Latitude = PreferenceUtil.getUserLatitude(getContext());
        Longitude = PreferenceUtil.getUserLongitude(getContext());
        distance = PreferenceUtil.getDistance(getContext());
        Log.e("DataMap","USerID"+UserId+"\n"+"SID"+ServiceId+"\n"+Latitude+"\n"+Longitude+"\n"+distance);

         try {
                    MerchantId=getArguments().getString("ID");
                    MLatitude=getArguments().getString("M_Lati");
                    MLongitude=getArguments().getString("M_Longi");
                    Type=getArguments().getString("Type");

                    Log.e("GetData",""+MerchantId+"\n"+MLongitude+"\n"+MLatitude+"\n"+Type+"\n"+distance);

                }catch (Exception e){
                    e.printStackTrace();
                }


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MarkergoogleMap=googleMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//                googleMap.setLatLngBoundsForCameraTarget(PACIFIC);
//                googleMap.getUiSettings().setZoomControlsEnabled(true);
//                googleMap.getUiSettings().setRotateGesturesEnabled(false);
//                googleMap.getUiSettings().setScrollGesturesEnabled(true);
//                googleMap.getUiSettings().setTiltGesturesEnabled(false);
//                googleMap.clear();


                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(6.5244, -3.3792))
                        .zoom(8)
                        .build();

                CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(googlePlex);
                googleMap.animateCamera(camUpd,2000,null);

              if (Networkstate.isNetworkAvailable(getContext())) {
                  try {
                      if (Type.equalsIgnoreCase("Merchant")){
                          new DetailService().execute(UserId,ServiceId,MerchantId);

                      }else if (Type.equalsIgnoreCase("Service")){
                          if (ServiceId.equalsIgnoreCase("1000000003")) {
                              if (Latitude.equals("0.0")&&Longitude.equals("0.0")){
                                  new ServiceListAPI().execute(UserId, ServiceId, "6.5244","3.3792" ,"5");

                              }else {
                                  new ServiceListAPI().execute(UserId, ServiceId, Latitude,Longitude ,"5");

                              }
                          }else {
                              if (distance=="Select Kilometers"){
                                  if (Latitude.equals("0.0")&&Longitude.equals("0.0")){
                                      new ServiceListAPI().execute(UserId, ServiceId, "6.5244","3.3792" ,"50+");

                                  }else {
                                      new ServiceListAPI().execute(UserId, ServiceId, Latitude,Longitude ,"50+");

                                  }

                              }else {
                                  if (Latitude.equals("0.0")&&Longitude.equals("0.0")){
                                      new ServiceListAPI().execute(UserId, ServiceId, "6.5244","3.3792" ,distance);

                                  }else {
                                      new ServiceListAPI().execute(UserId, ServiceId, Latitude,Longitude ,distance);

                                  }

                              }
                          }
                      }

                  }catch (Exception e){
                      e.printStackTrace();
                  }

              } else {
                Toast.makeText(getContext(), "Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
            }
                MarkergoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        Intent intent=new Intent(getContext(), DetailsActivity.class);
                        getContext().startActivity(intent);
                    }
                });



            }


        });
        return view;


    }


    private BitmapDescriptor bitmapDescriptorFromVector(FragmentActivity activity, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(activity, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        points.add(latLng);
    }




    public class ServiceListAPI extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL = new WebServiceURL();
            List<NameValuePair> serviceList = new ArrayList<>();
            serviceList.add(new BasicNameValuePair("user_id", strings[0]));
            serviceList.add(new BasicNameValuePair("service_id", strings[1]));
            serviceList.add(new BasicNameValuePair("latitude", strings[2]));
            serviceList.add(new BasicNameValuePair("longitude", strings[3]));
            serviceList.add(new BasicNameValuePair("distance", strings[4]));
            JSONObject jsonObject = webServiceURL.Service_providerMap(serviceList);
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
                Log.e("MapList", "" + jsonObject.toString());
                if (jsonObject!= null) {
                    Gson gson = new Gson();
                    serviceList = new ArrayList<>();
                    ResponseClass responseClass = gson.fromJson(jsonObject.toString(), ResponseClass.class);
                    if (responseClass.getSuccess() == 1) {

                            try {
                                serviceList = responseClass.getResult().getServiceProviderMapData();
                                if (serviceList.size()==0){

                                }else {
                            for (int i = 0; i < serviceList.size(); i++) {
                                servicename = serviceList.get(i).getUserName();
                                S_Latitude = serviceList.get(i).getCreationLat();
                                S_longitude = serviceList.get(i).getCreationLong();
                                Merchant=serviceList.get(i).getMerchantId();
                                builder.include(new LatLng(Double.parseDouble(S_Latitude),Double.parseDouble(S_longitude)));
                                Icon = serviceList.get(i).getMobileIcon();
                                AddMarker();
                                MarkergoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(Marker marker) {
                                        if (Type.equalsIgnoreCase("Service")){
                                            Intent intent=new Intent(getContext(), DetailsActivity.class);
                                            intent.putExtra("BusinessId",ServiceId);
                                            intent.putExtra("USERID",UserId);
                                            intent.putExtra("MerchantId",Merchant);
                                            getContext().startActivity(intent);
                                        }else {
                                            Intent intent=new Intent(getContext(), DetailsActivity.class);
                                            intent.putExtra("BusinessId",ServiceId);
                                            intent.putExtra("USERID",UserId);
                                            intent.putExtra("MerchantId",MERCHANT_ID);
                                            getContext().startActivity(intent);
                                        }


                                    }
                                });
                            }
                                    LatLngBounds bounds = builder.build();
                                    try {
                                        MarkergoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                        }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

        private void AddMarker() {
        if (Type.equalsIgnoreCase("Merchant")){
            if(ServiceId.equalsIgnoreCase("1000000001")){
                    MarkergoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(MLatitude), Double.parseDouble(MLongitude)))
                            .title(M_ServiceName).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.tires_map)));

            }else if (ServiceId.equalsIgnoreCase("1000000002")){
                    MarkergoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(MLatitude), Double.parseDouble(MLongitude)))
                            .title(M_ServiceName).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.smalllockout)));

            }else if(ServiceId.equalsIgnoreCase("1000000003")){
                    MarkergoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(MLatitude), Double.parseDouble(MLongitude)))
                            .title(M_ServiceName).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.smachenic)));


            }else if(ServiceId.equalsIgnoreCase("1000000004")){
                MarkergoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(MLatitude),Double.parseDouble(MLongitude)))
                        .title(M_ServiceName).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.battery)));

            }else if(ServiceId.equalsIgnoreCase("1000000005")){
                MarkergoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(MLatitude),Double.parseDouble(MLongitude)))
                        .title(M_ServiceName).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.stowing)));

            }else if (ServiceId.equalsIgnoreCase("1000000006")){
                MarkergoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(MLatitude),Double.parseDouble(MLongitude)))
                        .title(M_ServiceName).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.smallbody)));

            }

        }else if (Type.equalsIgnoreCase("Service")){
            if(ServiceId.equalsIgnoreCase("1000000001")){
                for (int i = 0; i < serviceList.size(); i++) {
                    MarkergoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(S_Latitude), Double.parseDouble(S_longitude)))
                            .title(servicename).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.tires_map)));
                }
            }else if (ServiceId.equalsIgnoreCase("1000000002")){
                for (int i = 0; i < serviceList.size(); i++) {
                    MarkergoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(S_Latitude), Double.parseDouble(S_longitude)))
                            .title(servicename).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.smalllockout)));
                }
            }else if(ServiceId.equalsIgnoreCase("1000000003")){
                for (int i = 0; i < serviceList.size(); i++) {
                    MarkergoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(S_Latitude), Double.parseDouble(S_longitude)))
                            .title(servicename).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.smachenic)));
                }

            }else if(ServiceId.equalsIgnoreCase("1000000004")){
                for (int i = 0; i < serviceList.size(); i++) {
                    MarkergoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(S_Latitude), Double.parseDouble(S_longitude)))
                            .title(servicename).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.battery)));
                }
            }else if(ServiceId.equalsIgnoreCase("1000000005")){
                for (int i = 0; i < serviceList.size(); i++) {
                    MarkergoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(S_Latitude), Double.parseDouble(S_longitude)))
                            .title(servicename).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.stowing)));
                }

            }else if (ServiceId.equalsIgnoreCase("1000000006")){
                for (int i = 0; i < serviceList.size(); i++) {
                    MarkergoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(S_Latitude), Double.parseDouble(S_longitude)))
                            .title(servicename).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.smallbody)));
                }
            }

        }
    }



        public class  DetailService extends AsyncTask<String,String,JSONObject>{

            @Override
            protected JSONObject doInBackground(String... strings) {
                WebServiceURL webServiceURL=new WebServiceURL();
                List<NameValuePair>detailList=new ArrayList<>();
                detailList.add(new BasicNameValuePair("user_id",strings[0]));
                detailList.add(new BasicNameValuePair("service_id",strings[1]));
                detailList.add(new BasicNameValuePair("merchant_id",strings[2]));
                JSONObject jsonObject=webServiceURL.DetailService_ProviderList(detailList);
                return jsonObject;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);
                try {
                    Log.e("DetailList",""+jsonObject.toString());
                    if (jsonObject!=null){
                        Gson gson=new Gson();
                        ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                        if (responseClass.getSuccess()==1){
                            String Token = responseClass.getResult().getUserLoginTokenData().getLoginToken();
                            String Login_token = PreferenceUtil.getAccessTokenFromServer(getContext());
                            if (Token.equals(Login_token)){
                                String latitude=responseClass.getResult().getServiceProviderDetails().getCreationLat();
                                String longitude=responseClass.getResult().getServiceProviderDetails().getCreationLong();
                                M_ServiceName=responseClass.getResult().getServiceProviderDetails().getUserName();
                                MERCHANT_ID=responseClass.getResult().getServiceProviderDetails().getMerchantId();
                                AddMarker();
                                builder.include(new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude)));

                            }else {
                                PreferenceUtil.clearPreferenceObject(getContext());
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);

                            }

                        }
                        LatLngBounds bounds = builder.build();
                        try {
                            MarkergoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    private void drawRouteOnMap(){
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int i=0;i<points.size();i++){
            LatLng marker=points.get(i);
            options.add(marker);
        }
        line = MarkergoogleMap.addPolyline(options);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}





