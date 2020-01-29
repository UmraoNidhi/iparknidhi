package com.wemsuser.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wemsuser.app.Activity.DetailsActivity;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.Activity.ReviewActivity;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Response.ServiceProviderDatum;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context mcontext;
    private ArrayList<ServiceProviderDatum>mshoplist;
    private ArrayList<String>Miles=new ArrayList<>();
    ImageView Rating1,Rating2,Rating3,Rating4,Rating5;
    String Phone_No;


    public HomeAdapter(Context context, ArrayList<ServiceProviderDatum> shopList) {
        this.mshoplist=shopList;
        this.mcontext=context;


    }

    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homexml,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( HomeAdapter.MyViewHolder myViewHolder, int i) {
        Animation animation = AnimationUtils.loadAnimation(mcontext, android.R.anim.slide_in_left);
        String MerchantId=mshoplist.get(i).getMerchantId();
        String Mer_name=mshoplist.get(i).getUserName();
        myViewHolder.itemView.startAnimation(animation);
        myViewHolder.text_heading.setText(toCamelCase(Mer_name));
        myViewHolder.text_address.setText(toCamelCase(mshoplist.get(i).getUserAddress()));
        Phone_No = mshoplist.get(i).getUserPhone();
        try {
            if (Phone_No!=null){
                String hint=Phone_No.replace(Phone_No.substring(0,Phone_No.length()-2),"********");
                myViewHolder.text_mobileMo.setText(hint);
            }else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        ImageView View=myViewHolder.imageView;
        String ImageURL = mshoplist.get(i).getMobileIcon();
        String NewURL=ImageURL.replace("thumb","mobileapp");
        Log.e("NewURL_Home",""+NewURL);
        Glide.with(mcontext).load(NewURL).into(View);
        String Rating = mshoplist.get(i).getTotalRating();
        Log.e("APiRating",""+Rating);
        String Count = mshoplist.get(i).getTotalCount();
        try {

            Float averageRating = (Float.parseFloat(Rating)/Float.parseFloat(Count));
            setRatingStar(averageRating);

            Log.e("AverageDetails",""+averageRating);


        }catch (Exception e){
            e.printStackTrace();
        }

//        new AverageRating().execute(MerchantId);

    }

    @Override
    public int getItemCount() {
        return mshoplist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView,text_mobileMo,text_heading,text_address;
        LinearLayout linearLayout,linear_rating;
        ImageView imageView;
        public MyViewHolder( View itemView) {
            super(itemView);
            Rating1 = itemView.findViewById(R.id.Rating_one);
            Rating2 = itemView.findViewById(R.id.Rating_two);
            Rating3 = itemView.findViewById(R.id.Rating_three);
            Rating4 = itemView.findViewById(R.id.Rating_four);
            Rating5 = itemView.findViewById(R.id.Rating_five);
            text_heading=itemView.findViewById(R.id.Text1);
            text_heading.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            text_address=itemView.findViewById(R.id.Text2);
            text_address.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            imageView=itemView.findViewById(R.id.Image_home);
            linearLayout=itemView.findViewById(R.id.Linear_home);
            linear_rating=itemView.findViewById(R.id.linear_rating);
            linear_rating.setOnClickListener(this);
            linearLayout.setOnClickListener(this);
            textView=itemView.findViewById(R.id.Review);
            text_mobileMo=itemView.findViewById(R.id.Text_mobileNo);






//            text_mobileMo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mcontext, ReviewActivity.class);
                    mcontext.startActivity(intent);
                }
            });

        }


        @Override
        public void onClick(View v) {
            String Merchant_Id=mshoplist.get(getLayoutPosition()).getMerchantId();
            String Merchant_Name = mshoplist.get(getLayoutPosition()).getUserName();
            Log.e("HomeAdapter",""+Merchant_Name+"\n"+Merchant_Id);
            PreferenceUtil.setMerchant_Id(mcontext,Merchant_Id);
            Intent intent=new Intent(mcontext, DetailsActivity.class);
            mcontext.startActivity(intent);

        }



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




    private void setRatingStar(Float count) {

        if (count == 1.00) {
            Rating1.setVisibility(View.VISIBLE);
        } else if (count == 2.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
        } else if (count == 3.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
            Rating3.setVisibility(View.VISIBLE);
        } else if (count == 4.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
            Rating3.setVisibility(View.VISIBLE);
            Rating4.setVisibility(View.VISIBLE);
        } else if (count == 5.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
            Rating3.setVisibility(View.VISIBLE);
            Rating4.setVisibility(View.VISIBLE);
            Rating5.setVisibility(View.VISIBLE);
        } else if (count > 0.00 && count < 1.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating1.setImageResource(R.drawable.half_star);
        } else if (count > 1.00 && count < 2.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating1.setImageResource(R.drawable.rating_fill);
            Rating2.setVisibility(View.VISIBLE);
            Rating2.setImageResource(R.drawable.half_star);
        } else if (count > 2.00 && count < 3.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
            Rating2.setImageResource(R.drawable.rating_fill);
            Rating3.setVisibility(View.VISIBLE);
            Rating3.setImageResource(R.drawable.half_star);
        } else if (count > 3.00 && count < 4.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
            Rating3.setVisibility(View.VISIBLE);
            Rating3.setImageResource(R.drawable.rating_fill);
            Rating4.setVisibility(View.VISIBLE);
            Rating4.setImageResource(R.drawable.half_star);
        } else if (count > 4.00 && count < 5.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
            Rating3.setVisibility(View.VISIBLE);
            Rating4.setVisibility(View.VISIBLE);
            Rating4.setImageResource(R.drawable.rating_fill);
            Rating5.setVisibility(View.VISIBLE);
            Rating5.setImageResource(R.drawable.half_star);
        }
    }
}
