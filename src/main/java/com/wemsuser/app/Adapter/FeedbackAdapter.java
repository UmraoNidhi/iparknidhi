package com.wemsuser.app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wemsuser.app.Activity.FeedbackActivity;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.Feedbackquestiondatum;
import com.wemsuser.app.utility.PreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    private ArrayList<Feedbackquestiondatum>mfeedback;
    private FeedbackActivity context;
    public HashMap<String,String> QuestionSelected=new HashMap<String, String>();
    private SelectedQuestionList selectedQuestionList;


    public FeedbackAdapter(FeedbackActivity feedbackQuestionList, ArrayList<Feedbackquestiondatum> questionList, FeedbackActivity feedbackActivity) {
        this.context=feedbackQuestionList;
        this.mfeedback=questionList;
        this.selectedQuestionList=feedbackQuestionList;

    }


    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedbackxml,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( FeedbackAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(mfeedback.get(i).getQuestion());

    }

    @Override
    public int getItemCount() {
        return mfeedback.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        TextView textView;
        CheckBox checkBox;
        public MyViewHolder( View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.Feedback_question);
            checkBox=itemView.findViewById(R.id.CheckBox);
            checkBox.setOnCheckedChangeListener(this);


        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (checkBox.isChecked()){
                String text=checkBox.getText().toString();
                int position=getLayoutPosition();

                if (position==0){
                    int checkboxId=checkBox.getId();
                    QuestionSelected.put("0","1000000001");
                    selectedQuestionList.questionId(QuestionSelected);
                    Log.e("FeedbackQuestion",""+checkboxId+"\n"+position);
                }else if (position==1){
                    int checkboxId=checkBox.getId();
                    QuestionSelected.put("1","1000000002");
                    selectedQuestionList.questionId(QuestionSelected);
                    Log.e("FeedbackQuestion",""+checkboxId+"\n"+position);
                }else if (position==2){
                    int checkboxId=checkBox.getId();
                    QuestionSelected.put("2","1000000003");
                    selectedQuestionList.questionId(QuestionSelected);
                }else if (position==3){
                    int checkboxId=checkBox.getId();
                    QuestionSelected.put("3","1000000004");
                    selectedQuestionList.questionId(QuestionSelected);
                }



            }
        }
    }

    public interface SelectedQuestionList{
        void questionId(HashMap<String,String>questionId);

    }
}
