package mk.ukim.finki.mpip.helloworld.web;

import java.util.List;

import mk.ukim.finki.mpip.helloworld.model.Country;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by ristes on 25.10.17.
 */

public interface CountriesApi {

    @GET("all")
    Call<List<Country>> allCountries();

    @GET("byName/{name}")
    Call<List<Country>> getByName(@Path("name") String name);
}
