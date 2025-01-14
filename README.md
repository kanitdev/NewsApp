# News App
A modern Android news application that allows users to browse breaking news, search for articles, and save their favorite news pieces for offline reading.

## Features
- Browse breaking news from multiple sources
- Search for specific news articles
- Save favorite articles for offline reading
- Clean and modern Material Design UI
- Pagination support for smooth scrolling
- Article sharing functionality
- Webview integration for full article reading

## Tech Stack
- **Kotlin** - Primary programming language
- **MVVM Architecture** - Recommended architecture for Android apps
- **Coroutines** - For asynchronous programming
- **Room Database** - For local data persistence
- **Retrofit** - For network requests
- **Navigation Component** - For in-app navigation
- **View Binding** - For efficient view access
- **RecyclerView** - For displaying lists of articles
- **NewsAPI** - As the news data source

## Architecture
The app follows MVVM (Model-View-ViewModel) architecture and Repository pattern:
- **UI Layer**: Activities & Fragments
- **ViewModel Layer**: NewsViewModel
- **Repository Layer**: NewsRepository
- **Data Layer**: Room Database & NewsAPI

## Setup
1. Clone the repository:
2. Get an API key from [NewsAPI](https://newsapi.org/)
3. Add your API key in `Constants.kt`:
4. Build and run the project
