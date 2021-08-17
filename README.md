# Giff
Android app which allows users to search, view, favourite, and unfavourite GIFs .

using MVVM architecture and latest components.

Language Used : Kotlin

Project Architecture: MVVM

Local Database for offline caching: Room with Kotlin-Coroutines Support

Webservice Manager: Retrofit2

Image Loading Library: glide

Multithreading: Kotlin-Coroutines

Other Libraries: LiveData, ViewModel,data binding, lottie animation,pagination,Kodein(kotlin dependency Injection),TabLayout,ViewPager

the app consiting of two screens

Trending Giff screen & Favourite Giff screen

the app deals with giphy.com and loads Trending GIF Endpoint : https://api.giphy.com/v1/gifs/trending and Search GIFs Endpoint https://api.giphy.com/v1/gifs/search.

first screen contains an Edit text for search giffs at the top.

By default first screen will load trending GIFs.

user can view giffs and make it to favourite and unfavourite from both screens

shared view model is used for commination between the fragments.

favorite button in first screen indicate GIF is a favourite or not

unit testing for room data base is added. (need to fix viewmodel unit test)

thank you for reading ☺️☺️☺️
