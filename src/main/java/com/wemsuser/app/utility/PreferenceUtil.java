package com.wemsuser.app.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

public class PreferenceUtil {
    public static Context mcontext;
    static public void setPreferenceObject(Context c, Object modal,String key) {
        /**** storing object in preferences  ****/
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String jsonObject = gson.toJson(modal);
        prefsEditor.putString(key, jsonObject);
        prefsEditor.commit();

    }
    static public void clearPreferenceObject(Context c) {
        getSharedPreferences(c).edit().clear().apply();
    }
    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getAccessTokenFromServer(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.TOKEN, "");
    }

    public static void setAccessTokenFromServer(Context context, String useremail) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.TOKEN, useremail).apply();
    }



    public static void setSaveLoginFlag(Context context, Boolean flag) {
        getSharedPreferences(context).edit().putBoolean(Constants.PreferenceConstants.USERSESSION, flag).apply();
    }

    public static Boolean getSaveLoginFlag(Context context) {
        return getSharedPreferences(context).getBoolean(Constants.PreferenceConstants.USERSESSION,false);
    }
    public static void setButtomFlag(Context context, Boolean flag) {
        getSharedPreferences(context).edit().putBoolean(Constants.PreferenceConstants.BUTTOMSHEET, flag).apply();
    }

    public static Boolean getButtomFlag(Context context) {
        return getSharedPreferences(context).getBoolean(Constants.PreferenceConstants.BUTTOMSHEET,false);
    }


        public static String getUserName(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.USERNAME, "");
        }

        public static void setUserName(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.USERNAME, username).apply();
        }

        public static String getUserPassword(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.USERPASSWORD, "");
        }

        public static void setUserPassword(Context context, String userpassword) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.USERPASSWORD, userpassword).apply();
        }
        public static String getUserId(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.ID, "");
        }

        public static void setUserId(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.ID, username).apply();
        }
        public static String getServiceId(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.SERVICE_ID, "");
        }

        public static void setServiceId(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.SERVICE_ID, username).apply();
        }

        public static String getServiceName(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.SERVICE_NAME, "");
        }

        public static void setServiceName(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.SERVICE_NAME, username).apply();
        }

        public static String getUserPhone(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.PHONE, "");
        }

        public static void setUserPhone(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.PHONE, username).apply();
        }
        public static String getUserEmail(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.EMAIL, "");
        }

        public static void setUserEmail(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.EMAIL, username).apply();
        }
        public static String getUserImage(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.IMAGE, "");
        }

        public static void setUserImage(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.IMAGE, username).apply();
        }

        public static String getUserLatitude(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.LAT, "");
        }

        public static void setUserLatitude(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.LAT, username).apply();
        }
        public static String getUserLongitude(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.LONG, "");
        }

        public static void setUserLongitude(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.LONG, username).apply();
        }
        public static String getDistance(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.DISTANCE, "");
        }

        public static void setDistance(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.DISTANCE, username).apply();
        }
        public static String getMerchant_Id(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.MERCHANT_ID, "");
        }

        public static void setMerchant_Id(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.MERCHANT_ID, username).apply();
        }

        public static String getUser_Vehical_Type_Id(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.VEHICHAL_ID, "");
        }

        public static void setUser_Vehical_Type_Id(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.VEHICHAL_ID, username).apply();
        }

        public static void setUser_CarId(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.CARTYPE, username).apply();
        }
        public static String getUser_CarId(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.CARTYPE, "");
        }

        public static void set_Fuel_TypeID(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.FUEL, username).apply();
        }
        public static String get_Fuel_TypeID(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.FUEL, "");
        }

        public static void setCarRegistration_year(Context context, String username) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.YEAR, username).apply();
        }
        public static String getCarRegistration_year(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.YEAR, "");
        }

        public static void setFeedback_Q_Id(Context context, String question) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.FEED_Q_ID, question).apply();
        }
        public static String getFeedback_Q_Id(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.FEED_Q_ID, "");
        }

        public static void setPackage_Id(Context context, String question) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.PACKAGE, question).apply();
        }
        public static String getPackage_Id(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.PACKAGE, "");
        }
        public static void setPackage_Expire(Context context, String question) {
            getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.DATE, question).apply();
        }
        public static String getPackage_Expire(Context context) {
            return getSharedPreferences(context).getString(Constants.PreferenceConstants.DATE, "");
        }


        //Review Details
            public static void setUser_Review(Context context, String question) {
                getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.REVIEW, question).apply();
            }
            public static String getUser_Review(Context context) {
                return getSharedPreferences(context).getString(Constants.PreferenceConstants.REVIEW, "");
            }
            public static void setUser_ReviewDate(Context context, String question) {
                getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.REVIEW_DATE, question).apply();
            }
            public static String getUser_ReviewDate(Context context) {
                return getSharedPreferences(context).getString(Constants.PreferenceConstants.REVIEW_DATE, "");
            }
            public static void setUser_Review_Rating(Context context, String question) {
                getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.REVIEW_RATING, question).apply();
            }
            public static String getUser_Review_Rating(Context context) {
                return getSharedPreferences(context).getString(Constants.PreferenceConstants.REVIEW_RATING, "");
            }
            public static void setUnread_counter(Context context, String question) {
                getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.UNREAD_COUNTER, question).apply();
            }
            public static String getUnread_counter(Context context) {
                return getSharedPreferences(context).getString(Constants.PreferenceConstants.UNREAD_COUNTER, "");
                    }
            public static void setRating_id(Context context, String question) {
                getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.RATINGID, question).apply();
            }
            public static String getRating_id(Context context) {
                return getSharedPreferences(context).getString(Constants.PreferenceConstants.RATINGID, "");
            }

            public static void setHome_banner(Context context, String question) {
                getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.HOME_BANNER, question).apply();
            }
            public static String getHome_banner(Context context) {
                return getSharedPreferences(context).getString(Constants.PreferenceConstants.HOME_BANNER, "");
            }










}


