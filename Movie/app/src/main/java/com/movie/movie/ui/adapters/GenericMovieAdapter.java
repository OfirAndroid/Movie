package com.movie.movie.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.movie.movie.R;
import com.movie.movie.listeners.RecyclerViewClickListener;
import com.movie.movie.models.BaseModel;
import com.movie.movie.models.ModelConstance;
import com.movie.movie.models.MovieCastItem;
import com.movie.movie.models.MovieItem;
import com.movie.movie.network.MovieApi;
import com.movie.movie.ui.MovieCastViewHolder;
import com.movie.movie.ui.MovieViewHolder;
import com.movie.movie.ui.ProgressViewHolder;
import com.movie.movie.util.AppUtil;

import java.util.ArrayList;


/*
 *  The GenericMovieAdapter class is an adapter who can handle different types (currently we have 3 types) of views
 * */

public class GenericMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

  private final int VIEW_PROG = 0; // progress type
  private final int VIEW_MOVIE_ITEM = 1; // movie type
  private final int VIEW_CAST_MOVIE = 2;// movie cast type
  public ArrayList<? extends BaseModel> mData;
  private LayoutInflater mInflater;
  private int lastPosition = -1; // last position used for tracking the animation of the view
  public RecyclerViewClickListener mClickListener; // this is a click listener for handling when a view is clicked


  public GenericMovieAdapter(Context context, ArrayList<? extends BaseModel> data, RecyclerViewClickListener listener) {
    this.mInflater = LayoutInflater.from(context);
    this.mData = data;
    this.mClickListener = listener;
  }

  // this method used for add list of data to the existing data we already have
  public void addData(ArrayList data) {
    mData.addAll(data);
  }

  // this method add a progress view to the recycler when we need to load more data
  public void addProgressBar() {
    if (!mData.contains(null)) {
      mData.add(null);
    }
  }

  // this method used for removing the progress view from the recycler when we finished to load the data
  public void removeProgressBar() {
    if (mData.contains(null)) {
      mData.remove(null);
    }
  }


  /*
   * Returns the view holder according to the view type
   * */
  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    RecyclerView.ViewHolder vh;

    if (viewType == VIEW_MOVIE_ITEM) {
      View view = mInflater.inflate(R.layout.top_rated, parent, false);
      view.setOnClickListener(this);
      vh = new MovieViewHolder(view);
    } else if (viewType == VIEW_CAST_MOVIE) {
      View view = mInflater.inflate(R.layout.movie_cast_item, parent, false);
      view.setOnClickListener(this);
      vh = new MovieCastViewHolder(view);
    } else {
      View view = mInflater.inflate(R.layout.progress_item, parent, false);
      vh = new ProgressViewHolder(view);

    }
    return vh;
  }

  /*
   * Binds the view holder according to the view type and populate the Ui data
   * */
  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    if (holder instanceof MovieViewHolder) {
      MovieItem item = (MovieItem) mData.get(position);
      ((MovieViewHolder) holder).title.setText(item.getTitle());
      ((MovieViewHolder) holder).year.setText(AppUtil.getYearFromDate(item.getReleaseDate(), ModelConstance.DATE_PATTERN));
      ((MovieViewHolder) holder).description.setText(item.getOverview());
      ((MovieViewHolder) holder).rate.setText(String.valueOf(item.getVoteAverage()));
      Glide.with(mInflater.getContext()).load(MovieApi.BASE_IMAGE + item.getPosterPath()).apply(RequestOptions.placeholderOf(R.drawable.ic_no_pic)).into(((MovieViewHolder) holder).img);
    } else if (holder instanceof MovieCastViewHolder) {
      MovieCastItem item = (MovieCastItem) mData.get(position);
      ((MovieCastViewHolder) holder).characterName.setText(item.getCharacterName() == null ? ModelConstance.NOT_AVAILABLE : item.getCharacterName());
      ((MovieCastViewHolder) holder).name.setText(item.getName() == null ? ModelConstance.NOT_AVAILABLE : item.getName());
      Glide.with(mInflater.getContext()).load(MovieApi.BASE_IMAGE + item.getProfilePath()).apply(RequestOptions.placeholderOf(R.drawable.ic_no_pic)).into(((MovieCastViewHolder) holder).img);
    } else if (holder instanceof ProgressViewHolder) {
      ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
    }

    if (holder != null)
      setAnimation(holder.itemView, position);

  }


  // set the animation for each view
  private void setAnimation(View viewToAnimate, int position) {
    if (position > lastPosition) {
      Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.fade_in);
      viewToAnimate.startAnimation(animation);
      lastPosition = position;
    }
  }


  // return the data size
  @Override
  public int getItemCount() {
    return mData.size();
  }


  // returns the view type according to the position
  @Override
  public int getItemViewType(int position) {
    BaseModel baseModel = mData.get(position);

    if (baseModel instanceof MovieCastItem) {
      return VIEW_CAST_MOVIE;
    }

    if (baseModel instanceof MovieItem) {
      return VIEW_MOVIE_ITEM;
    }

    return VIEW_PROG;
  }

  // clears all the data
  public void clearAllData() {
    mData.clear();
  }

  // call on click event whe view have been clicked
  @Override
  public void onClick(View v) {
    if (mClickListener != null) {
      mClickListener.onClick(v);
    }
  }

}
