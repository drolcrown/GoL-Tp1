GameOfLife
==========

Projeto da Disciplina de Técnicas de Programação I
Universidade de Brasília.

##### Especificação do projeto:
Baseado nos códigos disponibilizados no ambiente moodle da disciplina as seguintes melhorias devem ser implementadas no algoritmo do GameOfLife:


* Questão 01 do projeto

•conforme mencionado, diferentes alternativas podem ser usadas para flexibilizar a implementação do cálculo das próximas gerações.
    Entre elas:
    
      •Template Method
      •Strategy
      
•Usando o padrão Template Method, tornamos a classe GameEngine abstrata. Classes abstratas geralmente possuem pelo menos um método abstrato, não podem ser instanciadas e servem para estabelecer um tipo particular de contrato:  as classes concretas que herdam de uma classe abstrata devem prover uma implementação para os métodos abstratos declarados na super classe.  Os métodos shouldRevive e shouldKeepAlive
Devem ser declarados como abstratos e serem chamados pelo método concreto nextGeneration da classe GameEngine (caracterizando o padrão Template Método).

•No caso do TemplateMethod, a implementação dos métodos shouldRevive e shouldKeepAlive ́e concretizada em classes que herdam de GameEngine.  Note que, para mudar a regra de derivação durante uma partida, precisamos instanciar uma GameEngine.

•Usando o padrão Strategy, deve ocorrer uma extração da definição dos métodos shouldRevive e shouldKeepAlive para um trait (EstrategiaDeDerivacao) e fazer com que a classe GameEngine referencie uma instancia desse trait. O método que computa próxima geração faz as chamadas aos métodos shouldRevive e shouldKeepAlive extraídos para o trait.

•A implementação dos métodos shouldRevive e shouldKeepAlive e concretizada em classes que implementam o trait EstrategiaDeDerivacao . Note que, para mudar a regra de derivação durante uma partida, precisamos apenas atualizar a referência a uma estratégia de derivação na classe GameEngine não sendo necessário instanciar novamente essa classe
      
      
      
* Questão 02 do Projeto

•Implemente uma interface gráfica para o GameOfLife reusando as classes existentes.  A interface gráfica pose ser baseada na biblioteca libgdx.

•Torne a implementação mais flexível com o uso do padrão injeção de dependência (ID), de tal forma que os objetos que implementam os diferentes algoritmos para calcular as regras de derivação sejam injetados no programa (em vez de diretamente instanciados).




* Questão 03 do Projeto

•Implemente uma nova opção de menu que faz com que próximas gerações sejam computadas automaticamente. 

•Observe que a implementação atual não suporta a noção de ambiente infinito (as células próximas aos limites do tabuleiro não possuem oito células vizinhas).  Corrija essa falha de implementação.



* Questão 04 do Trabalho

•Estude o padrão de projeto memento e implemente um mecanismo de Undo, de tal forma que seja possível retornar (desfazer) n gerações.  Assuma n = 10.


Instruções de execução (Utilizando a IDE *IntelliJ IDEA*)
=========================================================


#Software necessário

O seguinte software é necessário para construir o ScalaFX:
* Instalar o ScalaFX no projeto:

   O seguinte software é necessário para construir o ScalaFX:
 

  1. [SBT](http://www.scala-sbt.org/) v.0.13.5 ou melhor
     - Descompacte o conteúdo em alguma pasta de sua preferência no projeto.
  2. [Scala](http://www.scala.org/)
      - ScalaFX 8.0 é construído com o Scala 2.10.2 ou mais recente.
      - ScalaFX 2.2 é construído com a versão Scala 2.9.3 +, 2.10. + Ou 2.11. +



* No projeto 

  • Em buid.sbt adicione o seguinte código:

          name := "GoL_Strategy"

          version := "1.0"

          scalaVersion := "2.12.2"

          libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.102-R11"


* Utilize a *JRE System Library [JavaSE-1.8]*.

------------------------------------------------------------------------------------------------------------------------
O JOGO

Usando o intellij o usuário pode executar o object main na pasta GoL_Strategy e começar o jogo pela própria IDE. 

Comece a jogar: File>New   (Isso criará uma matriz de celulas mortas)
Clique nos quadrados para tornar as células vivas.
O botão "Next Generation" criará a nova geração.
O botão "Play" criará novas gerações indefinidamente até o botão "Stop" ser acionado.
Os botões "UNDO" e "REDO" desfazem e refazem as ações do usuário.
Para mudar as regras do jogo vá no menu "Rules" e selecione a de preferência.

O usuário pode mudar a cor das cálulas vivas com os botões de cores no canto superoir esquerdo.
Existem configurações de células vivas pré setadas no jogo "Pulsar" e "Glider".

[Rafael Oliveira de Souza][15/0081537] 
