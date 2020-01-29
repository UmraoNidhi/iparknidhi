package com.wemsuser.app.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wemsuser.app.R;
import com.wemsuser.app.utility.PreferenceUtil;

public class MainActivity extends AppCompatActivity {
    private Boolean loggedInFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.carstartgarage);
        ring.start();



        Thread thread=new Thread(){
            public void run(){
                try {
                    sleep(2000);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    String UserId=PreferenceUtil.getUserId(MainActivity.this);
                    if (!UserId.isEmpty()){
                        Intent intent=new Intent(MainActivity.this,HelpyouActivity.class);
                        intent.putExtra("Back","Remove");
                        startActivity(intent);

                    }else if (loggedInFlag==false){
                        Intent intent=new Intent(MainActivity.this,IntroductionActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }

                }

            }

        };thread.start();
    }
}
