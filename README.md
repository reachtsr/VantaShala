[![N|Solid](http://allibilli.com/coloredallibilli.jpg)](http://allibilli.com)
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
````apple js
Below log represents a successful application start:

[restartedMain] 2017-10-21 18:41:54 INFO  c.vs.bootstrap.VSServletInitializer - Configuring Springboot Servlet

              A product of AlliBilli
                               _          __ _           _
             /\   /\__ _ _ __ | |_ __ _  / _\ |__   __ _| | __ _
             \ \ / / _` | '_ \| __/ _` | \ \| '_ \ / _` | |/ _` |
              \ V / (_| | | | | || (_| | _\ \ | | | (_| | | (_| |
               \_/ \__,_|_| |_|\__\__,_| \__/_| |_|\__,_|_|\__,_|

                                           Personal organic chef.
````

### Quick Technical Insights

    - gradle htmlDependencyReport
    - gradle build
    - gradle war
    - gradle projectReport