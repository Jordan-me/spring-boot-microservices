# Getting Started

### General details:
Service's name: RSocketTickersManagementService
Service's consumer name: RSocketTickersManagementServiceConsumer
#### Data Structure
* TickerBindBoundary:
```
        TickerBindBoundary{
            "tickerId":"1a2bc3d4e5f6",
            "relatedTickerIds":["1a2bc3d4e592", "1a2bc3d42aab"]
        }

```

* ExternalReferenceBoundary:
```
        ExternalReferenceBoundary{
            "system":"SFONotifications",
            "extrenalSystemId":"2022-DEC-22-MSG-5396"
        }


```

* Publisher:
```
        Publisher{
            "company":"SFO Port Authority",
            "email":"jane@sfport.com"
        }

```

* TickerBoundary:
```
        TickerBoundary{
            "tickerId":"1a2bc3d4e5f6",
            "publisher":{
                "company":"SFO Port Authority",
                "email":"jane@sfport.com"
            },
            "publishedTimestamp":"2023-03-22T16:13:57.190+0000",
            "tickerType":"generalNotification",
            "summary":"Port services function as usual through the holidays season",
            "extrenalReferences":[
                {
                    "system":"SFONotifications",
                    "extrenalSystemId":"2023-DEC-22-MSG-5396"
                }
            ],
            "relatedTickerIds":["1a2bc3d4e592", "1a2bc3d42aab"],
            "tickerDetails":{
                "category":"General Port Notifications",
                "status":"NOTIFIED",
                "replyOptions":{
                    "canBeReplied":false,
                    "source":"automated",
                    "protocol":"RSocket",
                    "method":"FNF"
                }
            }
        }

```
### How do I get set up?

For client Rsocket command line install [RSocket client](https://github.com/making/rsc/releases)    
[Consumer Reactive Service](../RSocketTickerManagementServiceConsumer/README.md)  
clone this repository

* Note that this is subproject so make sure to do build for the project and then for the service you interest for (Ticker service)

* Before you running the service it is important that you will upload mongo image from docker, this subproject will create tickersdb as database and TICKERS collection inside it.

* For convenience, we created a consumer for the above service - to run it, go to RSocketTickerManagementServiceConsumer and clone it, then run the services.

* Important note: the consumer needs the producer to run in the background because it connects to it.
