package com.wemsuser.app.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.R;
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
public class AppFeedbackFragment extends Fragment {
    EditText message;
    RadioGroup radioGroup;
    RadioButton button_one,button_two,button_three,button_four,button_five;
    String Message,Selected_radio,UserName;
    int id1=1,id2=2,id3=3,id4=4,id5=5;
    Button Submit;
    ProgressBar progressBar;



    public AppFeedbackFragment() {
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
        View view = inflater.inflate(R.layout.fragment_app_feedback, container, false);
        UserName = PreferenceUtil.getUserName(getContext());

        Submit = view.findViewById(R.id.Submit_AppFeedback);
        progressBar = view.findViewById(R.id.Progress);

        radioGroup = view.findViewById(R.id.Radio_group);
        button_one = view.findViewById(R.id.Radio1);
        button_two = view.findViewById(R.id.Radio2);
        button_three = view.findViewById(R.id.Radio3);
        button_four = view.findViewById(R.id.Radio4);
        button_five = view.findViewById(R.id.Radio5);
        button_one.setId(id1);
        button_two.setId(id2);
        button_three.setId(id3);
        button_four.setId(id4);
        button_five.setId(id5);

        message = view.findViewById(R.id.Edit_message);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == id1){
                    Selected_radio = "1";

                }else if (checkedId==id2){
                    Selected_radio= "2";
                }else if (checkedId==id3){
                    Selected_radio= "3";

                }else if (checkedId == id4){
                    Selected_radio ="4";
                }else if (checkedId == id5){
                    Selected_radio ="5";
                }

            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Networkstate.isNetworkAvailable(getContext())){

                    if (message!=null){
                        Message = message.getText().toString();
                    }
                    Log.e("Details",""+Selected_radio+Message);
                    new AppFeedback().execute(UserName,Selected_radio,Message);
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(getContext(),"Check Your Network Connection",Toast.LENGTH_LONG).show();

                }

            }
        });





        return view;
    }

    public class AppFeedback extends AsyncTask<String,String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL = new WebServiceURL();
            List<NameValuePair> userdata = new ArrayList<>();
            userdata.add(new BasicNameValuePair("user_name", strings[0]));
            userdata.add(new BasicNameValuePair("user_rating", strings[1]));
            userdata.add(new BasicNameValuePair("message", strings[2]));
            JSONObject jsonObject = webServiceURL.AppFeedback(userdata);
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
            if (jsonObject != null) {
                Log.e("Feedback", "" + jsonObject.toString());
                progressBar.setVisibility(View.GONE);

                try {
                    Gson gson = new Gson();
                    ResponseClass responseClass = gson.fromJson(jsonObject.toString(), ResponseClass.class);
                    if (responseClass.getSuccess() == 1) {
                        Toast.makeText(getContext(), "Thank you", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                    } else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
