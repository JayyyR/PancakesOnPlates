package com.joeracosta.mapapp;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.joeracosta.mapapp.view.MapScreens.WhiteScreen;
import com.joeracosta.mapapp.view.MapScreens.PurpleScreen;
import com.joeracosta.mapapp.view.MapScreens.StackHostScreen;

import me.mattlogan.library.Map.ViewMap;
import me.mattlogan.library.Map.ViewMapHost;


public class MainActivity extends AppCompatActivity implements ViewMapHost {

    private static final String MAP_TAG = "map";
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    String[] colorEntries = {"stack", "purple", "white"};

    private ViewMap viewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewMap = ViewMap.create((ViewGroup) findViewById(R.id.container));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, R.id.text, colorEntries));
        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        if (savedInstanceState != null) {
            viewMap.rebuildFromBundle(savedInstanceState, MAP_TAG);
        } else {
            viewMap.show(R.id.stackhost_screen, new StackHostScreen.Factory());
            drawerList.setItemChecked(0, true);
            setTitle(colorEntries[0]);

        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            switch (position){
                case 0:
                    viewMap.show(R.id.stackhost_screen, new StackHostScreen.Factory());
                    break;
                case 1:
                    viewMap.show(R.id.purple_screen, new PurpleScreen.Factory());
                    break;
                case 2:
                    viewMap.show(R.id.white_screen, new WhiteScreen.Factory());
                    break;
            }
            drawerList.setItemChecked(position, true);
            setTitle(colorEntries[position]);
            drawerLayout.closeDrawer(drawerList);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        viewMap.saveToBundle(outState, MAP_TAG);
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
        return viewMap;
    }

    @Override
    public void onBackPressed() {
        boolean handled = viewMap.onBackPressed();
        if (!handled){
            super.onBackPressed();
        }
    }
}
