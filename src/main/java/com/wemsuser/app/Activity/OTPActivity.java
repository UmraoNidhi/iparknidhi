package com.wemsuser.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import co.paystack.android.ui.OtpActivity;

public class OTPActivity extends AppCompatActivity  {
    private LinearLayout linearLayout;
    private EditText edit1, edit2, edit3, edit4, edit5;
    private Button button;
    EditText edit_code,edit_password,edit_confirm;
    ImageView imageView;
    String Password,Confirm_password,Code;
    TextView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        button=findViewById(R.id.Button_Reset);
        back_button = findViewById(R.id.Text_back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        linearLayout = findViewById(R.id.Linear_OTP);
        edit1 = findViewById(R.id.Edit_one);
        edit2 = findViewById(R.id.Edit_two);
        edit3 = findViewById(R.id.Edit_three);
        edit4 = findViewById(R.id.Edit_four);
        edit5 = findViewById(R.id.Edit_five);
        imageView=findViewById(R.id.shine_img1);

        edit_code=findViewById(R.id.Edit_code);
        edit_password=findViewById(R.id.Edit_password);
        edit_confirm=findViewById(R.id.Edit_Confirm);


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
                if (edit_code.getText().toString()!=null&&edit_password.getText().toString()!=null
                        &&edit_confirm.getText().toString()!=null){
                    Code=edit_code.getText().toString().trim();
                    Password=edit_password.getText().toString().trim();
                    Confirm_password=edit_confirm.getText().toString().trim();
                    if (Password.equals(Confirm_password)){
                       new ResetPassword().execute(Code,Confirm_password);
                    }else {
                        Toast.makeText(OTPActivity.this,"Password and Confirm Password doesn't match",Toast.LENGTH_LONG).show();
                    }
                }


//
//                String Edit_one=edit1.getText().toString().trim();
//                String Edit_two=edit2.getText().toString().trim();
//                String Edit_three=edit3.getText().toString().trim();
//                String Edit_four=edit4.getText().toString().trim();
//                String Edit_five=edit5.getText().toString().trim();
//                if (!Edit_one.isEmpty()&&!Edit_two.isEmpty()&&!Edit_three.isEmpty()&&!Edit_four.isEmpty()&&!Edit_five.isEmpty()){
//                    Intent intent=new Intent(OTPActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                }else {
//                    Toast.makeText(OTPActivity.this,"Please Enter OTP",Toast.LENGTH_LONG).show();
//                }

            }
        });

    }



    public class ResetPassword extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("otp",strings[0]));
            userData.add(new BasicNameValuePair("password",strings[1]));
            JSONObject jsonObject=webServiceURL.ResetPassword(userData);
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
                Log.e("ResetPassword",""+jsonObject.toString());
                try {
                    String Message=jsonObject.getString("message");
                    int Success=jsonObject.getInt("success");
                    if (Success==1){
                        Intent intent=new Intent(OTPActivity.this,LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(OTPActivity.this,Message,Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(OTPActivity.this,Message,Toast.LENGTH_LONG).show();
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
