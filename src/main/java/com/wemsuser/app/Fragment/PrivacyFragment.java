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
import android.widget.Toast;

import com.google.gson.Gson;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.Adapter.TermConditionAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.PrivacypolicyDatum;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyFragment extends Fragment {
    private RecyclerView recyclerView_privacy;
    private LinearLayoutManager linearLayoutManager;
    private TermConditionAdapter adapter;
    ArrayList<PrivacypolicyDatum>mprivacy;
    boolean Type=false;


    public PrivacyFragment() {
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
        View view= inflater.inflate(R.layout.fragment_privacy, container, false);
        recyclerView_privacy=(RecyclerView)view.findViewById(R.id.Recycler_privacy);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView_privacy.setLayoutManager(linearLayoutManager);
        if (Networkstate.isNetworkAvailable(getActivity())){
            new PrivacyData().execute();
        }else {
            Toast.makeText(getActivity(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public class PrivacyData extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>privacyData=new ArrayList<>();
            JSONObject jsonObject=webServiceURL.PrivacyPolicy(privacyData);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            ArrayList<PrivacypolicyDatum>mPrivacy;
            super.onPostExecute(jsonObject);
            try {
                Log.e("Privacy",""+jsonObject.toString());
                Gson gson=new Gson();
                ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                if (responseClass.getSuccess()==1){
                    mPrivacy=responseClass.getResult().getPrivacypolicyData();
                    adapter=new TermConditionAdapter(getActivity(),mPrivacy,true);
                    recyclerView_privacy.setAdapter(adapter);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
