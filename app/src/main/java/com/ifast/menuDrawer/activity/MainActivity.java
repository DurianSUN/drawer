package com.ifast.menuDrawer.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ifast.menuDrawer.R;
import com.ifast.menuDrawer.fragment.ArticleFragment;
import com.ifast.menuDrawer.fragment.BlankFragment;
import com.ifast.menuDrawer.fragment.SettingFragment;


public class MainActivity extends Activity {

    private String[] menuTitles;
    private DrawerLayout drawerLayout;
    private ListView menuListView;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence menuTitle, title;
    private SimpleAdapter simpleAdapter = null;


    private void init() {
        menuTitles = getResources().getStringArray(R.array.menus_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuListView = (ListView) findViewById(R.id.left_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(menuTitle);
                invalidateOptionsMenu();
            }

            public void OnDrawerClosed(View view) {
                getActionBar().setTitle(title);
                invalidateOptionsMenu();
            }
        };

    }


    private void initLeftMenu() {
        menuListView.setAdapter(new ArrayAdapter<String>(this, R.layout.menu_drawer_list_item, menuTitles));
        menuListView.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //add some code
            Log.i("position", "" + position);
            selectItem(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initLeftMenu();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        drawerLayout.setDrawerListener(mDrawerToggle);

        //If this activity not in stack.The default item.Init Flagment.
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Create a Fragment by menu id.
    private void selectItem(int position) {
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        String menuTitle = getResources().getStringArray(R.array.menus_array)[position];
        switch (position){
            case 0:{

                fragment = new BlankFragment();
                //Pass some data to
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                //
                menuListView.setItemChecked(position, true);
                setTitle(menuTitle);
                drawerLayout.closeDrawer(menuListView);
                break;
            }
            case 1:{
                fragment = new ArticleFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                //
                menuListView.setItemChecked(position, true);
                setTitle(menuTitle);
                drawerLayout.closeDrawer(menuListView);
                break;
            }
            case 3:{
                fragment = new SettingFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                menuListView.setItemChecked(position, true);
                setTitle(menuTitle);
                drawerLayout.closeDrawer(menuListView);
                break;
            }
        }




    }

}
