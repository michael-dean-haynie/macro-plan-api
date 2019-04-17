
# Macro Plan API

## Running Unit Tests
Currently no great solution for running unit tests (if there were any)

## Running Karate Tests


## Extra Notes
* For now running the application throws a postgres hibernate error that looks like this:
    * `Caused by: java.sql.SQLFeatureNotSupportedException: Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.`
    * Probable cause: [https://vkuzel.com/spring-boot-jpa-hibernate-atomikos-postgresql-exception](https://vkuzel.com/spring-boot-jpa-hibernate-atomikos-postgresql-exception)

* This project uses lombok so if you want to build it in your ide you will need the plugin (;