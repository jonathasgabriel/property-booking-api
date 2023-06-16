# Technologies

- Java 17
- Spring Framework

# Running the API

## Requirements

- Java 17

## Steps

1. Terminal into the project's folder.
2. Run `./mvnw clean install -DskipTests`.
3. Run `java -jar target/booking-0.0.1-SNAPSHOT.jar`. The API will be running on port 8080.
4. Alternatively, use any IDE of your preference: import the maven project, build it and run it.
5. The H2 in-memory database can be accessed on `localhost:8080/h2-console`. Data is lost once the API is terminated.
    - The username is `sa` (by default) and there is no password set (empty by default). These settings should be
      already
      suggested when the console is accessed.
    - Make sure the `JDBC URL` field is `jdbc:h2:mem:testdb`

# Testing the API

## Restrictions

_Bookings_ and _Blocks_ can only be created if they belong to a _Property_. This means that Properties must be created
first.

## Endpoints

You can use any client to perform HTTP requests against the API. The following endpoints are available:

- POST http://localhost:8080/api/properties
    - Body example `{
      "name": "property-name"
      }`
- GET  http://localhost:8080/api/properties
- POST http://localhost:8080/api/bookings
    - Body example `{
      "propertyId": 1,
      "start": "2023-05-04",
      "end": "2023-05-04"
      }`
- GET  http://localhost:8080/api/bookings
- GET  http://localhost:8080/api/bookings/1
- PUT  http://localhost:8080/api/bookings/1
    - Body example `{
      "start": "2023-05-08",
      "end": "2023-05-09"
      }`
- DELETE http://localhost:8080/api/bookings/1
- POST http://localhost:8080/api/blocks
    - Body example `{
      "propertyId": 1,
      "start": "2023-05-04",
      "end": "2023-05-04"
      }`
- GET  http://localhost:8080/api/blocks
- GET  http://localhost:8080/api/blocks/1
- PUT  http://localhost:8080/api/blocks/1
    - Body example `{
      "propertyId": 1,
      "start": "2023-05-08",
      "end": "2023-05-09"
      }`
- DELETE http://localhost:8080/api/blocks/1

# Next Steps

There are steps that can be taken in order to improve the existing API. Most of these steps were skipped
because we were conscious about the time spent to the project.

1. Add more tests.
2. Add validation for _blocks_. What happens if owners try to create _blocks_ when there are _bookings_ in place?
3. Work with times and timezones instead of just dates.
4. Allow to cancel _bookings_ without deleting them, by relying on a solution like status change.