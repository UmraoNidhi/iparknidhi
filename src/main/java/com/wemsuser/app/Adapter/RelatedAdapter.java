package com.wemsuser.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wemsuser.app.Activity.DetailsActivity;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.SimilarServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;

public class RelatedAdapter extends RecyclerView.Adapter<RelatedAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<SimilarServiceProvider>mSimilar;
    String M_Name,M_address,M_icon,M_Phone;
    public SDetail sDetail;
    HashMap<String,String>SimilarShop=new HashMap<String,String>();




    public RelatedAdapter(DetailsActivity detailsActivity, ArrayList<SimilarServiceProvider> similar, DetailsActivity activity) {
        this.context=detailsActivity;
        this.mSimilar=similar;
        this.sDetail= activity;
    }

    @Override
    public RelatedAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.similarxml,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( RelatedAdapter.MyViewHolder myViewHolder, int i) {
        M_Name=mSimilar.get(i).getUserName();
        M_address=mSimilar.get(i).getUserAddress();
        M_Phone=mSimilar.get(i).getUserPhone();
        myViewHolder.shopname.setText(toCamelCase(M_Name));
        myViewHolder.shopaddress.setText(toCamelCase(M_address));
        ImageView view=myViewHolder.imageView;
        String imageURL=mSimilar.get(i).getMobileIcon();
        String SimilarURL=imageURL.replace("thumb","mobileapp");
        Glide.with(context).load(SimilarURL).into(view);

    }

    @Override
    public int getItemCount() {
        return mSimilar.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView shopname,shopaddress;
        LinearLayout linearLayout;
        public MyViewHolder( View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.Image_repair);
            shopname=itemView.findViewById(R.id.Text_shop);
            shopaddress=itemView.findViewById(R.id.Text_shopAddress);
            linearLayout=itemView.findViewById(R.id.Linear_Similar);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SimilarShop.put("Merchant_Name",mSimilar.get(getLayoutPosition()).getUserName());
                    SimilarShop.put("M_Address",mSimilar.get(getLayoutPosition()).getUserAddress());
                    SimilarShop.put("M_phone",mSimilar.get(getLayoutPosition()).getUserPhone());
                    SimilarShop.put("ServiceProvided",mSimilar.get(getLayoutPosition()).getMapicon());
                    SimilarShop.put("Merchant_Id",mSimilar.get(getLayoutPosition()).getMerchantId());
                    SimilarShop.put("Type","Similar");
                    sDetail.similar(SimilarShop);


                }
            });
        }
    }


    public interface SDetail{
        void similar (HashMap<String,String> similarDetail);

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
