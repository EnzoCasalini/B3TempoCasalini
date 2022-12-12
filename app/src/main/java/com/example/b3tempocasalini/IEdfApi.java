package com.example.b3tempocasalini;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IEdfApi {

    String EDF_TEMPO_API_ALERT_TYPE = "TEMPO";

    // https://particulier.edf.fr/services/rest/referentiel/getNbTempoDays?TypeAlerte=TEMPO

    @GET("services/rest/referentiel/getNbTempoDays")
    Call<TempoDaysLeft> getTempoDaysLeft(@Query("TypeAlerte") String alertType);

    @GET("services/rest/referentiel/searchTempoStore")
    Call<TempoDaysColor> getTempoDaysColor(@Query("dateRelevant") String relevantDate, @Query("TypeAlerte") String alertType);

    @GET("services/rest/referentiel/historicTEMPOStore")
    Call<TempoHistory> getTempoHistory(@Query("dateBegin") String beginningDate, @Query("dateEnd") String endingDate);
}
