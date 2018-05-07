# IDENTITY E2E

Vehicle registration details verification App
See screenshots ScreenShot1.png,ScreenShot2.png and ScreenShot3.png,

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

The project was built with IntelliJ on MacBook but can be done with any basic IDE using Gradle

NB: Don't forget to point the configuration to the location of the gecko driver for Selenium

Please update the configuration details in

src/main/resources/application.properties
and
src/test/resources/application.properties

NB: If using IntelliJ, enable annontation processing for the compiler

### Installing

A step by step series of examples that tell you have to get a development env running


Go to the project root folder

```
cd $PROJECT_ROOT
```

To build the project, make sure src/test/resources/application.properties is configured according to your environment

```
./gradlew clean build
```
To run the project, make sure src/main/resources/application.properties is configured according to your environment

```
./gradlew bootRun
```
Chrome browser is currently configured to run in headless mode see src/main/resources/application.properties.

See the results by opening the url
```
http://localhost:8080/vehicleHome
```

## Running the tests

./gradlew clean build

## Deployment

Deployment can be done as a Dockerized App. See Dockerfile for the Docker image
Otherwise, just run

## Built With

* [SpringBoot] - The web framework used
* [Gradle] - Dependency Management
* [Apache POI] - Used to read excel files
* [Selenium WebDriver] - Used for querying gov.uk vehicle db
* [Docker] - for Containerisation


## Authors

* **Ebiere Ugo** - *Initial work* - [brugoconsulting](https://github.com/brugoconsulting)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

