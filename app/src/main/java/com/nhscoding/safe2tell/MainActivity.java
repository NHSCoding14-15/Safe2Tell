package com.nhscoding.safe2tell;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.nhscoding.safe2tell.API.PostParser;
import com.nhscoding.safe2tell.API.ProblemObject;
import com.nhscoding.safe2tell.API.ProblemParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        STORIES.OnFragmentInteractionListener,
        Card.OnFragmentInteractionListener,
        ViewPagerTest.OnFragmentInteractionListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

//    String[] drawerArray = getResources().getStringArray(R.array.drawerFields);

    int place;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    List Posts;
    List Problems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment objFragment = null;

        switch (position) {
            case 0:
                place = 0;
                mTitle = "Stories";
                objFragment = new STORIES();
                break;

            case 1:
                objFragment = new ABOUT_US();
                mTitle = "About Us";
                place = 1;
                break;

            case 2:
                place = 2;
                mTitle = "Learn";
                objFragment = new PROBLEM(0);
                break;

            case 3:
                place = 3;
                mTitle = "Tip";
                Intent intent = new Intent(this, SUBMIT_TIP.class);
                startActivity(intent);
                return;

            default:
                place = -1;
                mTitle = "ERROR";
                objFragment = new PlaceholderFragment();
                CardView c;
                break;

        }
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, objFragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        String title = getResources().getStringArray(R.array.drawerFields)[number - 1];
        mTitle = title;
    }

    public void restoreActionBar() {
        if ((place != 2) && (place != 4)) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        } else {
            PostParser postParser = new PostParser();
            postParser.execute();
            InputStream postIn;

            ProblemParser problemParser = new ProblemParser();
            problemParser.execute();
            InputStream problemIn;

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                postIn = postParser.get(5000, TimeUnit.MILLISECONDS);
                Posts = postParser.readJSONStream(postIn);

                problemIn = problemParser.get(5000, TimeUnit.MILLISECONDS);
                Problems = problemParser.readJSONStream(problemIn);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] problemArray = new String[Problems.size()];

            for (int i = 0; i < Problems.size(); i++) {
                ProblemObject problemObject = (ProblemObject) Problems.get(i);
                problemArray[i] = problemObject.Name;
            }

            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, problemArray);

            ActionBar.OnNavigationListener callback = new ActionBar.OnNavigationListener() {

                @Override
                public boolean onNavigationItemSelected(int i, long l) {
                    Fragment frag = new PROBLEM(i);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, frag)
                            .commit();
                    return true;
                }
            };

            ActionBar actionBar = getSupportActionBar();
            int a = actionBar.NAVIGATION_MODE_LIST;
            actionBar.setNavigationMode(a);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setListNavigationCallbacks(arrayAdapter, callback);
            mTitle = "Problem";
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            //((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
