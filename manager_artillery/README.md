# Manager Artillery - Minas Tirith: Code test

![MinasTirith](assets/minas_tirith.png)

# Comments about challenge

With shortcomings regarding Hexagonal, DDD, SOLID, Clean Code... this backend platform tries 
to create a service to manage a list of targets to choose the best of them and fire it by a 
group of catapult (another service)

# Steps to test it

Create a docker image
```
docker build -t mmm/mt_manager_artillary --no-cache .
```

(Optional) Stop the catapults whether they are deployed
```
./docker-compose down
```

Clean, pass the tests and built the code
```
./gradlew clean build
```

Deploys the catapults 
```
./docker-compose up -d
```

Deploy the manager artillery platform
```
./gradlew bootRun
```

Execute the test bash file
```
./test.sh
```
or

>[swagger link in the url](http://localhost:3000)

