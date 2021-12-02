package com.oliek.cartrout.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;


public abstract class BaseFragment extends Fragment {
    private static final String ACTIVITY_BUNDLE_EXTRA = "ACTIVITY_BUNDLE_EXTRA";
    public BaseFragment getFragmentActivity(){
        return this;
    }
    private ProgressDialog progressDialog;



    protected void showError(int errorMsgResId){
        Toast.makeText(this.getActivity(), errorMsgResId, Toast.LENGTH_LONG).show();
    }

    protected void showError(String errorMsgResId){
        Toast.makeText(this.getActivity(), errorMsgResId, Toast.LENGTH_LONG).show();
    }

    public <T extends FragmentActivity>  void replaceContentFragment(final T activity, final int containerId, final Fragment fragment, Bundle args){

        if (activity != null && !activity.isFinishing() && fragment != null) {
            FragmentTransaction beginTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragment.setArguments(args);
            beginTransaction.replace(containerId, fragment).commitAllowingStateLoss();
        }


    }
    public void showAlertDialog(String mesege, Context context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(mesege);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public <T extends FragmentActivity>   void replaceContentFragment(final T activity, final int containerId, final Fragment fragment) {
        if (activity != null && !activity.isFinishing() && fragment != null) {
            FragmentTransaction beginTransaction = activity.getSupportFragmentManager().beginTransaction();
            beginTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            beginTransaction.replace(containerId, fragment).commitAllowingStateLoss();
        }
    }

    public <T extends FragmentActivity>  void replaceAndAddContentFragment(final T activity, final int containerId, final Fragment fragment, Bundle args, String tag) {
        if (activity != null && !activity.isFinishing() && fragment != null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            fragment.setArguments(args);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.replace(containerId, fragment);
            transaction.addToBackStack(tag);
            transaction.commitAllowingStateLoss();

        }
    }

    protected  <T extends Activity> void showNextScreen(Class<T> activity, Bundle activityExtra){
        Intent intent = new Intent(this.getActivity(), activity);
        intent.putExtra(ACTIVITY_BUNDLE_EXTRA, activityExtra);
        getContext().startActivity(intent);
    }


    protected  <T extends Activity> void showNextScreenBack(Class<T> activity, Bundle activityExtra){

        Intent intent=new Intent(this.getActivity(),activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ACTIVITY_BUNDLE_EXTRA, activityExtra);
        getContext().startActivity(intent);
        getFragment().getActivity().finish();


    }


    protected  <T extends Activity> void showNextScreenByFinish(Class<T> activity, Bundle activityExtra){
        Intent intent = new Intent(this.getActivity(), activity);
        intent.putExtra(ACTIVITY_BUNDLE_EXTRA, activityExtra);
        getContext().startActivity(intent);
        getFragment().getActivity().finish();
    }

    protected Fragment getFragment(){
        return this.getFragment();
    }



    protected boolean havePermission(String permission) {
        return Build.VERSION.SDK_INT < GlobalConstants.SDK_INT_MARSHMALLOW || ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(final String permission, final String permissionExplanationMessage, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {

            DialogFactory.showDialog((AppCompatActivity)getContext(), R.string.permission_required, permissionExplanationMessage, R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ActivityCompat.requestPermissions((AppCompatActivity) getContext(),
                            new String[]{permission}, requestCode);
                }
            }, R.string.cancel, null, true);


        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{permission}, requestCode);
        }
    }
    public void onPermissionResult(int requestCode, boolean permissionGranted){}
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        boolean permissionStatus = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        onPermissionResult(requestCode, permissionStatus);
    }
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
}