package com.wemsuser.app.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.Activity.LoginActivity;
import com.wemsuser.app.Adapter.NotificationAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.NotoificationDatum;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private NotificationAdapter adapter;
    private ArrayList<String>mList=new ArrayList<>();
    ArrayList<NotoificationDatum>NotificationList=new ArrayList<>();
    String Phone;
    TextView textView_hint;
    ProgressBar progressBar;



    public NotificationFragment() {
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
        View view= inflater.inflate(R.layout.fragment_notification, container, false);

        Phone= PreferenceUtil.getUserPhone(getContext());

        textView_hint=view.findViewById(R.id.Text_Hint);
        progressBar=view.findViewById(R.id.Progress);



        recyclerView= view.findViewById(R.id.Recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        if (Networkstate.isNetworkAvailable(getContext())){
            new NotificationList().execute(Phone,"Customer");
        }else {
            Toast.makeText(getContext(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public class NotificationList extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("user_phone",strings[0]));
            userData.add(new BasicNameValuePair("user_type",strings[1]));
            JSONObject jsonObject=webServiceURL.NotificationList(userData);
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
            if (jsonObject!=null){
                Log.e("NotificationList",""+jsonObject.toString());
                try {
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        String Token = responseClass.getResult().getUserLoginTokenData().getLoginToken();
                        String Login_token = PreferenceUtil.getAccessTokenFromServer(getContext());
                        if (Token.equals(Login_token)){
                            NotificationList=responseClass.getResult().getNotoificationData();
                            adapter=new NotificationAdapter((FragmentActivity) getContext(),NotificationList);
                            recyclerView.setAdapter(adapter);
                        }else {
                            PreferenceUtil.clearPreferenceObject(getContext());
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                        }

                    }else {
                        textView_hint.setVisibility(View.VISIBLE);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new NotificationList().execute(Phone,"Customer");
    }
}
