package com.example.popularmovies.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorites")
public class MovieData implements Parcelable {

    @Ignore
    private static final String BASE_PATH = "http://image.tmdb.org/t/p/w185/";

    @PrimaryKey
    private int id;

    private String title;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private double voteAverage;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;

    private String overview;

    //Parcelable doesn't let you write a single boolean.
    //Using 0 to specify something that is not a favorite.
    //Using 1 to specify a favorite.

    @Ignore
    private int favorite = 0;

    public MovieData(String title,
                     String releaseDate,
                     double voteAverage,
                     String posterPath,
                     String overview,
                     int id) {

        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.overview = overview;
        this.id = id;
    }


    public MovieData(Parcel in) {
        title = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
        posterPath = in.readString();
        overview = in.readString();
        id = in.readInt();
        favorite = in.readInt();

    }


    public String getSmallPosterUrl() {
        return BASE_PATH + getPosterPath();
    }

    /* writeToParcel and describeContents are used to write parcels. */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeInt(id);
        dest.writeInt(favorite);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    public int isFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}