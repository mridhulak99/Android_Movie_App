package edu.uci.swe264p.retrofit;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.List;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private String BASE_IMG_PATH = "https://image.tmdb.org/t/p/w500/";
    private List<Movie> mData;

    MovieListAdapter(List<Movie> data) {
        this.mData = data;
        }
    public void setMovieList(List<Movie> movieList)
    {
        this.mData=movieList ;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView voteAverage;
        public TextView releaseDate;
        public TextView overview;
        public ImageView logo;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            voteAverage = itemView.findViewById(R.id.tvVote);
            releaseDate = itemView.findViewById(R.id.tvReleaseDate);
            overview = itemView.findViewById(R.id.tvOverview);
            logo = itemView.findViewById(R.id.ivMovie);
        }
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        return new MovieListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {
        Movie  movie = mData.get(position);
        //holder.tv.setText(movie);
        holder.title.setText(movie.getTitle());
        holder.voteAverage.setText(String.valueOf(movie.getVoteAverage()));
        holder.releaseDate.setText(movie.getReleaseDate());
        holder.overview.setText(movie.getOverview());
        Picasso.get().load(BASE_IMG_PATH+movie.getPosterPath()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
