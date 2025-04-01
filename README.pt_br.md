
### Install/Build :

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

+ Services:
    - [x] [CategoryService](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/services/CategoryService.java)
    - [x] [ProductService](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/services/ProductService.java)

+ Repositories(MongoDB):
  - [x] [CategoryRepository](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/repositories/CategoryRepository.java)
  - [x] [ProductRepository](./desafio_backend/src/main/java/com/gilberto009199/anotai/desafio_backend/repositories/ProductRepository.java)



# Teste para Candidato a Analista Backend

Caro desenvolvedor,

Bem-vindo ao Teste para Candidato a Analista Backend. Este teste tem como objetivo avaliar seu conhecimento geral e velocidade de desenvolvimento. Abaixo, você encontrará os detalhes e requisitos do teste.

## O Desafio

Sua tarefa é desenvolver uma API usando Node.js para um sistema de gerenciamento de catálogo de produtos em um aplicativo de marketplace. Você deve analisar e converter as seguintes histórias de usuário em rotas para a aplicação:

### Histórias de Usuário:

- Como usuário, quero registrar um produto com seu proprietário, para que eu possa acessar seus dados no futuro (título, descrição, preço, categoria, ID do proprietário).
- Como usuário, quero registrar uma categoria com seu proprietário, para que eu possa acessar seus dados no futuro (título, descrição, ID do proprietário).
- Como usuário, quero associar um produto a uma categoria.
- Como usuário, quero atualizar os dados de um produto ou categoria.
- Como usuário, quero excluir um produto ou categoria do meu catálogo.
- Um produto pode estar associado a apenas uma categoria por vez.
- Assuma que produtos e categorias pertencem a apenas um proprietário.

- Lembre-se de que este é um catálogo de produtos online, o que significa que haverá múltiplas requisições para edição de itens/categorias por segundo, assim como acessos ao endpoint de busca do catálogo.
- Considere o catálogo de produtos como uma compilação em JSON de todas as categorias e itens disponíveis pertencentes a um usuário. Dessa forma, o endpoint de busca do catálogo não precisará buscar informações diretamente do banco de dados.
- Sempre que houver uma alteração no catálogo de produtos, publique essa mudança no tópico "catalog-emit" no serviço AWS SQS.
- Implemente um consumidor que escute mudanças no catálogo para um proprietário específico.
- Quando o consumidor receber uma mensagem, busque no banco de dados o catálogo desse proprietário, gere o JSON do catálogo e publique-o em um bucket no serviço AWS S3.

## Tecnologias Obrigatórias

Você deve desenvolver este teste utilizando as seguintes tecnologias:

- **MongoDB** como banco de dados.
- **AWS SQS** para notificações de mudanças no catálogo.
- **AWS S3** para armazenamento do JSON do catálogo.
- **Node.js** para o backend.
- **Express.js** como framework web.

## Estrutura do Projeto

**Diagrama representando a estrutura final do projeto:**  

<img alt="diagram with struture S3 SQS for catalog" width="680" src="https://github.com/githubanotaai/new-test-backend-nodejs/assets/52219768/504ba448-f128-41db-ae86-18dc19c0dc9d">


---

## Instruções

Para iniciar o teste:

1. **Faça um fork** deste repositório.
2. **Crie uma branch** com seu nome completo.
3. **Envie o link** do repositório com seu teste completo.
4. **Não clone apenas o repositório**, pois isso dificultará a submissão do pull request.
5. **Utilize sua própria conta AWS** para configurar os serviços necessários.
6. **Atualize o README** com instruções de execução da aplicação.
7. **Cole o nome da branch** no sistema GUPY e indique a conclusão do teste.
8. **Nos dê feedback** sobre o teste, se desejar.

## Critérios de Avaliação

Avaliaremos os seguintes aspectos da sua solução:

- Conhecimento em **JavaScript, Node.js e Express.js**.
- Estruturação adequada das **camadas da aplicação**.
- Tratamento de **chamadas externas**.
- Uso eficiente de **variáveis de ambiente**.
- Implementação de **testes unitários**.
- Mecanismos de **logging**.
- Estratégias de **tratamento de erros**.
- **Qualidade da documentação**.
- Organização do código, separação de módulos, **legibilidade e comentários**.
- Histórico de **commits**.

Boa sorte! 🚀

