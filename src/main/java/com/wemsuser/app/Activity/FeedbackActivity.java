package com.wemsuser.app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wemsuser.app.Adapter.FeedbackAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.Feedbackquestiondatum;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity implements FeedbackAdapter.SelectedQuestionList {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FeedbackAdapter adapter;
    private ArrayList<String>feedbacklist=new ArrayList<>();
    private ArrayList<Feedbackquestiondatum>questionList=new ArrayList<>();
    ImageView imageView,image_animation;
    String UserId,MerchantId,ServiceTypeId,FeedbackRating,Feedback_Q_Id;
    private LinearLayout Sad,Happy,Satisfied;
    private Button Send_feeback;
    String Questionone,Selected_Q_Id;
    HashMap<String,String> QuestionData=new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        UserId= PreferenceUtil.getUserId(this);
        MerchantId=PreferenceUtil.getMerchant_Id(this);
        ServiceTypeId=PreferenceUtil.getServiceId(this);
        Sad=findViewById(R.id.Linear_sad);
        Happy=findViewById(R.id.Linear_happy);
        Satisfied=findViewById(R.id.Linear_satisfied);
        Send_feeback=findViewById(R.id.Button_feedback);

        Sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundcolor(Sad,Happy,Satisfied);
                FeedbackRating="Unhappy";
                Log.e("Rating",""+FeedbackRating);
            }
        });
        Happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundcolor(Happy,Sad,Satisfied);
                FeedbackRating="Neutral";
                Log.e("Rating",""+FeedbackRating);


            }
        });
        Satisfied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundcolor(Satisfied,Happy,Sad);
                FeedbackRating="Satisfied";
                Log.e("Rating",""+FeedbackRating);


            }
        });

        Send_feeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendFeedback().execute(UserId,MerchantId,ServiceTypeId,Selected_Q_Id,FeedbackRating);

            }
        });


        imageView=findViewById(R.id.Imageback);
        image_animation=findViewById(R.id.shine_img1);
        Animation animation1 = new TranslateAnimation(0, 320, 0, 0);
        animation1.setDuration(850);
        animation1.setRepeatMode(Animation.RESTART);
        animation1.setFillAfter(false);
        animation1.setRepeatCount(1000);
        animation1.setInterpolator(new AccelerateDecelerateInterpolator());
        image_animation.startAnimation(animation1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (Networkstate.isNetworkAvailable(this)){
            new FeedbackQuestionList().execute();
        }else {
            Toast.makeText(this,"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }



        recyclerView=findViewById(R.id.Recycler_feedback);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);



    }

    private void setBackgroundcolor(LinearLayout Selected,LinearLayout Unselected,LinearLayout Unselected1){
        Selected.setBackgroundColor(Color.parseColor("#FFC107"));
        Unselected.setBackgroundColor(Color.parseColor("#ffffff"));
        Unselected1.setBackgroundColor(Color.parseColor("#ffffff"));

    }

    @Override
    public void questionId(HashMap<String, String> questionId) {
        QuestionData=questionId;
        List<String> ValueList= new ArrayList<>(QuestionData.values());
        for (int i=0;i<ValueList.size();i++){
//            String Q_id=QuestionData.get(keyList.get(i));

            StringBuilder sb=new StringBuilder();

            String saprate="";
            for (String str:ValueList){
                sb.append(saprate);
                saprate=",";
                sb.append(str);
            }
            Selected_Q_Id=sb.toString();
            Log.e("QUESTION",""+questionId+"\n"+"\n"+Selected_Q_Id);

        }

        Questionone=questionId.values().toString();







    }

    public class FeedbackQuestionList extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            JSONObject jsonObject=webServiceURL.feedbackQuestionList(userData);
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
                    Log.e("FeedbackQuestion",""+jsonObject.toString());
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        questionList=responseClass.getResult().getFeedbackquestiondata();
                        adapter=new FeedbackAdapter(FeedbackActivity.this,questionList,FeedbackActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class SendFeedback extends AsyncTask<String,String,JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("user_id",strings[0]));
            userData.add(new BasicNameValuePair("merchant_id",strings[1]));
            userData.add(new BasicNameValuePair("service_type_id",strings[2]));
            userData.add(new BasicNameValuePair("feedback_question_id",strings[3]));
            userData.add(new BasicNameValuePair("feedback",strings[4]));
            JSONObject jsonObject=webServiceURL.SendFeedback(userData);
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
                    Log.e("SendFeedback",""+jsonObject.toString());
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        String Token = responseClass.getResult().getUserLoginTokenData().getLoginToken();
                        String Login_token = PreferenceUtil.getAccessTokenFromServer(FeedbackActivity.this);
                        if (Token.equals(Login_token)){
                            Toast.makeText(FeedbackActivity.this,"Feedback Submitted Successfully",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(FeedbackActivity.this,DetailsActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }else {
                            PreferenceUtil.clearPreferenceObject(FeedbackActivity.this);
                            Intent intent = new Intent(FeedbackActivity.this,LoginActivity.class);
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
