# desafio backend AnotaAi

 A proposta é construir uma API utilizando **MongoDB**, **AWS S3** e **AWS SQS** para gerenciar um catálogo de produtos de um marketplace.

O sistema deve permitir o cadastro de produtos e categorias, cada um vinculado a um proprietário. Os usuários poderão criar, atualizar e excluir esses itens, além de associar produtos a categorias (lembrando que um produto só pode pertencer a uma categoria por vez). Todas as informações devem estar organizadas por proprietário, e a busca pelo catálogo não deve depender diretamente do banco de dados.


#### Install/Build :

```shell
# AWS credencial set Region, AWS_KEY_ID, AWS_SECRET
$ sed -i "s|spring.cloud.aws.region.static=.*|spring.cloud.aws.region.static=us-east-1|" desafio_backend/src/main/resources/application.properties
$ sed -i "s|spring.cloud.aws.credentials.access-key=.*|spring.cloud.aws.credentials.access-key=${AWS_KEY_ID}|" desafio_backend/src/main/resources/application.properties
$ sed -i "s|spring.cloud.aws.credentials.secret-key=.*|spring.cloud.aws.credentials.secret-key=${AWS_SECRET}|" desafio_backend/src/main/resources/application.properties

# Build .jar
$ chmod +x desafio_backend/mvnw clean package
$ cd desafio_backend
$ ./mvnw clean package
$ cd ..

# terraform create infra in aws

$ terraform plan
$ terraform apply

```
#### Use API :

```shell

# Catalog of s3 json's
$ curl -X GET  http://localhost:8080/

# Use /category

$ curl -X GET  http://localhost:8080/category
$ curl -X POST http://localhost:8080/category \
     -H "Content-Type: application/json" \
     -d '{
        "title": "Category Title",
        "description": "Category Description",
        "ownerId": "7464587"
    }'
$ curl -X PUT http://localhost:8080/category/67ea362a2389093dfc9d22bc \
     -H "Content-Type: application/json" \
     -d '{
        "title": "Category Title UpDate",
        "description": "Category Description UpDate",
        "ownerId": "7464587"
    }'
$ curl -X DELETE http://localhost:8080/category/67ea362a2389093dfc9d22bc

# Use /product

$ curl -X GET  http://localhost:8080/product
$ curl -X POST http://localhost:8080/product \
     -H "Content-Type: application/json" \
     -d '{
        "title": "titulo teste",
        "description": "descriptaçaot teste",
        "price": 1,
        "ownerId": "7464587"
    }'
$ curl -X PUT http://localhost:8080/product/67ea344a2389093dfc9d22b1 \
     -H "Content-Type: application/json" \
     -d '{
        "title": "titulo teste",
        "description": "descriptaçaot teste",
        "price": 1,
        "category":{
            "id": "67ea362a2389093dfc9d22bc"
        }
        "ownerId": "7464587"
    }'
$ curl -X DELETE http://localhost:8080/product/67ea344a2389093dfc9d22b1
```

#### Code/Architecture:

+ Controllers:

  - [x] [/](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/CatalogController.java)
    - [x] [GET    /](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/CatalogController.java#L21)

  - [x] [/category](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/CategoryController.java)
    - [x] [GET    /category](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/CategoryController.java#L29)
    - [x] [GET    /category/{id}](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/CategoryController.java#L34)
    - [x] [POST   /category](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/CategoryController.java#L39)
    - [x] [PUT    /category/{id}](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/CategoryController.java#L49)
    - [x] [DELETE /category/{id}](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/CategoryController.java#L58)
  
  - [x] [/product](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/ProductController.java)
    - [x] [GET    /product](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/ProductController.java#L29)
    - [x] [GET    /product/{id}](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/ProductController.java#L34)
    - [x] [POST   /product](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/ProductController.java#L39)
    - [x] [PUT    /product/{id}](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/ProductController.java#L48)
    - [x] [DELETE /product/{id}](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/api/controllers/ProductController.java#L58)

+ Consumer(SQS):
    
    - [x] [CatalogConsumer](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/aws/consumer/CatalogConsumer.java)
    
+ Services:
    - [x] [CategoryService](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/services/CategoryService.java)
    - [x] [ProductService](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/services/ProductService.java)
    - [x] [S3Service](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/aws/services/S3Service.java)
    - [x] [SQSService](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/aws/services/SQSService.java)

+ Repositories(MongoDB):
  - [x] [CategoryRepository](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/repositories/CategoryRepository.java)
  - [x] [ProductRepository](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/repositories/ProductRepository.java)

#### Swagger:


* [Documentacao swagger JSON](./swagger.json) - Arquivo de Documentacao em JSON

![Swagger UI](./swagger_ui.png)


<h1>Backend Analyst Candidate Test</h1>
Dear developer,

Welcome to the Backend Analyst Candidate Test. This test aims to assess your general knowledge and development speed. Below, you will find the details and requirements for this test.


<strong>The Challenge</strong>

Your task is to develop an API using Node.js for a product catalog management system in a marketplace application. You should analyze and convert the following user stories into routes for the application:

<strong>User Stories:</strong>

- As a user, I want to register a product with its owner, so that I can access its data in the future (title, description, price, category, owner ID).
- As a user, I want to register a category with its owner, so that I can access its data in the future (title, description, owner ID).
- As a user, I want to associate a product with a category.
- As a user, I want to update the data of a product or category.
- As a user, I want to delete a product or category from my catalog.
- A product can only be associated with one category at a time.
- Assume that products and categories belong only to one owner.

- Keep in mind that this is an online product catalog, which means there will be multiple requests for editing items/categories per second, as well as accessing the catalog search endpoint.
- Consider the product catalog as a JSON compilation of all available categories and items owned by a user. This way, the catalog search endpoint does not need to fetch information from the database.
- Whenever there is a change in the product catalog, publish this change to the "catalog-emit" topic in the AWS SQS service.
- Implement a consumer that listens to catalog changes for a specific owner.
- When the consumer receives a message, search the database for that owner's catalog, generate the catalog JSON, and publish it to an AWS S3 service bucket.

<strong>You need to develop this test using the following technologies:</strong>

- MongoDB for the database.
- AWS SQS for the catalog change notifications.
- AWS S3 for storing the catalog JSON.
- Node.js for the backend.
- Express.js as the web framework.

<hr>
<strong>Diagram representing the final structure of the project:</strong> <br><br>

<img alt="diagram with struture S3 SQS for catalog" width="680" src="https://github.com/githubanotaai/new-test-backend-nodejs/assets/52219768/504ba448-f128-41db-ae86-18dc19c0dc9d">


<hr>

<strong>Instructions</strong>

<strong>To begin the test, fork this repository, create a branch with your full name, and send us the link to your completed test (link to your repository). If you only clone the repository, you won't be able to push changes, making the pull request more complicated.</strong>
- Use your own AWS account to set up the required services.
- Update the README file with instructions on how to run your application.
- Paste the branch name into the GUPY system and indicate the completion of the test.
- Feel free to provide us with feedback regarding the test.

<strong>Our Evaluation Criteria</strong>
We will assess the following aspects of your solution:

- Knowledge of JavaScript, Node.js, and Express.js.
- Proper structure of the application layers.
- Handling of outgoing calls.
- Effective use of environment variables.
- Implementation of unit tests.
- Logging mechanisms.
- Error handling strategies.
- Documentation quality.
- Code organization, module separation, readability, and comments.
- Commit history.
