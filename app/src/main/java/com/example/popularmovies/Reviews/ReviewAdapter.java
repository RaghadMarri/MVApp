package com.example.popularmovies.Reviews;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<Review> mReviews;

    public ReviewAdapter(List<Review> reviews) {
        mReviews = reviews;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View reviewView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        return new ReviewHolder(reviewView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Review review = mReviews.get(position);
        SpannableString author = new SpannableString(review.getAuthor() + ": "
                + review.getContent());
        author.setSpan(new StyleSpan(Typeface.BOLD), 0, review.getAuthor().length(),
                0);

        holder.mReviewTextView.setText(author);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        public TextView mReviewTextView;

        public ReviewHolder(View itemView) {
            super(itemView);
            mReviewTextView = itemView.findViewById(R.id.list_item_review_textview);
            mReviewTextView.setMovementMethod(new ScrollingMovementMethod());
        }
    }
}

