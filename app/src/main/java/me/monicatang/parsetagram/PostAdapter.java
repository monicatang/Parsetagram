package me.monicatang.parsetagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.monicatang.parsetagram.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    // pass in Tweets array in constructor

    private List<Post> mPosts;
    public Context context;

    public PostAdapter(List<Post> posts){
        mPosts = posts;
    }

    //for each row, inflate layout and cache references into ViewHolder
    //only invoked when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    // bind values based on position of element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPosts.get(position);

        //populate views
        holder.tvDescription.setText(post.getDescription());
        holder.tvUsername.setText(post.getUser().getUsername());

        Glide.with(context).load(post.getImage().getUrl()).into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // create ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.tvUsername) TextView tvUsername;
        @BindView(R.id.tvDescription) TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            //resolve view lookups
            ButterKnife.bind(this, itemView);

        }

    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
    }

}
