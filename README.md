## reactive-app-demo

### Assumptions
1. There are two services involved. 
2. One for receiving the booking requests, which I call the `booking-service` app and,
3. One for processing the payment for a booking request, which I call the `payment-service` app.
4. If the user has enough money in his/her account, the `payment-service` will provide an approved or rejected status
   in the response. This response is sent back to the `booking-service` for approving or rejecting booking reservations.

### Build and Test
```bash
./00-build
```
This script will run the unit tests and build the Docker images if the tests pass.

### Running the Services
```bash
docker-compose up
```
This will run the Docker containers: `mongodb`, `payment-service`, `booking-service`

### The payment-service app
The `payment-service` stores information regarding the user's account balance. This service is initialized with users
for testing which can be viewed by a PostMan or curl.
##### GET: `http://localhost:8081/users`
##### Response:
```json
[
    {
        "id": "a675a759-54dd-44d1-9fd1-76bd916a4239",
        "email": "jolly.jae@gmail.com",
        "firstName": "Jolly",
        "lastName": "Jae",
        "balance": 7000.0
    },
    {
        "id": "ba1923aa-2beb-4e2d-9b88-2700d3b0ddd2",
        "email": "keerthi.lee@gmail.com",
        "firstName": "Keerthi",
        "lastName": "Lee",
        "balance": 55.0
    },
    {
        "id": "f5a66098-388a-4177-9670-6f4597e00388",
        "email": "wella.sky@gmail.com",
        "firstName": "Wella",
        "lastName": "Sky",
        "balance": 1200.0
    },
    {
        "id": "5b5c416a-6369-41d5-afb0-81dc8a45f4df",
        "email": "hinata.shoyo@gmail.com",
        "firstName": "Hinata",
        "lastName": "Soyo",
        "balance": 500.0
    }
]
```
In addition, an `/authorize_transaction` API for processing payment is also exposed.
##### POST: `http://localhost:8081/authorize_transaction`
##### Content-Type: `application/json`

Body:
```json
{
    "Email": "keerthi.lee@gmail.com",
    "Transaction Number": "TR001",
    "Amount": 45.0,
    "First Name": "Keerthi",
    "Last Name": "Lee"
}
```
Response:
```json
{
   "Transaction Number": "TR001",
   "Email": "keerthi.lee@gmail.com",
   "First Name": "Keerthi",
   "Last Name": "Lee",
   "Status": "APPROVED"
}
```
This response is sent back to the `booking-service` for approving or rejecting booking reservations


### The booking-service app
TODO - to be implemented

