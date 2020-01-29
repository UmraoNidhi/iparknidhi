package com.wemsuser.app.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.wemsuser.app.Adapter.HelpYouAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Response.ServiceDatum;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HelpyouActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public ImageView imageView,Banner_Ads;
    private LinearLayoutManager linearLayoutManager;
    private HelpYouAdapter adapter;
    private TextView Car_No;
    private  Integer image_List[]={R.drawable.mechanic,R.drawable.carbodywork,R.drawable.tow,R.drawable.lockout,R.drawable.fueldelivery,
            R.drawable.tire_change, R.drawable.jumper_cable,R.drawable.jump_start};
    private LinearLayout linearLayout,linear_help;
    FrameLayout frameLayout;
    SpinKitView progressBar;
    String Token;
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpyou);

        button = findViewById(R.id.Button_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpyouActivity.this,Testpagination.class);
                startActivity(intent);
            }
        });

//        try {
//            String Back=getIntent().getStringExtra("Back");
//            if (Back.equalsIgnoreCase("Remove")){
//               imageView.setVisibility(View.GONE);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        Token = FirebaseInstanceId.getInstance().getToken();
        Log.e("TokenId", "" + Token);

//        String Model_No=getIntent().getStringExtra("Model");
        String Name= PreferenceUtil.getUserName(HelpyouActivity.this);


        Car_No=findViewById(R.id.Text_carNo);
        Car_No.setText("Welcome"+" "+toCamelCase(Name));

        linearLayout=findViewById(R.id.Linear_Network);
        frameLayout=findViewById(R.id.Frame_progress);
        progressBar=findViewById(R.id.spin_kit);
        linear_help=findViewById(R.id.Linear_Help);
        imageView=findViewById(R.id.Image_help);
        Banner_Ads=findViewById(R.id.image_banner);
        Banner_Ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebook= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wemsrsa.com/"));
                startActivity(facebook);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onBackPressed();
            }
        });




        recyclerView=findViewById(R.id.Recycler_help);
        recyclerView.setHasFixedSize(false);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (Networkstate.isNetworkAvailable(this)){
            linear_help.setVisibility(View.VISIBLE);
            new ServiceList().execute();
        }else {
            linear_help.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
//            Toast.makeText(this,"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        openLogoutPopup();
//        Intent intent=new Intent(HelpyouActivity.this,LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);

//        Intent intent=new Intent(Intent.ACTION_MAIN);
//          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);


//        super.onBackPressed();
    }

    public class ServiceList extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>Service=new ArrayList<>();
            JSONObject jsonObject=webServiceURL.ServiceList(Service);
            return jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Sprite circle = new Circle();
            progressBar.setIndeterminateDrawable(circle);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            ArrayList<ServiceDatum>mService;
            super.onPostExecute(jsonObject);
            frameLayout.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            try {
                Log.e("ServiceList",""+jsonObject.toString());
                if (jsonObject!=null){
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        mService=responseClass.getResult().getServiceData();
                        adapter=new HelpYouAdapter(HelpyouActivity.this,mService);
                        recyclerView.setAdapter(adapter);

                    }

                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void openLogoutPopup() {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.logoutxml);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView text_yes=dialog.findViewById(R.id.Text_yes);
        TextView text_No=dialog.findViewById(R.id.Text_no);
        text_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.clearPreferenceObject(HelpyouActivity.this);
                dialog.dismiss();
                Intent intent=new Intent(HelpyouActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        text_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public static String toCamelCase(final String init) {
        if (init==null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length()==init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }

}
