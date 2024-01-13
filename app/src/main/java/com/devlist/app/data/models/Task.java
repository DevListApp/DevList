package com.devlist.app.data.models;

import java.util.Date;

public class Task {
    private String titulo;
    private String notas;
    private Date horaInicio;
    private Date horaFim;
    private int prioridade;

    public Task(String titulo, String notas, Date horaInicio, Date horaFim, int prioridade) {
        this.titulo = titulo;
        this.notas = notas;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.prioridade = prioridade;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }
}
