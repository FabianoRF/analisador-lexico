package com.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Observações
// O trabalho desenvolvido não possui interface
// A entrada de código para análise é feita por meio do arquivo entrada.txt
// Ao fim da análise a tabela com os dados é exibida pelo terminal
// Falta: atribuição de valor inicial, escopo, tratativa para texto entre parenteses(aqui ele so é retirado da linha)


public class Main {
    public static List<String> palavrasReservadas;
    public static List<String> linhas = new ArrayList<>();
    public static List<LinhaTabela> tabela = new ArrayList<>();
    public static String simbolos = "()=,+-";
    public static int contId = 1;

    public static void main(String[] args) {
        populaPalavrasReservadas();
        leitor();
        processaTexto();
        exibeTabela();
    }

    public static void processaTexto() {
        // separa por linhas, armazena
        // passa um filtro retirando comentários
        // passa um filtro retirando o que esta sob aspas
        // passa um filtro em todos simbolos () e afins, colocando um espaço no lugar
        // add os simbolos por meio do insereTabela
        // separa por palavras armazena (separando por espaço)
        // verifica cada palavra se é reservada, se sim escreve tabela, se não prox etapa

        boolean ePalavraReservada = true;

        // inicio dos filtros
        for(int l = 0; l< linhas.size(); l++){
            String linha = linhas.get(l);

            // filtro remove comentário
            String regex = "!.*";
            linha = linha.replaceAll(regex, " ");
            LinhaTabela linhaTabela;

            // TODO: adicionar na tabela quando identificado um literal
            // filtro de conteudo entre aspas aqui so esta removendo falta adicionar como literal
            regex = "\"([^\"]*)\"";
            linha = linha.replaceAll(regex, " ");

            // filtro de simbolos
            // Aqui é verificado se os simbolos estão contidos na constante simbolo, se sim são adicionados na tabela
            for(int i = 0; i < linha.length(); i++){
                char letra = linha.charAt(i);
                if(simbolos.contains(String.valueOf(letra))){
                    linhaTabela = new LinhaTabela(contId, String.valueOf(letra), "SIMBOLO", "-", "-", "-", "-", l);
                    insereTabela(linhaTabela);
                    linha = linha.replaceFirst("\\" + letra, " ");
                }
            }
            // adiciona na lista de linhas
            linhas.set(l, linha);

            // ao final dos filtros é feito um split por espaço e as palavras são encaminhadas para o AFD ou adicionadas como palavras reservadas
            List<String> palavras = Arrays.asList(linha.split(" ")); // aqui há um problema com separação por espaços não resolvido
            for(String palavraAtual: palavras) {
                ePalavraReservada = verificaPalavraReservada(palavraAtual, l);
                if(!ePalavraReservada) {
                    executaAFD(palavraAtual, l); // chama o AFD
                }
            }
        }
    }

    // apos o filtro e antes do AFD é feito a verificação se a palavra é reservada
    public static boolean verificaPalavraReservada(String palavra, int linha) {
//         se for reservada
        if(palavrasReservadas.contains(palavra)) {
            LinhaTabela linhaTabela= new LinhaTabela(contId, palavra, "PALAVRA_RESERVADA", "-", "-", "-", "-", linha);
            insereTabela(linhaTabela);
            return true;
        }
        return false;
    }

    public static void executaAFD(String palavra, int nLinha) {
        // verifica um espaço vazio
        if (!palavra.contains(" ") && !palavra.isEmpty()) {
            int  i=0;
            boolean flag = true, status = true;
            char primeiroCaracterAlpha = palavra.charAt(0);//pega o primeiro caracter da string
            int tamPalavra = palavra.length();//tamanho da string
            LinhaTabela linhaTabela;

            if (Character.isAlphabetic(primeiroCaracterAlpha)){//Aqui é para Letra (Se primeira letra for Char o restante pode ser Char ou Num somente, ex valido: Var520 | ex não valido 56var (Seria o estado q0 do AFD indo para o q1)
                status = true;

                for(i=0; i<tamPalavra; i++){
                    if (Character.isAlphabetic(palavra.charAt(i)) || Character.isDigit(palavra.charAt(i))){
                        status = true;
                    } else {
                        status = false;
                    }
                }

                if(status == true){
                    linhaTabela = new LinhaTabela(contId, palavra, "ID", "-", "-", "-", "-", nLinha);
                    insereTabela(linhaTabela);
                }
            }else if(Character.isDigit(palavra.charAt(0))){ //Aqui para numeros (Seria o estado q0 do AFD indo para o q6)
                status = true;

                for (i=0 ; i<tamPalavra ; i++){
                    if (Character.isDigit(palavra.charAt(i))){//Verifica se é somente numero
                        status = true;

                    }else if(Character.isAlphabetic(palavra.charAt(i))){   //Verifica a presença de letra
                        status = false;

                    }else if(palavra.contains(".")){//Verifica a presença do "."
                        if(flag){
                            status = true;
                            flag = false;

                        }else {// Caso passe o numero de "."
                            status = false;
                        }
                    }
                }
                if(status == true){
                    linhaTabela = new LinhaTabela(contId, palavra, "NUM", palavra, "-", "-", "-", nLinha);
                    insereTabela(linhaTabela);
                }

            } else if (!Character.isAlphabetic(palavra.charAt(i)) && !Character.isDigit(palavra.charAt(i))){  // Aqui para os casos onde  não é uma palavra inciado com (A-Z) e (0-9)
                linhaTabela = new LinhaTabela(contId, palavra, "ERRO", "-", "-", "-", "-", nLinha);
                insereTabela(linhaTabela);
            }
        }
    }

    public static void insereTabela(LinhaTabela linhaTabela){
        // incremental do id da tabela
        contId++;
        tabela.add(linhaTabela);
    }

    // função para popular estruturas
    public static void populaPalavrasReservadas() {
        palavrasReservadas = new ArrayList<>();
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
        palavrasReservadas.add("//");
        palavrasReservadas.add("!");
        palavrasReservadas.add(".");
        palavrasReservadas.add(",");
    }

    // Função imprime tabela no terminal
    public static void exibeTabela(){//Função utilizada para exibir tabela pelo terminal
        int tamTabela = tabela.size();
        int i=0;

        System.out.println("-------------TABELA-------------\n");
        for(i=0; i<tamTabela; i++){
            System.out.print("\t[ID: "+tabela.get(i).getId()+" ]");
            System.out.print("\t[LEXEMA: "+tabela.get(i).getLexema()+" ]");
            System.out.print("\t[TOKEN: "+tabela.get(i).getToken()+" ]");
            System.out.print("\t[VALOR INICIAL: "+tabela.get(i).getValorInicial()+" ]");
            System.out.print("\t[ESCOPO: "+tabela.get(i).getEscopo()+" ]");
            System.out.print("\t[LINHAS: "+tabela.get(i).getLinhas()+" ]");
            System.out.print("\t[COLUNAS: "+tabela.get(i).getColunas()+" ]");
            System.out.print("\t[LINHA: "+tabela.get(i).getLinha()+" ]");
            System.out.print("\n___________________________________________________________________________________________________________________________________\n");
        }
    }

    // leitor de arquivo que adiciona cada linha em uma lista
    public static void leitor(){
        String nomeArq = "entrada.txt";
        try {
            FileReader arq = new FileReader(nomeArq);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";

            while (true) {
                if (linha != null) {
                    linhas.add(linha);
                } else
                    break;
                linha = lerArq.readLine();
            }
            lerArq.close();
            System.out.println("\nLeitura concluída!\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Erro na leitura do arquivo!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro na leitura do arquivo!");
        }
    }
}
