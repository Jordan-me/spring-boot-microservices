# Getting Started

### How do I get set up?
For client Rsocket command line install [RSocket client](https://github.com/making/rsc/releases)
clone this repository
for ease 
Note that this is subproject so make sure to do build for the project and then for the service you interest for (Ticker service)

Before you running the service it is important that you will upload mongo image from docker, this subproject will create tickersdb as database and TICKERS collection inside it.

For convenience, we created a consumer for the above service - to run it, go to RSocketTickerManagementServiceConsumer and clone it, then run the services at the same time -
Important note: the consumer needs the producer to run in the background because it connects to it.
