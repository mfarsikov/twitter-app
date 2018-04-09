# Simple Twitter interaction application

## Build and run
Setup environment variables

    SPRING_SOCIAL_TWITTER_APPID=your_app_id 
    SPRING_SOCIAL_TWITTER_APPSECRET=your_app_secret

Build JAR:

    ./gradlew build
    
Start from source codes:

    ./gradlew bootRun
    
Run JAR:

    java -jar build/lib/twitter-app-0.1.0.jar

## Description

### Disclaimer
There are a lot of flaws, I didn't try to cover all corner cases, just wanted to implement happy-path.

### Technology choice

#### Current implementation
I used Spring because it has rich harness to solve any kinds of problems, and it was ideal 
for implementing such a task using Java stack.

#### Further improvements
* Replace backend HTML generation with REST API and Single Page Application using component based architecture
* Replace servlet model (single thread per request) with asynchronous one using spring 5 web-flux and netty server or Eclipse Vert.x
* Use third party service (like Auth0) for authentication, authorization and user management

### Implemented features
* Sign up, log in, log out 
* Users are persisted in DB (for simplicity H2 in memory DB is used)
* Simple twits search
* Advanced search (by name, by date) with same syntax as provided by Twitter for their advanced search (https://twitter.com/search-home#)
* Viewed twits (their hashes) are stored in DB 
* "Forgot password" flow
* "I’m feeling lucky" button

### Not implemented features

#### "I'm lucky" storage reading optimization
The single optimization I'm thinking about - is caching. 
From one side it can be easily done using Spring, but from other side - looks like we do need it at all.

Any time after we read tweet history, we change it. 
So any attempt to cache some data ends up with invalidated cache.
Therefore we will not have any performance boost.

Also any performance optimizations must be proven. 