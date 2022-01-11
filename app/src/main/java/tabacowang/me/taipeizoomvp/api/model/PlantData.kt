package tabacowang.me.taipeizoomvp.api.model

import com.google.gson.annotations.SerializedName

data class PlantResponse(
    @SerializedName("result")
    val result: PlantResponseInfo?
)

data class PlantResponseInfo(
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("sort")
    val sort: String?,
    @SerializedName("results")
    val results: List<PlantData>?
)

data class PlantData(
    @SerializedName("_id")
    val _id: Int?,
    @SerializedName("F_Pic01_URL")
    val picUrl: String,
    @SerializedName("F_Location")
    val location: String?,
//    @SerializedName("F_Name_Ch")      // API本應正常的Key值
//    val nameCh: String?,
    @SerializedName("\uFEFFF_Name_Ch")
    val nameChBug: String?,
    @SerializedName("F_Name_En")
    val nameEn: String,
    @SerializedName("F_AlsoKnown")
    val nameAlias: String,
    @SerializedName("F_Brief")
    val brief: String,
    @SerializedName("F_Feature")
    val feature: String,
    @SerializedName("F_Function＆Application")
    val application: String,
    @SerializedName("F_Update")
    val lastUpdate: String
)