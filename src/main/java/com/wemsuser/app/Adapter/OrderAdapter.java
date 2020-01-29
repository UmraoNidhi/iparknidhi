package com.wemsuser.app.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wemsuser.app.Fragment.OrderFragment;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.UserOrderdatum;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private Context mcontext;
    private ArrayList<UserOrderdatum>morder;

    public OrderAdapter(Context context, ArrayList<UserOrderdatum> orderList) {
        this.morder=orderList;
        this.mcontext=context;
    }


    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orderxml,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( OrderAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.textService.setText(morder.get(i).getServiceName());
        myViewHolder.textMerchant.setText(morder.get(i).getMerchantName());
        myViewHolder.textFuel.setText(morder.get(i).getFuelTypeName());
        myViewHolder.textCar.setText(morder.get(i).getVehicleTypeName());


    }

    @Override
    public int getItemCount() {
        return morder.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView textService,textCar,textFuel,textMerchant;
        Button Submit_report;
        ImageView imageView;
        EditText editText;
        public MyViewHolder( View itemView) {
            super(itemView);

            textService=itemView.findViewById(R.id.Text_service);
            textCar=itemView.findViewById(R.id.Text_car);
            textFuel=itemView.findViewById(R.id.Text_fuel);
            textMerchant=itemView.findViewById(R.id.Text_Provider);


        }

//        @Override
//        public void onClick(View v) {
//            final Dialog dialog=new Dialog(mcontext);
//            dialog.setContentView(R.layout.reportxml);
//            imageView= dialog.findViewById(R.id.Image_cross);
//            editText= dialog.findViewById(R.id.Edit_report);
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//            Submit_report= dialog.findViewById(R.id.Report_submit);
//            Submit_report.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!editText.getText().toString().isEmpty()){
//                        dialog.dismiss();
//                    }else {
//                        Toast.makeText(mcontext,"Please Fill Your Report",Toast.LENGTH_LONG).show();
//                    }
//
//
//                }
//            });
//
//
//            dialog.show();
//
//        }
    }
}
