package online.pandaapps.gre.projecteuler;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

import online.pandaapps.gre.projecteuler.Euler.ProblemLanding;
import online.pandaapps.gre.projecteuler.Storage.SharedPrefStorage;

public class MainActivity extends AppCompatActivity {

    double version_number = 1.0;
    SharedPrefStorage spStorage;
    String serverDate, spDate;
    StringRequest getDateReq, getEulerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spStorage = new SharedPrefStorage(this);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());


        getDateReq = new StringRequest(Request.Method.GET, Constants.urlDate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject serverOp = new JSONObject(response);
                    serverDate = serverOp.getString("date_updated");
                    Date spDate_date = dateFormat.parse(spDate);
                    Date webDate_date = dateFormat.parse(serverDate);

                    if (webDate_date.after(spDate_date)){
                        // download the db file and save it
                        // update shared preference
                        spStorage.setDBdate(serverDate);
                    }

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof TimeoutError){
                    Snackbar.make(getWindow().getDecorView().getRootView(),Constants.NetworkError, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        getEulerDB = new StringRequest(Request.Method.GET, Constants.urlDB, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                        String problem = indiQuestion.getString("problem");
                        String images_link = indiQuestion.getString("image_links");



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof TimeoutError){
                    Snackbar.make(getWindow().getDecorView().getRootView(),Constants.NetworkError, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(getEulerDB);


//        int firstRun = spStorage.getMainActivityFirstRun();
//        if (firstRun == 0){
//            spStorage.setMainActivityFirstRun(1);
//            // download db and save to storage
//            // first run
//        }else {
//            // nth run
//            Volley.newRequestQueue(getApplicationContext()).add(getDateReq);
//
//        }

        try {
            version_number = Double.parseDouble(getPackageManager().getPackageInfo(getPackageName(),0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // check app version and reset sp if necessary

        double version_number_sp = Double.parseDouble(spStorage.getVersion());
        int compare_value = Double.compare(version_number,version_number_sp);
        if (compare_value>0){
            // app new
            spStorage.setVersion(String.valueOf(version_number));
            // set other shared preference to default value
        }else if (compare_value<0){
            // app old
            // no shared pref editing
        }else {
            // same version
            // no shared pref editing
        }
        // check db version
        spDate = spStorage.getDBdate();

        // update if necessary


        // first time download the content

        // demo starts ----- replace this piece of code with
        // splash animation and automated redirection
        Button demo = (Button) findViewById(R.id.demo);
        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ProblemLanding.class));
            }
        });
        // end of demo
    }
}
