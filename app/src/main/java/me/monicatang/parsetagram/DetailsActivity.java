package me.monicatang.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.monicatang.parsetagram.model.Post;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.ivImage) ImageView ivImage;
    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.tvTime) TextView tvTime;

    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        // unwrap tweets
        post = (Post) getIntent().getParcelableExtra(Post.class.getSimpleName());

        //populate views
        tvDescription.setText(post.getDescription());
        tvUsername.setText(post.getUser().getUsername());
        tvTime.setText(post.getCreatedAt().toString());

        Glide.with(this).load(post.getImage().getUrl()).into(ivImage);
    }
}
