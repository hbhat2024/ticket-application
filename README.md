# Example ticket app

### Building the app
/.mvnw clean package

### Running the app
java -jar .\target\ticketapp-0.0.1-SNAPSHOT.jar

### API endpoints
Following API endpoints are implemented:

* Submit purchase for a ticket: `POST /ticket`
where example body is
`
{
  "sourceLocation": "London",
  "destinationLocation": "Paris",
  "user": {
  "firstName": "John",
  "lastName": "Doe",
  "emailAddress": "jdoe@example.com"
  },
  "price": 20.0
  }
`
* Details of receipt for a user: `GET /ticket?userEmailAddress={userEmailAddress}`
* Seat allocation for given section: `GET /seat-allocation?section={section}`
* Remove a user from train: `DELETE /users?userEmailAddress={userEmailAddress}`
* Modify a user's seat: `PATCH /seat-allocation?userEmailAddress={userEmailEmailAddress}`
where exmple body is 
`
  {
  "section": "B",
  "seatNumber": 10
  }
`