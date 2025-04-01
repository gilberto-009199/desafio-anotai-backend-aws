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

