package com.wemsuser.app.Activity;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.wemsuser.app.Fragment.AboutUsFragment;
import com.wemsuser.app.Fragment.AppFeedbackFragment;
import com.wemsuser.app.Fragment.ContactUsFragment;
import com.wemsuser.app.Fragment.FAQFragment;
import com.wemsuser.app.Fragment.FollowUsFragment;
import com.wemsuser.app.Fragment.HomeFragment;
import com.wemsuser.app.Fragment.MapFragment;
import com.wemsuser.app.Fragment.NotificationFragment;
import com.wemsuser.app.Fragment.OrderFragment;
import com.wemsuser.app.Fragment.PrivacyFragment;
import com.wemsuser.app.Fragment.ProfileFragment;
import com.wemsuser.app.Fragment.SettingFragment;
import com.wemsuser.app.Fragment.SubscriptionFragment;
import com.wemsuser.app.Fragment.TermsConditionFragment;
import com.wemsuser.app.R;
import com.wemsuser.app.utility.PreferenceUtil;

import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private ExpandableListView expandableListView;
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    ExpandableListAdapter mMenuAdapter;
    FragmentTransaction fragmentTransaction;
    String currentFrag;
    private String backStateName;
    Boolean flag=false;
    private FragmentManager fragmentManager;
    public static Menu mMenu;
    public static int selectedId=-1,position;
    public static HashMap<Integer,String>Selected_Service=new HashMap<>();
    public static FloatingActionButton fab;
    Boolean flagtier=false,flagLockOut=false,flagmechanic=false,flagBattery=false,flagTowing=false,flagCar=false;
    BottomSheetDialog Bottomdialog;
    private LocationTracker locationTracker;
    NavigationView navigationView;
    private View notificationBadge;
    public static Toolbar toolbar;
    public static TextView textviewTitle;
    private Typeface typeface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.getTitle();

        final ActionBar abar = getSupportActionBar();
//        abar.setBackgroundDrawable(R.drawable.gradient_bg);//line under the action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar,null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.LEFT);
        textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Wems RSA");
