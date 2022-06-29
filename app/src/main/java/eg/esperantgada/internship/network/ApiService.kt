package eg.esperantgada.internship.network

import eg.esperantgada.internship.data.ApiPhotos
import eg.esperantgada.internship.utils.ENDPOINT
import retrofit2.http.GET

interface ApiService {

    @GET(ENDPOINT)
    suspend fun getImage() : ApiPhotos

}