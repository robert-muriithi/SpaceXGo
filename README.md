# Space X Go  🚀 
Space X Go is an android app built using Kotlin that consumes [Space X API](https://github.com/r-spacex/SpaceX-API) to display rockets, ships, launches and Space X company information. It has been built following Clean Architecture Principle, Repository Pattern, MVVM Architecture in the presentation layer as well as Jetpack components. Some of the features are Launch tracking & other details.

## Table Of Content.

- [Prerequisite](#prerequisite)
- [Architecture](#architecture)
    - [What is Clean Architecture](##why-clean-architecture)
    - [Why Clean Architecture](##why-clean-architecture)
    - [Layers](##layers)
        - [Domain](###domain)
        - [Data](###data)
        - [Presentation](###presentation)
- [Tech Stack](#techstack)
    - [Libraries](##libraries)
    - [Extras](##extras)
- [Helpful Resources](#helpful-resources)
- [Screenshots](#screenshots)

## Prerequisite.
To get the application, run the following command on your terminal `https://github.com/robert-muriithi/SpaceXGo.git` or open android studio and create a new project from version control and paste `https://github.com/robert-muriithi/SpaceXGo.git`


> NOTE: Using complex architecture such as clean architecture may result in increased code complexity due to the need for multiple data transformations (mappers) and models to maintain decoupling. This can steepen the learning curve and make the code harder to understand. This repository implements the clean architecture solely to demonstrate my proficiency, and it is not recommended for small scale projects like this one, where a simpler approach such as MVVM may be more suitable

## Architecture.

### What is Clean Architecture?
Clean Architecture is a software design approach that aims to decouple the various components of an application to improve its maintainability, testability, and scalability. It does so by organizing the code into concentric circles, with the innermost layer being the core of the application and the outer layers handling increasingly abstract concerns such as the presentation and persistence of data. This helps to maintain separation of concerns and keep the codebase organized, even as the application grows in size and complexity.

Clean architecture was introduced by Robert C. Martin in 2012. Read [Clean Code Blog](http://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html). It follows the SOLID principles, which are a set of guidelines for writing maintainable and scalable software.

<p align="center"><img src="" alt="Clean Architecture Diagram"></p>

### Why Clean Architecture?
- Improved separation of concerns: Clean architecture helps to maintain a clear separation between different components of an application, making it easier to understand and modify.
- Increased maintainability: By keeping code organized and decoupled, it becomes easier to modify and maintain over time.
- Better testability: Clean architecture encourages writing tests for each component in isolation, making it easier to validate the functionality of individual parts of the codebase.
- Improved scalability: The modular nature of clean architecture makes it easier to add new features and extend the codebase as the application grows in size and complexity.
- Better adaptability: Clean architecture helps to make an application more flexible and adaptable to change, as different components can be modified or replaced without affecting the rest of the codebase.

## Layers.

### 1. Domain.
This is the core layer of the application. The ```domain``` layer is independent of any other layers thus ] domain models and business logic can be independent from other layers.This means that changes in other layers will have no effect on domain layer eg.  screen UI (presentation layer) or changing database (data layer) will not result in any code change withing domain layer.

Components of domain layer include:
- __Models__: Defines the core structure of the data that will be used within the application.

- __Repositories__: Interfaces used by the use cases. Implemented in the data layer.

- __Use cases/Interactors__: They enclose a single action, like getting data from a database or posting to a service. They use the repositories to resolve the action they are supposed to do. They usually override the operator “invoke”, so they can be called as a function.

### 2. Data.
The ```data``` layer is responsibile for selecting the proper data source for the domain layer. It contains the implementations of the repositories declared in the domain layer. 

Components of data layer include:
- __Models__

    -__Dto Models__: Defines POJO of network responses.

    -__Entity Models__: Defines the schema of SQLite database.

- __Repositories__: Responsible for exposing data to the domain layer.

- __Mappers__: They perform data transformation between ```domain```, ```dto``` and ```entity``` models.

- __Network__: This is responsible for performing network operations eg. defining API endpoints using [Retrofit](https://square.github.io/retrofit/).

- __Cache__: This is responsible for performing caching operations using [Room](https://developer.android.com/training/data-storage/room).

- __Data Source__:  Responsible for deciding which data source (network or cache) will be used when fetching data.

### 3. Presentation.
The ```presentation``` layer contains components involved in showing information to the user. The main part of this layer are the views(activity and fragments) and viewmodels.

# Tech Stack.
This project uses many of the popular libraries, plugins and tools of the android ecosystem.
