package com.wemsuser.app.Services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.wemsuser.app.Activity.LoginActivity;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginToken extends AsyncTask<String,String, JSONObject> {
    String Token,Login_Token;
    Context context;
    String UserId;
    String LoginToken;

    public LoginToken(Context context, String userId,String logintoken) {
        this.context = context;
        UserId = userId;
        LoginToken = logintoken;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        WebServiceURL webServiceURL = new WebServiceURL();
        List<NameValuePair>userData = new ArrayList<>();
        userData.add(new BasicNameValuePair("user_id",UserId));
        JSONObject jsonObject = webServiceURL.LoginToken(userData);
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
            Log.e("LoginToken",""+jsonObject.toString());
            try {
                Gson gson = new Gson();
                ResponseClass responseClass = gson.fromJson(jsonObject.toString(),ResponseClass.class);
                if (responseClass.getSuccess()==1){
                    Token = responseClass.getResult().getUserLoginTokenData().getLoginToken();
                    Login_Token = PreferenceUtil.getAccessTokenFromServer(context);
                    Log.e("Shared_Token",""+Login_Token);

                    if (Token.equals(LoginToken)){

                    }else {
                       PreferenceUtil.clearPreferenceObject(context);
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);


                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
