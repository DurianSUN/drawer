package com.ifast.menuDrawer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ifast.menuDrawer.R;

import java.util.ArrayList;
import java.util.Locale;

public class SelectLanguagesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_languages);
        ListView language = (ListView) findViewById(R.id.language);
        language.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,getData()));
        language.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("position", "" + position);
                switch (position){
                    case 0:{
                        setLocale("en");
                        break;
                    }
                    case 1:{
                        setLocale("zh");
                        break;
                    }
                }
            }
        });
    }

    public ArrayList<String> getData(){
        String[] languages = getResources().getStringArray(R.array.language);
        ArrayList<String> list = new ArrayList<String>();
        for (String language:languages){
            list.add(language);
        }
        return list;
    }

    public void setLocale(String lang){
        Locale locale = new Locale(lang);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        conf.locale = locale;
        resources.updateConfiguration(conf,dm);
        Intent refresh = new Intent(this,MainActivity.class);
        startActivity(refresh);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_languages, menu);
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
}
