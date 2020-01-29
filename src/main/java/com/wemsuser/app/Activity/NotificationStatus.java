package com.wemsuser.app.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.WebServiceURL;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationStatus extends AppCompatActivity {
    TextView text_title,text_message,date;
    String Date,title,Message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_status);

        text_title=findViewById(R.id.Text_heading);
        text_message=findViewById(R.id.Text_description);
        date=findViewById(R.id.Text_Date);
        try {
            Date=getIntent().getStringExtra("Time");
            title=getIntent().getStringExtra("Title");
            Message=getIntent().getStringExtra("Message");

            if (Date!=null&&title!=null&&Message!=null){
                text_title.setText(title);
                text_message.setText(Message);
                date.setText(Date);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public class Notification extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("",strings[0]));
            userData.add(new BasicNameValuePair("",strings[1]));
            JSONObject jsonObject=webServiceURL.NotificationData(userData);
            return jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (jsonObject!=null){
                Log.e("NotificationStatus",""+jsonObject.toString());
                try {
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        String Status=responseClass.getResult().getReadStatus();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }



}
