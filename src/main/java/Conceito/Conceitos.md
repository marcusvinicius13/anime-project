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

###     Atalhos

-       ctrl + F9 : Builda o projeto;
-       ctrl + alt + O : corrige as importações.
-       ctrl + shift + F10 : Inicializa a aplicação.