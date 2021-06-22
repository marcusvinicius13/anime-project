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

###     Idepotêcia (RFC7231)

-       Os metodos https idepotentes são os métodos que não importando a quantidade de vezes que eles são execultados,
        eles não irão mudar o estado do servidor exemplo GET E DELETE, seção (4.2.2, 8.1.3)



###     Atalhos

-       ctrl + F9 : Builda o projeto.
-       Shift + F9 : Recompila o projeto.
-       ctrl + alt + O : corrige as importações.
-       ctrl + shift + F10 : Inicializa a aplicação.