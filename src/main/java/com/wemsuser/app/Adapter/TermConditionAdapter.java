package com.wemsuser.app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.PrivacypolicyDatum;
import com.wemsuser.app.Response.TermsandcondiDatum;

import java.util.ArrayList;

public class TermConditionAdapter extends RecyclerView.Adapter<TermConditionAdapter.MyTermViewHolder> {
     Context mcontext;
    private ArrayList<TermsandcondiDatum>List;
    private ArrayList<PrivacypolicyDatum>privacy;
    boolean mType=false;

    public TermConditionAdapter(Context context, ArrayList<TermsandcondiDatum> mtermsConditions) {
        this.mcontext=context;
        this.List=mtermsConditions;
    }

    public TermConditionAdapter(FragmentActivity activity, ArrayList<PrivacypolicyDatum> mPrivacy, boolean type) {
        this.mcontext=activity;
        this.privacy=mPrivacy;
        this.mType=type;
    }


    @Override
    public MyTermViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.termxml,viewGroup,false);
        MyTermViewHolder myViewHolder=new MyTermViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder( MyTermViewHolder myViewHolder, int i) {
        if (mType){
            String PrivacyTitle=privacy.get(i).getTitle();
            String P_description=privacy.get(i).getDescription();
            myViewHolder.textView.setText(PrivacyTitle);
            myViewHolder.webView.loadDataWithBaseURL(null,"<style>p{display: inline;height: auto;max-width: 100%;}</style>"+P_description
                    ,"text/html", "UTF-8",null);

        }else if (!mType){
            String Title=List.get(i).getTitle();
            String description=List.get(i).getDescription();

            myViewHolder.textView.setText(Title);
            String Content=String.valueOf(Html
                    .fromHtml("<![CDATA[<body style=\"text-align:justify; \">"
                            + description
                            + "</body>]]>"));

            String pish="<html><head><style >@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/BMitra.ttf\")}p {color:#4C4845;}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>"+description+"</body></html>";
            String pas = "</body></html>";
            String myHtmlString = pish + description + pas;
            myViewHolder.webView.loadDataWithBaseURL(null,Content, "text/html", "UTF-8", null);
//            myViewHolder.webView.loadDataWithBaseURL(null,"<style>p{display: inline;height: auto;max-width: 100%;}</style>"+description
//                    ,"text/html", "UTF-8",null);

        }


    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (mType) {
            size = privacy.size();
        } else if (!mType){
            size = List.size();
        }
        return size;
    }

        public class MyTermViewHolder extends RecyclerView.ViewHolder {
            WebView webView;
            TextView textView;

            public MyTermViewHolder( View itemView) {
                super(itemView);


                webView = (WebView) itemView.findViewById(R.id.WebView);
                textView = (TextView) itemView.findViewById(R.id.Text_title);


            }
        }

}
