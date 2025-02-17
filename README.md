# UML-Game-Backend

This repository contains the backend for
the [UML Game minigame (tbd)](tbd).

It persists the game data (configurations, game results, etc.) in a database and communicates with other backend
services.

## Table of contents

<!-- TOC -->
* [Links](#links)
* [REST API](#rest-api)
* [Development](#development)
* [Getting started](#getting-started)
  * [Docker-compose](#docker-compose)
  * [Project build](#project-build)
  * [With Docker](#with-docker)
  * [Testing Database](#testing-database)
* [Class Diagrams](#class-diagrams)
<!-- TOC -->

## Links

- User documentation for the minigame can be
  found [tbd](tbd).
- For the frontend, see the [Gamify-IT/umlgame repository](https://github.com/Gamify-IT/).
- The installation manual and setup instructions can be
  found [tbd](tbd).

## REST API

Rest mappings are defined in

- Game result
  controller: 
- Config
  controller: 

## Development

### Getting started
Make sure you have the following installed:

- Java: [JDK 1.17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher
- Maven: [Maven 3.6.3](https://maven.apache.org/download.cgi)
- Docker: [Docker](https://www.docker.com/)
- PostgreSQL: [PostgreSQL](https://www.postgresql.org/download/)

### Project build
To build the project, run:
```sh
mvn install
```

in the project folder.
Then go to the target folder:
```sh
cd target
```
and run:
```sh
java -jar umlgame-backend-0.0.1-SNAPSHOT.jar
```
to start the application.


### Build with docker
To run your local changes as a docker container, with all necessary dependencies,
build the Docker container with:

```sh
docker compose up --build
```
You can remove the containers with:
```sh
docker compose down
```

### Run local with dependencies
To run your local build within your IDE, but also have the dependencies running in docker, follow the steps
to build the project, then run the dependencies in docker with the following:
```sh
docker compose -f docker-compose-dev.yaml up 
```
You can remove the containers with:
```sh
docker compose -f docker-compose-dev.yaml down
```

### Testing Database

To set up a database with docker for testing you can use

```sh
docker run -d -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres  --rm --name umlgame-database postgres
```

To stop and remove it simply type

```sh
docker stop umlgame-database
```

## Class Diagrams

tbd
