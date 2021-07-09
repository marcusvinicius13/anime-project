#       Conceitos

###     Spring Boot

-       @Autowired : Essa é a  anotação para fazermos injeção de dependência.
        Porem não é uma boa prática, coloca-la em cida do campo, fica difícil criar testes unitários quando colocamos a 
        anotação em cima do campo.
        
        Para fazermos a injeção de depedência temos basicamente 3 formas. 
        
        1° @AutoWired, porem como já foi falado ele não é bem recomendado, e temos uma outra forma de 
        injetarmos via construtor.
        
        2° podemos criar o construtor na mão.
        
        3° utilizar a anotação do lombok para criar o contrutor automaticamente 
        @AllArgsConstructor ou @RequiredArgsConstructor, 
        mas para utilizar essas duas últimas formas é necessário que a variável seja final
        o mais correto é utilizar uma interface, e com isso o spring encontrar o objeto concreto para instanciar.
  
-       @Component : Essa anotação é utilizada para dizer que determinada classe é gerenciada pelo spring.
        Além dela temos outras as principais são : (@Service, @Controller, RestController, @Repository etcc...)

-       Lombok     : Para utilizar o lombok no projeto precisamos de algumas configurações: 
        1° Colocar as dependencias do projeto Lombok.
        2° Habilitar Annotation Processors em :
        (file -> settings -> Annotation Processors [X] enable annotation processing).
        3° Instalar o plugin do Lombok em (file -> settings -> plugins [pesquisa por lombok e instale]).

-       application.properties (Hoje em dia é uma boa prática renomear ele para application.yml)

-       server:
            error:
                include-stacktrace: on_param  (passar trace=true na url para ver o stacktrace)

-       Mapeamento com Jackson (caso o nome do atributo que venha na requisição não seja igual o que você espera,
        você pode fazer um mapeamento no seu pojo)
        Ex: nameCharacter e name, você precisa anotalo com :
        @JsonProperty("name")
        private String nameCharacter;

-       Temos uma forma a título de conhecimento, de criar um Builder do objeto, 
        ou seja transformar de forma automática, um objeto em um DTO, que é o Builder do Lombook.
        Ex: Anime anime = Anime.builder()
                .id(animePutRequestBody.getId())
                .name(animePutRequestBody.getName())
                .build();
        Claro que temos formas mas automática de utilizar framworks para fazer esse tipo de converção.

-       Com Java 8 podemos fazer uma busca dentro de uma lista utilizando stream
        return animes.stream()
        .filter(anime -> anime.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));

-       @Transactional : Essa anotação garante que caso haja uma exeção, 
        tenha um rollback da trasação no banco de dados! 

-       Rest Template : É utilizado para fazer requisições REST, para APIs externas


###     Map Struct

-       O Map Struct, é um FramWork, para mapeamento de DTOs, automático, mesmo se o nome dos campos forem diferentes, 
        ele aceita um mapeamento passando o do campo origem e o destino.
        

###     Idepotêcia (RFC7231)

-       Os metodos https idepotentes são os métodos que não importando a quantidade de vezes que eles são execultados,
        eles não irão mudar o estado do servidor exemplo GET E DELETE, seção (4.2.2, 8.1.3)
        Ex: @Mapping(source = "name", target = "nameSearch").


###     Atalhos

-       ctrl + F9 : Builda o projeto.
-       Shift + F9 : Recompila o projeto.
-       ctrl + alt + O : corrige as importações.
-       ctrl + shift + F10 : Inicializa a aplicação.
-       ctrl + alt + v : No pom gera uma variável do properties.
-       ctrl + L : Abre a consulta.
-       ctrl + O : Dentro da classe que implementa uma interface, ele abre os métodos que podem ser sobreescrito.



###     Docker - Compose

-       Cria o arquivo docker-compose.yml, onde ficação as configurações das imagens.

-       docker-compose up : Baixa a imagem e sobe o container no docker.
-       docker ps : Lista os containers em execução.
-       docker-compose down:  remos todos os containers.
-       docker ps -a Lista todos os container, até os que estão parados.
-       ctrl + C : para os containers que estão sendo executados.

###     Teste Banco de dados : @DataJpaTest

-       @DataJpaTest
        @DisplayName("Tests for AnimeRepository")
        @Log4j2 
        class AnimeRepositoryTest {
            ...
        }

###     Testes Unitários 

-       Na classe em que vamos escrever nossos testes, podemos anotala de duas principais formas.
        1ª Podemos anotala com o  @SpingBootTest : O problema dessa abordagem é que temos que ter o banco dados rodando,
        porque ele inicia o contexto da aplicação.
        2ª Podemos anotala com o @ExtendWith(SpringExtension.class), assim não precisa inicializar o contexto do spring.

###     Testes de Banco de Dados 



###     Testes de Integração

-       Os testes de integração o que difere dos demais é que ele simula uma execusão 
        até mesmo do servidor.


###     Falando em Testes vamos utilizar o Mokito para mocar os nossos dados.

-       @InjectMocks : Utilizamos quando queremos testar a classe em si!

-       @Moke : É utilizado para testar todas as classes que está dentro da classe que iremos testar.

-       Primeiro passo é definir o comportamento dos mokes, de cada método que queremos testar.


###     Profile de teste

-       Temos que fazer algumas configurações de profile dentro do pom.xml, 
        para utilizarmos o maven para executar, os nossos testes.

-       Com o profile configurado no pom.xml, executamos o comando :
        mvn test -Pintegration-tests (mvn test -P + o nome do id que foi configurado no profile)

