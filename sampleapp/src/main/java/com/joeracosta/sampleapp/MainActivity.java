package com.joeracosta.sampleapp;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.joeracosta.sampleapp.view.MapScreens.WhiteScreen;
import com.joeracosta.sampleapp.view.MapScreens.PurpleScreen;
import com.joeracosta.sampleapp.view.MapScreens.StackHostScreen;

import com.joeracosta.library.Map.ViewMap;
import com.joeracosta.library.Map.ViewMapHost;


public class MainActivity extends AppCompatActivity implements ViewMapHost {

    private static final String MAP_TAG = "map";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ViewMap mViewMap;
    private ActionBarDrawerToggle mDrawerToggle;

    private String[] colorEntries = {"Stack", "Purple", "White"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewMap = ViewMap.create((ViewGroup) findViewById(R.id.container));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setupDrawer();

        //rebuild from instance state if it's there
        if (savedInstanceState != null) {
            mViewMap.rebuildFromBundle(savedInstanceState, MAP_TAG);
        } else {
            mViewMap.show(R.id.stackhost_screen, new StackHostScreen.Factory());
            mDrawerList.setItemChecked(0, true);
            setTitle(colorEntries[0]);
        }

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            switch (position){
                case 0:
                    mViewMap.show(R.id.stackhost_screen, new StackHostScreen.Factory());
                    break;
                case 1:
                    mViewMap.show(R.id.purple_screen, new PurpleScreen.Factory());
                    break;
                case 2:
                    mViewMap.show(R.id.white_screen, new WhiteScreen.Factory());
                    break;
            }
            mDrawerList.setItemChecked(position, true);
            setTitle(colorEntries[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mViewMap.saveToBundle(outState, MAP_TAG);
        super.onSaveInstanceState(outState);
        Log.d("testing", "MainActivity onSaveInstanceState bundle:" + outState);
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getActionBar() != null) {
            getActionBar().setTitle(title);
        }
    }

    @Override
    public ViewMap getViewMap() {
        return mViewMap;
    }

    @Override
    public void onBackPressed() {
        boolean handled = mViewMap.onBackPressed();
        if (!handled){
            super.onBackPressed();
        }
    }


    //region side navigation setup
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawer(){
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, R.id.text, colorEntries));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    //endregion
}
