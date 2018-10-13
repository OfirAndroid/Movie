package com.movie.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.movie.movie.listeners.MovieCastListener;
import com.movie.movie.listeners.MovieDurationListener;
import com.movie.movie.listeners.RecyclerViewClickListener;
import com.movie.movie.managers.MovieManager;
import com.movie.movie.models.ModelConstance;
import com.movie.movie.models.MovieCastItem;
import com.movie.movie.models.MovieItem;
import com.movie.movie.network.MovieApi;
import com.movie.movie.network.entities.MovieCastResponse;
import com.movie.movie.network.entities.MovieDurationResponse;
import com.movie.movie.ui.adapters.GenericMovieAdapter;
import com.movie.movie.util.AppUtil;


/*
 * The MovieDetailsActivity handle the activity of the  selected movie item screen
 * */


public class MovieDetailsActivity extends AppCompatActivity implements MovieCastListener, RecyclerViewClickListener, MovieDurationListener {

  private static final String TAG = MovieDetailsActivity.class.getSimpleName();
  private TextView mName; // the movie name
  private TextView mYear; // the year of the movie published
  private ImageView mPoster; // the image of the movie
  private TextView mRate; // the rating  of the movie
  private TextView mOverview; // the overview  of the movie
  private TextView mDuration; // the duration  of the movie
  private RecyclerView mRecyclerView; // the recycler view  of the movie cast
  private GenericMovieAdapter mAdapter; // the adapter  for the recycler view
  private LinearLayoutManager mLayoutManager; // the layout manager for the recycler view

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_details);

    mName = findViewById(R.id.tv_name);
    mYear = findViewById(R.id.tv_year);
    mPoster = findViewById(R.id.img_poster);
    mRate = findViewById(R.id.tv_rate);
    mOverview = findViewById(R.id.tv_overview);
    mDuration = findViewById(R.id.tv_duration);
    mRecyclerView = findViewById(R.id.recycler_view);

    mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false); // the movie cast is an horizontal list
    mRecyclerView.setLayoutManager(mLayoutManager);


    rendererUi();

  }


  private void rendererUi() {

    // get the movie item we have selected from the main screen activity
    MovieItem item = (MovieItem) getIntent().getSerializableExtra(ModelConstance.MOVIE_DETAIL_ITEM);

    if (item == null) {
      return;
    }

    // getting the duration and the cast of the movie from the server
    MovieManager.getInstance().getDuration(item.getId(), this);
    MovieManager.getInstance().getCast(item.getId(), this);


    // sets the ui according to the item data
    mName.setText(item.getTitle() == null ? ModelConstance.NOT_AVAILABLE : item.getTitle());
    mYear.setText(item.getReleaseDate() == null ? ModelConstance.NOT_AVAILABLE : AppUtil.getYearFromDate(item.getReleaseDate(), ModelConstance.DATE_PATTERN));
    mRate.setText(String.valueOf(item.getVoteAverage()));
    mOverview.setText(item.getOverview() == null ? ModelConstance.NOT_AVAILABLE : item.getOverview());
    Glide.with(this).load(MovieApi.BASE_IMAGE + item.getPosterPath()).apply(RequestOptions.placeholderOf(R.drawable.ic_no_poster)).into(mPoster);
  }

  @Override
  public void OnReceivedMovieCast(MovieCastResponse movieCastResponse) {
    // we received the list of the movie cast, populate the data
    mAdapter = new GenericMovieAdapter(this, movieCastResponse.cast, this);
    mRecyclerView.setAdapter(mAdapter);
  }

  @Override
  public void OnFailedToReceiveMovieCast(String error) {
    // we  failed to receive the list of the movie cast
    Log.e(TAG, error);

  }

  @Override
  public void onClick(View view) {
    // got a click event on a cast member it the list, starts the actorActivity
    int position = mRecyclerView.getChildLayoutPosition(view);
    MovieCastItem item = (MovieCastItem) mAdapter.mData.get(position);
    Intent intent = new Intent(this, ActorDetailActivity.class);
    intent.putExtra(ModelConstance.ACTOR_ID, item.getId());
    startActivity(intent);
  }

  @Override
  public void OnReceivedMovieDuration(MovieDurationResponse duration) {
    //we got the movie duration, populate the data

    mDuration.setText(duration.runtime + " min");

  }

  @Override
  public void OnFailedToReceiveMovieDuration(String error) {
    //we failed to  get the movie duration
    Log.e(TAG, error);
  }
}
