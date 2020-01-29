package com.wemsuser.app.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wemsuser.app.Adapter.HomeAdapter;
import com.wemsuser.app.Adapter.PaginationAdapter;
import com.wemsuser.app.Adapter.PaginationAdapterCallback;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Response.ServiceProviderDatum;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Testpagination extends AppCompatActivity implements PaginationAdapterCallback {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    PaginationAdapter adapter;
    private static final int PAGE_START = 1;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private static final int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    ArrayList<ServiceProviderDatum>ServiceProvider;
    String UserId, Latitude,Longitude,ServiceId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testpagination);

        UserId = PreferenceUtil.getUserId(this);
        Latitude = PreferenceUtil.getUserLatitude(this);
        Longitude = PreferenceUtil.getUserLongitude(this);
        ServiceId = PreferenceUtil.getServiceId(this);


        progressBar = findViewById(R.id.Progress);


        recyclerView = findViewById(R.id.Recycler_test);
        recyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                    return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        loadFirstPage();





    }

    @Override
    public void retryPageLoad() {
        loadNextPage();

    }

    public class ServiceList extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair> userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("user_id",strings[0]));
            userData.add(new BasicNameValuePair("service_id",strings[1]));
            userData.add(new BasicNameValuePair("latitude",strings[2]));
            userData.add(new BasicNameValuePair("longitude",strings[3]));
            userData.add(new BasicNameValuePair("distance",strings[4]));
            userData.add(new BasicNameValuePair("page",strings[5]));
            userData.add(new BasicNameValuePair("limit",strings[6]));

            JSONObject jsonObject=webServiceURL.Service_providerList(userData);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Log.e("ServiceList",""+jsonObject.toString());

            try {
                if (jsonObject!=null){
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        ServiceProvider=responseClass.getResult().getServiceProviderData();
                        String BannerURL=responseClass.getResult().getBanner().getBannerImage();
                        adapter.addAll(ServiceProvider);
//                        String NewURL=BannerURL.replace("thumb","mobilebanner");
//                        Glide.with(this).load(NewURL).into(home_banner);
//                        Log.e("ImageURl",""+NewURL);

                        adapter.removeLoadingFooter();
                        isLoading = false;



                        adapter=new PaginationAdapter(Testpagination.this,ServiceProvider);
                        recyclerView.setAdapter(adapter);

                    }
                }else {
                    recyclerView.setVisibility(View.GONE);

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void loadFirstPage(){
//        hideErrorView();
        currentPage = PAGE_START;
        new ServiceList().execute(UserId,ServiceId,Latitude,Longitude,"50+","1","100");


        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;
    }

    private void loadNextPage(){

        new ServiceList().execute(UserId,ServiceId,Latitude,Longitude,"50+","1","100");
        adapter.removeLoadingFooter();
        isLoading = false;

        adapter.addAll(ServiceProvider);

        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;
    }
}
