package mk.ukim.finki.mpip.helloworld.web;

import mk.ukim.finki.mpip.helloworld.model.MovieDetails;
import mk.ukim.finki.mpip.helloworld.model.MoviesSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dejan on 14.11.2017.
 */

public interface MoviesApi
{
    @GET("?apikey=2d45a5fe&plot=short")
    Call<MoviesSearchResult> search(@Query("s") String query);

    @GET("?apikey=2d45a5fe&plot=full")
    Call<MovieDetails> getByImdbId(@Query("i") String id);
}
