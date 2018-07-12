package me.monicatang.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.monicatang.parsetagram.model.Post;


public class HomeActivity extends AppCompatActivity {

    ArrayList<Post> posts;
    PostAdapter postAdapter;

    // Views
    @BindView(R.id.rvFeed) RecyclerView rvFeed;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // resolve view lookups
        ButterKnife.bind(this);

        // set action bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // init Array List (data source)
        posts = new ArrayList<>();
        // construct adapter from data shtulvjrelrkvklbnhjfugtcrrhrtrcveource
        postAdapter = new PostAdapter(posts);
        // recyclerView setup (layout manager, use adapter)
        rvFeed.setLayoutManager(new LinearLayoutManager(this));
        rvFeed.setAdapter(postAdapter);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postAdapter.clear();
                loadTopPosts();
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                break;
                            case R.id.action_create:
                                Intent i = new Intent(HomeActivity.this, CreateActivity.class);
                                startActivity(i);
                                break;
                            case R.id.action_profile:
                                logOut();
                                break;
                        }
                        return true;
                    }
                });

        loadTopPosts();
    }

    private void logOut() {
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        if(ParseUser.getCurrentUser() == null) {
            Log.i("Logout", "Log out successful"); // this will now be null
        }

    }

    private void loadTopPosts() {
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); ++i) {
                        Log.d("HomeActivity","Post[" + i + "] = "
                                + objects.get(i).getDescription()
                                + "\nusername =" + objects.get(i).getUser().getUsername());
                    }
                    postAdapter.addAll(objects);
                    postAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
