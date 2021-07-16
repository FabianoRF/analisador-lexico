package com.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO:completar simbolos e colocar liral no processa palavras
//


public class Main {
    public static List<String> palavrasReservadas;
    public static List<String> linhas = new ArrayList<>();
    public static List<LinhaTabela> listaLinhaTabela = new ArrayList<>();
    public static String simbolos = "()=,+-"; //completar
    public static int contId = 0;

    public static void leitor(){
        String nomeArq = "entrada.txt";
        String texto = "";
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

    public static void main(String[] args) {
        populaPalavrasReservadas();
        leitor();

        processaTexto();

        Tabela tabela = new Tabela();
        tabela.exibeTabela((ArrayList) listaLinhaTabela);
    }


    public static void processaTexto() {
        // separa por linhas, armazena
        // passa um filtro retirando comentários
        // passa um filtro retirando o que esta sob parenteses
        // passa um filtro em todos simbolos () e afins, colocando um espaço no lugar
        // add os simbolos por meio do insereTabela
        // separa por palavras armazena (separando por espaço)
        // verifica cada palavra se é reservada, se sim escreve tabela, se não prox etapa
        // aqui lidar antes de passar pro AFD tratar exemplos como "parameter(pi = 3.14159)" que não serão separados por espaço nas etapas anteriores
        // o que sobrar vai para o AFD cuidar

        // inicio do filtro da linha
        for(int l = 0; l< linhas.size(); l++){
            String linha = linhas.get(l);

            // filtro remove comentário
            String regex = "!.*";
            linha = linha.replaceAll(regex, " ");

            // TODO: adicionar na tabela quando identificado um literal
            // filtro de conteudo entre parenteses aqui so esta removendo falta adicionar como literal
            regex = "\"([^\"]*)\"";
            linha = linha.replaceAll(regex, " ");
            LinhaTabela linhaTabela = new LinhaTabela(contId, String.valueOf(linha), "SIMBOLO", "-", "-", "-", "-", l);
//            insereTabela(linhaTabela, l);


            // filtro de simbolos
            // Aqui é verificado se os simbolos estão contidos na constante simbolo, se sim são adicionados na tabela
            for(int i = 0; i < linha.length(); i++){
                char letra = linha.charAt(i);
                if(simbolos.contains(String.valueOf(letra))){
                    linhaTabela = new LinhaTabela(contId, String.valueOf(letra), "SIMBOLO", "-", "-", "-", "-", i);
                    insereTabela(linhaTabela, i);
                    linha = linha.replaceFirst("\\" + letra, " ");
                }
            }
            // adiciona na lista de linhas
            linhas.set(l, linha);

            // ao final dos filtros é feito um split por espaço e as palavras são encaminhadas para o AFD
            List<String> palavras = Arrays.asList(linha.split(" ")); // aqui há um problema com separação por espaços não resolvido
            for(String palavraAtual: palavras) {
                executaAFD(palavraAtual, l); // chama o AFD
            }
        }
    }

    public static void executaAFD(String palavra, int nLinha) {
        // verifica se não foi mandado um espaço vazio
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
                    linhaTabela = new LinhaTabela(contId, palavra, "ID", "-", "-", "-", "-", i);
                    insereTabela(linhaTabela, nLinha);
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
                    linhaTabela = new LinhaTabela(contId, palavra, "NUM", "-", "-", "-", "-", i);
                    insereTabela(linhaTabela, nLinha);
                }

            } else if (!Character.isAlphabetic(palavra.charAt(i)) && !Character.isDigit(palavra.charAt(i))){  // Aqui para os casos onde  não é uma palavra inciado com (A-Z) e (0-9)
                linhaTabela = new LinhaTabela(contId, palavra, "ERRO", "-", "-", "-", "-", i);
                insereTabela(linhaTabela, nLinha);
            }
        }
    }

    public static void insereTabela(LinhaTabela linhaTabela, int numeroLinha){
        // incremental do id da tabela
        contId++;
        listaLinhaTabela.add(linhaTabela);
    }

//    public static void insereTabela(LinhaTabela palavra, int numeroLinha) { // passar linha por parametro
//        // Cria nova instancia de LinhaTabela incrementando o ID
//        contLexema++;
//        LinhaTabela linhaTabela= new LinhaTabela(contLexema);
//
////        // se for reservada
////        if(palavrasReservadas.contains(palavra)) {
////            linhaTabela.lexema = palavra;
////            linhaTabela.valorInicial = "-";
////            linhaTabela.escopo = "-";
////            linhaTabela.linhas = "-";
////            linhaTabela.colunas = "-";
////            linhaTabela.linha = 111; // definir um controle de linhas
////        }
//
//
//        //adiciona na estrutura de tabela principal
//        tabela.add(linhaTabela);
//
//    }

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
