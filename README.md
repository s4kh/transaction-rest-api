# Transaction rest API
A RESTful API (including data model and the backing implementation)
for money transfers between accounts.
 ## Build
 `mvn clean package`
 ## Run
 `java -jar target/quickstart-0.0.1-SNAPSHOT-jar-with-dependencies.jar`
 
 ## API success responses
  **POST** `/account`
  
  **REQUEST SAMPLE**
  
  ```json
  {
    "amount": 432000.12120,
    "currency": "MNT"
  }
 ```
 
  **RESPONSE SAMPLE**
  
  ```json
  {
    "id": 1,
    "amount": 432000.12120,
    "currency": "MNT"
  }
 ```
 
 **GET** `/account/1`
  
  **RESPONSE SAMPLE**
  
  ```json
  {
    "id": 1,
    "amount": 432000.12120,
    "currency": "MNT"
  }
 ```
 
 **POST** `/transfer`
 
   **REQUEST SAMPLE**
  
  ```json
  {
    "fromAcc": 1,
    "toAcc": 2,
    "amount": 1000,
    "currency": "MNT"
  }
 ```
 Note: Currency can be one of the `fromAcc` or `toAcc`'s currency.
 
  **RESPONSE SAMPLE**
  
  ```json
  {
    "message": "Success"
  }
 ```
 