//        textviewTitle.setTypeface(typeface);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(false);
        abar.setHomeButtonEnabled(true);


        fab = (FloatingActionButton) findViewById(R.id.fab_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bottomdialog=new BottomSheetDialog(HomeActivity.this);
                Bottomdialog.setContentView(R.layout.buttomsheet);
                final LinearLayout linear_tier = Bottomdialog.findViewById(R.id.Linear_tier);
                final LinearLayout linear_lockout = Bottomdialog.findViewById(R.id.Linear_lockout);
                final LinearLayout linear_machanic = Bottomdialog.findViewById(R.id.Linear_mechanic);
                final LinearLayout linear_battery = Bottomdialog.findViewById(R.id.Linear_battery);
                final LinearLayout linear_towing = Bottomdialog.findViewById(R.id.Linear_towing);
                final LinearLayout linear_corBoady = Bottomdialog.findViewById(R.id.Linear_carbody);
                linear_tier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!flagtier){
                            flagtier=true;
                            PreferenceUtil.setServiceName(HomeActivity.this,"Tires");
                            SelectedServiveType(linear_tier,linear_battery,linear_corBoady,linear_lockout,linear_machanic,linear_towing);
                            PreferenceUtil.setServiceId(HomeActivity.this,"1000000001");
                            displaySelectedFragment(new HomeFragment(),backStateName);
                            String serviceName=PreferenceUtil.getServiceName(HomeActivity.this);
                            Bottomdialog.dismiss();
                            HomeFragment.textView.setText(serviceName);
                            backStateName="Home";
                            setTitle("Wems RSA");

                        }else {
                            flagtier=false;
                            linear_tier.setBackgroundColor(Color.parseColor("#ffffff"));

                        }

                    }
                });
                linear_lockout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!flagLockOut){
                            flagLockOut=true;
                            PreferenceUtil.setServiceName(HomeActivity.this,"Lockout");
                            SelectedServiveType(linear_lockout,linear_tier,linear_battery,linear_corBoady,linear_machanic,linear_towing);
                            PreferenceUtil.setServiceId(HomeActivity.this,"1000000002");
                            displaySelectedFragment(new HomeFragment(),backStateName);
                            backStateName="Home";
                            Bottomdialog.dismiss();
                            String serviceName=PreferenceUtil.getServiceName(HomeActivity.this);
                            HomeFragment.textView.setText(serviceName);
                            setTitle("Wems RSA");

                        }else {
                            flagLockOut=false;
                            linear_lockout.setBackgroundColor(Color.parseColor("#ffffff"));
                        }

                    }
                });
                linear_machanic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!flagmechanic){
                            flagmechanic=true;
                            PreferenceUtil.setServiceName(HomeActivity.this,"Mechanics");
                            SelectedServiveType(linear_machanic,linear_lockout,linear_tier,linear_battery,linear_corBoady,linear_towing);
                            PreferenceUtil.setServiceId(HomeActivity.this,"1000000003");
                            displaySelectedFragment(new HomeFragment(),backStateName);
                            backStateName="Home";
                            Bottomdialog.dismiss();
                            String serviceName=PreferenceUtil.getServiceName(HomeActivity.this);
                            HomeFragment.textView.setText(serviceName);
                            setTitle("Wems RSA");
                        }else {
                            flagmechanic=false;
                            linear_machanic.setBackgroundColor(Color.parseColor("#ffffff"));
                        }

                    }
                });
                linear_battery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!flagBattery){
                            flagBattery=true;
                            PreferenceUtil.setServiceName(HomeActivity.this,"Electrical/Battery");
                            SelectedServiveType(linear_battery,linear_corBoady,linear_lockout,linear_machanic,linear_tier,linear_towing);
                            PreferenceUtil.setServiceId(HomeActivity.this,"1000000004");
                            displaySelectedFragment(new HomeFragment(),backStateName);
                            backStateName="Home";
                            Bottomdialog.dismiss();
                            String serviceName=PreferenceUtil.getServiceName(HomeActivity.this);
                            HomeFragment.textView.setText(serviceName);
                            setTitle("Wems RSA");
                        }else {
                            flagBattery=false;
                            linear_battery.setBackgroundColor(Color.parseColor("#ffffff"));

                        }
                    }
                });
                linear_towing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!flagTowing){
                            flagTowing=true;
                            PreferenceUtil.setServiceName(HomeActivity.this,"Tow Truck");
                            SelectedServiveType(linear_towing,linear_battery,linear_corBoady,linear_lockout,linear_machanic,linear_tier);
                            PreferenceUtil.setServiceId(HomeActivity.this,"1000000005");
                            displaySelectedFragment(new HomeFragment(),backStateName);
                            backStateName="Home";
                            Bottomdialog.dismiss();
                            String serviceName=PreferenceUtil.getServiceName(HomeActivity.this);
                            HomeFragment.textView.setText(serviceName);
                            setTitle("Wems RSA");
                        }else {
                            flagTowing=false;
                            linear_towing.setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                    }
                });
                linear_corBoady.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!flagCar){
                            flagCar=true;
                            PreferenceUtil.setServiceName(HomeActivity.this,"Car Body Work");
                            SelectedServiveType(linear_corBoady,linear_towing,linear_battery,linear_lockout,linear_machanic,linear_tier);
                            PreferenceUtil.setServiceId(HomeActivity.this,"1000000006");
                            displaySelectedFragment(new HomeFragment(),backStateName);
                            backStateName="Home";
                            Bottomdialog.dismiss();
                            String serviceName=PreferenceUtil.getServiceName(HomeActivity.this);
                            HomeFragment.textView.setText(serviceName);
                            setTitle("Wems RSA");
                        }else {
                            flagCar=false;
                            linear_corBoady.setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                    }
                });

                Bottomdialog.show();
//                Snackbar.make(view, "How Can We Connect You", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Helppopup();

            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);
        backStateName="HomeFragment";
        displaySelectedFragment(new HomeFragment(),backStateName);

