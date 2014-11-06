package com.ifast.menuDrawer.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.ifast.menuDrawer.R;
import com.ifast.menuDrawer.utils.HttpUtils;


public class ArticleDetailActivity extends Activity {

    private String getArticleDetailURL;
    WebView articleDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Bundle bundle = getIntent().getExtras();
        String articleNo = bundle.getString("articleNo");
        articleDetail= (WebView) findViewById(R.id.article_detail);
        articleDetail.getSettings().setJavaScriptEnabled(true);
        getArticleDetailURL = getResources().getString(R.string.article_detail_url);
        articleDetail.loadUrl(getArticleDetailURL+articleNo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article_detail, menu);
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

    private class getArticleDetailTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return HttpUtils.sendPostMessage(params[0],"utf-8");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
