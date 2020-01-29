package com.wemsuser.app.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wemsuser.app.Activity.AddVehicleActivity;
import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.Activity.ImageRotateClass;
import com.wemsuser.app.Activity.Pager;
import com.wemsuser.app.Activity.Utility;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.KeyGenerationClass;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    TabLayout tabLayout;
    FrameLayout frameLayout;
    ViewPager viewPager;
    TextView UserName, UserNo, UserEmail;
    String Name, Phone, Email, UserPhone;
    Button ADD_car;
    CircleImageView circleImageView;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private Uri fileUri;
    Bitmap imageBm1=null,gallery_image=null;
    int layout1_width, layout1_height;
    private int code;
    File pictureFile1,file;
    String imageName1,imagename2, image_URl,Profile,packageId;
    Boolean image1 = false, image2= false;
    ImageView banner_URL,Update_Profile;
    int GALLERY_REQUEST;
    private static final int READ_EXT_REQUEST = 1;

    Dialog Profile_dialog;
    ImageView image_premium;




    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivity.mMenu.findItem(R.id.action_Map).setVisible(false);
        HomeActivity.mMenu.findItem(R.id.action_List).setVisible(false);
        HomeActivity.fab.findViewById(R.id.fab_button).setVisibility(View.GONE);
        HomeActivity.mMenu.findItem(R.id.Refresh).setVisible(false);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(false);