//        addBadgeView();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);


        }else if (currentFrag.equalsIgnoreCase("HomeFragment")){
            Intent intent = new Intent(HomeActivity.this,HelpyouActivity.class);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
//
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        mMenu =menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_Map) {
            backStateName = "MapView";
            displaySelectedFragment(new MapFragment(), backStateName);
//            item.setIcon(R.mipmap.filtermap);
            MenuItem menuItemMap= mMenu.findItem(R.id.action_Map);
            menuItemMap.setVisible(false);
            MenuItem menuItemList= mMenu.findItem(R.id.action_List);
            menuItemList.setVisible(true);
            return true;
        }else if (id==R.id.action_List){
            backStateName="ListView";
            displaySelectedFragment(new HomeFragment(), backStateName);
//            item.setIcon(R.drawable.mapview);
            MenuItem menuItemMap= mMenu.findItem(R.id.action_Map);
            menuItemMap.setVisible(true);
            MenuItem menuItemList= mMenu.findItem(R.id.action_List);
            menuItemList.setVisible(false);
            return true;
        }else if (id==R.id.Refresh){
            Fragment fragment=new HomeFragment();
            locationTracker=new LocationTracker(this);
            String New_Latitude= Double.toString(locationTracker.getLatitude());
            String New_longitude=Double.toString(locationTracker.getLongitude());
            backStateName="Home";
            setTitle("Wems RSA");
            Bundle bundle=new Bundle();
            bundle.putString("NewLatitude",""+New_Latitude);
            bundle.putString("NewLongitude",""+New_longitude);
            fragment.setArguments(bundle);
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }


        return super.onOptionsItemSelected(item);
    }

    private void Helppopup(){
        final Dialog helpdialog=new Dialog(HomeActivity.this);
        helpdialog.setContentView(R.layout.homehelp);
        Button save=helpdialog.findViewById(R.id.Button_save);
        RadioGroup radioGroup=helpdialog.findViewById(R.id.radio);
        final RadioButton radio1 =helpdialog.findViewById(R.id.RadioButton1);
        String Button_text=radio1.getText().toString();
        RadioButton radio2=helpdialog.findViewById(R.id.RadioButton2);
        String Button_text1=radio2.getText().toString();
        RadioButton radio3=helpdialog.findViewById(R.id.RadioButton3);
        String Button_text2=radio3.getText().toString();
        RadioButton radio4=helpdialog.findViewById(R.id.RadioButton4);
        String Button_text4=radio4.getText().toString();
        RadioButton radio5=helpdialog.findViewById(R.id.RadioButton5);
        String Button_text5=radio5.getText().toString();
        RadioButton radio6=helpdialog.findViewById(R.id.RadioButton6);
        String Button_text6=radio6.getText().toString();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedId=group.getCheckedRadioButtonId();
                View radioButton=group.findViewById(selectedId);
                position=group.indexOfChild(radioButton);
                RadioButton butt=(RadioButton)group.getChildAt(position);
                Log.e("SelectedRadioID",""+position+"\n"+butt);


                if (position==0){
                  PreferenceUtil.setServiceId(HomeActivity.this,"1000000001");

                }else if (position==1){
                    PreferenceUtil.setServiceId(HomeActivity.this,"1000000002");

                }else if (position==2){
                    PreferenceUtil.setServiceId(HomeActivity.this,"1000000003");

                }else if (position==3){
                    PreferenceUtil.setServiceId(HomeActivity.this,"1000000004");

                }else if (position==4){
                    PreferenceUtil.setServiceId(HomeActivity.this,"1000000005");


                }else if (position==5){
                    PreferenceUtil.setServiceId(HomeActivity.this,"1000000006");

                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedId!=-1){
                    helpdialog.dismiss();
//                    if (position!=-1){
                        displaySelectedFragment(new HomeFragment(),backStateName);
                        backStateName="Home";
                        setTitle("Wems RSA");
                   // }

                }else {
                    Toast.makeText(HomeActivity.this,"Please Select Service",Toast.LENGTH_LONG).show();

                }
            }
        });


        helpdialog.show();
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.home) {
            displaySelectedFragment(new HomeFragment(),backStateName);
            backStateName="Home";
            textviewTitle.setText("Home");

        } else if (id == R.id.profile) {
            Fragment profile=new ProfileFragment();
            textviewTitle.setText(item.getTitle());
            Bundle bundle=new Bundle();
            bundle.putString("Profile","Disable");
            profile.setArguments(bundle);
            backStateName="Profile";
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, profile);
            fragmentTransaction.commit();
