# Documentação DevList

- Aplicativo Versão 1.0
    
    

# Sobre o DevList

O **DevList** é um aplicativo dedicado a simplificar a gestão de tarefas e checklists para todos os usuários. Desenvolvido com praticidade e eficiência em mente, o DevList oferece uma interface intuitiva para criar, organizar e monitorar listas de tarefas, proporcionando uma experiência otimizada para o dia a dia.

### Origem do Nome “DevList”
<img style="width: 150px" src="app/src/main/ic_launcher-playstore.png">

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
    - Função gerador de senha forte:
        Um gerador de senha forte é uma ferramenta essencial na proteção digital hoje em dia. Ele cria combinações complexas de letras maiúsculas, minúsculas, números e caracteres especiais, mais letras e numeros dificultando ataques de força bruta. Essas senhas são praticamente impossíveis de serem adivinhadas, proporcionando uma camada robusta de segurança para contas online e dados sensíveis. Além de garantir a segurança, um bom gerador de senha permite personalização, como ajuste de comprimento e tipos específicos de caracteres. Utilizar senhas fortes é fundamental para proteger informações contra hackers e garantir a privacidade pessoal e empresarial na era digital. Um botão para registro de usuário que realiza validações de senha e campos obrigatórios antes de exibi um alerta para aceitar os termos de uso para caso a senha não atender os critérios do app se atender os critérios do app ele cria um usuário com os dados fornecidos mostrando uma mensagem de falha ou sucesso no cadastro, caso recuse os termos o app alerta que é preciso aceitar para continuar o cadastro

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

## 8. Política de privacidade 

### POLÍTICA DE PRIVACIDADE
`SEÇÃO 1 - INFORMAÇÕES GERAIS`

A presente Política de Privacidade contém informações sobre coleta, uso, armazenamento, tratamento e proteção dos dados pessoais dos usuários e visitantes do aplicativo DevList, com a finalidade de demonstrar absoluta transparência quanto ao assunto e esclarecer a todos interessados sobre os tipos de dados que são coletados, os motivos da coleta e a forma como os usuários podem gerenciar ou excluir as suas informações pessoais.

O presente documento foi elaborado em conformidade com a Lei Geral de Proteçâo de Dados Pessoais (Lei 13.709/18), o Marco Civil da Internet (Lei 12.965/14) (e o Regulamento da UE n. 2016/6790). Ainda, o documento poderá ser atualizado em decorrência de eventual atualização normativa, razão pela qual se convida o usuário a consultar periodicamente esta seção.

`SEÇÃO 2 - COMO RECOLHEMOS OS DADOS PESSOAIS DO USUÁRIO E DO VISITANTE?`

Os dados pessoais do usuário e visitante são recolhidos pela plataforma da seguinte forma:

Quando o usuário cria uma conta/perfil na plataforma DevList: esses dados são os dados de identificação básicos, como  e-mail, nome completo. A partir deles, podemos identificar o usuário e o visitante, além de garantir uma maior segurança e bem-estar às suas necessidade. Ficam cientes os usuários e visitantes de que seu perfil na plataforma estará acessível a todos demais usuários e visitantes da plataforma DevList.

Quando um usuário e visitante acessa o aplicativo DevList, as informações sobre interação e acesso são coletadas pela empresa para garantir uma melhor experiência ao usuário e visitante. Estes dados podem tratar sobre as palavras-chaves utilizadas em uma busca, o compartilhamento de um documento específico, comentários, visualizações de páginas, perfis, seus IPs de acesso, dentre outras que poderão ser armazenadas e retidas.

`SEÇÃO 3 - QUAIS DADOS PESSOAIS RECOLHEMOS SOBRE O USUÁRIO E VISITANTE?`

Os dados pessoais do usuário e visitante recolhidos são os seguintes:

Dados para a criação da conta/perfil no aplicativo DevList :: e-mail, nome completo, senha.

`SEÇÃO 3.1 - PARA QUE FINALIDADES UTILIZAMOS OS DADOS PESSOAIS DO USUÁRIO E VISITANTE?`

Os dados pessoais do usuário e do visitante coletados e armazenados pela plataforma DevList tem por finalidade:

Bem-estar do usuário e visitante: aprimorar o produto e/ou serviço oferecido, facilitar, agilizar e cumprir os compromissos estabelecidos entre o usuário e a empresa, melhorar a experiência dos usuários e fornecer funcionalidades específicas a depender das características básicas do usuário.

Melhorias da plataforma: compreender como o usuário utiliza os serviços da plataforma, para ajudar no desenvolvimento de negócios e técnicas.

Dados de cadastro: para permitir o acesso do usuário a determinados conteúdos da plataforma, exclusivo para usuários cadastrados

