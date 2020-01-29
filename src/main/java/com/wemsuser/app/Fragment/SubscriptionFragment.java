package com.wemsuser.app.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.Adapter.SubscriptionAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Response.Subscriptiondatum;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.paystack.android.PaystackSdk;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscriptionFragment extends Fragment {

    private RecyclerView recyclerView,recycler_basic;
    LinearLayoutManager linearLayoutManager,linearLayoutManager1;
    SubscriptionAdapter adapter;
    ArrayList<Subscriptiondatum>msubscription=new ArrayList<>();
    ArrayList<Subscriptiondatum>premium=new ArrayList<>();
    int Freeindex=0;
    Boolean Basic=false;
    TextView Activated;
    String PackageId;


    public SubscriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivity.mMenu.findItem(R.id.action_Map).setVisible(false);
        HomeActivity.mMenu.findItem(R.id.action_List).setVisible(false);
        HomeActivity.fab.findViewById(R.id.fab_button).setVisibility(View.GONE);
        HomeActivity.mMenu.findItem(R.id.Refresh).setVisible(false);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_subscription, container, false);

        recyclerView=view.findViewById(R.id.Recycler_subcription);

        Activated=view.findViewById(R.id.TextView);
        PackageId= PreferenceUtil.getPackage_Id(getContext());
        String ExpireDate=PreferenceUtil.getPackage_Expire(getContext());
        if (!PackageId.equalsIgnoreCase("")&&!ExpireDate.equalsIgnoreCase("")){
            Activated.setVisibility(View.VISIBLE);
            Log.e("ExpireDate",""+ExpireDate+"\n"+PackageId);
            Activated.setText("Your Subscription will Expire on"+"\n"+ExpireDate);
        }



        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        if (Networkstate.isNetworkAvailable(getContext())){
            new SubscriptionList().execute();
        }else {
            Toast.makeText(getContext(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }

        return view;
    }


    public class SubscriptionList extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            JSONObject jsonObject=webServiceURL.SubscriptionList(userData);
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
                    Log.e("SebscriptionList",""+jsonObject.toString());
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){

                        msubscription = responseClass.getResult().getSubscriptiondata();
                        for (int i=0;i<msubscription.size();i++){
                            String packageName=msubscription.get(i).getPackageName();

                                if (packageName.equalsIgnoreCase("Free")){
                                    Freeindex=i;
                                }else {

                                    premium.add(msubscription.get(i));

                                }
                        }
                        premium.add(msubscription.get(Freeindex));
                        adapter=new SubscriptionAdapter(getContext(),premium);
                        recyclerView.setAdapter(adapter);



                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
