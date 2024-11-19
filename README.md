
# Vehicle Service Project

In summary, this project is a Spring Boot REST API for managing vehicles, and includes endpoints to create, retrieve, update, and delete vehicles (CRUD operations) using an H2 in-memory database for development purposes. The application also includes a Dockerfile to containerize the application and maintain easy and consistent deployments across systems. 

## Features

- GET /vehicle: Retrieve all vehicles.
- GET /vehicle/{vin}: Retrieve a specific vehicle by its VIN.
- POST /vehicle: Create a new vehicle with a unique identifier (VIN).
- PUT /vehicle/{vin}: Update a specific vehicle by its VIN. 
- DELETE /vehicle/{vin}: Delete a specific vehicle by its VIN. 



## Technologies Used

- Programming Language: Java 21
- Frameworks and Libraries: Spring Boot, Spring Data JPA, Spring MVC, Spring Validation (Jakarta Bean Validation)
- Database: H2 In-Memory Database
- Testing Tools: MockMvc, JUnit 5, Mockito
- Build and Dependency Management: Maven 
- Containerization: Docker 

## Prerequisites
Make sure the following are installed on your system 
- Java 21 
- Maven (latest version recommended)
- Docker (optional, but recommended, for containerized deployment)
## Setup and Running the Application

### Running Locally 
- Clone the repository 
```git clone https://github.com/Aveesh22/vehicle-service.git```

- Go into project directory
```cd vehicle-service```

- Run the application using Spring Boot
```mvn spring-boot:run```

### Running with Docker 
- Clone the repository 
```git clone https://github.com/Aveesh22/vehicle-service.git```

- Go into project directory
```cd vehicle-service```

- Build the project
```mvn clean package```

- Build the Docker image 
```docker build -t vehicle-service .```

- Run the Docker container 
```docker run -p 8080:8080 vehicle-service``` 




## Access the Application

- Base URL: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
- For JDBC URL, Username, and Password please reference application.properties 
## API Documentation

### 1. Get all vehicles 
- Endpoint: ```GET /vehicle```
- Response: Returns all records in the Vehicle table 
- Response Status: ```200 OK```
- Example Response: 
```
[
    {
        "vin": "1HGCM80633A178906",
        "manufacturerName": "Toyota",
        "description": "Mid-Size",
        "horsePower": 150,
        "modelName": "Camry",
        "modelYear": 2020,
        "purchasePrice": 25000.00,
        "fuelType": "Gasoline"
    },
    {
        "vin": "1HGCM80633A178926",
        "manufacturerName": "Toyota",
        "description": "Pickup Truck",
        "horsePower": 120,
        "modelName": "Tacoma",
        "modelYear": 2022,
        "purchasePrice": 30000.00,
        "fuelType": "Gasoline"
    }
]
```

### 2. Get a vehicle by VIN
- Endpoint: ```GET /vehicle/{vin}```
- Response: Returns the vehicle record for the provided VIN
- Response Status: ```200 OK``` (or ```400 Bad Request``` if the VIN is not found) 
- Example Response: 
```
{
        "vin": "1HGCM80633A178906",
        "manufacturerName": "Toyota",
        "description": "Mid-Size",
        "horsePower": 150,
        "modelName": "Camry",
        "modelYear": 2020,
        "purchasePrice": 25000.00,
        "fuelType": "Gasoline"
}
```

### 3. Create a new vehicle
- Endpoint: ```POST /vehicle```
- Response: Returns the created vehicle 
- Response Status: ```201 Created``` (or ```400 Bad Request``` if a duplicate VIN exists) 
- Example Response: 
```
{
        "vin": "1HGCM80633A178906",
        "manufacturerName": "Toyota",
        "description": "Mid-Size",
        "horsePower": 150,
        "modelName": "Camry",
        "modelYear": 2020,
        "purchasePrice": 25000.00,
        "fuelType": "Gasoline"
}
```

### 4. Update a vehicle
- Endpoint: ```PUT /vehicle/{vin}```
- Response: Returns the updated vehicle 
- Response Status: ```200 OK``` (or ```400 Bad Request``` if a duplicate VIN exists) 
- Example Response: 
```
{
        "vin": "1HGCM80633A178906",
        "manufacturerName": "Toyota",
        "description": "Mid-Size",
        "horsePower": 150,
        "modelName": "Camry",
        "modelYear": 2020,
        "purchasePrice": 29000.00,
        "fuelType": "Gasoline"
}
```


### 5. Delete a vehicle
- Endpoint: ```DELETE /vehicle/{vin}```
- Response: No response
- Response Status: ```204 No Content``` (or ```400 Bad Request``` if no VIN was found) 


## Testing

- This project uses MockMvc, JUnit 5, and Mockito for testing. To run all tests using Maven, use the command: 
``` 
mvn test
```
## License

[MIT](https://choosealicense.com/licenses/mit/)

