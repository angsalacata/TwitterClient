package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;


import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private List<Tweet> adapterTweets;
    Context context;
    //setter for tweets member variable
    public TweetAdapter(List<Tweet> tweets){
        this.adapterTweets = tweets; }//can also be just adapterTweets = tweets.

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //put this layout to a view!!
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        //construct dat ViewHolder
        return new ViewHolder(tweetView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = adapterTweets.get(position);
        holder.txtvUsername.setText(tweet.user.name);
        holder.txtvBody.setText(tweet.body);
        holder.txtvTimestamp.setText(tweet.timeStamp);

        //glide library work
    String imageUrl = tweet.user.profileURL;
    GlideApp.with(context)
            .load(imageUrl)
            .into(holder.imvProfileImage);


    }

    @Override
    public int getItemCount() {
        return adapterTweets.size();
    }

    //need to pass in collection of tweetsm array
    //for each row, need to inflate layout, pass into view holder class
    //have to bind data of that tweet to the layout of that row, based on position of element
    //


    //view holder will connect a single tweet to the item_tweet layout
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //declare fields
        ImageView imvProfileImage;
        TextView txtvUsername;
        TextView txtvBody;
        TextView txtvTimestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            //connect them with the views in the item_tweet file
            imvProfileImage = (ImageView) itemView.findViewById(R.id.imvProfileImage);
            txtvUsername = (TextView) itemView.findViewById(R.id.txtvUsername);
            txtvBody = (TextView) itemView.findViewById(R.id.txtvBody);
            txtvTimestamp = (TextView) itemView.findViewById(R.id.txtvTimestamp);

        }
    }

}
