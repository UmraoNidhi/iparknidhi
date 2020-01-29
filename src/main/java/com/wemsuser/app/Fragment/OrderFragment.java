package com.wemsuser.app.Fragment;


import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.Activity.LoginActivity;
import com.wemsuser.app.Adapter.OrderAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Response.UserOrderdatum;
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
public class OrderFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private OrderAdapter adapter;
    private ArrayList<String>Order=new ArrayList<>();
    private ArrayList<UserOrderdatum>orderList=new ArrayList<>();
    String UserId;
    private TextView textView;


    public OrderFragment() {
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
        View view= inflater.inflate(R.layout.fragment_order, container, false);

        UserId= PreferenceUtil.getUserId(getContext());
        textView=view.findViewById(R.id.Text_noData);



        recyclerView=view.findViewById(R.id.Order_recycler);
        recyclerView.setHasFixedSize(false);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        if (Networkstate.isNetworkAvailable(getContext())){
            new OrderList().execute(UserId);
        }else {
            Toast.makeText(getContext(),"Please Check your network connection",Toast.LENGTH_LONG).show();
        }

        return view;
    }


    public class OrderList extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("user_id",strings[0]));
            JSONObject jsonObject=webServiceURL.UserOrderData(userData);
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
                    Log.e("UserOrderData",""+jsonObject.toString());
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){

                        String Token = responseClass.getResult().getUserLoginTokenData().getLoginToken();
                        String Login_token = PreferenceUtil.getAccessTokenFromServer(getContext());
                        if (Token.equals(Login_token)){
                            orderList=responseClass.getResult().getUserOrderdata();
                            adapter=new OrderAdapter(getContext(),orderList);
                            recyclerView.setAdapter(adapter);
                        }else {
                           PreferenceUtil.clearPreferenceObject(getContext());
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                        }


                    }else {
                        textView.setVisibility(View.VISIBLE);
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
