package com.indraarianggi.moviedb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.indraarianggi.moviedb.model.Movie;

import java.util.ArrayList;

/**
 * Created by indraarianggi on 20/12/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_movie, parent, false);

        MovieViewHolder movieViewHolder = new MovieViewHolder(view);

        return movieViewHolder;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMovie;
        TextView tvJudul;
        TextView tvRelease;

        public MovieViewHolder(View itemView) {
            super(itemView);

            ivMovie = (ImageView)itemView.findViewById(R.id.iv_movie);
            tvJudul = (TextView)itemView.findViewById(R.id.tv_title_value);
            tvRelease = (TextView)itemView.findViewById(R.id.tv_release_value);
        }
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        String pathGambar = movies.get(position).getPosterPath();
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + pathGambar)
                .into(holder.ivMovie);

        holder.tvJudul.setText(movies.get(position).getTitle());
        holder.tvRelease.setText(movies.get(position).getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
