package online.pandaapps.gre.projecteuler.Euler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import online.pandaapps.gre.projecteuler.MoreInfo.AboutApp;
import online.pandaapps.gre.projecteuler.SwipeMenu.BaseActivity;
import online.pandaapps.gre.projecteuler.Utils.Constants;
import online.pandaapps.gre.projecteuler.R;
import online.pandaapps.gre.projecteuler.Storage.SQLITE3storage;

public class ProblemLanding extends BaseActivity implements View.OnClickListener {

    Button number,recent,difficulty, news;
    SQLITE3storage sqlite3storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_landing);

        sqlite3storage = new SQLITE3storage(this);

        number = (Button) findViewById(R.id.number);
        recent = (Button) findViewById(R.id.recent);
        difficulty = (Button) findViewById(R.id.difficulty);
        news = (Button) findViewById(R.id.news);

        number.setOnClickListener(this);
        recent.setOnClickListener(this);
        difficulty.setOnClickListener(this);
        news.setOnClickListener(this);

        news.setText("mathjax");


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.number:
                Intent numberAct = new Intent(getApplicationContext(),Number.class);
                numberAct.putExtra(Constants.nAndDFlag,"1");
                startActivity(numberAct);
                break;
            case R.id.recent:
                Intent recentAct = new Intent(getApplicationContext(),Recent.class);
                recentAct.putExtra(Constants.RecentFlagTitle,Constants.RecentFlagTitleValueRecentButton);
                startActivity(recentAct);
                break;
            case R.id.difficulty:
                Intent difficultyAct = new Intent(getApplicationContext(),Number.class);
                difficultyAct.putExtra(Constants.nAndDFlag,"2");
                startActivity(difficultyAct);
                break;
            case R.id.news:
//                Intent news = new Intent(getApplicationContext(),NEWs.class);
//                news.putExtra(Constants.newsORaboutFlag,Constants.news);
//                startActivity(news);

                Intent news = new Intent(getApplicationContext(),AboutApp.class);
                startActivity(news);
                break;
        }

    }
}
