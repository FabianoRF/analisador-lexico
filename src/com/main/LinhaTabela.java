package com.main;

public class LinhaTabela {
    public int id;
    public String lexema;
    public String token;
    public String valorInicial;
    public String escopo;
    public String linhas;
    public String colunas;
    public int linha;

    public LinhaTabela(int id) {
        this.id = id;
    }

    public LinhaTabela(int id, String lexema, String token, String valorInicial, String escopo, String linhas, String colunas, int linha) {
        this.id = id;
        this.lexema = lexema;
        this.token = token;
        this.valorInicial = valorInicial;
        this.escopo = escopo;
        this.linhas = linhas;
        this.colunas = colunas;
        this.linha = linha;
    }
}
