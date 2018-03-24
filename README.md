# Hoga
A mobile image box allows you to search and download free and beautiful pictures which you definitely love. 

Hoga = Hộp(Vietnamese) + Gazo-画像 (Japanese)

## Status: WORK IN PROGRESS

## Target Audience
* For beautiful photo lover:  
Build and run this app to get amazing images into your pocket. 

* For technical explorer:  
This project is built on **Clean Architecture* using Kotlin. 
Besides, there are several technical stuffs such as reactive programming and dependence injection you might be interested in.

## Features
- Users can search pictures from various free image services
- Users can download their favorite pictures
- Users can set phone screen background with these pictures

## Technical Specification
* Programming language: Kotlin
* Architecture: Clean architecture
* Coding convention: [Code style](https://github.com/mcrafts/mobile-guidelines/blob/master/android-code-conventions.md)
* Libraries: RxJava2, Dagger2, OkHttp, Retrofit, JUnit, Mockito, Espresso.

## How to build it

Thanks to Unsplash, this project uses their crafted clean API to fetch beautiful images.  
- Firstly, let's head to [Unsplash's developer page](https://unsplash.com/developers) to get your own API token.  
- Secondly, put it inside your `local.properties` file as following:
```gradle
unsplashToken=Client-ID ***************
```  
- Finally, here you go. Let's run.

## License
This project is available under the MIT license. See the LICENSE file for more info.