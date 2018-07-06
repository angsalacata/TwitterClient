package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

TextView txtvUserhandle;
TextView txtvDetailBody;
ImageView imvProfilePicture;


String detailsTweetbody;
String detailsImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setTitle("Details");

        txtvUserhandle = (TextView) findViewById(R.id.txtvDetailsUserhandle);
        txtvDetailBody = (TextView) findViewById(R.id.txtvDetailBody);
        imvProfilePicture = (ImageView) findViewById(R.id.t)
        Tweet receivedTweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));


        detailsImageUrl = receivedTweet.user.profileURL;
        txtvUserhandle.setText(receivedTweet.user.screenName);
        txtvDetailBody.setText(receivedTweet.body);

        GlideApp.with(this)
                .load(detailsImageUrl)
                .into()
    }
}
