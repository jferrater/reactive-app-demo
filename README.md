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
```bash
curl http://localhost:8081/users
```
In addition, an `/authorize_transaction` API for processing payment is also exposed. Sending payment request using curl:
```bash
curl -H "Content-type: application/json" \ 
      -X POST \
      -d '{"email": "keerthi.lee@gmail.com","request-id": "TR001", "amount": 45.0}' \
      http://localhost:8081/authorize_transaction
```
A sample response:
```bash
{
    "requestId": "TR001",
    "email": "keerthi.lee@gmail.com",
    "status": "APPROVED"
}
```
This response is sent back to the `booking-service` for approving or rejecting booking reservations


### The booking-service app
TODO - to be implemented

