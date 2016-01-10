package projects.scrolltab;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import projects.scrolltab.R;

public class top_national_daily extends AppCompatActivity {
    ImageView output;
    ImageLoadTask t;
    String loginURL;
    // List <String> data ;
    String data;
    List<String> html = new ArrayList<String>();
    int j;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    List<String> entries = new ArrayList<String>();


    cusadapter adapt;
    RequestQueue requestQueue;
    ListView l;
    List<news> n = new ArrayList<news>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_national_daily);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bun=getIntent().getBundleExtra("url");

        loginURL=bun.getString("urls");
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.content_top_national_daily, null, false);
        requestQueue = Volley.newRequestQueue(this);
        l= (ListView) findViewById(R.id.listview2);
        // output = (ImageView) findViewById(R.id.jsondata);
        StringRequest jor = new StringRequest(Request.Method.GET, loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // String response = stripHtml(request);
                try {
                    JSONArray new_array = new JSONArray(response);
                    // j=-1;
                    for (int i = 0; i < new_array.length(); i++) {


                        JSONObject baseObject = new_array.getJSONObject(i);
                        JSONObject titleObject = baseObject.getJSONObject("title");
                        news newi = new news();
                        newi.setTitle(stripHtml(titleObject.getString("rendered")));
                        JSONObject contentObject = baseObject.getJSONObject("content");
                        newi.setContent(stripHtml(contentObject.getString("rendered")));
                        //  content = contentObject.getString("rendered");
                        // Toast.makeText(getApplicationContext(),newi.getContent(),Toast.LENGTH_SHORT).show();

                        StackOverflowXmlParser s = new StackOverflowXmlParser();
                        // StringToInputStreamExample cl;
                        try {
                            html = s.parse(new ByteArrayInputStream(contentObject.getString("rendered").getBytes()));

                            newi.setImageurl(html.get(i));

                        } catch (IOException e) {
                            Log.d("log_tag", "onCreate IO Exception");
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            Log.d("LOG_TAG", "onCreate XMLPullParser Exception");
                            e.printStackTrace();
                        }

                        n.add(newi);

                    }
                    adapt.notifyDataSetChanged();

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

        adapt = new cusadapter(this, n);
        l.setAdapter(adapt);

    }

    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }

    class cusadapter extends BaseAdapter {
        Context context;
        public List<news> newsarr = new ArrayList<>();

        cusadapter(Context c, List<news> newsarr) {

            this.context = c;
            this.newsarr = newsarr;


        }

        @Override
        public int getCount() {
            return newsarr.size();
        }

        @Override
        public Object getItem(int position) {
            return newsarr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.cus_view, parent, false);
            TextView head = (TextView) row.findViewById(R.id.textView);
            TextView conten = (TextView) row.findViewById(R.id.textView2);
            ImageView img = (ImageView) row.findViewById(R.id.imageView);
            news n = newsarr.get(position);
            head.setText(n.getTitle());
            conten.setText(n.getContent());
            new ImageLoadTask(n.getImageurl(), img).execute();


            return row;
        }
    }

    public class StackOverflowXmlParser {
        // We don't use namespaces
        private final String ns = null;
        String link = "default";

        public List parse(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);

                parser.nextTag();
                Log.d("iii", parser.getName());
                return readFeed(parser);
            } finally {
                in.close();
            }
        }

        // ...
        private List readFeed(XmlPullParser parser) {
            Log.d("iii", "inside" + parser.getName());
            try {

                while (parser.next() != XmlPullParser.END_TAG) {

                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();

                    Log.d("ll", "Entered Tag : " + name);
                    // Starts by looking for the entry tag
                    if (name.equals("a")) {

                        // Log.d(LOG_TAG , "Found A!!");
                        link = parser.getAttributeValue(null, "href");
                        //Log.d(LOG_TAG, "Entries : " + entries.size());
                        // break;

                        /*if(parser.getName()==null)
                        {
                            //parser.nextTag();
                           // continue;
                        }*/

                        entries.add(link);


                        // break;
                    } else {
                        // Log.d(LOG_TAG , "skip called");
                        skip(parser);
                        // Log.d(LOG_TAG , "skip returned");*/

                        Log.d("kk", "else part called");
                    }

                }
                // Log.d(LOG_TAG , "Inside try Entries : " + entries.size());
            } catch (XmlPullParserException e) {
                link = "http://a5.mzstatic.com/us/r30/Purple3/v4/19/f8/10/19f8103a-1ab0-6161-809a-84b255395c7a/icon175x175.jpeg";
                // e.printStackTrace();
                Log.d("LOG_TAG", "XML Pull Parser Exception");
            } catch (IOException e) {
                e.printStackTrace();
                //  Log.d(LOG_TAG , "IO Exception");
            }
            Log.d("kk", "Outside try Entries : " + parser.getName());
            return entries;
        }


    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }
    }
}
