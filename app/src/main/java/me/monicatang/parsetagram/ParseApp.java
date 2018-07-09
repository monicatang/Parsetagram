package me.monicatang.parsetagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("monica-tang")
                .clientKey("monica-tang-instagram")
                .server("http://parsetagram-monicat.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
