package com.nhscoding.safe2tell.;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentNavigationDrawer extends DrawerLayout {
    private ActionBarDrawerToggle drawerToggle;
    private ListView lvDrawer;
    private ArrayAdapter<String> drawerAdapter;
    private ArrayList<FragmentNavItem> drawerNavItems;
    private int drawerContainerRes;

    public FragmentNavigationDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FragmentNavigationDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentNavigationDrawer(Context context) {
        super(context);
    }

    // setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer), R.layout.drawer_list_item, R.id.flContent);
    public void setupDrawerConfiguration(ListView drawerListView, int drawerItemRes, int drawerContainerRes) {
        // Setup navigation items array
        drawerNavItems = new ArrayList<FragmentNavigationDrawer.FragmentNavItem>();
        // Set the adapter for the list view
        drawerAdapter = new ArrayAdapter<String>(getActivity(), drawerItemRes,
                new ArrayList<String>());
        this.drawerContainerRes = drawerContainerRes;
        // Setup drawer list view and related adapter
        lvDrawer = drawerListView;
        lvDrawer.setAdapter(drawerAdapter);
        // Setup item listener
        lvDrawer.setOnItemClickListener(new FragmentDrawerItemListener());
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = setupDrawerToggle();
        setDrawerListener(drawerToggle);
        // set a custom shadow that overlays the main content when the drawer
        setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // Setup action buttons
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    // addNavItem("First", "First Fragment", FirstFragment.class)
    public void addNavItem(String navTitle, String windowTitle, Class<? extends Fragment> fragmentClass) {
        drawerAdapter.add(navTitle);
        drawerNavItems.add(new FragmentNavItem(windowTitle, fragmentClass));
    }

    /** Swaps fragments in the main content view */
    public void selectDrawerItem(int position) {
        // Create a new fragment and specify the planet to show based on
        // position
        FragmentNavItem navItem = drawerNavItems.get(position);
        Fragment fragment = null;
        try {
            fragment = navItem.getFragmentClass().newInstance();
            Bundle args = navItem.getFragmentArgs();
            if (args != null) {
                fragment.setArguments(args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(drawerContainerRes, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        lvDrawer.setItemChecked(position, true);
        setTitle(navItem.getTitle());
        closeDrawer(lvDrawer);
    }


    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }


    private FragmentActivity getActivity() {
        return (FragmentActivity) getContext();
    }

    private ActionBar getSupportActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    private void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    private class FragmentDrawerItemListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectDrawerItem(position);
        }
    }

    private class FragmentNavItem {
        private Class<? extends Fragment> fragmentClass;
        private String title;
        private Bundle fragmentArgs;

        public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass) {
            this(title, fragmentClass, null);
        }

        public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass, Bundle args) {
            this.fragmentClass = fragmentClass;
            this.fragmentArgs = args;
            this.title = title;
        }

        public Class<? extends Fragment> getFragmentClass() {
            return fragmentClass;
        }

        public String getTitle() {
            return title;
        }

        public Bundle getFragmentArgs() {
            return fragmentArgs;
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(getActivity(), /* host Activity */
                this, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                // setTitle(getCurrentTitle());
                // call onPrepareOptionsMenu()
                getActivity().supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                // setTitle("Navigate");
                // call onPrepareOptionsMenu()
                getActivity().supportInvalidateOptionsMenu();
            }
        };
    }

    public boolean isDrawerOpen() {
        return isDrawerOpen(lvDrawer);
    }


}




