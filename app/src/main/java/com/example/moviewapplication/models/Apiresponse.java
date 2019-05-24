package com.example.moviewapplication.models;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviewapplication.R;

import java.io.Serializable;
import java.util.List;

import androidx.databinding.BindingAdapter;

public class Apiresponse  {
      int page;
      int total_results;
      int total_pages;
      List<OneMovie>results;

    public Apiresponse(int page, int total_results, int total_pages, List<OneMovie> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public Apiresponse() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<OneMovie> getResults() {
        return results;
    }

    public void setResults(List<OneMovie> results) {
        this.results = results;
    }


   public class OneMovie  implements Serializable {
        int vote_count;
        int id;
        Boolean video;
        float vote_average;
        String title;
        Double popularity;
        String poster_path;
        String original_language;
        String original_title;
        List<Integer>genre_ids;
        String backdrop_path;
        Boolean adult;
        String overview;
        String release_date;

        public OneMovie() {
        }

        public OneMovie(int vote_count, int id, Boolean video, float vote_average, String title,
                        Double popularity, String poster_path, String original_language, String original_title, List<Integer> genre_ids,
                        String backdrop_path, Boolean adult, String overview, String release_date) {
            this.vote_count = vote_count;
            this.id = id;
            this.video = video;
            this.vote_average = vote_average;
            this.title = title;
            this.popularity = popularity;
            this.poster_path = poster_path;
            this.original_language = original_language;
            this.original_title = original_title;
            this.genre_ids = genre_ids;
            this.backdrop_path = backdrop_path;
            this.adult = adult;
            this.overview = overview;
            this.release_date = release_date;
        }


        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Boolean getVideo() {
            return video;
        }

        public void setVideo(Boolean video) {
            this.video = video;
        }

        public float getVote_average() {
            return vote_average;
        }

        public void setVote_average(float vote_average) {
            this.vote_average = vote_average;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Double getPopularity() {
            return popularity;
        }

        public void setPopularity(Double popularity) {
            this.popularity = popularity;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public void setGenre_ids(List<Integer> genre_ids) {
            this.genre_ids = genre_ids;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public Boolean getAdult() {
            return adult;
        }

        public void setAdult(Boolean adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }




    }
    @BindingAdapter("android:imageurlpaid")
    public  static void lOaadimage(View view , String Imageurl)
    {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.plsceholder);
        requestOptions.error(R.drawable.plsceholder);
        Context context = view.getContext();
        Glide.with(context).setDefaultRequestOptions(requestOptions).load("http://image.tmdb.org/t/p/w500/"+Imageurl).into((ImageView) view);
        // Log.d("ASASA","http://a64b285c.ngrok.io"+Imageurl);
    }
}
