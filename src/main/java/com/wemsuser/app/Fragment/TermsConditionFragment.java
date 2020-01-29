package com.wemsuser.app.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.Adapter.TermConditionAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Response.TermsandcondiDatum;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsConditionFragment extends Fragment {


    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    TermConditionAdapter adapter;
    ArrayList<String>mTerms=new ArrayList<>();
    WebView webView;
    TextView textUpdated;


    public TermsConditionFragment() {
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
        View view= inflater.inflate(R.layout.fragment_terms_condition, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.Recycler_term);
        linearLayoutManager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        webView=view.findViewById(R.id.Web_TC);
        textUpdated = view.findViewById(R.id.Text_updated);


        if (Networkstate.isNetworkAvailable(getContext())){
            new TermsCondition().execute();
        }else {
            Toast.makeText(getActivity(),"Check Your Network Connection",Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public class TermsCondition extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            JSONObject jsonObject=webServiceURL.TermCondition(userData);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            JsonArray termCondition;
            String mtermsConditions;
            JSONObject Result;
            super.onPostExecute(jsonObject);
            Log.e("TermsCondition",""+jsonObject.toString());
            try {
                if (jsonObject!=null){
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        mtermsConditions=responseClass.getResult().getTermsandcondiData().getDescription();
                        String data = responseClass.getResult().getTermsandcondiData().getCreationDate();
                        StringTokenizer tk = new StringTokenizer(data);
                        String Current_date = tk.nextToken();
                        String time = tk.nextToken();

                        String Content=String.valueOf(Html
                                .fromHtml("<![CDATA[<body style=\"text-align:justify; \">"
                                        + mtermsConditions
                                        + "</body>]]>"));
//                                for(int i=0;i<mtermsConditions.size();i++){
//                                    String Title=mtermsConditions.get(i).getTitle();
//                                 String description=mtermsConditions.get(i).getDescription();

//                                    adapter=new TermConditionAdapter(getContext(),mtermsConditions);
//                                    recyclerView.setAdapter(adapter);
                        WebSettings webSettings = webView.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webView.loadData(Content, "text/html", "UTF-8");

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                        try {
                            Date date = format.parse(Current_date);
                            System.out.println(date);
                            String monthString  = (String) DateFormat.format("MMM",  date);
                            String year  = (String) DateFormat.format("yyyy",  date);

                            Log.e("DataMonth",""+monthString+year);
                            String Creationdata = monthString+" "+year;
                            textUpdated.setText("Updated on"+" "+Creationdata);


                            Date date1 = new Date();
                            String dateTime = format.format(monthString);
                            System.out.println("Current Date Time : " + dateTime);
                            Log.e("Month",""+dateTime);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }




//                    }




                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
