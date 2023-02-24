# Getting Started

### How do I get set up?
[Our OpenAPI](http://localhost:8083/webjars/swagger-ui/index.html#/)  
clone this repository  
Note that this is subproject so make sure to do build for the project and then for the service you interest for (Ticker Management service consumer)

* Before you running this service you need to run the [Producer RSocket Service](../RSocketTickerManagementService/README.md)  


### General details:
Service's name: RSocketTickersManagementService
Service's consumer name: RSocketTickersManagementServiceConsumer
#### URLS
* swagger-ui: http://localhost:8083/webjars/swagger-ui/index.html#/
1. `POST/ticker`  
   accept new ticker and return the ticker saved on TICKERS collection reactively.
    * [Ticker Structure](../RSocketTickerManagementService/README.md)

2. `GET/tickers?page={page}&size={size}`  
   returns all the saved tickers on TICKERS collection reactively. If there are no tickers empty array will return.

3. `PUT/ticker/bind`  
   adds relationships between a Ticker whose ID is defined in the input as tickerId and one or more other Tickers, whose IDs are defined in the array sent in the input as relatedTickerIds.
   * If one of the identifiers sent to the service does not exist, the service will not do anything.

4. `GET/tickers/byIds/{ids}`
   An operation that is activated using a channel that receives many JSON files that include Ticker identifiers, and returns all requested Tickers.
   * If one of the identifiers requested in the input does not exist, the operation will ignore it.
   
5. `GET/ticker/relatedTickers/{ids}`
   An operation that is activated through a channel that receives many Ticker IDs and returns all the Tickers that are related to them.
   * Note that connections between Tickers are bidirectional in nature. That is, if "ticker1" is returned from this operation for "ticker2", then "ticker2" will be returned when this operation receives as input the identifier of "ticker1".

6. `GET/ticker/byExternalReferences/{systems}/{externalSystemIds}`
   An operation that is activated through a channel that receives many JSON files that each include an ID of an item in an external system (ExternalReferenceBoundary), and returns all the Tickers for which it appears in the array defined for them in the externalReferences field.
   * If one of the identifiers of an item in an external system requested in the input does not exist in any Ticker, the operation will ignore it.
   * [ExternalReference Structure](../RSocketTickerManagementService/README.md)
   
7. `GET/ticker/byCompany/{company}?page={page}&size={size}`  
   An operation that is activated using a stream and returns all the Tickers, created by users from a certain company.