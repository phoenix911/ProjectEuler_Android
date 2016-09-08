package online.pandaapps.gre.projecteuler.Euler;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import online.pandaapps.gre.projecteuler.R;
import online.pandaapps.gre.projecteuler.Storage.SQLITE3storage;
import online.pandaapps.gre.projecteuler.SwipeMenu.BaseActivity;
import online.pandaapps.gre.projecteuler.Utils.Constants;

public class SingleProblemOffline extends BaseActivity {

    int problemID;
    int problem_id,difficulty,solved_by;
    String date_published,time_published,title,question,images;
    SQLITE3storage dbStorage;
    TextView indiPTitle, infoFab, pseudoFab;
    WebView indiPQuestion;
    TextView topText;
    ProgressDialog progress;

    Elements webEle;
    Document doc;
    String data;

    String webDataStart = "<HTML>\n" +
            "<head>\n" +
            "<meta charset=\"utf-8\" />\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"themes/default/style_default.css\" />\n" +
            "\n" +
            "<script type=\"text/x-mathjax-config\">\n" +
            "   MathJax.Hub.Config({\n" +
            "      jax: [\"input/TeX\", \"output/HTML-CSS\"],\n" +
            "      tex2jax: {\n" +
            "         inlineMath: [ [\"$\",\"$\"], [\"\\\\(\",\"\\\\)\"] ],\n" +
            "         displayMath: [ [\"$$\",\"$$\"], [\"\\\\[\",\"\\\\]\"] ],\n" +
            "         processEscapes: true\n" +
            "      },\n" +
            "      \"HTML-CSS\": { availableFonts: [\"TeX\"] }\n" +
            "   });\n" +
            "</script>\n" +
            "<script type=\"text/javascript\" src=\"https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS_HTML-full,Safe\">\n" +
            "</script>\n" +
            "\n" +
            "</head>\n" +
            "  <body>";

    String webEndData = "  </body>\n" +
            "  </HTML>";


    FloatingActionButton fab,fab1,fab2;
    Animation fab_open,fab_close,rotate_forward,rotate_backward,textUp,textDown;
    Boolean isFabOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_problem);

        dbStorage = new SQLITE3storage(this);

        Intent getProblemID = getIntent();
        problemID= 1;
//        problemID= Integer.parseInt(getProblemID.getStringExtra(Constants.col1ID));
        final Cursor problem = dbStorage.getIndividualProblem(problemID);
        while (problem.moveToNext()){
            problem_id = problem.getInt(0);
            date_published = problem.getString(1);
            time_published = problem.getString(2);
            difficulty = problem.getInt(3);
            title = problem.getString(4);
            solved_by = problem.getInt(5);

        }

        progress=new ProgressDialog(this);
        progress.setMessage("fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        indiPTitle = (TextView) findViewById(R.id.problemTitleIndi);
        indiPQuestion = (WebView) findViewById(R.id.problemQuest);
        topText = (TextView) findViewById(R.id.textTop);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        infoFab = (TextView) findViewById(R.id.infoFAB);
        pseudoFab = (TextView) findViewById(R.id.pseudoFAB);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        textUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottomup);
        textDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottomdown);

        final AlertDialog.Builder alertDescription = new AlertDialog.Builder(this,R.style.alertDialog);
        alertDescription.setTitle("Description for "+problemID);
        String descriptionMsg = "Difficulty : " + difficulty+"\nPublished On : "+
                date_published+"\nSolved By : "+solved_by;
        alertDescription.setMessage(descriptionMsg);
        alertDescription.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDescription.setCancelable(true);


        String top = "Problem: "+ problemID;
        topText.setText(top);

        final Dialog UserInputPseudo = new Dialog(SingleProblemOffline.this, R.style.alertDialog);
        UserInputPseudo.setCancelable(false);
        UserInputPseudo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        UserInputPseudo.setContentView(R.layout.dialog_show_tut);
        UserInputPseudo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        final EditText pseudoCode = (EditText) UserInputPseudo.findViewById(R.id.tvpseudoCode);
        pseudoCode.setMovementMethod(new ScrollingMovementMethod());
        Button OK = (Button) UserInputPseudo.findViewById(R.id.btOK);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(UserInputPseudo.getWindow().getAttributes());
        lp.width = 800;
        lp.height = 800;
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInputPseudo.dismiss();
                ((ViewGroup)pseudoCode.getParent()).removeView(pseudoCode);
                String twoCents = pseudoCode.getText().toString();
                dbStorage.setComment(problemID,twoCents);

            }
        });
        UserInputPseudo.getWindow().setAttributes(lp);



        indiPTitle.setText(title);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
                Cursor comment = dbStorage.getComment(problemID);
                if (comment.getCount()!=0){
                    while (comment.moveToNext()){
                        String commentFromDB = comment.getString(1);
                        pseudoCode.setText(commentFromDB);
                    }
                }

                UserInputPseudo.show();

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
                alertDescription.show();
            }
        });

        final Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), Constants.NetworkError, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        progress.show();
        final String urlProblem = "https://projecteuler.net/sign_in";
//        final String urlProblem = "https://projecteuler.net/problem="+Integer.toString(problemID);
        StringRequest requestQuestion = new StringRequest(
                Request.Method.GET,
                urlProblem,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        doc = Jsoup.parse(response);
                        webEle = doc.select("div#content");
//                        webEle = doc.select("div.problem_content");
                        if (webEle!= null && !webEle.isEmpty()) {
                            data = webEle.toString();

                        } else {
                            // show exception
                            System.out.println("aaa");
                        }
                        String mime = "text/html";
                        String encoding = "utf-8";
                        String completeData = webDataStart+"\n"+data+"\n"+webEndData;

                        indiPQuestion.loadDataWithBaseURL(urlProblem, completeData, mime, encoding, null);
                        indiPQuestion.getSettings().setLoadsImagesAutomatically(true);
                        indiPQuestion.getSettings().setJavaScriptEnabled(true);
                        indiPQuestion.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                if (error instanceof NoConnectionError || error instanceof TimeoutError) {

                    snackbar.show();
                }

            }
        }

        );

        Volley.newRequestQueue(getApplicationContext()).add(requestQuestion);


    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            fab.setImageResource(R.drawable.panda);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            infoFab.setVisibility(View.INVISIBLE);
            pseudoFab.setVisibility(View.INVISIBLE);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;

        } else {

            fab.startAnimation(rotate_forward);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            fab.setImageResource(R.drawable.panda);
            fab.getDrawable().mutate().setTint(getResources().getColor(R.color.fab_color));
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            infoFab.setVisibility(View.VISIBLE);
            infoFab.setAnimation(textUp);
            pseudoFab.setVisibility(View.VISIBLE);
            pseudoFab.setAnimation(textUp);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }
}
