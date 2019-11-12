package com.example.popularmovies.Trailers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.R;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {
    private List<Trailer> mVideos;

    public TrailerAdapter(List<Trailer> videos) {
        mVideos = videos;
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();


        View videoView = LayoutInflater.from(context).inflate(R.layout.list_item_trailer, parent, false);


        com.example.popularmovies.Trailers.TrailerViewClickListener listener = new
                com.example.popularmovies.Trailers.TrailerViewClickListener() {
                    @Override
                    public void onClick(View view, int i) {
                        String key = mVideos.get(i).getKey();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + key));
                        context.startActivity(intent);
                    }
                };

        TrailerHolder videoHolder = new TrailerHolder(videoView, listener);
        return videoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        //Get the data position.
        Trailer video = mVideos.get(position);

        //Set the views based on the data.
        holder.mVideoTextView.setText(video.getName());

    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }



    public class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mVideoTextView;
        private com.example.popularmovies.Trailers.TrailerViewClickListener mVideoViewClickListener;

        public TrailerHolder(View itemView, com.example.popularmovies.Trailers.TrailerViewClickListener listener) {
            super(itemView);
            mVideoTextView = itemView.findViewById(R.id.list_item_trailer_textview);
            mVideoViewClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mVideoViewClickListener.onClick(v, getAdapterPosition());
        }
    }
}
