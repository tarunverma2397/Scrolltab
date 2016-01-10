package projects.scrolltab;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class other_news extends AppCompatActivity {
    RequestQueue requestQueue;
    ListView l;
    List<String> titlist = new ArrayList<String>();
    List<String> conlist = new ArrayList<>();
    adapt adapter;
    String loginURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.content_other_news, null, false);
        requestQueue = Volley.newRequestQueue(this);
        l = (ListView) findViewById(R.id.othernewslist);
        Bundle bun=new Bundle();
        bun=getIntent().getBundleExtra("url");
        loginURL=bun.getString("urls");
        // output = (ImageView) findViewById(R.id.jsondata);
        StringRequest jor = new StringRequest(Request.Method.GET, loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // String response = stripHtml(request);
                try {
                    JSONArray new_array = new JSONArray(response);

                    for (int i = 0; i < new_array.length(); i++) {


                        JSONObject baseObject = new_array.getJSONObject(i);
                        JSONObject titleObject = baseObject.getJSONObject("title");
                        titlist.add(stripHtml(titleObject.getString("rendered")));
                        JSONObject contentObject = baseObject.getJSONObject("content");
                        conlist.add(stripHtml(contentObject.getString("rendered")));


                    }

                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jor);

        adapter = new adapt(this, titlist,conlist);
        l.setAdapter(adapter);

    }

    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }

    public class adapt extends BaseAdapter {
        List<String> titlist = new ArrayList<String>();
        List<String> conlist = new ArrayList<>();
        Context context;

        adapt(Context context, List<String> title, List<String> content) {
            titlist = title;
            conlist = content;
            this.context = context;
        }

        @Override
        public int getCount() {
            return titlist.size();
        }

        @Override
        public Object getItem(int position) {
            return titlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.cusforothernews, parent, false);
            TextView head = (TextView) row.findViewById(R.id.otherhead);
            TextView conten = (TextView) row.findViewById(R.id.othercont);
            head.setText(titlist.get(position));
            conten.setText(conlist.get(position));

            return row;
        }
    }
}
