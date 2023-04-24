# Spring Anti-Fraud-System

This project demonstrates (in a simplified form) the principles of anti-fraud systems in the financial sector. For this
project, we will work on a system with an expanded role model, a set of REST endpoints responsible for interacting with
users, and an internal transaction validation logic based on a set of heuristic rules.

## Requirements

To build and run this project you need:

- [JDK 17](https://www.openjdk.java.net/projects/jdk/17/)
- [Gradle 7.2](https://gradle.org/install/)

## Get started

1. Clone this repository

```shell
git clone https://github.com/SEM24/Anti-Fraud-System.git
```

2. Build and run the project

```shell
./gradlew build
./gradlew bootRun
```

The endpoints can be accessed using a browser or a tool that allows you to send HTTP requests
like [Postman](https://www.getpostman.com/). There are several endpoints that you can use to interact with the system.
Request the according endpoint in a format shown in the examples below.

**Please note, that the first user you create will be an admin. This can only be changed in the database.**

## Endpoints

|                                               | Anonymous | MERCHANT | ADMINISTRATOR | SUPPORT |
|-----------------------------------------------|-----------|----------|---------------|---------|
| POST /api/auth/user                           | +         | +        | +             | +       |
| DELETE /api/auth/user/{username}              | -         | -        | +             | -       |
| GET /api/auth/list                            | -         | -        | +             | +       |
| PUT /api/auth/role                            | -         | -        | +             | -       |
| PUT /api/auth/access                          | -         | -        | +             | -       |
| POST /api/antifraud/transaction               | -         | +        | -             | -       |
| POST, DELETE, GET api/antifraud/suspicious-ip | -         | -        | -             | +       |
| POST, DELETE, GET api/antifraud/stolencard    | -         | -        | -             | +       |
| GET /api/antifraud/history                    | -         | -        | -             | +       |
| PUT /api/antifraud/transaction                | -         | -        | -             | +       |

_'+' means the user with the role above can access that endpoint. '-' means the user with the role above does not have
access to that endpoint._