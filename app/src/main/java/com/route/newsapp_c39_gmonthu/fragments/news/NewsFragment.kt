package com.route.newsapp_c39_gmonthu.fragments.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.route.newsapp_c39_gmonthu.R
import com.route.newsapp_c39_gmonthu.api.ApiManager
import com.route.newsapp_c39_gmonthu.model.ArticlesItem
import com.route.newsapp_c39_gmonthu.model.ArticlesResponse
import com.route.newsapp_c39_gmonthu.model.Constants
import com.route.newsapp_c39_gmonthu.utils.NewsCard
import com.route.newsapp_c39_gmonthu.utils.NewsSourcesTabRow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewsFragmentContent(
    modifier: Modifier = Modifier,
    categoryId: String,
    viewModel: NewsViewModel = viewModel()
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.pattern),
                contentScale = ContentScale.Crop
            )
    ) {//4-
        NewsSourcesTabRow(categoryId) { sourceId ->
            // Observe ->   // Observable  has Data ->
            // Observe ->
            // Observe ->
            viewModel.fetchNewsBySource(sourceId)
        }

        //{ sourceId ->
        // 1- Serialization -> Convert kotlin to json ,,,
        //    Deserialization -> Convert json to kotlin
        // 2-  interface callback ->
        //     A) RecyclerView on Clicks ->
        //     B) 2 Fragments -> interface callbacks -> To Do App (BottomSheet Fragment )
        //                      Activity ->   taskListFragment=             TasksListFragment()
        //                                   taskListFragment.getTodosFromDatabase()
        //
        NewsList(viewModel.newsStatesItems.toList())
    }
}

@Composable
fun NewsList(list: List<ArticlesItem>) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        items(list.size) { position ->
            NewsCard(list[position])
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsFragmentContentPreview() {
    NewsFragmentContent(categoryId = "")
}
