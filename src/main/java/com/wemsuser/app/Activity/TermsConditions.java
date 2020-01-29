package com.wemsuser.app.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.wemsuser.app.Fragment.TermsConditionFragment;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class TermsConditions extends AppCompatActivity {
    WebView webView;
    public static TextView textviewTitle;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);
//        textviewTitle = (TextView) findViewById(R.id.actionbar_textview);
//        textviewTitle.setText("Terms &amp Conditions");

        webView = findViewById(R.id.Web_term);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        webView.loadUrl("https://www.wemsrsa.com/terms-conditions");

        if (Networkstate.isNetworkAvailable(this)){
            new TermsCondition().execute();
        }else {
            Toast.makeText(this,"Check Your Network Connection",Toast.LENGTH_LONG).show();
        }

        back = findViewById(R.id.Image_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



    public class TermsCondition extends AsyncTask<String,String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair> userData=new ArrayList<>();
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
//                            textUpdated.setText("Updated on"+" "+Creationdata);


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
