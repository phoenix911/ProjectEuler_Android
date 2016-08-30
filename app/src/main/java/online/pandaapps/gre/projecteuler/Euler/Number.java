package online.pandaapps.gre.projecteuler.Euler;

import android.content.Intent;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

import online.pandaapps.gre.projecteuler.Constants;
import online.pandaapps.gre.projecteuler.R;
import online.pandaapps.gre.projecteuler.Storage.SQLITE3storage;
import online.pandaapps.gre.projecteuler.Utils.ViewHolder.RecyclerAdapter;
import online.pandaapps.gre.projecteuler.Utils.ViewHolder.RecyclerAdapterGridView;

public class Number extends AppCompatActivity {

    SQLITE3storage dbStorage;
    String[] problemList;

    TextView topText;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        topText = (TextView) findViewById(R.id.titleNum);
        dbStorage = new SQLITE3storage(this);


        Intent nAndD = getIntent();
        int flagnAndD = Integer.parseInt(nAndD.getStringExtra(Constants.nAndDFlag));

        switch (flagnAndD){
            case 1:
                topText.setText("Based On Number");
                int totalProblem = dbStorage.getCount();
                int pGroup = totalProblem/25;
                int displayGroup = pGroup+1;
                problemList = new String [displayGroup];
                for (int i = 0; i < displayGroup; i++) {
                    problemList[i]= Integer.toString(i*25+1);
                }
                adapter = new RecyclerAdapterGridView(problemList,1);
                break;
            case 2:
                topText.setText("Based On Difficulty");
                int marker = 0;
                int difficulty = 5;
                problemList = new String [20];
                while (difficulty<=100){
                    problemList[marker] = Integer.toString(difficulty);
                    difficulty += 5;
                    marker += 1;
                }
                adapter = new RecyclerAdapterGridView(problemList,2);
                break;
        }
        recyclerView = (RecyclerView) findViewById(R.id.numberList);
        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);




    }
}
