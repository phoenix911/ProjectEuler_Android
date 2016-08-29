package online.pandaapps.gre.projecteuler.Euler;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import online.pandaapps.gre.projecteuler.Constants;
import online.pandaapps.gre.projecteuler.R;
import online.pandaapps.gre.projecteuler.Storage.SQLITE3storage;

public class SingleProblem extends AppCompatActivity {

    int problemID;
    int problem_id,difficulty,solved_by;
    String date_published,time_published,title,question,images;
    SQLITE3storage dbStorage;
    TextView indi_pID,indi_pTitle, indi_question, infoFab, pseudoFab;

    FloatingActionButton fab,fab1,fab2;
    Animation fab_open,fab_close,rotate_forward,rotate_backward,textUp,textDown;
    Boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_problem);

        dbStorage = new SQLITE3storage(this);


        Intent getProblemID = getIntent();
        problemID= Integer.parseInt(getProblemID.getStringExtra(Constants.col1ID));
        Cursor problem = dbStorage.getIndividualProblem(problemID);
        while (problem.moveToNext()){
            problem_id = problem.getInt(0);
            date_published = problem.getString(1);
            time_published = problem.getString(2);
            difficulty = problem.getInt(3);
            title = problem.getString(4);
            question = problem.getString(5);
            images = problem.getString(6);
            solved_by = problem.getInt(3);

        }

        indi_pID = (TextView) findViewById(R.id.problemIdIndi);
        indi_pTitle = (TextView) findViewById(R.id.problemTitleIndi);
        indi_question = (TextView) findViewById(R.id.problemQuest);
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

        indi_pID.setText("Problem "+problem_id);
        indi_pTitle.setText(title);
        Spanned spannedText;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            spannedText = Html.fromHtml(question,Html.FROM_HTML_MODE_LEGACY);
        } else {
            spannedText = Html.fromHtml(question);
        }
        indi_question.setMovementMethod(new ScrollingMovementMethod());
        indi_question.setText(spannedText);

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
                Toast.makeText(SingleProblem.this, "fab1", Toast.LENGTH_SHORT).show();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
                Toast.makeText(SingleProblem.this, "fab2", Toast.LENGTH_SHORT).show();
            }
        });


    }




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
            fab.setImageResource(R.drawable.pandared);
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
