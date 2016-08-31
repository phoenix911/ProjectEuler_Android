package online.pandaapps.gre.projecteuler.Euler;

import android.app.ProgressDialog;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import online.pandaapps.gre.projecteuler.Constants;
import online.pandaapps.gre.projecteuler.R;

public class SolvedBy extends AppCompatActivity {

    WebView webview;
    Elements elements;
    String data ;
    String url = "https://projecteuler.net/news";
    Document document;
    ProgressDialog progress;
//    String url = "https://projecteuler.net/rss2_euler.xml";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solved_by);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progress=new ProgressDialog(this);
        progress.setMessage("fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();


        webview = (WebView) findViewById(R.id.webview);



        StringRequest documentReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        document = Jsoup.parse(response);
                        elements = document.select("div#content");
                        if (elements != null && !elements.isEmpty()) {
                            data = elements.toString();
                        } else {
                            // show exception
                            System.out.println("aaa");
                        }
                        String mime = "text/html";
                        String encoding = "utf-8";
                        webview.loadDataWithBaseURL(url, data, mime, encoding, null);
                        webview.getSettings().setLoadsImagesAutomatically(true);
                        webview.getSettings().setJavaScriptEnabled(true);
                        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                        progress.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                if (error instanceof NoConnectionError || error instanceof TimeoutError) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), Constants.NetworkError, Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        }
        );

        Volley.newRequestQueue(getApplicationContext()).add(documentReq);





    }
}
