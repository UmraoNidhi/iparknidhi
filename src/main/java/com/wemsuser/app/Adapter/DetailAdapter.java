package com.wemsuser.app.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wemsuser.app.Activity.DetailsActivity;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ServiceProviderService;
import com.wemsuser.app.Response.SimilarServiceProvider;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ServiceProviderService>mServices;
    private Integer ImageList[] ;
    public DetailAdapter(DetailsActivity detailsActivity, ArrayList<ServiceProviderService> services) {
        this.context=detailsActivity;
        this.mServices=services;

    }

    @Override
    public DetailAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detailxml,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( DetailAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(mServices.get(i).getServiceName());
        String Service_image=mServices.get(i).getMobileIcon();
        ImageView view= myViewHolder.imageView;
        Glide.with(context).load(Service_image).into(view);
//        myViewHolder.imageView.setImageResource(ImageList[i]);

    }

    @Override
    public int getItemCount() {
        return mServices.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.Text_service);
            imageView=itemView.findViewById(R.id.Service_image);
        }
    }
}
