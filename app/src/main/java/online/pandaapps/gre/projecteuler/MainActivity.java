package online.pandaapps.gre.projecteuler;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.os.Handler;

import online.pandaapps.gre.projecteuler.Euler.SingleProblemOffline;
import online.pandaapps.gre.projecteuler.Storage.SQLITE3storage;
import online.pandaapps.gre.projecteuler.Storage.SharedPrefStorage;
import online.pandaapps.gre.projecteuler.Utils.Constants;

public class MainActivity extends AppCompatActivity {

    SharedPrefStorage spStorage;
    String serverDate, spDate;
    StringRequest getDateReq, getEulerDB;
    SQLITE3storage dbStorage;
    ImageView loadingAnimation;
    TextView status;
    Date spDate_date, serverDate_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spStorage = new SharedPrefStorage(this);
        dbStorage = new SQLITE3storage(this);

        loadingAnimation = (ImageView) findViewById(R.id.loaderImage);
        status = (TextView) findViewById(R.id.DownloadStatus);
        Animation homeLogoAnim = AnimationUtils.loadAnimation(this, R.anim.home_screen);
        loadingAnimation.startAnimation(homeLogoAnim);
        homeLogoAnim.setRepeatCount(Animation.INFINITE);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        status.setText(R.string.connecting_server);
        final Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), Constants.NetworkError, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        getDateReq = new StringRequest(Request.Method.GET, Constants.urlDate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                status.setText(R.string.blank);
                try {
                    JSONObject serverOp = new JSONObject(response);
                    serverDate = serverOp.getString("date_updated");
//                    System.out.println(serverDate);
                    spDate = spStorage.getDBdate();
                    spDate_date = dateFormat.parse(spDate);
                    serverDate_date = dateFormat.parse(serverDate);

                    if (serverDate_date.after(spDate_date)) {
                        spStorage.setUpdateDBFlag(1);
                        spStorage.setDBdate(serverDate);
                    }

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                status.setText(R.string.blank);
                if (error instanceof NoConnectionError || error instanceof TimeoutError) {
                    snackbar.show();
                }
            }
        });



        status.setText(R.string.ready);
        getEulerDB = new StringRequest(Request.Method.GET, Constants.urlDB, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                status.setText(R.string.blank);
                try {
                    JSONArray serverOp = new JSONArray(response);
                    for (int i = 0; i < serverOp.length(); i++) {
                        JSONObject indiQuestion = serverOp.getJSONObject(i);

                        int slNo = indiQuestion.getInt("id");
                        String title = indiQuestion.getString("title");
                        int difficulty = indiQuestion.getInt("difficulty");
                        int solvedBy = indiQuestion.getInt("solved_by");
                        String datePublished = indiQuestion.getString("date");
                        String timePublished = indiQuestion.getString("time");
                        dbStorage.insertData(slNo, datePublished, timePublished, title, difficulty, solvedBy);

                    }
                    Intent start = new Intent(getApplicationContext(),SingleProblemOffline.class);
//                    Intent start = new Intent(getApplicationContext(),ProblemLanding.class);
                    startActivity(start);
                    overridePendingTransition(R.anim.bottomup,R.anim.bottomup);
                    finish();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                status.setText(R.string.blank);
                if (error instanceof NoConnectionError || error instanceof TimeoutError) {
                    snackbar.show();
                }
            }
        });

        // first time download the content

        int firstRun = spStorage.getMainActivityFirstRun();
        if (firstRun == 0) {
            System.out.println("first run");
            spStorage.setMainActivityFirstRun(1);
            Volley.newRequestQueue(getApplicationContext()).add(getEulerDB);
            dbStorage.setComment(0,"abc");

            // download db and save to storage
            // first run
        } else {
            // nth run
            Volley.newRequestQueue(getApplicationContext()).add(getDateReq);
            System.out.println("nth run");
            int dbDownload = spStorage.getUpdateDBFlag();

            if (dbDownload == 1) {
                this.deleteDatabase(Constants.dbName);
                Volley.newRequestQueue(getApplicationContext()).add(getEulerDB);
                spStorage.setUpdateDBFlag(100);
            }else {
                // delay of 4 sec
                // start next activity
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent landing = new Intent(getApplicationContext(),SingleProblemOffline.class);
//                        Intent landing = new Intent(getApplicationContext(),ProblemLanding.class);
                        startActivity(landing);
                        overridePendingTransition(R.anim.bottomup,R.anim.bottomup);
                        finish();
                    }
                }, 2500);
            }

        }


    }

}