//            displaySelectedFragment(new ProfileFragment(),backStateName);




        } else if (id == R.id.Feeback) {
            displaySelectedFragment(new AppFeedbackFragment(),backStateName);
            backStateName="AppFeedback";
            textviewTitle.setText(item.getTitle());

        } else if (id == R.id.Notification) {
            displaySelectedFragment(new NotificationFragment(),backStateName);
            backStateName="Notification";
            textviewTitle.setText(item.getTitle());

        } else if (id == R.id.About) {
            displaySelectedFragment(new AboutUsFragment(),backStateName);
            backStateName="AboutUs";
            textviewTitle.setText(item.getTitle());

        } else if (id == R.id.Privacy) {
            backStateName="Privacy";
            displaySelectedFragment(new PrivacyFragment(),backStateName);
            textviewTitle.setText(item.getTitle());

        }else if (id==R.id.Contact){
            backStateName="Contact Us";
            displaySelectedFragment(new ContactUsFragment(),backStateName);
            textviewTitle.setText(item.getTitle());

        }else if(id==R.id.Terms){
            displaySelectedFragment(new TermsConditionFragment(),backStateName);
            backStateName="Terms Condition";
            textviewTitle.setText(item.getTitle());

        }else if (id==R.id.subscription){
            displaySelectedFragment(new SubscriptionFragment(),backStateName);
            backStateName="Subscription";
            textviewTitle.setText(item.getTitle());
        }
        else if (id==R.id.Follow){
            displaySelectedFragment(new FollowUsFragment(),backStateName);
            backStateName="Follow Us ";
            textviewTitle.setText(item.getTitle());

        } else if(id==R.id.Friend){
            shareApp();

        }else if (id==R.id.Setting){
            displaySelectedFragment(new FAQFragment(),backStateName);
            backStateName="Settings";
            textviewTitle.setText(item.getTitle());


        }else if (id==R.id.logout){
            openLogoutPopup();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void addBadgeView() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(2);

        notificationBadge = LayoutInflater.from(this).inflate(R.layout.notification_layout, menuView, false);

        itemView.addView(notificationBadge);
    }


    private void openLogoutPopup() {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.logoutxml);
        TextView text_yes=dialog.findViewById(R.id.Text_yes);
        TextView text_No=dialog.findViewById(R.id.Text_no);
        text_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PreferenceUtil.getSaveLoginFlag(HomeActivity.this)){
                    PreferenceUtil.clearPreferenceObject(HomeActivity.this);

                }
                PreferenceUtil.setUserId(HomeActivity.this,"");
                dialog.dismiss();
                Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        text_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    private void shareApp() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Hey download this app and save your self from sudden emergency in case of your road journey"+"\n"+"https://play.google.com/store/apps/details?id=com.wemsuser.app";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "WEMSRSA");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    private void displaySelectedFragment(Fragment fragment, String backstack_name) {
        currentFrag= backstack_name;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, backstack_name);
        fragmentTransaction.addToBackStack(backstack_name);
        fragmentTransaction.commit();


    }

    public void SelectedServiceId() {
        if (position==0){
            Selected_Service.put(position,"Tires");

        }else if (position==1){
            Selected_Service.put(position,"Lockout");

        }else if (position==2){
            Selected_Service.put(position,"Mechanics");

        }else if (position==3){
            Selected_Service.put(position,"Electrical/Battery");

        }else if (position==4){
            Selected_Service.put(position,"Tow Truck");


        }else if (position==5){
            Selected_Service.put(position,"Car Body Work");

        }
        Log.e("SelectedRadioID",""+position);
    }

    public void SelectedServiveType(LinearLayout Selected ,LinearLayout Unselected1,
                                    LinearLayout Unselected2,LinearLayout Unselected3,
                                    LinearLayout Unselected4,LinearLayout Unselected5  ){

        Selected.setBackground(getResources().getDrawable(R.drawable.popupbackground));
        Unselected1.setBackgroundColor(Color.parseColor("#ffffff"));
        Unselected2.setBackgroundColor(Color.parseColor("#ffffff"));
        Unselected3.setBackgroundColor(Color.parseColor("#ffffff"));
        Unselected4.setBackgroundColor(Color.parseColor("#ffffff"));
        Unselected5.setBackgroundColor(Color.parseColor("#ffffff"));
    }



    }


