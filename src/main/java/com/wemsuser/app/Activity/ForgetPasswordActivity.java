package com.wemsuser.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.WebServiceURL;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Button button;
    ImageView imageView;
    TextInputEditText edit_phone;
    ProgressBar progressBar;
    String Email;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView back_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        button=findViewById(R.id.Button_submit);
        imageView=findViewById(R.id.shine_img1);
        edit_phone=findViewById(R.id.Edit_phone_No);
        progressBar=findViewById(R.id.Progress_bar);
        back_button = findViewById(R.id.Text_back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });



        Animation animation1 = new TranslateAnimation(0, 320, 0, 0);
        animation1.setDuration(850);
        animation1.setRepeatMode(Animation.RESTART);
        animation1.setFillAfter(false);
        animation1.setRepeatCount(1000);
        animation1.setInterpolator(new AccelerateDecelerateInterpolator());
        imageView.startAnimation(animation1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Phone_Number=edit_phone.getText().toString().trim();
                if (!Phone_Number.isEmpty()){
                    if (Phone_Number.length()<12){
                        new Forgetpassword().execute(Phone_Number);

                    }else {
                        Toast.makeText(ForgetPasswordActivity.this,"Please Enter Valid Mobile Number",Toast.LENGTH_LONG).show();

                    }

                }else {
                    Toast.makeText(ForgetPasswordActivity.this,"Please Enter Your Mobile Number",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    public class Forgetpassword extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("phonenumber",strings[0]));
            JSONObject jsonObject=webServiceURL.ForgetPassword(userData);
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
                Log.e("ForgetPassword",""+jsonObject.toString());
                try {
                    String message=jsonObject.getString("message").toString();
                    int Success=jsonObject.getInt("success");
                    if (Success==1){
                        Intent intent=new Intent(ForgetPasswordActivity.this,OTPActivity.class);
                        startActivity(intent);
                        Toast.makeText(ForgetPasswordActivity.this,message,Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(ForgetPasswordActivity.this,message,Toast.LENGTH_LONG).show();
                    }

//                    Gson gson=new Gson();
//                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
//                    if (responseClass.getSuccess()==1){
//
//                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}
