package com.movie.movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.movie.movie.listeners.ActorListener;
import com.movie.movie.managers.MovieManager;
import com.movie.movie.models.ActorItem;
import com.movie.movie.models.ModelConstance;
import com.movie.movie.network.MovieApi;


/*
* The ActorDetailActivity handle the activity of the actor screen
* */

public class ActorDetailActivity extends AppCompatActivity implements ActorListener {

  private static final String TAG = ActorDetailActivity.class.getSimpleName();
  private ImageView mImg; // actor's image
  private TextView mName; // actor's name
  private TextView mBirthday; // the date of the actor's birthday
  private TextView mPlaceOfBirth; // the address of the place of birth
  private TextView mBio; // the actor's biography

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_actor_detail);

    mImg = findViewById(R.id.img_actor);
    mName = findViewById(R.id.tv_name);
    mBirthday = findViewById(R.id.tv_birthday);
    mPlaceOfBirth = findViewById(R.id.tv_place_of_birth);
    mBio = findViewById(R.id.tv_biography);


    long id = getIntent().getExtras().getLong(ModelConstance.ACTOR_ID); // getting the actor's id


    MovieManager.getInstance().getActorDetails(id, this); // get the actor details from the server according to the id
  }

  private void rendererUi(ActorItem item) {
    // set the ui data
    mName.setText(item.getName() == null ? ModelConstance.NOT_AVAILABLE : item.getName());
    mBirthday.setText(item.getBirthday() == null ? ModelConstance.NOT_AVAILABLE : item.getBirthday());
    mPlaceOfBirth.setText(item.getPlaceOfBirth() == null ? ModelConstance.NOT_AVAILABLE : item.getPlaceOfBirth());
    mBio.setText(item.getBiography() == null ? ModelConstance.NOT_AVAILABLE : item.getBiography());
    Glide.with(this).load(MovieApi.BASE_IMAGE + item.getProfilePath()).apply(RequestOptions.placeholderOf(R.drawable.ic_no_pic)).into(mImg);
  }

  @Override
  public void OnReceivedActorDetails(ActorItem response) {
    // we got the detail of the actor
    rendererUi(response);
  }

  @Override
  public void OnFailedToReceiveActorDetails(String error) {
    // we failed to get detail of the actor
    Log.e(TAG, error);
  }
}
