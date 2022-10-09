# Personality Test

Simple personality test which evaluates users in five aspects: Extraversion, agreeableness, conscientiousness, emotional stability, and openness to experiences.


## Table of Contents

* [General info](#general-info)
* [Features](#features)
* [Technologies](#technologies)
* [Setup](#setup)
* [Sources](#sources)

## General Info

On the first tab the general information can be seen about the website. On the "Test" tab users can perform the personality test and see their results via a chart. On the "Averages" tab the average results of users that have already done the test can be seen and it can be filtered by gender, age, and average score. On the "Users" tab all users are shown which are registered and it allows to see individual tests of a user and update/delete users. On the last tab all results are shown. Additionally, they can be inspected, deleted, and the answers can be modified.

## Features

* Perform test
* Filter through all results
* Create, read, update, and delete users
* Create, read, update, and delete results

## Technologies

* **Client**
  * **Language**: TypeScript
  * **Framework**: React Bootstrap
  * **Router**: React Router DOM
  * **HTTP Library**: Axios
  * **Style**: Bootswatch - Cosmo
  * **Chart**: React Charts
* **Server**
  * **Build Tool**: Apache Maven
  * **Language**: Java
  * **Framework**: Spring Boot
  * **JPA**: Hibernate
  * **Database**: PostgreSQL
  * **Testing**: JUnit 5 + Mockito
* **Other**
  * **Virtualization**: Docker

## Setup

Assuming that you have Docker Engine release 1.13.0+ installed and the repository cloned, you can execute these commands:

```
$ cd PersonalityTest/
$ docker-compose up
```

Once everything is built and the container is running you can visit the site at: http://localhost:3000/home/

## Sources

* [React](https://reactjs.org/)
* [React Bootstrap](https://react-bootstrap.github.io/)
* [Bootstrap](https://getbootstrap.com/)
* [Axios](https://axios-http.com/)
* [Bootswatch](https://bootswatch.com/)
* [React Charts](https://react-charts.js.org/)
* [Apache Maven](https://maven.apache.org/)
* [Spring Boot](https://spring.io/projects/spring-boot/)
* [Hibernate](https://hibernate.org/)
* [PostgreSQL](https://www.postgresql.org/)
* [Docker](https://www.docker.com//)