package com.example.popularmovies.Reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {



        @SerializedName("results")
        @Expose
        private List<Review> mResults = null;

        public interface ReviewsAcquiredListener {
            void onReviewsAcquired(List<Review> reviews);
        }

        public List<Review> getReviewList() {
            return mResults;
        }


}
