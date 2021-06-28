package br.unibh.sdm.healthlabexames.api;

import java.util.List;

import br.unibh.sdm.healthlabexames.entidades.Exame;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExamesService {

    @Headers({
            "Accept: application/json",
            "User-Agent: helthlab"
    })
    @GET("relatoriosexames")
    Call<List<Exame>> getExames();

    @GET("relatoriosexames/{matricula}")
    Call<Exame> getExames(@Path("matricula") String matricula);


    @POST("relatoriosexames")
    Call<Exame> atualizaexame( @Body Exame exame);

}
