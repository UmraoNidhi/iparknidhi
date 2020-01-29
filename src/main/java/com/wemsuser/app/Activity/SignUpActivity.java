package com.wemsuser.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private TextView textView,term_condition;
    private TextInputEditText Edit_name,Edit_Password,Edit_confirm,Edit_email,Edit_Phone,Edit_OTP;
    String User_Name,User_Email,User_Phone,User_Password,User_confirm,User_OTP;
    private Button button;
    String DeviceId;
    ImageView imageView;
    ProgressBar progressBar;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);




        button=findViewById(R.id.Button_login);
        checkBox = findViewById(R.id.CheckBox);
        progressBar=findViewById(R.id.Progress_signIn);
        imageView=findViewById(R.id.shine_img1);
        term_condition = findViewById(R.id.Term_condition);
        term_condition.setClickable(true);
        Animation animation1 = new TranslateAnimation(0, 320, 0, 0);
        animation1.setDuration(850);
        animation1.setRepeatMode(Animation.RESTART);
        animation1.setFillAfter(false);
        animation1.setRepeatCount(1000);
        animation1.setInterpolator(new AccelerateDecelerateInterpolator());
        imageView.startAnimation(animation1);



        Edit_name=findViewById(R.id.Edit_Username);
        Edit_email=findViewById(R.id.Edit_Email);
        Edit_Password=findViewById(R.id.Edit_Password);
        Edit_Phone=findViewById(R.id.Edit_Phone);
        Edit_confirm=findViewById(R.id.Edit_Confirm);
        Edit_OTP=findViewById(R.id.Edit_OTP);

        term_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,TermsConditions.class);
                startActivity(intent);
            }
        });






        textView=findViewById(R.id.Text_signUp);
        textView.setText(Html.fromHtml("Have an account ?"+"<b>"+"Login"+"</b>"));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Name=Edit_name.getText().toString().trim();
                User_Phone=Edit_Phone.getText().toString().trim();
                User_Password=Edit_Password.getText().toString().trim();
                User_confirm=Edit_confirm.getText().toString().trim();
                User_OTP=Edit_OTP.getText().toString().trim();


                if (!User_Name.isEmpty()&&!User_Password.isEmpty()&&
                        !User_Phone.isEmpty()&&!User_confirm.isEmpty()){
                    if (User_Phone.length()<12){
                        if (User_Password.equals(User_confirm)){
                            User_Email = Edit_email.getText().toString().trim();
                            if (User_Email!=null) {
                                if (User_Email.matches(emailPattern)){
                                    if (Networkstate.isNetworkAvailable(SignUpActivity.this)){
                                        progressBar.setVisibility(View.VISIBLE);
                                        if (checkBox.isChecked()==true){
                                            new UserRegistration().execute(User_Name,User_Phone,User_Password,DeviceId,User_Email);

                                        }else {
                                            Toast.makeText(SignUpActivity.this,"Please accept terms & conditions",Toast.LENGTH_LONG).show();

                                        }




                                    }else {
                                        Toast.makeText(SignUpActivity.this,"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    Toast.makeText(SignUpActivity.this,"Please Enter Valid Email Address",Toast.LENGTH_LONG).show();

                                }


                            }


                        }else {
                            Toast.makeText(SignUpActivity.this,"Password and Confirm Password should be same",Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(SignUpActivity.this,"Please Enter Valid Phone Number",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(SignUpActivity.this,"Please Fill All Details",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }



    public class UserRegistration extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("fullname",strings[0]));
            userData.add(new BasicNameValuePair("phonenumber",strings[1]));
            userData.add(new BasicNameValuePair("userpassword",strings[2]));
            userData.add(new BasicNameValuePair("deviceId",strings[3]));
            userData.add(new BasicNameValuePair("email",strings[4]));
            JSONObject jsonObject=webServiceURL.userRegistration(userData);
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
            Log.e("UserRagistration",""+jsonObject);
            try {
                if (jsonObject!=null){
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                        intent.putExtra("Registration","1");
                        startActivity(intent);
                        Toast.makeText(SignUpActivity.this,responseClass.getMessage(),Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(SignUpActivity.this,responseClass.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
