package online.pandaapps.gre.projecteuler.SwipeMenu;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import online.pandaapps.gre.projecteuler.Euler.Number;
import online.pandaapps.gre.projecteuler.Euler.ProblemLanding;
import online.pandaapps.gre.projecteuler.Euler.Recent;
import online.pandaapps.gre.projecteuler.MoreInfo.AboutApp;
import online.pandaapps.gre.projecteuler.MoreInfo.NEWs;
import online.pandaapps.gre.projecteuler.R;
import online.pandaapps.gre.projecteuler.Storage.SharedPrefStorage;
import online.pandaapps.gre.projecteuler.Utils.Constants;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout fullLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedNavItemId;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullLayout.findViewById(R.id.activity_content);
//        setContentView(fullLayout);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        super.setContentView(fullLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        // setting updated dated not necessary for other app
        SharedPrefStorage sp = new SharedPrefStorage(this);
        String[] du = sp.getDBdate().split("-");
        navigationView.getMenu().getItem(7)
                .setTitle("Updated On: "+du[0]+"/"+du[1]+"/"+du[2].substring(Math.max(du[2].length() - 2, 0)));
        // end of updating date

        if (useToolbar())
        {
            setSupportActionBar(toolbar);
//            toolbar.setVisibility(View.GONE);

        }
        else
        {
            toolbar.setVisibility(View.GONE);
        }

        setUpNavView();

    }



    protected boolean useToolbar()
    {
        return true;
    }

    protected void setUpNavView()
    {
        navigationView.setNavigationItemSelectedListener(this);

        if( useDrawerToggle()) { // use the hamburger menu
            drawerToggle = new ActionBarDrawerToggle(this, fullLayout, toolbar,
                    R.string.nav_drawer_opened,
                    R.string.nav_drawer_closed);

            fullLayout.setDrawerListener(drawerToggle);
            drawerToggle.syncState();
        } else if(useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(getResources()
                    .getDrawable(R.drawable.abc_ic_ab_back_material));
        }
    }

    protected boolean useDrawerToggle()
    {
        return true;
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        fullLayout.closeDrawer(GravityCompat.START);
        selectedNavItemId = menuItem.getItemId();

        return onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.col1:
                Intent col1 = new Intent(this, ProblemLanding.class);
                col1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(col1);
                return true;
            case R.id.col2:
                Intent col2 = new Intent(this, Recent.class);
                col2.putExtra(Constants.RecentFlagTitle,Constants.RecentFlagTitleValueRecentButton);
                col2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(col2);
                return true;
            case R.id.col3:
                Intent col3 = new Intent(this, Number.class);
                col3.putExtra(Constants.nAndDFlag,"1");
                col3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(col3);
                return true;
            case R.id.col4:
                Intent col4 = new Intent(this, Number.class);
                col4.putExtra(Constants.nAndDFlag,"2");
                col4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(col4);
                return true;
            case R.id.col5:
                Intent col5 = new Intent(this, NEWs.class);
                col5.putExtra(Constants.newsORaboutFlag,Constants.news);
                col5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(col5);
                return true;
            case R.id.col6:
                Intent col6 = new Intent(this, NEWs.class);
                col6.putExtra(Constants.newsORaboutFlag,Constants.about);
                col6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(col6);
                return true;
            case R.id.col7:
                Intent col7 = new Intent(this, AboutApp.class);
                col7.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(col7);
                return true;
            case R.id.col8:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
