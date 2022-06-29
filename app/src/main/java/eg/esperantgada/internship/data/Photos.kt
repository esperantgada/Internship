package eg.esperantgada.internship.data

import com.google.gson.annotations.SerializedName

data class Photos(

    @SerializedName("photo")
    val photo: List<Photo>

)