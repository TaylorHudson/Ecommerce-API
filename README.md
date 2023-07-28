## Prerequisites

Before running the project, make sure you have the following prerequisites installed on your machine:

1. Java Development Kit (JDK) 17 or higher - [Download JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
2. Maven - [Download Maven](https://maven.apache.org/download.cgi)
3. Integrated Development Environment (IDE) - recommend use [IntelliJ IDEA](https://www.jetbrains.com/idea/)

## How to run the project

Follow the steps below to run the project locally:

1. Open your terminal

2. Choose the folder where the project will be saved

3. Clone the repository to your local machine
```
git clone git@github.com:TaylorHudson/CompassUOL-SP-Challenge-03-Squad-8-TaylorHudson.git
```

4. Navigate to the project directory
```
cd CompassUOL-SP-Challenge-03-Squad-8-TaylorHudson
```

5. Compile the Microservices using Maven
``` 
cd eureka-server
mvn clean install

cd ..

cd ms-auth
mvn clean install

cd ..

cd ms-gateway
mvn clean install

cd ..

cd ms-notification
mvn clean install

cd ..

cd ms-products
mvn clean install
```

6. Run the Spring Boot application
```
cd eureka-server
mvn spring-boot:run

cd ms-auth
mvn spring-boot:run

cd ms-gateway
mvn spring-boot:run

cd ms-notification
mvn spring-boot:run

cd ms-products
mvn spring-boot:run
```

### After a successful execution, the application will be available at `http://localhost:8000/`.

# Endpoints
Below are listed the available endpoints in this API:

## User

### Create User
This endpoint allows creating a new resource.

#### Parameters:
- firstName: The first name of the user.
- lastName: The last name of the user.
- email: The email of the user.
- password: The password of the user (The password must contain at least 8 characters).
- roles: Permissions that define which resources the user will be able to access (By default the ROLE of id 1 - ADMIN and id 2 - OPERATOR).

Use the following cURL command to make the call:
```bash
curl --location 'http://localhost:8000/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "JohnDoe@gmail.com",
    "password": "john.doe123",
    "roles": [2]
}'
```
Adjust the request body as needed to provide the updated user information.


### Update User
This endpoint allows updating a resource. 

#### Request Headers
- Authorization: The access token provided in the request header. Replace the token in the example with a valid access token.

#### Parameters:
- firstName: The first name of the user.
- lastName: The last name of the user.
- email: The email of the user.
- password: The password of the user (The password must contain at least 8 characters).
- roles: Permissions that define which resources the user will be able to access (By default the ROLE of id 1 - ADMIN and id 2 - OPERATOR).

Use the following cURL command to make the call:
```bash
curl --location --request PUT 'http://localhost:8000/users/ID' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer TOKEN' \
--data-raw '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "JohnDoeUpdated@gmail.com",
    "password": "john.doe321",
    "roles": [1, 2]
}'
```

Replace the token in the Authorization header and the example ID in the endpoint URL with the actual token and
user ID you want to update. Adjust the request body as needed to provide the updated user information.


### Find User By ID
This endpoint allows you to find a resource.

#### Request Headers
- Authorization: The access token provided in the request header. Replace the token in the example with a valid access token.

Use the following cURL command to make the call:
```bash
curl --location 'http://localhost:8000/users/ID' \
--header 'Authorization: Bearer TOKEN' \
```

Replace the token in the Authorization header and the example ID in the endpoint URL with the actual token and
user ID you want to find.


## Auth

### Login
This endpoint allows you to login.

Use the following cURL command to make the call:
```bash
curl --location 'http://localhost:8000/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "johnDoe@gmail.com",
    "password": "john.doe123"
}'
```


## Product

### Create Product
This endpoint allows creating a new resource. 

#### Request Headers
- Authorization: The access token provided in the request header. Replace the token in the example with a valid access token.

#### Parameters:
- name: The name of the product (The product name must be at least 2 characters long).
- description: The last name of the user (The product description must be at least 5 characters long).
- price: The price of the user (The product price must be greater than 0).
- imgUrl: URL that leads to a product image.
- categories: Categories of the product (By default the CATEGORY of id 1 - Electronic and id 2 - Apparel).

Use the following cURL command to make the call:
```bash
curl --location 'http://localhost:8000/products' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer TOKEN' \
--data '{
    "name": "Product 1",
    "description": "The new generation product 1",
    "price": 1000.00,
    "imgUrl": "",
    "categories": [1]
}'
```

Replace the token in the Authorization header with the actual token. Adjust the request body as needed to provide the
product information.


### Find Product By ID
This endpoint allows you to find a resource.

#### Request Headers
- Authorization: The access token provided in the request header. Replace the token in the example with a valid access token.

#### Parameters:
- name: The name of the product (The product name must be at least 2 characters long).
- description: The last name of the user (The product description must be at least 5 characters long).
- price: The price of the user (The product price must be greater than 0).
- imgUrl: URL that leads to a product image.
- categories: Categories of the product (By default the CATEGORY of id 1 - Electronic and id 2 - Apparel).

Use the following cURL command to make the call:
```bash
curl --location 'http://localhost:8000/products/ID' \
--header 'Authorization: Bearer TOKEN' \
```

Replace the token in the Authorization header and the example ID in the endpoint URL with the actual token and
product ID you want to find.


### Find All Product
This endpoint allows you to find all resources.

#### Request Headers
- Authorization: The access token provided in the request header. Replace the token in the example with a valid access token.

#### Request Params
- page (default: 0): Specifies the page number for pagination.
- direction (default: asc): Specifies the sorting direction (asc or desc).
- orderBy (default: price): Specifies the field by which the results should be sorted.
- linesPerPage (default: 10): Specifies the number of product items to be displayed per page.

Use the following cURL command to make the call:
```bash
curl --location 'http://localhost:8000/products?page=0&direction=desc&orderBy=name&linesPerPage=5' \
--header 'Authorization: Bearer TOKEN' \
```

Replace the token in the Authorization header with the actual token.


### Update Product
This endpoint allows updating a resource.

#### Request Headers
- Authorization: The access token provided in the request header. Replace the token in the example with a valid access token.

#### Parameters:
- name: The name of the product (The product name must be at least 2 characters long).
- description: The last name of the user (The product description must be at least 5 characters long).
- price: The price of the user (The product price must be greater than 0).
- imgUrl: URL that leads to a product image.
- categories: Categories of the product (By default the CATEGORY of id 1 - Electronic and id 2 - Apparel).

Use the following cURL command to make the call:
```bash
curl --location --request PUT 'http://localhost:8000/products/ID' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer TOKEN' \
--data '{
    "name": "Product 3",
    "description": "The new generation product 3",
    "imgUrl": "",
    "price": 3000.0,
    "categories": [1]
}'
```

Replace the token in the Authorization header and the example ID in the endpoint URL with the actual token and
product ID you want to update. Adjust the request body as needed to provide the updated product information.


### Delete Product
This endpoint allows deleting a resource.

#### Request Headers
- Authorization: The access token provided in the request header. Replace the token in the example with a valid access token.

#### Parameters:
- name: The name of the product (The product name must be at least 2 characters long).
- description: The last name of the user (The product description must be at least 5 characters long).
- price: The price of the user (The product price must be greater than 0).
- imgUrl: URL that leads to a product image.
- categories: Categories of the product (By default the CATEGORY of id 1 - Electronic and id 2 - Apparel).

Use the following cURL command to make the call:
```bash
curl --location --request DELETE 'http://localhost:8000/products/ID' 
```

Replace the token in the Authorization header and the example ID in the endpoint URL with the actual token and
product ID you want to update. Adjust the request body as needed to provide the updated product information.


## Roles

### Create Role
This endpoint allows creating a new resource.

#### Request Headers
- Authorization: The access token provided in the request header. Replace the token in the example with a valid access token.

#### Parameters:
- name: The name of the role.

Use the following cURL command to make the call:
```bash
curl --location 'http://localhost:8000/roles' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer TOKEN' \
--data '{
    "name": "ROLE_..."
}'
```

Replace the token in the Authorization header with the actual token. Adjust the request body as needed to provide the created role information.


### Find All Role
This endpoint allows you to find all resources.

#### Request Headers
- Authorization: The access token provided in the request header. Replace the token in the example with a valid access token.

Use the following cURL command to make the call:
```bash
curl --location 'http://localhost:8000/roles' \
--header 'Authorization: Bearer accessToken' \
```

Replace the token in the Authorization header with the actual token. 


### Update Role
This endpoint allows updating a resource.

#### Request Headers
- Authorization: The access token provided in the request header. Replace the token in the example with a valid access token.

Use the following cURL command to make the call:
```bash
curl --location --request PUT 'http://localhost:8000/roles/ID' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer TOKEN' \
--data '{
    "name": "ROLE_..."
}'
```

Replace the token in the Authorization header with the actual token. 
