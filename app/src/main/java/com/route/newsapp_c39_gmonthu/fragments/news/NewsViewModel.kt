package com.route.newsapp_c39_gmonthu.fragments.news

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.route.newsapp_c39_gmonthu.api.ApiManager
import com.route.newsapp_c39_gmonthu.model.ArticlesItem
import com.route.newsapp_c39_gmonthu.model.ArticlesResponse
import com.route.newsapp_c39_gmonthu.model.Constants
import com.route.newsapp_c39_gmonthu.model.SourcesItem
import com.route.newsapp_c39_gmonthu.model.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsViewModel : ViewModel() {
    // Jetpack compose states + UI Logic and API calls and database calls
    // Lifecycle Aware  -> destroy for activity means destroy for view Model also
    // Memory Leak
    val selectedIndexState = mutableIntStateOf(0)
    val newsSources = mutableStateListOf<SourcesItem>()
    val newsStatesItems = mutableStateListOf<ArticlesItem>()

    fun fetchNewsBySource(sourceId: String) {
        ApiManager.getNewsServices()
            .getNewsBySource(Constants.API_KEY, sourceId)
//                    .execute() // main Thread
            .enqueue(object : Callback<ArticlesResponse> {
                override fun onResponse(
                    call: Call<ArticlesResponse>,
                    response: Response<ArticlesResponse>
                ) {
                    newsStatesItems.clear()
                    val newsList = response.body()?.articles
                    if (newsList?.isNotEmpty() == true) {
                        newsStatesItems.addAll(newsList)
                    }
                    // Documentation -> Thread -> Room
                }
                // Main Thread ->  handle button clicks => UserNavigation
                // Background Thread -> Networking -> Local Database  <-> Heavy loading task
                // Instagram -> APIs Sign up


                override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {

                }

            })

    }

    fun fetchSources(categoryId: String) {
        ApiManager.getNewsServices()
            .getNewsSources(Constants.API_KEY, categoryId)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    val sources = response.body()?.sources
                    if (sources?.isNotEmpty() == true) {
                        newsSources.addAll(sources)
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }


            })  // enqueue -> background Thread

    }
}

