package com.main;

import org.w3c.dom.ls.LSOutput;

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
        String ex = "program circle\n   implicit none\n   real r=1.0, pi=0\n   parameter(pi = 3.14159)\n   write(\"Raio:\")\nstop";
//        System.out.println(ex);


        processaTexto(ex);

    }

    public static void processaTexto(String texto) {
        // 1 separa por linhas, armazena
        // 2 separa por palavras armazena (separando por espaço)
        // verifica cada palavra se é reservada, se sim escreve tabela, se não prox etapa
        // aqui lidar antes de passar pro AFD tratar exemplos como "parameter(pi = 3.14159)" que não serão separados por espaço nas etapas anteriores
        // o que sobrar vai para o AFD cuidar

        List<String> linhas = Arrays.asList(texto.split("\n"));
        System.out.println(linhas);

        //aqui para cada linha separar as palavras
        List<String> palavras = Arrays.asList(texto.split(" ")); // aqui há um problema com separação por espaços não resolvido


        for (String palavraAtual: palavras) {
            if(palavrasReservadas.contains(palavraAtual)) {
                insereTabela(palavraAtual);
            }
        }


        //        System.out.println(palavrasReservadas.contains("implicit")); // true ou false

    }

    public static void insereTabela(String palavra) {
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
