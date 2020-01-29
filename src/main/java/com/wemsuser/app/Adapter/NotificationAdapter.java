package com.wemsuser.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wemsuser.app.Activity.NotificationStatus;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.NotoificationDatum;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.Constants;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<NotoificationDatum>List;
    String NotificationId,UserId,Title,Message,Time;
    public LinearLayout linearLayout;
    public NotificationAdapter(FragmentActivity activity, ArrayList<NotoificationDatum> mList) {
        this.context=activity;
        this.List=mList;


    }

    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( NotificationAdapter.MyViewHolder myViewHolder, int i) {
        ArrayList<String>Unread_counter = new ArrayList<>();
        myViewHolder.title.setText(List.get(i).getNotificationTitle());
        myViewHolder.description.setText(List.get(i).getNotificationMessage());
        myViewHolder.Date.setText(List.get(i).getCreationDate());
        String flag= List.get(i).getReadStatus();
        NotificationId=List.get(i).getId();
        if (flag.equalsIgnoreCase("R")){
            myViewHolder.linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else {
            myViewHolder.linearLayout.setBackgroundColor(Color.parseColor("#FEC200"));
            Unread_counter.add(flag);
            Log.e("UnreadCounter",""+Unread_counter.size());
            String Unread_Notification = String.valueOf(Unread_counter.size());
            PreferenceUtil.setUnread_counter(context,Unread_Notification);
        }
//        Toast.makeText(context,Unread_counter.size(),Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,Date;
        LinearLayout linearLayout;
        public MyViewHolder( View itemView) {
            super(itemView);

            UserId = PreferenceUtil.getUserId(context);

            title=itemView.findViewById(R.id.Text_heading);
            description=itemView.findViewById(R.id.Text_description);
            Date=itemView.findViewById(R.id.Text_Date);
            linearLayout=itemView.findViewById(R.id.Linear_notification);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Notification().execute(UserId,List.get(getLayoutPosition()).getId());
                    Title = List.get(getLayoutPosition()).getNotificationTitle();
                    Message = List.get(getLayoutPosition()).getNotificationMessage();
                    Time =   List.get(getLayoutPosition()).getCreationDate();

                }
            });

        }
    }


    public class Notification extends AsyncTask<String,String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            java.util.List<NameValuePair> userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("user_id",strings[0]));
            userData.add(new BasicNameValuePair("notification_id",strings[1]));
            JSONObject jsonObject=webServiceURL.NotificationData(userData);
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
                Log.e("NotificationStatus",""+jsonObject.toString());
                try {
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        Intent intent=new Intent(context, NotificationStatus.class);
                        intent.putExtra("Title",Title);
                        intent.putExtra("Message",Message);
                        intent.putExtra("Time",Time);

                        context.startActivity(intent);
                        String Status=responseClass.getResult().getReadStatus();
                        if (Status.equalsIgnoreCase("R")){
                           linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        }else {

                            linearLayout.setBackgroundColor(Color.parseColor("#FEC200"));

                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


}
