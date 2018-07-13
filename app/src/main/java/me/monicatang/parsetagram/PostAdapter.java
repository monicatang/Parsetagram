package me.monicatang.parsetagram;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
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
    public Activity activity;

    public PostAdapter(List<Post> posts, Activity activity){
        mPosts = posts;
        this.activity = activity;
    }

    //for each row, inflate layout and cache references into ViewHolder
    //only invoked when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView, mPosts);
        return viewHolder;
    }

    // bind values based on position of element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        String username = post.getUser().getUsername();

        //populate views
        holder.tvDescription.setText(post.getDescription());
        holder.tvUsername.setText(username);
        holder.ivHeart.setImageResource(R.drawable.ufi_heart);
        holder.ivDirect.setImageResource(R.drawable.direct);
        holder.ivComment.setImageResource(R.drawable.ufi_comment);
        holder.ivSave.setImageResource(R.drawable.ufi_save);
        holder.tvUsernameHeader.setText(username);
        holder.ivMedia.setImageResource(R.drawable.media_option_button);

        String rawTime = post.getCreatedAt().toString();

        holder.tvRelativeTime.setText(post.getRelativeTimeAgo(rawTime));

        Glide.with(context).load(post.getImage().getUrl()).into(holder.ivImage);

    }


    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // create ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.tvUsername) TextView tvUsername;
        @BindView(R.id.tvDescription) TextView tvDescription;
        @BindView(R.id.tvRelativeTime) TextView tvRelativeTime;
        @BindView(R.id.ivHeart) ImageView ivHeart;
        @BindView(R.id.ivDirect) ImageView ivDirect;
        @BindView(R.id.ivComment) ImageView ivComment;
        @BindView(R.id.ivSave) ImageView ivSave;
        @BindView(R.id.tvUsernameHeader) TextView tvUsernameHeader;
        @BindView(R.id.ivMedia) ImageView ivMedia;

        private List<Post> mPosts;

        public ViewHolder(View itemView, List<Post> posts) {
            super(itemView);

            //resolve view lookups
            ButterKnife.bind(this, itemView);
            mPosts = posts;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            //ensure position is valid
            if (position != RecyclerView.NO_POSITION) {
                //get post at the position
                Post post = mPosts.get(position);
                FragmentTransaction ft = ((HomeActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                DetailsFragment dFragment = DetailsFragment.newInstance(post);
                ft.addToBackStack("details");
                ft.replace(R.id.feed, dFragment);
                ft.commit();
            }
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
