package online.pandaapps.gre.projecteuler.Euler;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import online.pandaapps.gre.projecteuler.Constants;
import online.pandaapps.gre.projecteuler.R;
import online.pandaapps.gre.projecteuler.Storage.SQLITE3storage;

public class Recent extends AppCompatActivity {

    SQLITE3storage dbStorage;
    Cursor problems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        dbStorage = new SQLITE3storage(this);

        Intent getFlag = getIntent();
        int Flag = Integer.parseInt(getFlag.getStringExtra(Constants.RecentFlagTitle));

        switch (Flag){
            case 1:
                // from recent button
                problems = dbStorage.getAllByDifficulty(0);
                System.out.println(problems);
                break;
            case 2:
                break;
        }

        if (problems.getCount() == 0){
            // show no question available
            System.out.println(problems.getCount());
        }else {
            // add data to list view
            while (problems.moveToNext()){
                int problem_id = problems.getInt(0);
                String problem_title = problems.getString(1);
                System.out.println(problem_id+". "+problem_title);
            }
        }



    }
}
