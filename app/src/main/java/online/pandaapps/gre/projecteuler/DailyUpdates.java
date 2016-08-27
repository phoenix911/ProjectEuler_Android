package online.pandaapps.gre.projecteuler;

/**
 * Created by sangeet on 27/08/16.
 */
public class DailyUpdates {
    /**
     * MainActivity or splash
     *
     * Two volley request
     * 1. check date
     * 2. download questions JSON
     *
     * check version of app
     * if version of app is newer than the old one
     * reset shared preference
     *
     * check for first run
     * true: then download the db and update the
     *      show tutorial
     *      update date to shared preference
     * false: download the date from server check if change is there
     *      if change is there download db
     *      else start landing activity
     *
     * show connecting with server message and downloading dates and updating db
     *
     */
}
