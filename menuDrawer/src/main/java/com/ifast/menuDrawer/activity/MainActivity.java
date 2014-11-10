package com.ifast.menuDrawer.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
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
import com.ifast.menuDrawer.fragment.AccountFragment;
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

        menuTitles = getResources().getStringArray(R.array.menus_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuListView = (ListView) findViewById(R.id.left_drawer);

        menuListView.setAdapter(new ArrayAdapter<String>(this, R.layout.menu_drawer_list_item, menuTitles));
        menuListView.setOnItemClickListener(new DrawerItemClickListener());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                Log.i("onDrawerClosed()", "drawer 关闭");
                Log.i("title", "" + title);
                getActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                Log.i("OnDrawerOpened()", "drawer 打开");
                Log.i("menuTitle", "" + menuTitle);
                getActionBar().setTitle(menuTitle);
                invalidateOptionsMenu();
            }
        };
//        initLeftMenu();
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(menuListView);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence vTitle) {
        title = vTitle;
        getActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    //Create a Fragment by menu id.
    private void selectItem(int position) {
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String menuTitle = getResources().getStringArray(R.array.menus_array)[position];
        switch (position) {
            case 0: {
                fragment = new BlankFragment();
                break;
            }
            case 1: {
                fragment = new ArticleFragment();
                break;
            }
            case 2: {
                fragment = new AccountFragment();
                break;
            }
            case 3: {
                fragment = new SettingFragment();
                break;
            }
        }
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        menuListView.setItemChecked(position, true);
        Log.i("selectItem().menu title", "" + menuTitle);
        setTitle(menuTitle);
        drawerLayout.closeDrawer(menuListView);
    }
}
