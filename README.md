## Board Game Café Management System

An event reservation system for board game cafés to manage table bookings, game library inventories, and special game
night
events.

#### The project uses the following tech stack:

> * Spring Boot
> * Java 17
> * Maven
> * PostgreSQL
> * H2 in-memory database
> * MockMVC
> * JUnit
> * Mockito
> * Hibernate

#### PostgreSQL Database Configuration:

> Add the following configuration in your _application.properties_ file (_location_:
> src/main/resources/application.properties):

> * spring.datasource.url=jdbc:postgresql://localhost:5432/DATABASE_NAME
> * spring.datasource.username=USERNAME
> * spring.datasource.password=PASSWORD
> * spring.datasource.driver-class-name=org.postgresql.Driver
> * spring.jpa.hibernate.ddl-auto=update
> * spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
> * logging.level.org.hibernate.SQL=DEBUG
> * logging.level.org.hibernate.type.descriptor.sql=TRACE

#### Swagger UI

> Link: http://localhost:8080/swagger-ui/index.html#/