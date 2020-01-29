package com.wemsuser.app.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.AboutDatum;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {

    TextView textView,text_title;
    WebView webView;
    ImageView imageView;
    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivity.mMenu.findItem(R.id.action_Map).setVisible(false);
        HomeActivity.mMenu.findItem(R.id.action_List).setVisible(false);
        HomeActivity.mMenu.findItem(R.id.Refresh).setVisible(false);
        HomeActivity.fab.findViewById(R.id.fab_button).setVisibility(View.GONE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about_us, container, false);
        setHasOptionsMenu(false);

        webView=(WebView)view.findViewById(R.id.Web_about);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.loadUrl("https://www.wemsrsa.com/api/webView/AboutUS");
        webView.setWebChromeClient(new WebChromeClient());

//        webView.clearCache(true);
//        webView.clearHistory();






        if (Networkstate.isNetworkAvailable(getContext())){
//            new AboutData().execute();
        }else {
            Toast.makeText(getContext(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }


        String Text="Objective of"+"\n"+" WemsRSA is"+"\n"+" to help travelers";



        return view;
    }



    public class AboutData extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>mabout=new ArrayList<>();
            JSONObject jsonObject=webServiceURL.AboutUs(mabout);
            return jsonObject ;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            ArrayList<AboutDatum>about;
            super.onPostExecute(jsonObject);
            try {
                if (jsonObject!=null){
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        about=responseClass.getResult().getAboutData();
                        for (int i=0;i<about.size();i++){
                            String description=about.get(i).getFullDescription();
                            String title=about.get(i).getBannerTitle();
                            String image=about.get(i).getBannerImage();
                            String Sub_title=about.get(i).getBannerSubTitle();

                            Glide.with(getActivity()).load(image).into(imageView);
//                            String pish="<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/BMitra.ttf\")}p {color:#808080;}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>"+discription+"</body></html>";
                            String pish="<html><head><style >@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/BMitra.ttf\")}p {color:#4C4845;}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>"+description+"</body></html>";




//                            String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/BMitra.ttf\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>";
                            String pas = "</body></html>";
                            String myHtmlString = pish + description + pas;


//                            webView.loadDataWithBaseURL(null,myHtmlString, "text/html", "UTF-8", null);
                            webView.loadUrl("http://rsa.algosoftech.in/api/webView/AboutUS");
//
                            textView.setText(Sub_title);
                            text_title.setText(title);

                        }

                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
