# Catapult - Minas Tirith: Code test

![MinasTirith](assets/catapult.png)

# Steps to test it

Create a docker image
```
docker build -t mmm/catapult --no-cache .
```

Run the docker image
```
docker run -p 3003:3000 -e GENERATION=2 -ti mmm/catapult:latest
```

Call the service
```
curl -d '{"target": { "x": 0,"y": 40 },"enemies":12}' -H "Content-Type: application/json" -X POST http://localhost:3003/fire
```