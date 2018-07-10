package me.monicatang.parsetagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import me.monicatang.parsetagram.model.Post;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("monica-tang")
                .clientKey("monica-tang-instagram")
                .server("http://parsetagram-monicat.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
