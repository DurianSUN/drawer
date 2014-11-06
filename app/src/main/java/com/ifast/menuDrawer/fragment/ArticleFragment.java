package com.ifast.menuDrawer.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ifast.menuDrawer.R;
import com.ifast.menuDrawer.activity.ArticleDetailActivity;
import com.ifast.menuDrawer.entity.Article;
import com.ifast.menuDrawer.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhiwen on 2014/11/4.
 */
public class ArticleFragment extends Fragment {

    private String articlesURL /*= "http://mobile.fundsupermart.com.sg/mobile/datafeedretriever?mid=LatestArticles&locale=zh"*/;

    SimpleAdapter adapter;
    View rootView;
    ListView articleItem;

    public void setData(List<HashMap<String, Object>> data) {
        this.data = data;
    }

    List<HashMap<String, Object>> data = null;

    public ArticleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_article, container, false);
        articleItem = (ListView) rootView.findViewById(R.id.articles);
        if(isAdded()){
            articlesURL = getResources().getString(R.string.latest_articles);
            Log.i("articlesURL", articlesURL);
        }
        new myAsyncTask().execute(articlesURL);
        return rootView;

    }

    private class myAsyncTask extends AsyncTask<String, Void, List<HashMap<String, Object>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<HashMap<String, Object>> doInBackground(String... params) {
            JSONObject jobj = JSON.parseObject(HttpUtils.sendPostMessage(params[0], "UTF-8"));
            JSONArray arr = jobj.getJSONArray("LatestArticles");
            List<Article> articles = JSON.parseArray(arr.toString(), Article.class);
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            HashMap<String, Object> map = null;
            for (Article article : articles) {
                map = new HashMap<String, Object>();
                map.put("article_title", article.getTitle());
                map.put("article_PubDate", article.getPubDate());
                map.put("article_category", article.getCategory());
                map.put("article_articleNo", article.getArticleNo());
                list.add(map);
            }
            return list;
        }

        @Override
        protected void onPostExecute(final List<HashMap<String, Object>> result) {
            adapter = new SimpleAdapter(
                    rootView.getContext(),
                    result,
                    R.layout.article_list_item,
                    new String[]{"article_title", "article_PubDate", "article_category", "article_articleNo"},
                    new int[]{R.id.article_title, R.id.article_PubDate, R.id.article_category, R.id.article_articleNo});
            articleItem.setAdapter(adapter);
            articleItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view.findViewById(R.id.article_articleNo);
                    //String articleNo = result.get(position).get("article_articleNo").toString();
                    String articleNo = textView.getText().toString();
                    Log.i("article_articleNo", articleNo);
                    Intent intent = new Intent(rootView.getContext(),ArticleDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("articleNo",articleNo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }

}