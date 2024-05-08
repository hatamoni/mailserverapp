# Spring Boot App with MySQL Database Configuration
This is a sample Spring Boot application with MySQL database configuration.

## Technologies Used
- Spring Boot (version 3.2.5)
- MySQL Database (8.0.19)

## Prerequisites
- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://dev.mysql.com/downloads/mysql/)

## Setup
1. Clone the repository
```
git clone https://github.com/hatamoni/mailserverapp
```
2. Create a MySQL database and table using the following SQL script

Create a MySQL database
```
CREATE DATABASE emailserver;
```
Create the table
```
create table if not exists emailserver.email
(
email_id           bigint auto_increment
primary key,
email_body         varchar(255) not null,
email_cc           varchar(255) null,
email_from         varchar(255) not null,
email_to           varchar(255) not null,
state              varchar(255) not null,
created_by         varchar(255) null,
created_date       datetime(6)  null,
last_modified_by   varchar(255) null,
last_modified_date datetime(6)  null
);
```

OR

you can use the prepared statements in file 'CREATE_DB_WITH_TABLES.txt' to create the needed database and the table.

src/main/resources/CREATE_DB_WITH_TABLES.txt


3. Update `application.properties` file with your MySQL database configurations
```
spring.datasource.url=jdbc:mysql://**YOUR_HOST_NAME_OR_LOCAL_IP_ADDRESS**:**YOUR_PORT**/emailserver
spring.datasource.username=**YOUR_MYSQL_USER**
spring.datasource.password=**YOUR_MYSQL_USER_PASSWORD**

# In my case 
spring.datasource.url=jdbc:mysql://localhost:3306/emailserver
spring.datasource.username=msapi
spring.datasource.password=ms#api#123

```
4. Build and run the application
```
mvn clean install
java -jar target/mailserverapp-1.0.0.jar
```

## Usage
- Launch the Spring Boot application
- Access the application at `http://**YOUR_HOST_NAME_OR_LOCAL_IP_ADDRESS**:8484` in my case : http://localhost:8484

## Check the database

You can check the database by running the following SQL query
```
SELECT * FROM emailserver.email;
```

# Check the application health
You can check the health of the application by accessing the following URL
`http://**YOUR_HOST_NAME_OR_LOCAL_IP_ADDRESS**:8484` 

in my case : http://localhost:8484/actuator/health

# Documentation for Email-Server API v1
You can access the documentation for the Email-Server API v1 by accessing the following URL

`http://**YOUR_HOST_NAME_OR_LOCAL_IP_ADDRESS**:8484/swagger-ui.html`

in my case : http://localhost:8484/swagger-ui.html



# Test the application
You can use the swagger-ui (Link mentioned in the Documentation for Email-Server API v1) 

---

PLEASE NOTE:

When testing via swagger-ui you need to remove the values for the fields in the request body when testing the POST request to create emails.

- createdDate
- createdBy
- lastModifiedDate
- lastModifiedBy

---

OR

Use tools like Postman to test the REST endpoints provided by the application.

In case of Postman you can use my collection of requests to test the application found under

src/main/resources/emailerverapp.postman_collection.json




# Additional Notes
Default port for the application is 8484, you can change it in application.properties file.
Make sure MySQL is running and the database mentioned in application.properties exists.
