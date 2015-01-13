package com.example.john.safe2tell;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by John on 12/30/2014.
 */
public class menu3_Fragment extends Fragment {
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu3_layout, container, false);
        return rootview;
    }
}




/*import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup; */

/**
 * Created by John on 12/30/2014.
 */
/*public class menu3_Fragment extends FragmentActivity implements ActionBar.TabListener {

    ActionBar actionBar;
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu3_layout, container, false);
        return rootview;
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1= actionBar.newTab();
        tab1.setText("Tab 1");
        tab1.setTabListener(this);

        ActionBar.Tab tab2= actionBar.newTab();
        tab1.setText("Tab 2");
        tab1.setTabListener(this);

        ActionBar.Tab tab3= actionBar.newTab();
        tab1.setText("Tab 3");
        tab1.setTabListener(this);

        ActionBar.Tab tab4= actionBar.newTab();
        tab1.setText("Tab 4");
        tab1.setTabListener(this);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

}*/
