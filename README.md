# Qanterium

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

### Backend part of the financial management application.

Technological stack used: **java 17, spring-boot 3, open-api 3(ex. swagger), maven, liquibase, postgresql, docker-compose**


## Structure:
 - **input** (Controllers folder)
   - **transaction** (Contains a CRUD controller for transactions elements)
   - **expensecard** (Contains a CRUD controller for expense cards)
   - **member** (Contains a CRUD controller for members)
   - **counter** (Contains a CRUD controller for counting member transactions)
 - **exchange** (Contains a exchange facade for exchange pair and amount conversion operations)
   - **convert** (Contains a service for converting the amount into another currency)
   - **rate** (Contains a service for obtaining exchange pair)
 - **security** (Contains a controller for user registration and authentication)
 - **common** (Contains common elements for the project)
