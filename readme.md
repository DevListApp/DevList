# Documentação DevList

- Aplicativo Versão 1.0
    
    

# Sobre o DevList

O **DevList** é um aplicativo dedicado a simplificar a gestão de tarefas e checklists para todos os usuários. Desenvolvido com praticidade e eficiência em mente, o DevList oferece uma interface intuitiva para criar, organizar e monitorar listas de tarefas, proporcionando uma experiência otimizada para o dia a dia.

### Origem do Nome “DevList”

![Untitled](app/logo/logo_dev_list.png)

“Dev” significa “developer”, desenvolvedor em inglês. Apesar do nome se basear em um profissional de computação, a ideia do app é ser utilizada por todos, não somente por desenvolvedores. Foi pensado este nome, pois é para ser um aplicativo onde os processos podem ser mais otimizados e a pode ter uma boa experiência do usuário.

“List” significa “Lista”, que são lista de tarefas ou até mesmo checklist, que são ferramentas eficazes, para simplificar as atividades do dia a dia, conseguindo dividir as etapas em partes, e serem resolvidas com mais praticidade e eficiência.

## Projeto Inicial

### Protótipo

O pontapé inicial do nosso projeto começou com a criação de um protótipo no Figma, uma plataforma de design colaborativo. Nesse estágio crucial, cada detalhe do aplicativo de lista de tarefas foi cuidadosamente concebido e visualizado, desde a interface do usuário até a experiência do usuário.

O Figma permitiu que nossa equipe desenvolvesse e refinasse a estrutura do aplicativo de maneira interativa e eficiente.

Essa abordagem no Figma foi crucial para garantir que o app de lista de tarefas atendesse às expectativas e necessidades dos usuários desde o seu conceito. A colaboração contínua durante a criação do protótipo foi fundamental para garantir a eficácia e a usabilidade do aplicativo, estabelecendo uma base sólida para o desenvolvimento posterior.

### Design do App

O design intuitivo e harmonizado é um conceito fundamental para criar produtos e experiências que sejam naturalmente compreensíveis e agradáveis para os usuários.  Se baseando na ideia de que o design deve ser acessível e fácil de usar, levando em consideração a intuição humana e a fluidez na interação.

Ao desenvolver um design intuitivo, nós buscamos criar interfaces que sigam padrões reconhecíveis e consistentes, permitindo que os usuários compreendam facilmente como interagir sem a necessidade de instruções complexas. Isso é alcançado por meio da organização lógica dos elementos, como menus, botões e navegação, de modo a refletir a forma como as pessoas naturalmente esperam que as coisas funcionem.

A harmonização no design se concentra na estética, na combinação de cores, tipografia, formas e layouts para criar uma experiência visualmente agradável e coesa. A harmonia visual reduz a carga cognitiva do usuário, facilitando a absorção de informações e a navegação.

# Instalação

## Requisitos do Sistema

- Android 7.0 ou superior

# Funcionalidades

### 1. Função Login

A função login é o método para acessar o aplicativo. Login (de “conectar”) é um conjunto de verificação de credenciais para identificar se um usuário já está cadastrado no banco de dados para conseguir acessar o app. 

`[LOGIN]`

### 2. Função Cadastro

A função cadastro é o método principal para acessar o aplicativo. O primeiro acesso do usuário deve ser por esta função, onde ele devera cadastrar no banco de dados, as informação que são solicitadas, como email e senha, como principais requisitos iniciais.

`[CADASTRO]`

### 3. Função Cadastrar Tarefa

Essa função permite aos usuários criar e armazenar informações sobre diferentes atividades ou afazeres que precisam ser realizados, adicionando titulo, descrição, prazo e até mesmo prioridade da tarefa.

`[Button: **+** ]`

### 4. Função Editar Tarefa

Essa função permite ao usuário, ao clicar na tarefa, editar suas informações, como título, descrição, data e prioridade da atividade.

`[Ação: Clicar na Tarefa ]`

### 5. Status Tarefa

Em cada tarefa temos o status da mesma, onde o usuário irá acompanhar o andamento da atividade. E com a possibilidade de editar o status.

**Existem três status:** 

- **Concluída (Verde):** Ao marcar a tarefa como concluída, a cor verde indicará que a atividade foi realizada com sucesso, após marcar o check-box.
- **A fazer (Laranja):** Uma tarefa em aberto será destacada em laranja, indicando que está pendente de execução, em andamento, que não passou do prazo final para ser realizada.
- **Atrasada (Vermelha):** Se a data de conclusão passou e a tarefa não foi marcada como concluída, a cor vermelha sinalizará que a atividade está atrasada.

### 6. Função Calendário

Ao adicionar uma tarefa no aplicativo, é possível definir dois prazos essenciais: a data de início e a data de conclusão. Esses prazos fornecem uma estrutura temporal clara para suas atividades, permitindo uma melhor organização e acompanhamento do progresso.

- **Tarefa em Andamento:** Se a data atual estiver dentro do intervalo entre a data de início e a de conclusão, a tarefa será automaticamente marcada como "Em Andamento". Essa categoria destaca as tarefas que estão no prazo.
- **Tarefa Atrasada:** Se a data de conclusão passou e a tarefa não foi marcada como concluída, ela será automaticamente categorizada como "Atrasada". Isso ajuda a identificar e gerenciar atividades que precisam de atenção urgente.
- Ao clicar no ícone de checkbox, você indica que a atividade foi concluída com sucesso, alterando o status da tarefa para "Concluída" e destacando-a em verde.

### 7. Função Validação de Email

A função de validação de e-mail garante que apenas endereços de e-mail válidos sejam aceitos no sistema, por exemplo os que possuem "@gmail.com" e outros domínios considerados válidos. Ao inserir um endereço de e-mail, o sistema verifica se ele contém o símbolo "@" e se o domínio está na lista de domínios permitidos. Caso ambas as condições sejam atendidas, o sistema considera o e-mail como válido, permitindo a sua utilização no contexto desejado. Essa abordagem restrita ajuda a manter a integridade dos dados e a garantir que apenas e-mails permitidos sejam aceitos pelo sistema.

## Funcionalidades

- Adicionar tarefa
- Editar Tarefa
- Excluir Tarefa
- Marcar Tarefa como Concluída
- Visualizar Tarefas Pendentes
- Visualizar Tarefas Concluídas
- Visualizar Tarefas Atrasadas
- Tela de Perfil
- Login
- Cadastro
- Recuperação de Senha

## Tecnologias Usadas

- Java
- Firebase