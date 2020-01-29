package com.wemsuser.app.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.Activity.LoginActivity;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.Contactreasondatum;
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
public class ContactUsFragment extends Fragment {
    Spinner spinner;
    private ArrayList<String>TopicList=new ArrayList<>();
    ArrayList<Contactreasondatum>ContactList=new ArrayList<>();
    private Button button;
    private EditText editText,editSubject;
    String User_Id,Message,Subject;
    ProgressBar progressBar;
    ImageView imageView,Banner_Ads;


    public ContactUsFragment() {
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
        View view= inflater.inflate(R.layout.fragment_contact_us, container, false);
        setHasOptionsMenu(false);

        User_Id= PreferenceUtil.getUserId(getContext());
        progressBar=view.findViewById(R.id.Progress);
        imageView=view.findViewById(R.id.shine_img1);
        Banner_Ads=view.findViewById(R.id.image_banner);
        Animation animation1 = new TranslateAnimation(0, 320, 0, 0);
        animation1.setDuration(850);
        animation1.setRepeatMode(Animation.RESTART);
        animation1.setFillAfter(false);
        animation1.setRepeatCount(1000);
        animation1.setInterpolator(new AccelerateDecelerateInterpolator());
        imageView.startAnimation(animation1);


        button=view.findViewById(R.id.Button_login);
        editText=view.findViewById(R.id.Edit_Text);
        editSubject=view.findViewById(R.id.Edit_subject);

        Banner_Ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebook= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wemsrsa.com/"));
                startActivity(facebook);
            }
        });



                button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().isEmpty()){
                    Message=editText.getText().toString().trim();
                    Subject=editSubject.getText().toString().trim();
                    if (Networkstate.isNetworkAvailable(getContext())){
                        new ContactUs().execute(User_Id,Subject,Message);
                        progressBar.setVisibility(View.VISIBLE);
                    }else {
                        Toast.makeText(getActivity(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();

                    }

                }else {
                    Toast.makeText(getActivity(),"Please fill Details",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public class ContactUsSubject extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            JSONObject jsonObject=webServiceURL.ContactSubjectList(userData);
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
                    Log.e("ContactSubject",""+jsonObject.toString());
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        ContactList=responseClass.getResult().getContactreasondata();
                        TopicList.clear();
                        TopicList.add(0,"Select Subject");
                        for (int i=0;i<ContactList.size();i++){
                            String SubjectList=ContactList.get(i).getSubject();
                            TopicList.add(SubjectList);

                            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,TopicList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);


                        }

                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class ContactUs extends AsyncTask<String,String,JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("user_id",strings[0]));
            userData.add(new BasicNameValuePair("subject",strings[1]));
            userData.add(new BasicNameValuePair("message",strings[2]));
            JSONObject jsonObject=webServiceURL.ContactUs(userData);
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
                    Log.e("ContactUsMessage",""+jsonObject.toString());
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){

                        String Token = responseClass.getResult().getUserLoginTokenData().getLoginToken();
                        String Login_token = PreferenceUtil.getAccessTokenFromServer(getContext());
                        if (Token.equals(Login_token)){
                            Toast.makeText(getActivity(),"Thank you.",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getActivity(),HomeActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                        }

                    }




                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
