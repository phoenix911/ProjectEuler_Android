package online.pandaapps.gre.projecteuler.Euler;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

import online.pandaapps.gre.projecteuler.Constants;
import online.pandaapps.gre.projecteuler.R;
import online.pandaapps.gre.projecteuler.Storage.SQLITE3storage;
import online.pandaapps.gre.projecteuler.Utils.ViewHolder.RecyclerAdapter;

public class Recent extends AppCompatActivity {

    SQLITE3storage dbStorage;
    Cursor problems;
    String[] problemID,problemTitle;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        recyclerView = (RecyclerView) findViewById(R.id.problemsList);
//        adapter = new RecyclerAdapter()

        dbStorage = new SQLITE3storage(this);

        Intent getFlag = getIntent();
        int Flag = Integer.parseInt(getFlag.getStringExtra(Constants.RecentFlagTitle));

        switch (Flag){
            case 1:
                // from recent button
                problems = dbStorage.getAllByDifficulty(0);
                break;
            case 2:
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

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }
}
