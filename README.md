# FC Technical Test (Assets Selection)

## Subject

This technical test consists on the creation of a HTTP endpoint which receives a POST request to get a list of assets to activate.
The request needs 2 values to filter and select assets from the database:
- date : date of the activation
- volume : power needed for this activation in kW

The main objective is to return a list of available assets which can be activated to satisfy the power demand.
Each asset has an activation cost. So, one of the objectives is to minimize total activation cost.

In this test, multiple strategies have been implemented to compare results.

## Run

### Prerequisites
- Java 21
- Gradle

### Run application

In a bash terminal, enter
```
./gradlew bootRun
```