package com.okay.testcenter.domain;

import javax.validation.constraints.NotNull;

public class Env {

    @NotNull
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private String port;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }






}
