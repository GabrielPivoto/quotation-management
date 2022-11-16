# Quotation Management

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)

---

### First IDP project

REST application whose purpose is to store quotes from stock market.

<h4 align="left"> 
	Author ✏️: <a href="https://github.com/GabrielPivoto">Gabriel Pivoto</a>
</h4>

#### If you want, you can download the presentation:

[![Dowload](https://custom-icon-badges.demolab.com/badge/-Presentation-F25237?style=for-the-badge&logo=download&logoColor=white)](https://github.com/GabrielPivoto/quotation-management/raw/master/presentation/ApresentacaoProjeto1.pdf)

### What is needed 🧾
- [x] [JDK](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- [x] [Maven](https://maven.apache.org/download.cgi)
- [x] [Docker](https://www.docker.com/)
- [x] [Postman](https://www.postman.com/)

---

### Starting ▶️

Clone this repository:

```
git clone https://github.com/GabrielPivoto/quotation-management.git
```

Run the following commands on Docker:

- Create the network:

```
docker network create inatel
```

- Start the MySql database:

```
docker container run --name mysql --network=inatel -e MYSQL_ROOT_PASSWORD=root -e
MYSQL_DATABASE=bootdb -p 3306:3306 -p 33060:33060 -d mysql
```

- Start the Stock Manager application:

```
docker container run –-name stockmanager --network=inatel -p 8080:8080 -d adautomendes/stock-manager
```

- Build the Quotation Management image:

```
docker build -t quotation management .
```

- Start the Quotation management application:

```
docker container run --name quotation-management --network=inatel -p 8081:8081 --env SPRING_PROFILES_ACTIVE=prod --env MYSQL_CONTAINER=mysql --env STOCK_MANAGER_CONTAINER=stockmanager --env QUOTATION_MANAGER_CONTAINER=quotation-management quotation-management
```

---

### How to use ⚙️

A user can register as many quotes as he wants for a stock. It is also possible to register quotes from different stocks.
However, a stock can only be registered in Quotation Management if it is already registered in Stock Manager.

It is possible to register a new stock in Stock Manager as it follows:

- POST ``` http://localhost:8080/stock```

```
{
    "id": "petr7", 
    "description": "test petr" 
}
```

To read all stocks from Stock Manager:

- GET ``` http://localhost:8080/stock```

To register a new stock in Quotation Management:

- POST ``` http://localhost:8081/stock```

```
{
   "stockId": "teste1",
    "quotesMap": {
        "2022-06-07": 23.0
    }
}
```
To read all stocks from Quotation Management:

- GET ``` http://localhost:8081/stock```

To read a stock by stockId from Quotation Management:

- GET ``` http://localhost:8081/stock/stockId```

To deleye a stock by stockId from Quotation Management:

- DELETE ``` http://localhost:8081/stock/stockId```

---

### Docker-Compose 🐋

First of all, it is necessary to create a Quotation Management container.
Before building the image, run the following command in the root directory 
of the project:

```
mvn clean install -DskipTests
```

Also in the root directory, run the following command to
build the image:

```
docker build -t quotation-management .
```

Finally:

```
docker compose up
```