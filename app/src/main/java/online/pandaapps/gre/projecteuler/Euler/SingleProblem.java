package online.pandaapps.gre.projecteuler.Euler;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import online.pandaapps.gre.projecteuler.Constants;
import online.pandaapps.gre.projecteuler.R;
import online.pandaapps.gre.projecteuler.Storage.SQLITE3storage;

public class SingleProblem extends AppCompatActivity {

    int problemID;
    int problem_id,difficulty,solved_by;
    String date_published,time_published,title,question,images;
    SQLITE3storage dbStorage;
    TextView indi_pID,indi_pTitle, indi_question;

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
        System.out.println(question);
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




    }


}
