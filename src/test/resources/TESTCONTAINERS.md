# Testcontainers (MySQL) for persistence tests

This project includes a JPA persistence test (`AccountPersistenceAdapterTest`) that can run against an isolated MySQL database using Testcontainers.

## Prerequisites
- Docker Desktop (or compatible Docker daemon) running.

## Maven dependencies
Add these test dependencies to `pom.xml`:

- `org.testcontainers:junit-jupiter`
- `org.testcontainers:mysql`

And add the MySQL JDBC driver (runtime/test):

- `com.mysql:mysql-connector-j`

Typical Maven snippet:

```xml
<dependency>
  <groupId>org.testcontainers</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>1.19.8</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>org.testcontainers</groupId>
  <artifactId>mysql</artifactId>
  <version>1.19.8</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>com.mysql</groupId>
  <artifactId>mysql-connector-j</artifactId>
  <version>8.3.0</version>
</dependency>
```

## Running the test
Run the single test class:

```bash
mvn -Dtest=AccountPersistenceAdapterTest test
```

If Docker is not available, the test will fail to start the MySQL container.

