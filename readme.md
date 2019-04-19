
# Macro Plan API
## Build
mvn clean install

## Run
mvn spring-boot:run

## Running Unit Tests
Currently no great solution for running unit tests (if there were any)

## Running Karate Tests
* Start api endpoints
* Run karate tests in eclipse (mvn test does not work for now: [related issue on stack-overflow](https://stackoverflow.com/questions/53010200/maven-surefire-could-not-find-forkedbooter-class))
    * Right click /src/test/java/endpoints/TestParallel.java and run as JUnit test
    * OR use a run maven run configuration like `test -Dtest=TestParallel`


## Extra Notes
* For now running the application throws a postgres hibernate error that looks like this:
    * `Caused by: java.sql.SQLFeatureNotSupportedException: Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.`
    * Probable cause: [https://vkuzel.com/spring-boot-jpa-hibernate-atomikos-postgresql-exception](https://vkuzel.com/spring-boot-jpa-hibernate-atomikos-postgresql-exception)

* This project uses lombok so if you want to build it in your ide you will need the plugin (;