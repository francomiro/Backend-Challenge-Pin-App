# PinApp Backend - Challenge

## Description

This is an API created for the PinApp challenge, developed in Java with Spring Boot and deployed on an AWS EC2 instance with an Amazon RDS (MySQL) database.

## Tecnologies
- **Java 17**
- **Spring Boot**
- **MySQL**
- **Docker y Docker Compose**
- **AWS (EC2, RDS, ECR)**
- **Maven**
- **Hibernate**
- **Swagger**

## Configuration and Usage

### Option 1 - Run API with Docker in your computer

#### Required Software

- **Docker**: To create and run the necessary containers.

#### Deployment Instructions with Docker

#### 1. Clone the Repository

#### 2. Edit Configuration
Before starting the containers, configure the **Dockerfile** with this Database URL:

    ENV SPRING_DATASOURCE_URL jdbc:mysql://mysql:3306/

#### 3. Start the Containers with Docker Compose

Start the containers (both the database and the API) by running the following command:

    docker-compose up --build

The API will be accessible at the following URL:

    http://localhost:8080/swagger-ui/index.html


### Opción 2: Consume the API from the AWS Server

If you prefer to consume the API deployed on the AWS server, use one of the following URLs:

#### API URL

    http://3.145.110.34:8080/swagger-ui/index.html

    http://ec2-3-145-110-34.us-east-2.compute.amazonaws.com:8080/swagger-ui/index.html

This URL allows you to access the API documentation generated with Swagger and test the different available endpoints.

### 4. Test the API
**Create a new client**

- **URL**: /clientes/creacliente
- **Method**: POST
- **Description**: Create a new client.
- **Body (JSON)**

#### Request (example)##

    {
      "firstName": "Franco",
      "lastName": "Miro",
      "age": 25,
      "birthDay": "1999-03-02"
    }
Note: The age must match the birth date.

**RESPONSE**
- **201 CREATED**: If the client was registered successfully.
- **400 Bad Request**: If any required field is missing or does not meet the required format.


**Get client KPIs**

- **URL**: /clientes/kpideclientes
- **Method**: GET
- **Description**: Get clients KPIs (average age and standard deviation).
- **Body (JSON)**


**RESPONSE**
- **200 OK**: If the API can calculate the KPI
   Response (example)
  
      {
        "averageAge": 25,
        "standardDeviation": 2
      }
  
- **404 NOT FOUND**: If there isn't any client in database to calculate KPI
   Response (example)
  
      {
        "error": "No clients found"
      }


  **Get Clients List with Estimated Death Date**

- **URL**: /clientes/listclientes
- **Method**: GET
- **Description**: Get clients list with estimated death date.
- **Body (JSON)**
- **Note:** The life expectancy used for the estimated death date is 75 years.


**RESPONSE**
- **200 OK**: If the clients list was retrieved successfully.
   Response (example)
  
      [
        {
          "id": 1,
          "firstName": "Franco",
          "lastName": "Miro",
          "age": 25,
          "birthDate": "1999-03-02",
          "estimatedDeathDate": "2074-03-02"
        },
        {
          "id": 2,
          "firstName": "Juan",
          "lastName": "Miro",
          "age": 25,
          "birthDate": "1999-03-02",
          "estimatedDeathDate": "2074-03-02"
        }
      ]
  
- **404 NOT FOUND**: If there isn't any client in database to list
   Response (example)

      {
        "error": "No clients found"
      }


### 5. TESTS
The project includes unit and integration tests to ensure the correct functioning of the system features.
To run the application's tests, you need to have the local environment configured. In the application.properties, you should enter your local database details.