//package com.nhscoding.safe2tell;
//
//import android.support.v7.app.ActionBarActivity;
//import android.app.Activity;
//import android.support.v7.app.ActionBar;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.ActionBarDrawerToggle;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.content.SharedPreferences;
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
///**
// * Fragment used for managing interactions for and presentation of a navigation drawer.
// * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
// * design guidelines</a> for a complete explanation of the behaviors implemented here.
// */
//public class NavigationDrawerFragment extends Fragment {
//
//    /**
//     * Remember the position of the selected item.
//     */
//    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
//
//    /**
//     * Per the design guidelines, you should show the drawer on launch until the user manually
//     * expands it. This shared preference tracks this.
//     */
//    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
//
//    /**
//     * A pointer to the current callbacks instance (the Activity).
//     */
//    private NavigationDrawerCallbacks mCallbacks;
//
//    /**
//     * Helper component that ties the action bar to the navigation drawer.
//     */
//    private ActionBarDrawerToggle mDrawerToggle;
//
//    private DrawerLayout mDrawerLayout;
//    private ListView mDrawerListView;
//    private View mFragmentContainerView;
//
//    private int mCurrentSelectedPosition = 0;
//    private boolean mFromSavedInstanceState;
//    private boolean mUserLearnedDrawer;
//
//    public NavigationDrawerFragment() {
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Read in the flag indicating whether or not the user has demonstrated awareness of the
//        // drawer. See PREF_USER_LEARNED_DRAWER for details.
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);
//
//        if (savedInstanceState != null) {
//            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
//            mFromSavedInstanceState = true;
//        }
//
//        // Select either the default item (0) or the last selected item.
//        selectItem(mCurrentSelectedPosition);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        // Indicate that this fragment would like to influence the set of actions in the action bar.
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        mDrawerListView = (ListView) inflater.inflate(
//                R.layout.fragment_navigation_drawer, container, false);
//        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selectItem(position);
//            }
//        });
//        mDrawerListView.setAdapter(new ArrayAdapter<String>(
//                getActionBar().getThemedContext(),
//                android.R.layout.simple_list_item_activated_1,
//                android.R.id.text1,
//                new String[]{
//                        getString(R.string.title_section1),
//                        getString(R.string.title_section2),
//                        getString(R.string.title_section3),
//                }));
//        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
//        return mDrawerListView;
//    }
//
//    public boolean isDrawerOpen() {
//        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
//    }
//
//    /**
//     * Users of this fragment must call this method to set up the navigation drawer interactions.
//     *
//     * @param fragmentId   The android:id of this fragment in its activity's layout.
//     * @param drawerLayout The DrawerLayout containing this fragment's UI.
//     */
//    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
//        mFragmentContainerView = getActivity().findViewById(fragmentId);
//        mDrawerLayout = drawerLayout;
//
//        // set a custom shadow that overlays the main content when the drawer opens
//        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//        // set up the drawer's list view with items and click listener
//
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//
//        // ActionBarDrawerToggle ties together the the proper interactions
//        // between the navigation drawer and the action bar app icon.
//        mDrawerToggle = new ActionBarDrawerToggle(
//                getActivity(),                    /* host Activity */
//                mDrawerLayout,                    /* DrawerLayout object */
//                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
//                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
//                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
//        ) {
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//                if (!isAdded()) {
//                    return;
//                }
//
//                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                if (!isAdded()) {
//                    return;
//                }
//
//                if (!mUserLearnedDrawer) {
//                    // The user manually opened the drawer; store this flag to prevent auto-showing
//                    // the navigation drawer automatically in the future.
//                    mUserLearnedDrawer = true;
//                    SharedPreferences sp = PreferenceManager
//                            .getDefaultSharedPreferences(getActivity());
//                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
//                }
//
//                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
//            }
//        };
//
//        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
//        // per the navigation drawer design guidelines.
//        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
//            mDrawerLayout.openDrawer(mFragmentContainerView);
//        }
//
//        // Defer code dependent on restoration of previous instance state.
//        mDrawerLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mDrawerToggle.syncState();
//            }
//        });
//
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
//    }
//
//    private void selectItem(int position) {
//        mCurrentSelectedPosition = position;
//        if (mDrawerListView != null) {
//            mDrawerListView.setItemChecked(position, true);
//        }
//        if (mDrawerLayout != null) {
//            mDrawerLayout.closeDrawer(mFragmentContainerView);
//        }
//        if (mCallbacks != null) {
//            mCallbacks.onNavigationDrawerItemSelected(position);
//        }
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mCallbacks = (NavigationDrawerCallbacks) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mCallbacks = null;
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        // Forward the new configuration the drawer toggle component.
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // If the drawer is open, show the global app actions in the action bar. See also
//        // showGlobalContextActionBar, which controls the top-left area of the action bar.
//        if (mDrawerLayout != null && isDrawerOpen()) {
//            inflater.inflate(R.menu.global, menu);
//            showGlobalContextActionBar();
//        }
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//        if (item.getItemId() == R.id.action_example) {
//            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    /**
//     * Per the navigation drawer design guidelines, updates the action bar to show the global app
//     * 'context', rather than just what's in the current screen.
//     */
//    private void showGlobalContextActionBar() {
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//        actionBar.setTitle(R.string.app_name);
//    }
//
//    private ActionBar getActionBar() {
//        return ((ActionBarActivity) getActivity()).getSupportActionBar();
//    }
//
//    /**
//     * Callbacks interface that all activities using this fragment must implement.
//     */
//    public static interface NavigationDrawerCallbacks {
//        /**
//         * Called when an item in the navigation drawer is selected.
//         */
//        void onNavigationDrawerItemSelected(int position);
//    }
//}
