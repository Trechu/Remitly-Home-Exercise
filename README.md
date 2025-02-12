# Remitly Home Exercise
This repository contains the code for an exercise given to interns as a part of Remitly recruitment process in February 2025.

<br>

# Technologies used
The project was written in **Java**, using the **Spring** framework. The database used for storing parsed and incoming data is H2.

<br>

# How to run
After cloning the repo you can open the folder `Remitly-Home-Exercise` in an IDE (e.g. IntelliJ IDEA). All dependencies are contained within the included project files so they should get pulled automatically. 

<br>

In order to start the application, navigate to `src/main/java/com/example/remitlyhomeexercise` where you will find the file `RemitlyHomeExerciseApplication.java`, then simply start that file and the app should start working without any problems.

<br>

All endpoints are accessible via `localhost:8080`.

<br>

# Project structure and configuration

The `main` folder contains the source files for the project as well as resources.
***! IMPORTANT !***
The project was made with instructions that were given to us via email in mind, but they didn't specify everything, so there are a couple assumptions that I made:
1. Firstly, I assumed that there is an already existing database of SWIFT codes and countries, as indicated by the spreadsheet provided with the instructions. As a result of that, the SWIFT addition endpoint works ***ONLY*** if you provide an `countryISO2` code that was included in the parsed data because it does not add a new ISO2 code into the database, since the field `TIME ZONE` is missing from the request, thus making the addition of a new ISO code problematic.
2. Some of the tests also use the mock data that was provided with the instructions, so when starting tests ***IT IS HIGHLY ADVISED*** to use the same spreadsheet data. The file `SWIFT_CODES.tsv` should be included in the `resources` folder and is the one that contains all the data that the tests need to function (that being some pre existing SWIFT codes and country ISO2 codes)
3. Lastly, the data parser assumes the data is in the `.tsv` format, so each column is separated with a `\t` sign.

The code is divided into packages as follows:
1. `configuration` - data parser used in database initialization
2. `controller` - endpoint controller used in communicating with the application via external means
3. `dto` - data transfer objects, which are the representation of data structures that are sent between the client and the server
4. `exceptions` - self explanatory
5. `model` - entities stored in the database
6. `repository` - interfaces responsible for database access logic
7. `service` - classes responsible for buisness logic

<br>

The `application.properties` file provides the enviromental variable `file.to.parse` which tells the parser which file to use as the foundation for the database, so change the file name accordingly ***(the file must still be included in the `resources` folder)***. It also allows you to change the database and cors configuration (although the latter should already allow for the requests to be accepted from any source).

<br>

The `tests` folder contains both unit and integration tests.

<br>

# Testing
The tests that exhaust the nature of the application were included with the project in the `tests` folder. If you want to test the application, simply run all the tests in that folder.

<br>

You can also test the application by sending requests to the endpoints directly. All of them are accessible on `localhost:8080` once the application is started. For specific endpoint names, refer to the exercise instruction.
