package com.wemsuser.app.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wemsuser.app.Activity.CardDetailActivity;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.Subscriptiondatum;
import com.wemsuser.app.utility.PreferenceUtil;

import java.util.ArrayList;
import java.util.Collections;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.MyViewHolder> {
    private ArrayList<Subscriptiondatum>subscription;
    private Context context;
    Dialog dialog;
    Boolean Basic;
    String PackageID;
    public SubscriptionAdapter(Context context, ArrayList<Subscriptiondatum> msubscription) {
        this.context=context;
        this.subscription=msubscription;
    }


    @Override
    public SubscriptionAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subscriptionxml,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(SubscriptionAdapter.MyViewHolder myViewHolder, int i) {
//        Collections.reverse(subscription);
//        if (i+1<4){


        PackageID= PreferenceUtil.getPackage_Id(context);



            myViewHolder.textTitle.setText(subscription.get(i).getPackageName());
            String URL=subscription.get(i).getDescription();
            Log.e("Subscription",""+URL);
            myViewHolder.description.loadData(URL,"text/html","UTF-8");
            myViewHolder.text_Month.setText("/"+subscription.get(i).getPackagePack());
            myViewHolder.text_price.setText("=N="+" "+subscription.get(i).getPackagePrice());

            if (true){
                myViewHolder.textTitle.setText(subscription.get(i).getPackageName());
                String URL1=subscription.get(i).getDescription();
                Log.e("Subscription",""+URL);
                myViewHolder.description.loadData(URL1,"text/html","UTF-8");
                myViewHolder.text_Month.setText("/"+subscription.get(i).getPackagePack());
                myViewHolder.text_price.setText("=N="+" "+subscription.get(i).getPackagePrice());

            }

        if (i==0){
            myViewHolder.frameLayout.setBackgroundResource(R.drawable.colormonthly);
            myViewHolder.TextBasic.setText("Premium");
            myViewHolder.TextBasic.setVisibility(View.VISIBLE);
            if (PackageID!=null){
                if (PackageID.equals("1000000002"))
                    myViewHolder.button.setText("Activated");
            }
        }else if (i==1){
            myViewHolder.frameLayout.setBackgroundResource(R.drawable.colormonthly);
            myViewHolder.TextBasic.setText("Premium");
            myViewHolder.TextBasic.setVisibility(View.VISIBLE);
            if (PackageID!=null){
                if (PackageID.equals("1000000004"))
                    myViewHolder.button.setText("Activated");
            }
        }else if (i==2){
            myViewHolder.frameLayout.setBackgroundResource(R.drawable.colormonthly);
            myViewHolder.TextBasic.setText("Premium");
            myViewHolder.TextBasic.setVisibility(View.VISIBLE);
            if (PackageID!=null){
                if (PackageID.equals("1000000003"))
                myViewHolder.button.setText("Activated");
            }
        }else if (i==3){
            myViewHolder.frameLayout.setBackgroundResource(R.drawable.colorfee);
            myViewHolder.TextBasic.setVisibility(View.VISIBLE);
            myViewHolder.button.setText("Free");
            myViewHolder.text_price.setVisibility(View.GONE);
            myViewHolder.text_Month.setVisibility(View.GONE);
            myViewHolder.TextSymbol.setVisibility(View.GONE);



        }




    }
    @Override
    public int getItemCount() {
        return subscription.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle,text_price,text_Month,TextBasic,TextSymbol;
        WebView description;
        ImageView imageView,background;
        Button button;
        FrameLayout frameLayout;

        public MyViewHolder( View itemView) {
            super(itemView);

            description=itemView.findViewById(R.id.webView);
            textTitle=itemView.findViewById(R.id.Text_title);
            text_price=itemView.findViewById(R.id.Text_price);
            text_Month=itemView.findViewById(R.id.Text_month);
            imageView=itemView.findViewById(R.id.shine_img1);
            button=itemView.findViewById(R.id.BuyNow);
            background=itemView.findViewById(R.id.Image_text);
            frameLayout=itemView.findViewById(R.id.Frame);
            TextBasic=itemView.findViewById(R.id.Text_basic);
            TextSymbol=itemView.findViewById(R.id.Symbol);





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


                    if (getLayoutPosition()==3){
                        button.setClickable(false);
                    }else if (getLayoutPosition()==0){
                        Intent intent=new Intent(context, CardDetailActivity.class);
                        intent.putExtra("amount","100");
                        intent.putExtra("Valid","1month");
                        intent.putExtra("PackageId","1000000002");
                        intent.putExtra("Package_name","Monthly");
                        context.startActivity(intent);

                    }else if(getLayoutPosition()==1){
                        Intent intent=new Intent(context, CardDetailActivity.class);
                        intent.putExtra("amount","500");
                        intent.putExtra("Valid","6month");
                        intent.putExtra("PackageId","1000000004");
                        intent.putExtra("Package_name","6 Months");
                        context.startActivity(intent);
                    }else if(getLayoutPosition()==2){
                        Intent intent=new Intent(context, CardDetailActivity.class);
                        intent.putExtra("amount","1000");
                        intent.putExtra("Valid","1year");
                        intent.putExtra("PackageId","1000000003");
                        intent.putExtra("Package_name","Yearly");
                        context.startActivity(intent);
                    }


//                    dialog=new Dialog(context);
//                    dialog.setContentView(R.layout.subscriptionalert);
//                    ImageView imageView=dialog.findViewById(R.id.Image_cross);
//                    dialog.show();
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });

                }
            });

        }
    }


}
