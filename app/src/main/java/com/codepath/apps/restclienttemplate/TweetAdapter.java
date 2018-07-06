package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private List<Tweet> adapterTweets;
    Context context;
    public static final int REQUEST_CODE = 1;
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
        //this might not be correct
        final Tweet tweet = adapterTweets.get(position);
        //this is the screen name, not the twitter handle
        holder.txtvUsername.setText(tweet.user.name);
        holder.txtvBody.setText(tweet.body);
        holder.txtvTimestamp.setText(tweet.timeStamp);
        holder.replyButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent replyIntent = new Intent(context, ComposeActivity.class);
              replyIntent.putExtra("ID", tweet.uiID);
              replyIntent.putExtra("username", tweet.user.screenName);

              context.startActivity(replyIntent);
          }
      });


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
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //declare fields
        ImageView imvProfileImage;
        TextView txtvUsername;
        TextView txtvBody;
        TextView txtvTimestamp;
        ImageButton replyButton;

        public ViewHolder(View itemView) {
            super(itemView);
            //connect them with the views in the item_tweet file
            imvProfileImage = (ImageView) itemView.findViewById(R.id.imvProfileImage);
            txtvUsername = (TextView) itemView.findViewById(R.id.txtvUsername);
            txtvBody = (TextView) itemView.findViewById(R.id.txtvBody);
            txtvTimestamp = (TextView) itemView.findViewById(R.id.txtvTimestamp);
            //replybutton methods
            replyButton = (ImageButton) itemView.findViewById(R.id.imvreplyButton);

            //this will allow for the entire tweet to be clicked and go to the details page
            itemView.setOnClickListener(this);
//
        }

        @Override
        public void onClick(View v) {
            int viewPosition = getAdapterPosition();
            if (viewPosition != RecyclerView.NO_POSITION){
                Tweet tweet = adapterTweets.get(viewPosition);

                Intent detailsIntent = new Intent(context, DetailsActivity.class);
                detailsIntent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                context.startActivity(detailsIntent);

            }
        }
    }


    //clean all elements of the recycler
    public void clear(){
        adapterTweets.clear();
        notifyDataSetChanged();
    }

    //Add a list of items - change to type used
    public void addAll(List<Tweet> list){
        adapterTweets.addAll(list);
        notifyDataSetChanged();
    }

}
