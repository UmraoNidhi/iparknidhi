package com.wemsuser.app.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.Adapter.FAQAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.FaqDatum;
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
public class FAQFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FAQAdapter adapter;
    private ArrayList<String>QuestionList=new ArrayList<>();



    public FAQFragment() {
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
        View view= inflater.inflate(R.layout.fragment_faq, container, false);

        QuestionList.add("Where do u store my data");
        QuestionList.add("Where do u store my data");
        QuestionList.add("Where do u store my data");
        QuestionList.add("Where do u store my data");
        QuestionList.add("Where do u store my data");
        QuestionList.add("Where do u store my data");
        QuestionList.add("Where do u store my data");




        recyclerView=view.findViewById(R.id.Recycler_FAQ);
        recyclerView.setHasFixedSize(false);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        if (Networkstate.isNetworkAvailable(getContext())){
            new FAQ().execute();
        }else {
            Toast.makeText(getContext(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public class FAQ extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>FAQData=new ArrayList<>();
            JSONObject jsonObject=webServiceURL.FAQ(FAQData);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            ArrayList<FaqDatum>mfaq;
            super.onPostExecute(jsonObject);
            try {
                if (jsonObject!=null){
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        mfaq=responseClass.getResult().getFaqData();
                        adapter=new FAQAdapter(getActivity(),mfaq);
                        recyclerView.setAdapter(adapter);


                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
