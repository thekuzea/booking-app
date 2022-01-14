### How to...
#### Boot up MongoDB database server within Docker
`docker run --name mongodb -p 27017:27017 -d mongo:latest`

#### Start an existing MongoDB container
`docker start mongodb`

###
#### Build all modules
`.\gradlew clean build`

###
#### Run backend server
`.\gradlew booking-app-server:bootRun`
