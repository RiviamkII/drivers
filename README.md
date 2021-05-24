## Running the application with docker-compose

- Clone the project, ```cd drivers```
- Run ```mvn clean package```
- Run ```docker build -t springboot-drivers .``` 
- Run ```docker-compose up -d``` 
- Open [swagger](http://localhost:9080/swagger-ui/index.html) in broswer

### Brief description
After all containers run, there is not any data for store or driver, so we have to create store and driver first.

endpoint of ```/driver/mysql``` will directly add a new driver to db.

endpoint of ```/driver``` will send a new driver to kafka, then it will be consumed to add to db.
