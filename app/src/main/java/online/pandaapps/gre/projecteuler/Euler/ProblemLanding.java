package online.pandaapps.gre.projecteuler.Euler;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import online.pandaapps.gre.projecteuler.Constants;
import online.pandaapps.gre.projecteuler.R;
import online.pandaapps.gre.projecteuler.Storage.SQLITE3storage;

public class ProblemLanding extends AppCompatActivity implements View.OnClickListener {

    Button number,recent,difficulty,solvedBy;
    SQLITE3storage sqlite3storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_landing);

        sqlite3storage = new SQLITE3storage(this);

        number = (Button) findViewById(R.id.number);
        recent = (Button) findViewById(R.id.recent);
        difficulty = (Button) findViewById(R.id.difficulty);
        solvedBy = (Button) findViewById(R.id.solvedby);

        number.setOnClickListener(this);
        recent.setOnClickListener(this);
        difficulty.setOnClickListener(this);
        solvedBy.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.number:
//                startActivity(new Intent(getApplicationContext(),Number.class));
//                Cursor a = sqlite3storage.getIndividualProblem(200);
//                System.out.println(a.getColumnCount());
                break;
            case R.id.recent:
                Intent recentAct = new Intent(getApplicationContext(),Recent.class);
                recentAct.putExtra(Constants.RecentFlagTitle,Constants.RecentFlagTitleValueRecentButton);
                startActivity(recentAct);
                break;
            case R.id.difficulty:
                startActivity(new Intent(getApplicationContext(),Difficulty.class));
                break;
            case R.id.solvedby:
                startActivity(new Intent(getApplicationContext(),SolvedBy.class));
                break;
        }

    }
}
