package com.mitelcel.pack.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mitelcel.pack.R;


public class FragmentHandler {

    /**
     * ATTENZIONE: Quando si usa questo metodo per inserire il primo fragment nell'activity, la variabile "backStackName" deve essere passato null!!!
     * @param fragmentManager
     * @param backStackName
     * @param tag
     * @param fragment
     * @param fragContainerResId
     * @return
     */
    public static boolean addFragmentInBackStack(FragmentManager fragmentManager, String backStackName, String tag, Fragment fragment, int fragContainerResId){

        /**
         *
         */
        if(fragmentManager.findFragmentByTag(tag) == null || (fragmentManager.findFragmentByTag(tag) != null && !fragmentManager.findFragmentByTag(tag).isVisible())){
            FragmentTransaction ft;
            ft = fragmentManager.beginTransaction();

            ft.replace(fragContainerResId, fragment, tag);
            if(backStackName != null){
                ft.addToBackStack(backStackName);
            }

            ft.commit();
            return true;
        }
        return false;
    }

    /**
     * @param fragmentManager
     * @param tag
     * @param fragment
     * @param fragContainerResId
     * @return
     */
    public static boolean addFragment(FragmentManager fragmentManager, String tag, Fragment fragment, int fragContainerResId){

        /**
         *
         */
        if(fragmentManager.findFragmentByTag(tag) == null || (fragmentManager.findFragmentByTag(tag) != null && !fragmentManager.findFragmentByTag(tag).isVisible())){
            FragmentTransaction ft;
            ft = fragmentManager.beginTransaction();
            ft.add(fragContainerResId, fragment, tag);
            ft.commit();
            return true;
        }
        return false;
    }

    /**
     * @param fragmentManager
     * @param tag
     * @param fragment
     * @param fragContainerResId
     * @return
     */
    public static boolean replaceFragment(FragmentManager fragmentManager, String tag, Fragment fragment, int fragContainerResId){

        /**
         *
         */
        if(fragmentManager.findFragmentByTag(tag) == null || (fragmentManager.findFragmentByTag(tag) != null && !fragmentManager.findFragmentByTag(tag).isVisible())){
            FragmentTransaction ft;
            ft = fragmentManager.beginTransaction();
            ft.replace(fragContainerResId, fragment, tag);
            ft.commit();
            return true;
        }
        return false;
    }

    public static void clearFragmentBackStack(FragmentManager fragmentManager, String backStackName){
        fragmentManager.popBackStack (backStackName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static boolean addFragmentInBackStackWithAnimation(FragmentManager fragmentManager, String backStackName, String tag, Fragment fragment, int fragContainerResId){

        /**
         *
         */

        if(fragmentManager.findFragmentByTag(tag) == null /*|| (fragmentManager.findFragmentByTag(tag) != null && !fragmentManager.findFragmentByTag(tag).isVisible())*/){

            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);

            ft.replace(fragContainerResId, fragment, tag);
            if(backStackName != null){
                ft.addToBackStack(backStackName);
            }
            ft.commit();
            return true;
        }else{
            fragmentManager.popBackStack();
        }
        return false;
    }

}
