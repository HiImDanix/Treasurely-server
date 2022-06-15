package com.danielandrej.treasure_hunt.task;

import javax.persistence.*;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column

    private String qrCodeValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task(String qrCodeValue) {
        this.qrCodeValue = qrCodeValue;
    }

    public Task() {
    }

    public String getQrCodeValue() {
        return qrCodeValue;
    }

    public Task setQrCodeValue(String qrCodeValue) {
        this.qrCodeValue = qrCodeValue;
        return this;
    }
}