O tratamento de dados pessoais para finalidades não previstas nesta Política de Privacidade somente ocorrerá mediante comunicação prévia ao usuário, de modo que os direitos e obrigações aqui previstos permanecem aplicáveis.

`SEÇÃO 4 - POR QUANTO TEMPO OS DADOS PESSOAIS FICAM ARMAZENADOS?`

Os dados pessoais do usuário e visitante são armazenados pela plataforma durante o período necessário para a prestação do serviço ou o cumprimento das finalidades previstas no presente documento, conforme o disposto no inciso I do artigo 15 da Lei 13.709/18.

Os dados podem ser removidos ou anonimizados a pedido do usuário, excetuando os casos em que a lei oferecer outro tratamento.

Ainda, os dados pessoais dos usuários apenas podem ser conservados após o término de seu tratamento nas seguintes hipóteses previstas no artigo 16 da referida lei:

I - cumprimento de obrigação legal ou regulatória pelo controlador;

II - estudo por órgão de pesquisa, garantida, sempre que possível, a anonimização dos dados pessoais;

III - transferência a terceiro, desde que respeitados os requisitos de tratamento de dados dispostos nesta Lei;

IV - uso exclusivo do controlador, vedado seu acesso por terceiro, e desde que anonimizados os dados.

`SEÇÃO 5 - SEGURANÇA DOS DADOS PESSOAIS ARMAZENADOS`

A plataforma se compromete a aplicar as medidas técnicas e organizativas aptas a proteger os dados pessoais de acessos não autorizados e de situações de destruição, perda, alteração, comunicação ou difusão de tais dados.

A plataforma não se exime de responsabilidade por culpa exclusiva de terceiro, como em caso de ataque de hackers ou crackers, ou culpa exclusiva do usuário, como no caso em que ele mesmo transfere seus dados a terceiros. O aplicativo compromete a comunicar o usuário em caso de alguma violação de segurança dos seus dados pessoais.

Os dados pessoais armazenados são tratados com confidencialidade, dentro dos limites legais. No entanto, podemos divulgar suas informações pessoais caso sejamos obrigados pela lei para fazê-lo.

`SEÇÃO 6 - OS DADOS PESSOAIS ARMAZENADOS SERÃO TRANSFERIDOS A TERCEIROS?`

Os dados pessoais não podem ser compartilhados com terceiros.


`SEÇÃO 7 - CONSENTIMENTO`

Ao utilizar os serviços e fornecer as informações pessoais na plataforma, o usuário está consentindo com a presente Política de Privacidade.

O usuário, ao cadastrar-se, manifesta conhecer e pode exercitar seus direitos de cancelar seu cadastro, acessar e atualizar seus dados pessoais e garante a veracidade das informações por ele disponibilizadas.

O usuário tem direito de retirar o seu consentimento a qualquer tempo, para tanto deve entrar em contato através do e-mail devlist2024@gmail.com.

`SEÇÃO 8 - ALTERAÇÕES PARA ESSA POLÍTICA DE PRIVACIDADE`

Reservamos o direito de modificar essa Política de Privacidade a qualquer momento, então, é recomendável que o usuário e visitante revise-a com frequência.

As alterações e esclarecimentos vão surtir efeito imediatamente após sua publicação na plataforma. Quando realizadas alterações os usuários serão notificados. Ao utilizar o serviço ou fornecer informações pessoais após eventuais modificações, o usuário e visitante demonstra sua concordância com as novas normas.

Diante da fusão ou venda da plataforma à outra empresa os dados dos usuários podem ser transferidas para os novos proprietários para que a permanência dos serviços oferecidos.

`SEÇÃO 9 – JURISDIÇÃO PARA RESOLUÇÃO DE CONFLITOS`

Para a solução de controvérsias decorrentes do presente instrumento será aplicado integralmente o Direito brasileiro.
Os eventuais litígios deverão ser apresentados no foro da comarca em que se encontra a sede da empresa.


## 9. Implementação de políticas robustas de segurança para senhas.

Criterios para uma senha forte 
A medida que o usuário digita a senha o app verifica se atende todos os critérios para melhor segurança do usuário, aqui estão os critérios de uma senha forte a cada critério atingindo incrementa 20pts na forca da senha :
* Se a senha for maior ou igual a 8 
* Se a senha conter de letras maiúsculas  
* Se a senha conter letras minúsculas  
* Se a senha conter números 
* Se a senha conter caracteres especiais
Contendo todos esses critérios retornando 100 na forca da senha a senha é aceita e o cadastro feito, não contendo os critérios ele não retorna 100 e mostra um alerta de senha fraca
outra opção é o usuário clicar num botão de gerador de senha forte, esta senha forte atingira todos os requisitos  

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
- Gerador de senha
- Recuperação de Senha

## Tecnologias Usadas

- Java
- Firebase
