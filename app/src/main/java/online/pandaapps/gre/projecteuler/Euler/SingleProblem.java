package online.pandaapps.gre.projecteuler.Euler;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import online.pandaapps.gre.projecteuler.Utils.Constants;
import online.pandaapps.gre.projecteuler.R;
import online.pandaapps.gre.projecteuler.Storage.SQLITE3storage;

public class SingleProblem extends AppCompatActivity {

    int problemID;
    int problem_id,difficulty,solved_by;
    String date_published,time_published,title,question,images;
    SQLITE3storage dbStorage;
    TextView indiPID, indiPTitle, indiPQuestion, infoFab, pseudoFab;



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
        final Cursor problem = dbStorage.getIndividualProblem(problemID);
        while (problem.moveToNext()){
            problem_id = problem.getInt(0);
            date_published = problem.getString(1);
            time_published = problem.getString(2);
            difficulty = problem.getInt(3);
            title = problem.getString(4);
            question = problem.getString(5);
            images = problem.getString(6);
            solved_by = problem.getInt(7);

        }

        indiPID = (TextView) findViewById(R.id.problemIdIndi);
        indiPTitle = (TextView) findViewById(R.id.problemTitleIndi);
        indiPQuestion = (TextView) findViewById(R.id.problemQuest);
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

        final AlertDialog.Builder alertPseudo = new AlertDialog.Builder(this,R.style.alertDialog);
        alertPseudo.setTitle("your 2 cents on problem "+problemID);
        final EditText pseudoCode = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10,10);
        lp.setMargins(10,2,10,2);

        pseudoCode.setLayoutParams(lp);
        pseudoCode.setBackgroundColor(00000000);
        pseudoCode.setHint(" type");
        alertPseudo.setView(pseudoCode);
        alertPseudo.setCancelable(false);

        alertPseudo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ((ViewGroup)pseudoCode.getParent()).removeView(pseudoCode);
                String twoCents = pseudoCode.getText().toString();
                dbStorage.setComment(problemID,twoCents);
                // put it to database
            }
        });

        indiPID.setText("Problem "+problem_id);
        indiPTitle.setText(title);
        Spanned spannedText;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            spannedText = Html.fromHtml(question,Html.FROM_HTML_MODE_LEGACY);
        } else {
            spannedText = Html.fromHtml(question);
        }
        indiPQuestion.setMovementMethod(new ScrollingMovementMethod());
        indiPQuestion.setText(spannedText);

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

                alertPseudo.show();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
                alertDescription.show();
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
