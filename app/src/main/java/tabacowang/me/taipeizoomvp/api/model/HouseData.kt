package tabacowang.me.taipeizoomvp.api.model

import com.google.gson.annotations.SerializedName

data class HouseResponse(
    @SerializedName("result")
    val result: HouseResponseInfo?
)

data class HouseResponseInfo(
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("sort")
    val sort: String?,
    @SerializedName("results")
    val results: List<HouseData>?
)

data class HouseData(
    @SerializedName("E_Pic_URL")
    val picUrl: String?,
    @SerializedName("E_Geo")
    val gps: String?,
    @SerializedName("E_Info")
    val info: String?,
    @SerializedName("E_no")
    val no: String?,
    @SerializedName("E_Category")
    val category: String?,
    @SerializedName("E_Name")
    val name: String?,
    @SerializedName("E_Memo")
    val memo: String?,
    @SerializedName("_id")
    val _id: Int?,
    @SerializedName("E_URL")
    val webUrl: String?
)
