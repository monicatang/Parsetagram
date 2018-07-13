package me.monicatang.parsetagram;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.monicatang.parsetagram.model.Post;

public class DetailsFragment extends Fragment {


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
    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;

    public static DetailsFragment newInstance(Post post) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("post", post);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_details, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        ButterKnife.bind(this, view);

        // unwrap post
        Post post = getArguments().getParcelable("post");
        String username = post.getUser().getUsername();
        Context context = view.getContext();

        //populate views
        tvDescription.setText(post.getDescription());
        tvUsername.setText(username);
        ivHeart.setImageResource(R.drawable.ufi_heart);
        ivDirect.setImageResource(R.drawable.direct);
        ivComment.setImageResource(R.drawable.ufi_comment);
        ivSave.setImageResource(R.drawable.ufi_save);
        tvUsernameHeader.setText(username);
        ivMedia.setImageResource(R.drawable.media_option_button);

        String rawTime = post.getCreatedAt().toString();

        tvRelativeTime.setText(post.getRelativeTimeAgo(rawTime));

        Glide.with(context).load(post.getImage().getUrl()).into(ivImage);

        Glide.with(context).load(R.drawable.user_placeholder)
                .apply(RequestOptions.circleCropTransform()).into(ivProfileImage);
    }
}