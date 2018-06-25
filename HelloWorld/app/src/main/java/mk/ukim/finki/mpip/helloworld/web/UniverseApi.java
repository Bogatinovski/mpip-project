package mk.ukim.finki.mpip.helloworld.web;

import java.util.List;

import mk.ukim.finki.mpip.helloworld.model.CelestialBodyListItem;
import mk.ukim.finki.mpip.helloworld.model.MovieDetails;
import mk.ukim.finki.mpip.helloworld.model.MoviesSearchResult;
import mk.ukim.finki.mpip.helloworld.model.Universe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dejan on 14.11.2017.
 */

public interface UniverseApi
{
    @GET("api/Universe")
    Call<Universe> getHomePage();

    @GET("api/Asteroids")
    Call<List<CelestialBodyListItem>> getAsteroids();

    @GET("api/Constellations")
    Call<List<CelestialBodyListItem>> getConstellations();

    @GET("api/Galaxies")
    Call<List<CelestialBodyListItem>> getGalaxies();

    @GET("api/Planets")
    Call<List<CelestialBodyListItem>> getPlanets();

    @GET("api/Satellites")
    Call<List<CelestialBodyListItem>> getSatellites();


    @GET("api/Stars")
    Call<List<CelestialBodyListItem>> getStars();
}
