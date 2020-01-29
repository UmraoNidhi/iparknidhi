package com.wemsuser.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wemsuser.app.Activity.HelpyouActivity;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ServiceDatum;
import com.wemsuser.app.utility.PreferenceUtil;

import java.util.ArrayList;

public class HelpYouAdapter extends RecyclerView.Adapter<HelpYouAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ServiceDatum>Help;


    public HelpYouAdapter(Context helpyouActivity, ArrayList<ServiceDatum> mService) {
        this.context=helpyouActivity;
        this.Help=mService;
    }


    @Override
    public HelpYouAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.helpxml,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( HelpYouAdapter.MyViewHolder myViewHolder, int i) {
        String imageURL=Help.get(i).getMobileIcon();
        String URl=imageURL.replace("thumb","helpyouapp ");
        myViewHolder.textView.setText(Help.get(i).getServiceName());
        ImageView imageView=myViewHolder.imageView;
        Glide.with(context).load(imageURL).into(imageView);

    }

    @Override
    public int getItemCount() {
        return Help.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;
        LinearLayout linearLayout;
        public MyViewHolder( View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.Text_help);
            imageView=itemView.findViewById(R.id.Image_icon);
            linearLayout=itemView.findViewById(R.id.Linear_help);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String Service_Id=Help.get(getLayoutPosition()).getServiceId();
            String SERVICE_NAME=Help.get(getLayoutPosition()).getServiceName();
            PreferenceUtil.setServiceId(context,Service_Id);
            PreferenceUtil.setServiceName(context,SERVICE_NAME);
            Intent intent=new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        }
    }
}
