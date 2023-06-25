package com.example.buildingblocks.data.model.retrofit.stockInfo.model
import com.google.gson.annotations.SerializedName


data class StockUrlData(
    val meta: Meta,
    val data: List<Datum>
)

data class Meta(
    val found: Int,
    val returned: Int,
    val limit: Int,
    val page: Int
)

data class Highlight(
    val highlight: String,
    val sentiment: Double,
    @SerializedName("highlighted_in")
    val highlightedIn: String
)

data class Entity(
    val symbol: String,
    val name: String,
    val exchange: String,
    @SerializedName("exchange_long")
    val exchangeLong: String,
    val country: String,
    val type: String,
    val industry: String,
    @SerializedName("match_score")
    val matchScore: Double,
    @SerializedName("sentiment_score")
    val sentimentScore: Double,
    val highlights: List<Highlight>
)

data class Datum(
    val uuid: String,
    val title: String,
    val description: String,
    val keywords: String,
    val snippet: String,
    val url: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val language: String,
    @SerializedName("published_at")
    val publishedAt: String,
    val source: String,
    @SerializedName("relevance_score")
    val relevanceScore: Any?, // Use 'Any?' if the field can be null
    val entities: List<Entity>,
    val similar: List<Any> // Change the type to the appropriate data class if 'similar' has a defined structure
)

