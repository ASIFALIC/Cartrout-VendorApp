package com.oliek.cartrout;

public class GlobalConstants {

    public static String BASE_URL="https://www.cartrout.com/api/v1/";
    public static String BASE_IMAGE_URL="https://www.cartrout.com/";
    public static final String ACTIVITY_BUNDLE_EXTRA = "ACTIVITY_BUNDLE_EXTRA";


    //Permissions

    public static final int STORAGE_PERMISSION_REQUEST_CODE = 103;
    public static final int SMS_PERMISSION_REQUEST_CODE = 110;
    public static final int READ_CONTACTS_PERMISSION_REQUEST_CODE = 104;
    public static final int READ_GSERVICES_PERMISSION_REQUEST_CODE = 105;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 106;
    public static final int ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE = 107;
    public static final int REQUEST_SMS_WRITE_PERMISSION = 112;
    public static final int REQUEST_SMS_READ_PERMISSION = 101;
    public static final int REQUEST_SMS_RECEIVE_PERMISSION = 113;
    public static final int REQUEST_ACCESS_CAMERA = 102;
    public static final int REQUEST_ACCESS_LOCATION = 107;
    public static final int REQUEST_ACCESS_CATEGORY_CAMERA = 108;
    public static final int REQUEST_ACCESS_CATEGORY_STORAGE = 109;
    public static final int REQUEST_PERMISSION_READ_STORAGE_CODE=110;
    public static final int REQUEST_PERMISSION_CALL_CODE=120;
    public static final int SDK_INT_MARSHMALLOW = 23;
    public static final int PTODUCT_TYP_ID=7;
    public static final String DIRECTORY_IMAGE ="Images" ;




    public static final String STORAGE_PERMISSION_REQUEST_MESSAGE = "Writing Storage permission is required";
    public static final String READ_MESSAGE_PERMISSION_REQUEST_MESSAGE = "Read SMS permission is required";
    public static final String CAMERA_PERMISSION_REQUEST_MESSAGE="Camera permission is required";
    public static final String ACCESS_FINE_LOCATION_PERMISSION_REQUEST_MESSAGE="Location permission is required";
    public static final String REQUEST_PERMISSION_READ_STORAGE_MESSAGE="Storage permission is required";
    public static final String READ_SMS_PERMISSION_MESSAGE="SMS read permission is required";
    public static final String WRITE_SMS_PERMISSION_MESSAGE="SMS write permission is required";
    public static final String RECEIVE_SMS_PERMISSION_MESSAGE="SMS receive permission is required";
    public static final String REQUEST_PERMISSION_CALL="Call action permission required";

    public static final String VIEW = "view";

    public static final String EDIT = "Are you sure you want to edit ?";
    public static final String UPDATE = "are you sure you want to update ?";
    public static final String DELETE = "are you sure you want to delete ?";
    public static final String CANCEL = "are you sure you want to cancel ?";


    public static final String LOGIN_ADMIN = "please contact the admin";
    public static final String LOGIN = "please contact the admin,you are not authorized to Login";
    public static final String ADMIN_CONTACT = "you are not authorized. please contact the admin";
    public static final String LIMIT_CONTACT = "you reached the maximum . please contact the admin";
    public static String SUCCESS="succes";
    public static final Object STATUS = "status";

    public static String NO_INTERNET = "Bad network connection";
    public static String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static String TAG="tag";

    public static int SPLASH_TIME_OUT = 4100;

    public static final String USER_PREFS = "USER_PREFS";
    public static final String PREF_KEY_USER ="pref_key_user";
    public static final String PREF_KEY_TOKEN ="pref_key_token";
    public static final String PREF_KEY_SONG ="pref_key_song";
    public static final String SPLASH_BACKGROUND_COUNT = "splash_background_count";
    public static final String LOGIN_BACKGROUND_COUNT = "login_background_count";
    public static final String REGISTRATION_BACKGROUND_COUNT = "registration_background_count";
    public static final String PREF_KEY_SUB ="pref_key_sub";

    //Bundle or data passing
    public static final String DATA ="data";
    public static final String SONG ="SONG";
    public static final String DUA ="DUA";
    public static final String SONG_LIST ="SONGLIST";
    public static final String PLAY ="PLAY";
    public static final String PAUSE ="PAUSE";
    public static final String ADD_FAVORITE ="ADD FAVORITE";
    public static final String REMOVE_FAVORITE ="REMOVE FAVORITE";
    public static final String SONG_INDEX ="SongIndex";

    //SongBuyNow callbacks
    public static final String BUY_NOW ="BUYNOW";
    public static final String CART ="CART";

    public static final String PLAY_SECONDS ="PlaySeconds";
    public static final String DIALOG_SENDER_HOME ="Home";
    public static final String DIALOG_SENDER_PLAYER ="Payer";

    //network success messages
    public static final String FAVORITE_SONG_ADDED_MESSAGE ="Song added to favorites";
    public static final String FAVORITE_SONG_REMOVED_MESSAGE ="Song removed from favorites";
    public static final String CART_SONG_ADDED_MESSAGE ="Song added to cart";
    public static final String CART_SONG_REMOVED_MESSAGE ="Song removed from cart";

    public static final String DOWNLOAD_PENDING_MESSAGE = "";


    public static String plus ="+";


    /*ITEM STATUS*/
    public static final String KEY_ACTVE= "1";
    public static final String KEY_INACTVE= "0";

    /*ORDER STATUS*/
    public static final String KEY_CANCELLED= "0";
    public static final String KEY_PLACED= "1";
    public static final String KEY_CONFIRMED= "2";
    public static final String KEY_READY_TO_PICK= "3";
    public static final String KEY_ASSIGNED= "9";


    public static final String KEY_0= "Pending";
    public static final String KEY_1= "Confirmed";
    public static final String KEY_2= "Delivered";
    public static final String KEY_3="Cancelled";
    public static final String KEY_4T="Ready to Pick Up";
    public static final String KEY_4H="Out For Delivery";


}


