package com.joeracosta.mapapp;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.joeracosta.mapapp.view.BlueScreen;
import com.joeracosta.mapapp.view.GreenScreen;
import com.joeracosta.mapapp.view.RedScreen;

import me.mattlogan.library.ScreenMap.ViewMap;


public class MainActivity extends AppCompatActivity implements ViewMapActivity {

    private static final String MAP_TAG = "map";
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    String[] colorEntries = {"red", "green", "blue"};

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
            viewMap.show(R.id.red_screen, new RedScreen.Factory());
            drawerList.setItemChecked(0, true);
            setTitle(colorEntries[0]);

        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            switch (position){
                case 0:
                    viewMap.show(R.id.red_screen, new RedScreen.Factory());
                    break;
                case 1:
                    viewMap.show(R.id.green_screen, new GreenScreen.Factory());
                    break;
                case 2:
                    viewMap.show(R.id.blue_screen, new BlueScreen.Factory());
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
}
