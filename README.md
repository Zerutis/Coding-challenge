# Coding-challenge

There is no validation, exceptions class.

Couldn't figure out why can't connect application with mysql in docker container.

My steps (there been various combinations - latest is showed):
* `docker run -p 4406:3306 --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=coding_challenge -d mysql` 
* `maven install`
* `docker build -t coding-challenge-webapp .` from project root
* `docker run -p 9090:8080 --name coding-challenge-webapp --link mysql-standalone:mysql -d coding-challenge-webapp`
