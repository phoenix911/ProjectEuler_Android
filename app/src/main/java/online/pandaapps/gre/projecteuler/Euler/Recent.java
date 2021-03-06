package online.pandaapps.gre.projecteuler.Euler;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import online.pandaapps.gre.projecteuler.SwipeMenu.BaseActivity;
import online.pandaapps.gre.projecteuler.Utils.Constants;
import online.pandaapps.gre.projecteuler.R;
import online.pandaapps.gre.projecteuler.Storage.SQLITE3storage;
import online.pandaapps.gre.projecteuler.Utils.ViewHolder.RecyclerAdapter;

public class Recent extends BaseActivity {

    SQLITE3storage dbStorage;
    Cursor problems;
    String[] problemID,problemTitle;
    TextView topText;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        topText = (TextView) findViewById(R.id.textTop);

        recyclerView = (RecyclerView) findViewById(R.id.problemsList);
//        adapter = new RecyclerAdapter()

        dbStorage = new SQLITE3storage(this);

        Intent getFlag = getIntent();
        int Flag = Integer.parseInt(getFlag.getStringExtra(Constants.RecentFlagTitle));

        switch (Flag){
            case 1:
                // from recent button
                topText.setText("New Problems");
                problems = dbStorage.getAllByDifficulty(0);
                break;
            case 2:
                int start = Integer.parseInt(getFlag.getStringExtra("first_problem").trim());
                int end = start+24;
                String top = "From Problem "+start+" to "+end;
                topText.setTextSize(22);
                topText.setText(top);
                problems = dbStorage.getInRange(start,end);
                break;
            case 3:
                int difficulty = Integer.parseInt(getFlag.getStringExtra("first_problem").trim());
                top = "Difficulty: "+ difficulty;
                topText.setText(top);
                problems = dbStorage.getAllByDifficulty(difficulty);
                break;
        }

        if (problems.getCount() == 0){
            // show no question available
            System.out.println(problems.getCount());
        }else {
            // add data to list view
            problemID = new String [problems.getCount()];
            problemTitle = new String [problems.getCount()];
            int marker = 0;
            while (problems.moveToNext()){
                int problem_id = problems.getInt(0);
                String problem_title = problems.getString(1);
                problemID[marker] = "Problem "+problem_id;
                problemTitle[marker] = problem_title;
                marker+=1;
            }
        }

        adapter = new RecyclerAdapter(problemID,problemTitle);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }
}
