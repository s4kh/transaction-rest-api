# Transaction rest API
A RESTful API (including data model and the backing implementation)
for money transfers between accounts.
 ## Build
 `mvn clean package`
 ## Run
 `java -jar`
 
 ## API
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
