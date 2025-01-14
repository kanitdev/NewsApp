package com.example.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Locale.IsoCountryCode

class NewsViewModel(
    val newsRepository: NewsRepository
):ViewModel() {
    //change
    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResposne:NewsResponse? = null

    val searchNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse:NewsResponse?=null

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String)=viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response=newsRepository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNews.postValue(handelBreakingNewsResponse(response))
    }
    fun searchNews(searchQuery:String)=viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery,searchNewsPage)
        searchNews.postValue(handelSearchNewsResponse(response))
    }
    private fun handelBreakingNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                breakingNewsPage++
                if (breakingNewsResposne == null){
                    breakingNewsResposne = resultResponse
                }else{
                    val oldArticle = breakingNewsResposne?.articles
                    val newArticles = resultResponse.articles
                    oldArticle?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResposne ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handelSearchNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                if(response.isSuccessful){
                    response.body()?.let { resultResponse->
                        searchNewsPage++
                        if (searchNewsResponse == null){
                            searchNewsResponse = resultResponse
                        }else{
                            val oldArticle = searchNewsResponse?.articles
                            val newArticles = resultResponse.articles
                            oldArticle?.addAll(newArticles)
                        }
                        return Resource.Success(searchNewsResponse ?: resultResponse)
                    }
                }
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    fun saveArticle(article: Article)=viewModelScope.launch {
        newsRepository.upsert(article)
    }
    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) =viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}