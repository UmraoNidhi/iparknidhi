package com.wemsuser.app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wemsuser.app.Fragment.FAQFragment;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.FaqDatum;

import java.util.ArrayList;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.MyViewHolder> {
    private ArrayList<FaqDatum>mList;
    private Context context;

    public FAQAdapter(Context faqFragment, ArrayList<FaqDatum> questionList) {
        this.context=faqFragment;
        this.mList=questionList;

    }

    @Override
    public FAQAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.faqxml,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( FAQAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.text_question.setText(mList.get(i).getQuestion());
        myViewHolder.textAnswer.setText(mList.get(i).getAnswer());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text_question,textAnswer;
        LinearLayout linearLayout;
        ImageView imageView,image_close;
        public MyViewHolder( View itemView) {
            super(itemView);
            text_question=itemView.findViewById(R.id.Text_question);
            textAnswer=itemView.findViewById(R.id.Text_answer);
            linearLayout=itemView.findViewById(R.id.Linear_Text);
            imageView=itemView.findViewById(R.id.FAQ_open);
            image_close=itemView.findViewById(R.id.FAQ_close);
            image_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    image_close.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                }
            });
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            image_close.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);

        }
    }
}
