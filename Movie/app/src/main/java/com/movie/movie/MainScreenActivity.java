package com.movie.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnScrollChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movie.listeners.MovieListener;
import com.movie.movie.listeners.RecyclerViewClickListener;
import com.movie.movie.managers.MovieManager;
import com.movie.movie.models.ModelConstance;
import com.movie.movie.models.MovieItem;
import com.movie.movie.network.entities.MovieTopRatedResponse;
import com.movie.movie.ui.adapters.GenericMovieAdapter;
import com.movie.movie.util.AppUtil;

import java.util.ArrayList;

/*
 *
 * The MainScreenActivity include the search screen and the top rated movie screen
 * */

public class MainScreenActivity
        extends AppCompatActivity
        implements MovieListener, View.OnTouchListener, OnScrollChangeListener, TextView.OnEditorActionListener, SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {

  private final String TAG = MainScreenActivity.class.getSimpleName();
  private GenericMovieAdapter mAdapter; // adapter for the recycle view
  private RecyclerView mRecyclerView; // recycle view for the list of the movies
  private int pageNumber = 1; // pageNumber param is for tracking on what page we are currently
  private EditText mSearch; // the search for a movie
  private CardView mSearchContainer; // the search container used for making changes in the search view
  private TextView mTitle; // the title at the top of the screen
  private boolean mIsInSearchMode; // mIsInSearchMode is a param for tracking on which mode we are search\top rated
  private LinearLayoutManager mLayoutManager; // the layout manager for the recycler view
  private SwipeRefreshLayout mSwipeToRefresh; // swipe to refresh the list

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main__screen);

    mRecyclerView = findViewById(R.id.recycler_view);
    mTitle = findViewById(R.id.tv_title);
    mSearch = findViewById(R.id.search);
    mSearchContainer = findViewById(R.id.search_container);
    mSwipeToRefresh = findViewById(R.id.swipeRefresh);

    mSwipeToRefresh.setOnRefreshListener(this);


    mLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mAdapter = new GenericMovieAdapter(this, new ArrayList<>(), this);
    mRecyclerView.setAdapter(mAdapter);


    showTopRatedMovie(1);

    mRecyclerView.setOnScrollChangeListener(this);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());


    mSearch.setOnTouchListener(this);
    mSearch.setOnEditorActionListener(this);

  }


  // this method show from the server the top rated movie according page number
  private void showTopRatedMovie(int pageNumber) {
    if (!AppUtil.isNetworkConnected(this)){
      mSwipeToRefresh.setRefreshing(false);
      toastNetworkError();
      return;
    }

    this.pageNumber = pageNumber;
    MovieManager.getInstance().getTopRatedMovies(pageNumber, this);
  }



  // this method search for a movie in the server according to the page number and the search query
  private void showSearchedMovies(String str, int pageNumber) {
    if (!AppUtil.isNetworkConnected(this)){
      mSwipeToRefresh.setRefreshing(false);
      toastNetworkError();
      return;
    }
    this.pageNumber = pageNumber;
    MovieManager.getInstance().searchMovies(str, pageNumber, this);
  }


  // display network error to the user
  private void toastNetworkError() {
    Toast.makeText(this,"No internet connection",Toast.LENGTH_SHORT).show();
  }


  // handles the back pressed event
  @Override
  public void onBackPressed() {

    // if we are in search mode we go back to top rated movie mode
    if (mIsInSearchMode) {
      setSearchMode(false);
      showTopRatedMovie(1);
      mSearch.getText().clear();
      return;
    }

    super.onBackPressed();
  }


  // toggle the activity mode search or top rated
  private void setSearchMode(boolean searchMode) {
    mIsInSearchMode = searchMode;
    mSearchContainer.setElevation(mIsInSearchMode ? 20 : 0);
    mTitle.setVisibility(mIsInSearchMode ? View.GONE : View.VISIBLE);
  }

  // update the ui data
  private void updateUiData(MovieTopRatedResponse response) {
    // we clear the data when it's the first page so we will not have an old data in the list
    if (pageNumber == 1) {
      mAdapter.clearAllData();
    }

    // adding the new data
    mAdapter.addData(response.results);
    mAdapter.notifyDataSetChanged();
  }

  @Override
  public void OnReceivedMovies(MovieTopRatedResponse response) {
    // we got movie data from the server stopping the refreshing and the progressbar
    mSwipeToRefresh.setRefreshing(false);
    mAdapter.removeProgressBar();
    updateUiData(response);
  }


  @Override
  public void OnFailedToReceiveMovies(String error) {
    // we failed to get the movie data from the server stopping the refreshing and the progressbar
    mSwipeToRefresh.setRefreshing(false);
    mAdapter.removeProgressBar();
    Log.e(TAG, error);
  }


  // on touch for the search
  @Override
  public boolean onTouch(View v, MotionEvent event) {
    // when we press the search we go to search mode
    if (event.getAction() == MotionEvent.ACTION_UP) {
      setSearchMode(true);
    }
    return false;
  }


  @Override
  public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
    // when we reach the end of the list we load the next page and adding progressBar
    int lastVisiblePosition = mLayoutManager.findLastVisibleItemPosition();

    if (lastVisiblePosition < mAdapter.getItemCount() - 1) {
      return;
    }
    mAdapter.addProgressBar();
    pageNumber++;

    if (mIsInSearchMode) {
      String search = mSearch.getText().toString().trim();
      if (!search.isEmpty())
        showSearchedMovies(search, pageNumber);
    } else {
      showTopRatedMovie(pageNumber);
    }
  }

  @Override
  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    //  hide the keyboard and preform search when click on search
    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
      AppUtil.hideKeyboard(this);
      performSearch(mSearch.getText().toString());
    }

    return false;
  }

  //preform the search
  private void performSearch(String str) {
    str = str.trim();
    if (str.isEmpty()) {
      return;
    }
    showSearchedMovies(mSearch.getText().toString(), 1);
  }

  @Override
  public void onRefresh() {
    //  refreshing according to the activity mode
    if (mIsInSearchMode) {
      showSearchedMovies(mSearch.getText().toString(), 1);
    } else {
      showTopRatedMovie(1);
    }
  }

  @Override
  public void onClick(View v) {
    //  got click on a view in the recycler view starts the MovieDetailsActivity
    int position = mRecyclerView.getChildLayoutPosition(v);
    MovieItem item = (MovieItem) mAdapter.mData.get(position);
    Intent intent = new Intent(MainScreenActivity.this, MovieDetailsActivity.class);
    intent.putExtra(ModelConstance.MOVIE_DETAIL_ITEM, item);
    startActivity(intent);
  }
}
