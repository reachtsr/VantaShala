[![N|Solid](http://allibilli.com/coloredallibilli.jpg)](http://allibilli.com)  

[![Build Status](https://semaphoreci.com/api/v1/haigopi/vantashala/branches/master/shields_badge.svg)](https://semaphoreci.com/haigopi/vantashala)
[![GitHub commits](https://img.shields.io/badge/Author-Gopi%20K%20Kancharla-brightgreen.svg)](http://allibilli.com)
[![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/spring-social/spring-social-google/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/spring-social/spring-social-google.svg)](https://github.com/haigopi/VantaShala/issues)

# VantaShala.com
    A Personal Organic Chef. 

# Goals

  - Helping Society with healthy home cooked food. This is a real problem and we need a real solution.
  - Better than what you cook at home.
  - Healthy food means to be tasty too.
  - Traditional slow cooking process.

# Features
  - Let the Chefs publish what they will be cooking for coming week.
  - Users can then place the orders
  - Orders will get split dynamically to the chefs
  - And Chefs should deliver.
  - Cancellations can't be done once the chef accepted the order.
  - Order visulization should be a priority to the customer. 

### Installation

```sh

Install MongoDB and make it up and running.

Default Spring profile set to: dev
If change needed provide the environment: -Dspring.profiles.active=dev -Djasypt.encryptor.password=XXXXXXX
Configuration available under bootstrap resources.
No explict configuration required to set to run on your local machine
```
##### Below log represents a successful context load:

[![N|Solid](docs/Context.png)](http://allibilli.com)

### Quick Technical Insights

    - gradle htmlDependencyReport
    - gradle build
    - gradle projectReport
    - gradle bootRun - UI: http://localhost:8080/vs/index.html
    
### Swagger(BootRun)
    
    - http://localhost:8080/vs/rest/swagger.json
    - UI:  http://localhost:8080/vs/swagger/index.html
    
### DOCKER BUILD:
    gradle docker -i
    
### CI/CD:
    https://semaphoreci.com/haigopi/vantashala    

### USA ZipCodes
    mongoimport ui\src\main\resources\zips.json --db "XXXXXX" --collection "XXXXXXX"    

##### Test Results:

[![N|Solid](docs/tests.jpg)](http://allibilli.com)        