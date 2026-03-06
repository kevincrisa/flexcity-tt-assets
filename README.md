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


## Design decisions

### Strategy pattern

To compare results from different calculation modes, all modes are implemented using Strategy pattern:
- AssetsSearchByIncreasingVolume
- AssetsSearchByIncreasingCost
- AssetsSearchByRatioVolumeCost

![Strategy diagram](resources/img/img.png)

This pattern allows to add another search algorithms without modifying others and each strategy can be tested separately.
We can select one of the strategies in the POST request.


### Domain-driven architecture

Structure of the project:
- controller : AssetController → receive the POST request, call the application (AssetService) and returns the response
- application : AssetService → verify if a strategy exists based on mode sent by POST request and call the domain class to perform calculation mode
- domain : Classes implements AssetRequestStrategy → filter the assets to found which are available at date in the request and perform the calculation to return list of available assets

There are 2 data class in the domain:
- Asset : describes an asset (code, name, activationCost, availability and volume)
- AssetRequest: describes an asset request (representation of the body sent by POST request) (date, volume, mode)


### Strategies

Different strategies have been implemented to compare them and found the best approach to select the best combination of assets with minimizing total activation cost

#### Search by increasing volume

This approach consists to sort available assets by increasing volume and add each asset until the requested volume is reached.
```
val availableAssetsSortByIncreasingVolume = availableAssetsAtDate.sortedBy { it.volume }

val assetsSelected = mutableListOf<AvailableAsset>()
var requestedVolume = request.volume

for (asset in availableAssetsSortByIncreasingVolume){
    if(requestedVolume <= 0) break
    val availableVolume = minOf(asset.volume, requestedVolume)
    assetsSelected.add(AvailableAsset(asset.code, availableVolume, asset.activationCost))
    requestedVolume -= availableVolume
}
```

The advantage of this strategy is its complexity O(n log n) (can be optimized) so the execution time will be short even with a large set of assets.

But, if the volume requested is big and the number of assets is big too, this strategy can return a large list of available assets for a cost which can be large.

Example:
| Asset  | Volume  | Activation cost  |
|---|---|---|
| Asset 1  | 50  | 40  |
| Asset 2  | 60  | 50  |
| Asset 3  | 100  | 80  |





