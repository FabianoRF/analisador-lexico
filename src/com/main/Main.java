package com.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static List<String> palavrasReservadas;
    public static int contLexema = 0;
    public static List<LinhaTabela> tabela = new ArrayList<>();

    public static void main(String[] args) {
	// write your code here

        populaPalavrasReservadas();


        // passo 2: criar leitura
        String ex = "program circle\n   implicit none\n   real r=1.0, pi=0\n   parameter(pi = 3.14159)\n   write(\"Raio:\")\n   write(\"Raio:\")\nsstop";
//        System.out.println(ex);


        processaTexto(ex);

    }

    public static void processaTexto(String texto) {
        // 1 separa por linhas, armazena
        // passa um filtro retirando o que esta sob parenteses
        // passa um filtro em todos simbolos () e afins, colocando um espaço no lugar
        // add os simbolos por meio do insereTabela
        // separa por palavras armazena (separando por espaço)
        // verifica cada palavra se é reservada, se sim escreve tabela, se não prox etapa
        // aqui lidar antes de passar pro AFD tratar exemplos como "parameter(pi = 3.14159)" que não serão separados por espaço nas etapas anteriores
        // o que sobrar vai para o AFD cuidar

        List<String> linhas = Arrays.asList(texto.split("\n"));


        // inicio do filtro da linha

        String simbolos = "()=,"; //completar


        for(int l = 0; l< linhas.size(); l++){
            String linha = linhas.get(l);



            // filtro de conteudo entre parenteses aqui so esta removendo falta adicionar como literal
            String regex = "\"([^\"]*)\"";
            linha = linha.replaceAll(regex, " ");
//            insereTabela(String.valueOf(letra), l);


            // filtro de simbolos funcional, falta mapear todos simbolos
            for(int i = 0; i < linha.length(); i++){
                char letra = linha.charAt(i);
                if(simbolos.contains(String.valueOf(letra))){
                        insereTabela(String.valueOf(letra), l);
                    linha = linha.replaceFirst("\\" + letra, " ");
                }
            }
            linhas.set(l, linha);

            // ao final dos filtros fazer um split por espaço e encaminhar cada palavra para o AFD
            List<String> palavras = Arrays.asList(linha.split(" ")); // aqui há um problema com separação por espaços não resolvido

            for(String palavraAtual: palavras) {
                executaAFD(palavraAtual); // aqui nosso amigo AFD entra em ação
            }
        }



        for (String linha: linhas) {
            System.out.println("\n"+linha);
        }

    }

    public static void executaAFD(String palavra) {

    }

    public static void insereTabela(String palavra, int numeroLinha) { // passar linha por parametro
        // Cria nova instancia de LinhaTabela incrementando o ID
        contLexema++;
        LinhaTabela linhaTabela= new LinhaTabela(contLexema);

        // se for reservada
        if(palavrasReservadas.contains(palavra)) {
            linhaTabela.lexema = palavra;
            linhaTabela.valorInicial = "-";
            linhaTabela.escopo = "-";
            linhaTabela.linhas = "-";
            linhaTabela.colunas = "-";
            linhaTabela.linha = 111; // definir um controle de linhas
        }


        //adiciona na estrutura de tabela principal
        tabela.add(linhaTabela);

    }

    // funçoes para popular estruturas
    public static void populaPalavrasReservadas() {
        palavrasReservadas = new ArrayList<String>();

        palavrasReservadas.add("program");
        palavrasReservadas.add("implicit");
        palavrasReservadas.add("none");
        palavrasReservadas.add("integer");
        palavrasReservadas.add("real");
        palavrasReservadas.add("complex");
        palavrasReservadas.add("character");
        palavrasReservadas.add("logical");
        palavrasReservadas.add("read");
        palavrasReservadas.add("print");
        palavrasReservadas.add("if");
        palavrasReservadas.add("then");
        palavrasReservadas.add("else");
        palavrasReservadas.add("end");
        palavrasReservadas.add("go");
        palavrasReservadas.add("endif");
        palavrasReservadas.add("enddo");
        palavrasReservadas.add("to");
        palavrasReservadas.add("pause");
        palavrasReservadas.add("parameter");
        palavrasReservadas.add("while");
        palavrasReservadas.add("do");
        palavrasReservadas.add("call");
        palavrasReservadas.add("subroutine");
        palavrasReservadas.add("function");
        palavrasReservadas.add("return");
        palavrasReservadas.add(".eq.");
        palavrasReservadas.add(".ne.");
        palavrasReservadas.add(".lt.");
        palavrasReservadas.add(".le.");
        palavrasReservadas.add(".gt.");
        palavrasReservadas.add(".ge.");
        palavrasReservadas.add(".or.");
        palavrasReservadas.add(".and.");
        palavrasReservadas.add(".not.");
        palavrasReservadas.add("+");
        palavrasReservadas.add("-");
        palavrasReservadas.add("*");
        palavrasReservadas.add("/");
        palavrasReservadas.add("**");
        palavrasReservadas.add("(");
        palavrasReservadas.add(")");
//      tratar o "" de alguma forma diferente
        palavrasReservadas.add("//");
        palavrasReservadas.add("!");
        palavrasReservadas.add(".");
        palavrasReservadas.add(",");

    }

    // criar func para popular AFD
    public static void populaAFD() {

    }
}
