package com.example.b3tempocasalini;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IEdfApi {

    // https://particulier.edf.fr/services/rest/referentiel/getNbTempoDays?TypeAlerte=TEMPO

    @GET("services/rest/referentiel/getNbTempoDays")
    Call<TempoDaysLeft> getTempoDaysLeft(@Query("TypeAlerte") String alertType);
}
