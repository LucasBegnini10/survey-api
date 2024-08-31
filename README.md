# Survey Service - Spring Boot

Este projeto é um backend desenvolvido em Spring Boot utilizando Maven como ferramenta de build e gerenciamento de dependências. O serviço foi criado para gerenciar surveys (pesquisas) e armazenar respostas de usuários. A aplicação segue os princípios da Arquitetura Hexagonal (Ports and Adapters), promovendo uma clara separação de responsabilidades e facilitando a manutenção e evolução do sistema.

## Tecnologias Utilizadas
- **Spring Boot**: Framework principal para o desenvolvimento do backend.
- **Maven**: Gerenciamento de dependências e build do projeto.
- **MongoDB**: Banco de dados NoSQL utilizado para persistir as informações dos surveys e suas respostas.
- **RabbitMQ**: Utilizado para comunicação assíncrona entre componentes do sistema.
- **Arquitetura Hexagonal**: Estrutura o projeto para garantir que a lógica de negócios permaneça isolada de preocupações externas (como persistência e comunicação).

# Testes
- **Testes Unitários**: Validação dos componentes individuais, garantindo que cada parte do sistema funcione corretamente de forma isolada.
- **Testes de Integração**: Verificação do funcionamento correto da aplicação como um todo, integrando múltiplos componentes (ex: MongoDB e RabbitMQ).