//        try {
//            if (getArguments()!=null){
//                Profile=getArguments().getString("Profile");
//                Log.e("HomeProfile",""+Profile);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }





        Name = PreferenceUtil.getUserName(getContext());
        Email = PreferenceUtil.getUserEmail(getContext());
        Phone = PreferenceUtil.getUserPhone(getContext());



        tabLayout = view.findViewById(R.id.simpleTabLayout);
        ADD_car = view.findViewById(R.id.Button_add);
        viewPager = view.findViewById(R.id.simpleViewPager);
        UserName = view.findViewById(R.id.User_Name);
        UserNo = view.findViewById(R.id.TextNo);
        UserEmail = view.findViewById(R.id.TextEmail);
        banner_URL=view.findViewById(R.id.image_banner);
        Update_Profile=view.findViewById(R.id.Update);
        image_premium = view.findViewById(R.id.Image_Premium);
        circleImageView = view.findViewById(R.id.Profile_image);
        UserName.setText(toCamelCase(Name));
        UserEmail.setText(Email);
        UserNo.setText(Phone);
        setupViewPager(viewPager);

        try {
            packageId = PreferenceUtil.getPackage_Id(getContext());
            Log.e("PackageId",""+packageId);
            if (packageId!=null){
                if (packageId.equalsIgnoreCase("0")){
                    image_premium.setVisibility(View.INVISIBLE);
                }else {
                    image_premium.setVisibility(View.VISIBLE);
                }
            }else {
//                Linear_premium.setVisibility(View.INVISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        banner_URL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebook= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wemsrsa.com/"));
                startActivity(facebook);
//                new BannerAds().execute("profile-banner-app");
            }
        });




        Update_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile_dialog = new Dialog(getContext());
                Profile_dialog.setContentView(R.layout.profilexml);
                TextView gallery = Profile_dialog.findViewById(R.id.Text_gallery);
                TextView Takephoto = Profile_dialog.findViewById(R.id.Text_TakePhoto);
                TextView Cancel = Profile_dialog.findViewById(R.id.Text_Cancel);
                Profile_dialog.show();
                Takephoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image1 = true;
                        checkPermission();
                        captureImage();
                    }
                });
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkGalleryPermission()){
                            selectImage();
                        }else {
                            requestGalleryPermissions();

                        }


                    }
                });
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Profile_dialog.dismiss();
                    }
                });




            }
        });


        ADD_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddVehicleActivity.class);
                startActivity(intent);
            }
        });


        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }


    private void setupViewPager(ViewPager viewPager) {

        Pager adapter = new Pager(getChildFragmentManager());
        adapter.addFragment(new CarListFragment(), "  Car List  ");
        adapter.addFragment(new OrderFragment(), " Service List");
        viewPager.setAdapter(adapter);


    }

    public static String toCamelCase(final String init) {
        if (init == null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length() == init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }


    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,}, 100);
        }
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                Bundle extras = data.getExtras();
                imageBm1 = (Bitmap) extras.get("data");
                Bitmap newBitmap1 = Bitmap.createScaledBitmap(imageBm1, 500,
                        500, false);
                circleImageView.setImageBitmap(newBitmap1);
                saveImage(imageBm1, requestCode);

            }else {
                if ( requestCode == GALLERY_REQUEST )
                    if ( data != null ) {
                        try {
                            android.net.Uri selectedImage = data.getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            Log.w("filePathColoumn", "" + filePathColumn);
                            android.database.Cursor cursor =getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            Log.w("Image cursor", "" + cursor);
                            if ( cursor == null ) {
                                selectedImage = data.getData();
                                String path = selectedImage.getPath();
                                Log.w("ImagePath", "" + path);
                                file = new File(path);
                                gallery_image = BitmapFactory.decodeFile(file.getAbsolutePath());
                            } else {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String filePath = cursor.getString(columnIndex);
                                Log.w("filepath", "" + filePath);
                                cursor.close();
                                file = new File(filePath);
                                pictureFile1 = file;
                                Log.w("File of image", "" + file);
                                gallery_image = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(), data.getData());
//                                saveImage(gallery_image, requestCode);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                circleImageView.setImageBitmap(gallery_image);
                if (Networkstate.isNetworkAvailable(getContext())){
                    new  UploadImage().execute(Phone);

                }else {
                    Toast.makeText(getContext(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
                }
                    Log.e("Gallery",""+gallery_image);

            }
        }
    }

    private void saveImage(Bitmap imageBitmap, int requestCode) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            code = CAMERA_CAPTURE_IMAGE_REQUEST_CODE;
            pictureFile1 = getOutputMediaFile();
            Log.e("GETString", "" + pictureFile1.getName());
            if (pictureFile1 == null) {
                Log.d("File", "Error creating media file, check storage permissions: ");// e.getMessage());
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile1);
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
                if (Networkstate.isNetworkAvailable(getContext())){
                    new  UploadImage().execute(Phone);

                }else {
                    Toast.makeText(getContext(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
                }
            } catch (FileNotFoundException e) {
                Log.d("error in file", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("error in file", "Error accessing file: " + e.getMessage());
            }
        }else {

        }
    }

    private File getOutputMediaFile() {
        Log.e("Dir", "" + Environment.getExternalStorageDirectory());
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/DCIM/Camera/ProfileImage");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File mediaFile = null;
        if (code == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            imageName1 = "Profile_photo" + Calendar.getInstance().getTimeInMillis() + ".jpg";
            if (image1) {
//                imageText1.setText("");
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + imageName1);
                String str = mediaFile.toString();
                Log.e("File to string", "" + str + "\n media file:" + mediaFile);
            }

        }else {

        }
        return mediaFile;
    }


    public class UploadImage extends AsyncTask<String, String, String> {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg; charset=utf-8");
        String sendimage = "https://www.wemsrsa.com/api/userImage";
        String result;

        @Override
        protected String doInBackground(String... strings) {
            WebServiceURL webServiceURL = new WebServiceURL();
            try {
                RequestBody requestBody;
                requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("userphone", Phone)
                        .addFormDataPart("userimage", pictureFile1.getName(), RequestBody.create(MEDIA_TYPE_PNG, pictureFile1))
                        .build();

                Log.e("Requestbody",""+requestBody);


                Request request = new Request.Builder()
                        .header("Apikey", KeyGenerationClass.getEncryptedKey())
                        .url(sendimage)
                        .post(requestBody)
                        .build();
                Log.e("Request",""+request);
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();
                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (!result.isEmpty()) {
            }
            Log.e("USER_Image", result);

                try {
                    JSONObject res = new JSONObject(result);
                    if (res.getInt("success")==1) {
                        Toast.makeText(getContext(), "" + res.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONObject Profile_result = res.getJSONObject("result");
                        JSONObject userdetail = Profile_result.getJSONObject("userData");
                        String Profile_pic=userdetail.getString("user_image");
                        image_URl=Profile_pic.replace("/thumb","");
                        PreferenceUtil.setUserImage(getContext(),image_URl);
                        Profile_dialog.dismiss();

                        Glide.with(getContext()).load(image_URl).into(circleImageView);

                        Log.e("ProfileURl",""+image_URl);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    @Override
    public void onResume() {
        try {
            String ImageURL=PreferenceUtil.getUserImage(getContext());
            if (ImageURL!=null){
                Glide.with(getContext()).load(ImageURL).into(circleImageView);
            }
            else {
                circleImageView.setBackgroundResource(R.drawable.user);

            }
            Log.e("ProfileUrl",""+ImageURL);
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onResume();
    }

    public class BannerAds extends AsyncTask<String,String,JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("page_type",strings[0]));
            JSONObject jsonObject=webServiceURL.BannerAds(userData);
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
                Log.e("BannerADs",""+jsonObject.toString());
                Gson gson=new Gson();
                ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                if (responseClass.getSuccess()==1){
                    String BannerLink=responseClass.getResult().getBannerData().getRedirectUrl();



                }
            }
        }
    }


    private void selectImage() {
        try {
            if ( ActivityCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST);
            } else {
                requestGalleryPermissions();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestGalleryPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXT_REQUEST);
    }
    private boolean checkGalleryPermission() {
        int result1 = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }

}





