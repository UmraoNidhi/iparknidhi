package com.wemsuser.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.wemsuser.app.BuildConfig;
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

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class LoginActivity extends AppCompatActivity {

    private TextView signUp,forgetPassword,Version_name;
    private Button login;
    private TextInputEditText Edit_UserName,Edit_Password;
    String Username,password,PackageId,Package_EndDate;
    private CheckBox checkBox;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private LocationTracker locationTracker;
    double latitude,longitude;
    String DeviceId,Latitude,Longitude, Dataintent="",refreshedToken;
    Boolean SaveLogin=false;
    ImageView imageView;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("DeviceId",""+refreshedToken);

        try {
            if (getIntent().hasExtra("Registration")){
                Dataintent=getIntent().getStringExtra("Registration");
                Log.e("NewRegistration",""+Dataintent);

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        imageView=findViewById(R.id.shine_img1);
        progressBar=findViewById(R.id.Progress_login);
        Version_name = findViewById(R.id.Version_name);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            Version_name.setText(version);
            Log.e("VersionName",""+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



        Animation animation1 = new TranslateAnimation(0, 320, 0, 0);
        animation1.setDuration(850);
        animation1.setRepeatMode(Animation.RESTART);
        animation1.setFillAfter(false);
        animation1.setRepeatCount(1000);
        animation1.setInterpolator(new AccelerateDecelerateInterpolator());
        imageView.startAnimation(animation1);




        locationTracker=new LocationTracker(this);
        latitude=locationTracker.getLatitude();
        longitude=locationTracker.getLongitude();
        Latitude=Double.toString(latitude);
        Longitude=Double.toString(longitude);
        if (Latitude.equals(0.0)&& Longitude.equals(0.0)){
            latitude=locationTracker.getLatitude();
            longitude=locationTracker.getLongitude();
            String Latitude=Double.toString(latitude);
            String Longitude=Double.toString(longitude);
            PreferenceUtil.setUserLatitude(LoginActivity.this,Latitude);
            PreferenceUtil.setUserLongitude(LoginActivity.this,Longitude);
            Log.e("Location",""+"Latitude"+latitude+"\nLongitude"+longitude);
            Log.e("LocationString",""+"Latitude"+Latitude+"\nLongitude"+Longitude);

        }else {
            PreferenceUtil.setUserLatitude(LoginActivity.this,Latitude);
            PreferenceUtil.setUserLongitude(LoginActivity.this,Longitude);
            Log.e("Location",""+"Latitude"+latitude+"\nLongitude"+longitude);
            Log.e("LocationString",""+"Latitude"+Latitude+"\nLongitude"+Longitude);

        }








        Edit_UserName=findViewById(R.id.Edit_Username);
        Edit_Password=findViewById(R.id.Edit_Password);
        requestPermission();


        checkBox=findViewById(R.id.Checkbox);
        SaveLogin=PreferenceUtil.getSaveLoginFlag(this);



        signUp=findViewById(R.id.SignUp);
        signUp.setText(Html.fromHtml("Not a member yet ?"+"<b>"+"Join Now"+"</b>"));
        forgetPassword=findViewById(R.id.Forget_password);
        login=findViewById(R.id.Button_login);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username=Edit_UserName.getText().toString().trim();
                password=Edit_Password.getText().toString().trim();
                PreferenceUtil.setUserPassword(LoginActivity.this,password);

                try {
//                    if (Username.length()==10){
                        if (!Username.isEmpty()&&!password.isEmpty()){
                            if (Networkstate.isNetworkAvailable(LoginActivity.this)){
                                progressBar.setVisibility(View.VISIBLE);
                                new UserLogin().execute(Username,password,refreshedToken);

                            }else {
                                Toast.makeText(LoginActivity.this,"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(LoginActivity.this,"Please Fill Details",Toast.LENGTH_LONG).show();
                        }
//                    }else {
//                        Toast.makeText(LoginActivity.this,"Please Enter a valid No",Toast.LENGTH_LONG).show();
//                    }

                }catch (Exception e){
                    e.printStackTrace();
                }





            }
        });
    }

    private void showPassword() {
        String Name=PreferenceUtil.getUserPhone(this);
        String Password=PreferenceUtil.getUserPassword(this);
            Edit_UserName.setText(Name);
            Edit_Password.setText(Password);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted && cameraAccepted) {
//                        Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    }
                    else {
//                        Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public class UserLogin extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userdata=new ArrayList<>();
            userdata.add(new BasicNameValuePair("loginnumber",strings[0]));
            userdata.add(new BasicNameValuePair("loginpassword",strings[1]));
            userdata.add(new BasicNameValuePair("deviceId",strings[2]));
            JSONObject jsonObject=webServiceURL.userLogin(userdata);
            return jsonObject;


        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            JSONObject result;
            super.onPostExecute(jsonObject);
            progressBar.setVisibility(View.GONE);
            try {

                Log.e("LoginResponce",""+jsonObject.toString());
                if (jsonObject!=null){
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        result=jsonObject.optJSONObject("result");
                        String VehicleData=responseClass.getResult().getVehicleData();
                        Log.e("VehicleResponce",""+VehicleData);
                        String UserName=responseClass.getResult().getUserData().getUserName();
                        String UserPhone=responseClass.getResult().getUserData().getUserPhone();
                        String UserEmail=responseClass.getResult().getUserData().getUserEmail();
                        String UserImage=responseClass.getResult().getUserData().getUserImage();
                        String UserId=responseClass.getResult().getUserData().getUserId();
                        PackageId=responseClass.getResult().getUserData().getPackageId();
                        String PackageEndDate=responseClass.getResult().getUserData().getPackageEnd();
                        String Login_token = responseClass.getResult().getUserData().getLoginToken();

                        if (checkBox.isChecked()){
                            PreferenceUtil.setSaveLoginFlag(LoginActivity.this,true);
                        }else {
                            PreferenceUtil.setSaveLoginFlag(LoginActivity.this,false);
                        }
                        String new_Image = UserImage.replace("/thumb","");
                        PreferenceUtil.setPackage_Expire(LoginActivity.this,PackageEndDate);
                        PreferenceUtil.setPackage_Id(LoginActivity.this,PackageId);
                        PreferenceUtil.setUserName(LoginActivity.this,UserName);
                        PreferenceUtil.setUserEmail(LoginActivity.this,UserEmail);
                        PreferenceUtil.setUserPhone(LoginActivity.this,UserPhone);
                        PreferenceUtil.setUserId(LoginActivity.this,UserId);
                        PreferenceUtil.setUserImage(LoginActivity.this,new_Image);
                        PreferenceUtil.setAccessTokenFromServer(LoginActivity.this,Login_token);

                        if (VehicleData.equalsIgnoreCase("Y")){
                            Intent login=new Intent(LoginActivity.this,HelpyouActivity.class);
                            startActivity(login);

                        }else{
                            Intent login=new Intent(LoginActivity.this,AddVehicleActivity.class);
                            startActivity(login);
                        }

                    }
                    else {
                        Toast.makeText(LoginActivity.this,responseClass.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PreferenceUtil.getSaveLoginFlag(LoginActivity.this)){
            showPassword();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();

    }
}
