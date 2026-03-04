# FC Technical Test (Assets Selection)

## Subject

This technical test consists on the creation of an HTTP endpoint which receives a POST request to get a list of assets to activate.
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
- Postman to communicate with the application

### Run application

In a bash terminal, in the root directory of the project, enter
```
./gradlew bootRun
```

The application starts with the following address:
```
http://localhost:8080
```

### Run tests

To run tests, in a bash terminal, in the root directory of the project, enter:
```
./gradlew test
```

## Communication with API

There is only one endpoint to communicate with the application, with a POST request to:
```
/assets/available
```

So, the full address is:
```
http://localhost:8080/assets/available
```

In Postman:
- in Header tab, you have to add 'application/json' value to 'Content-Type' key.
- in Body tab, you have to select "raw" and put JSON data with date, volume, and mode (optional):
```
{
  "date": "2026-03-10",
  "volume": 100,
  "mode": "RATIO" //OPTIONAL
}
```

The mode is optional because "RATIO" mode is set by default, but you can select the following modes:
- "VOLUME": the available assets are sorted by volume increasing
- "COST": the available assets are sorted by cost increasing
- "RATIO": the available assets are sorted by ratio volume/cost descending


