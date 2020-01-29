package com.wemsuser.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.KeyGenerationClass;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class CardDetailActivity extends AppCompatActivity {

    Button PayNow;
    TextView edit_name, edit_phone,edit_valid,edit_amount,edit_email;
    String cardNumber = "4084084084084081";
    int expiryMonth =12; //any month in the future
    int expiryYear=20 ; // any year in the future. '2018' would work also!
    String cvv = "408";
    Card card;
    String User_Email,User_Name,User_Phone,USer_amount,Valid,Date,UserId,PackageId,PackageName;
    String Amount,ValidFor,NewAmount;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        PaystackSdk.initialize(CardDetailActivity.this);

        Date= KeyGenerationClass.getDate();







        User_Email= PreferenceUtil.getUserEmail(this);
        User_Name=PreferenceUtil.getUserName(this);
        User_Phone=PreferenceUtil.getUserPhone(this);
        UserId=PreferenceUtil.getUserId(this);

        back = findViewById(R.id.Image_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        edit_name=(TextView)findViewById(R.id.Edit_CardNo);
        edit_phone=(TextView)findViewById(R.id.PhoneNo);
        edit_valid=(TextView)findViewById(R.id.Price);
        edit_amount=(TextView)findViewById(R.id.Text_Price);
        edit_email=(TextView)findViewById(R.id.Email);



        PayNow = findViewById(R.id.Button_pay);

        if (User_Email!=null&& User_Name!=null&& User_Phone!=null){
            edit_email.setText(User_Email);
            edit_phone.setText(User_Phone);
            edit_name.setText(User_Name);
        }

        try {
            Amount=getIntent().getStringExtra("amount");
            ValidFor=getIntent().getStringExtra("Valid");
            PackageId=getIntent().getStringExtra("PackageId");
            PackageName=getIntent().getStringExtra("Package_name");
//            Toast.makeText(this,"NEWAmount"+Amount,Toast.LENGTH_LONG).show();

            NewAmount = Amount+"00";
            if (Amount!=null&& ValidFor!=null){
                edit_amount.setText(Amount);
                edit_valid.setText(ValidFor);
            }
//            Toast.makeText(this,"NEWAmount"+NewAmount,Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();
        }

        Log.e("Details",""+Amount+"\n"+ValidFor);





//            try {
//                if (Edit_MM.getText().toString()!=null&&Edit_YY.getText().toString()!=null
//                        &&Edit_CVC.getText().toString()!=null&&edit_CardNo.getText().toString()!=null
//                        &&edit_bankName.getText().toString()!=null){
//                    edit_mm=Edit_MM.getText().toString().trim();
//                    Bank_name=edit_bankName.getText().toString().trim();
//                    Card_No=edit_CardNo.getText().toString().trim();
//                    edit_cvc=Edit_CVC.getText().toString().trim();
//                    edit_yy=Edit_YY.getText().toString().trim();
//
//                    Log.e("CardDetail",""+edit_mm+"\n"+edit_yy+"\n"+Bank_name+"\n"+Card_No+"\n"+edit_cvc);
//
//                    expiryMonth=Integer.parseInt(edit_mm);
//                    expiryYear=Integer.parseInt(edit_yy);
//
//                    card = new Card(Card_No, expiryMonth, expiryYear, edit_cvc);
//                }else {
//                    Log.e("Empty",""+"no data");
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }




//        if (card.isValid()) {
            PayNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    performCharge();
                }
            });
            // charge card
//        } else {
//            //do something
//        }




    }


    public static void setPaystackKey(String publicKey) {
        PaystackSdk.setPublicKey(publicKey);
    }

    private void performCharge() {
        //create a Charge object
        final int amount=Integer.parseInt(NewAmount);
        Charge charge = new Charge();
        charge.setCard(card);
        charge.setEmail(User_Email);
        charge.setAmount(amount);
//        charge.setAmount()


        PaystackSdk.chargeCard(CardDetailActivity.this,charge,new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                String paymentReference = transaction.getReference();
                new PaymentDetails().execute(UserId,PackageId,Amount,PackageName,ValidFor,Date,User_Email);
                Log.e("Success",""+transaction);
                Toast.makeText(CardDetailActivity.this, "Transaction Successful! payment reference: "
                        + paymentReference, Toast.LENGTH_LONG).show();
            }

            @Override
            public void beforeValidate(Transaction transaction) {
                Log.e("Validate",""+transaction);
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                Log.e("Error",""+transaction);
            }
        });
    }


    public class PaymentDetails extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("user_id",strings[0]));
            userData.add(new BasicNameValuePair("package_id",strings[1]));
            userData.add(new BasicNameValuePair("package_price",strings[2]));
            userData.add(new BasicNameValuePair("package_name",strings[3]));
            userData.add(new BasicNameValuePair("package_pack",strings[4]));
            userData.add(new BasicNameValuePair("order_date",strings[5]));
            userData.add(new BasicNameValuePair("user_email",strings[6]));
            JSONObject jsonObject=webServiceURL.PaymentSuccess(userData);
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
                Log.e("PaymentSuccess",""+jsonObject.toString());
                try {
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){

                        String Token = responseClass.getResult().getUserLoginTokenData().getLoginToken();
                        String Login_token = PreferenceUtil.getAccessTokenFromServer(CardDetailActivity.this);
                        if (Token.equals(Login_token)){

                        }else {
                            PreferenceUtil.clearPreferenceObject(CardDetailActivity.this);
                            Intent intent = new Intent(CardDetailActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
