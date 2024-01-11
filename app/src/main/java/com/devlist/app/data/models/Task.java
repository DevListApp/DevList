package com.devlist.app.data.models;

import java.util.Date;

public class Task {
    // Atributos da classe representando os dados de uma tarefa
    String title; // Título da tarefa
    String note; // Nota ou descrição da tarefa
    Date dt_start_scheduled; // Data de início programada da tarefa
    Date dt_end_scheduled; // Data de término programada da tarefa
    Integer status; // Status da tarefa (pode ser um código ou enumeração)

    // Construtor da classe para inicializar os atributos ao criar uma instância
    public Task(String title, String note, Date dt_start_scheduled, Date dt_end_scheduled, Integer status) {
        this.title = title;
        this.note = note;
        this.dt_start_scheduled = dt_start_scheduled;
        this.dt_end_scheduled = dt_end_scheduled;
        this.status = status;
    }

    // Métodos getter e setter para acessar e modificar os atributos da tarefa

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDt_start_scheduled() {
        return dt_start_scheduled;
    }

    public void setDt_start_scheduled(Date dt_start_scheduled) {
        this.dt_start_scheduled = dt_start_scheduled;
    }

    public Date getDt_end_scheduled() {
        return dt_end_scheduled;
    }

    public void setDt_end_scheduled(Date dt_end_scheduled) {
        this.dt_end_scheduled = dt_end_scheduled;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
