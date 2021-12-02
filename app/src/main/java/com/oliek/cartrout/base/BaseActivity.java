package com.oliek.cartrout.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.oliek.cartrout.R;

import java.lang.reflect.Field;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ACTIVITY_BUNDLE_EXTRA = "ACTIVITY_BUNDLE_EXTRA";
    public static final String KEY_MOBILE_NUMBER = "KEY_MOBILE_NUMBER";

    private Context mContext;
    public AppCompatActivity getActivity(){
        return this;
    }

    private ProgressDialog progressDialog;
    public void showProgressDialog(boolean isShowing) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        if (isShowing) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
    }


    @Override
    public void onClick(View v) {

    }

    protected void setAsFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected void setAsFullScreenWithstatusBar(View positionView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams lp = positionView.getLayoutParams();
            lp.height = statusBarHeight;
            positionView.setLayoutParams(lp);
        }
    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * A shortcut method to use {@link Context} directly than calling VIEW.getContext()
     * @return {@link Context}
     */

    /**
     * Method to show next scree/activity
     * @param activity - Class to be displayed - shoule extend activity
     * @param activityExtra - bunble to be passed through the intent
     */
    protected  <T extends Activity> void showNextScreenByFinish(Class<T> activity, Bundle activityExtra){
        Intent intent = new Intent(this, activity);
        intent.putExtra(ACTIVITY_BUNDLE_EXTRA, activityExtra);
        getActivity().startActivity(intent);
        getActivity().finish();
    }


    protected  <T extends Activity> void showNextScreenByFinishAll(Class<T> activity, Bundle activityExtra){
        Intent intent = new Intent(this, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ACTIVITY_BUNDLE_EXTRA, activityExtra);
        getActivity().startActivity(intent);
        getActivity().finish();
    }


    protected  <T extends Activity> void showNextScreenBack(Class<T> activity, Bundle activityExtra){

        Intent intent=new Intent(this,activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.putExtra(ACTIVITY_BUNDLE_EXTRA, activityExtra);
        getActivity().startActivity(intent);
        getActivity().finish();


    }




    protected  <T extends Activity> void showNextScreen(Class<T> activity, Bundle activityExtra){
        Intent intent = new Intent(this, activity);
        intent.putExtra(ACTIVITY_BUNDLE_EXTRA, activityExtra);
        getActivity().startActivity(intent);
    }

    /**
     *
     * @param activity
     * @param activityExtra
     * @param a1 - entering new activity animation
     * @param a2 - exiting old activity  animation
     * @param <T>
     */
    protected  <T extends Activity> void showAnimatedNextScreen(Class<T> activity, Bundle activityExtra, int a1, int a2){
        Intent intent = new Intent(this, activity);
        intent.putExtra(ACTIVITY_BUNDLE_EXTRA, activityExtra);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(a1,a2);
    }

    /**
     * @return {@link Bundle} - which is passed through intent to current {@link Activity}
     */
    protected Bundle getBundle(){
        return getActivity().getIntent().getBundleExtra(ACTIVITY_BUNDLE_EXTRA);
    }

    /**
     * Shows general errors and warnings
     * @param errorMsgResId - String resource id of error message to be shown
     */
    protected void showError(int errorMsgResId){
        Toast.makeText(this, errorMsgResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows general errors and warnings
     * @param errorMsgResId - String error message to be shown
     */
    protected void showError(String errorMsgResId){
        Toast.makeText(this, errorMsgResId, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected abstract void showPopUp();

    /**
     * Changing the fragment from an Activity
     * @param activity
     * @param containerId
     * @param fragment
     * @param <T>
     */
    public <T extends FragmentActivity>   void replaceContentFragment(final T activity, final int containerId, final Fragment fragment) {
        if (activity != null && !activity.isFinishing() && fragment != null) {
            FragmentTransaction beginTransaction = activity.getSupportFragmentManager().beginTransaction();
            beginTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            beginTransaction.replace(containerId, fragment).commit();
        }
    }
    public <T extends FragmentActivity>  void replaceContentFragment(final T activity, final int containerId, final Fragment fragment, Bundle args){

        if (activity != null && !activity.isFinishing() && fragment != null) {
            FragmentTransaction beginTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragment.setArguments(args);
            beginTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            beginTransaction.replace(containerId, fragment).commit();
        }


    }

    public <T extends FragmentActivity>  void replaceAndAddContentFragment(final T activity, final int containerId, final Fragment fragment, Bundle args, String tag) {
        if (activity != null && !activity.isFinishing() && fragment != null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            //    fragment.setArguments(args);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(containerId, fragment);
            transaction.addToBackStack(tag);
//            transaction.commitAllowingStateLoss();
            transaction.commit();

        }
    }

}
