package com.wemsuser.app.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wemsuser.app.Activity.ReviewActivity;
import com.wemsuser.app.Adapter.ReviewAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.AllReviewrating;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllReviewFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ReviewAdapter adapter;
    private ProgressBar progressBar;
    private ArrayList<AllReviewrating>AllReview=new ArrayList<>();
    private TextView RatingList;
    String UserId,MerchantId,ServiceTypeId,UserImage;



    public AllReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_review, container, false);


        UserId= PreferenceUtil.getUserId(getContext());
        MerchantId=PreferenceUtil.getMerchant_Id(getContext());
        ServiceTypeId=PreferenceUtil.getServiceId(getContext());

        progressBar=view.findViewById(R.id.Progress_bar);


        ReviewActivity.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                new AllReviewList().execute(MerchantId,ServiceTypeId);

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                new AllReviewList().execute(MerchantId,ServiceTypeId);


            }
        });



        recyclerView=view.findViewById(R.id.Recycler_review);
        recyclerView.setHasFixedSize(false);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        RatingList=view.findViewById(R.id.Text_List);
        if (Networkstate.isNetworkAvailable(getContext())){
            new AllReviewList().execute(MerchantId,ServiceTypeId);
        }

        return view;
    }

    public class AllReviewList extends AsyncTask<String,String,JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair> userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("merchant_id",strings[0]));
            userData.add(new BasicNameValuePair("service_type_id",strings[1]));
            JSONObject jsonObject=webServiceURL.AllRatingList(userData);
            return jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressBar.setVisibility(View.GONE);
            try {
                if (jsonObject!=null){
                    Log.e("RatingList",""+jsonObject.toString());
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        AllReview=responseClass.getResult().getAllReviewrating();
                        adapter=new ReviewAdapter(getContext(),AllReview);
                        recyclerView.setAdapter(adapter);
                        if (AllReview!=null){
                            RatingList.setVisibility(View.GONE);
                        }
                    }else {

                        RatingList.setVisibility(View.VISIBLE);

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
    }





}
