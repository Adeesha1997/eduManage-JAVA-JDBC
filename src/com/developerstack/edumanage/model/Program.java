package com.developerstack.edumanage.model;

import java.util.Arrays;

public class Program {
    private String code;
    private String name;
    private String[] technology;
    private String teacherId;
    private double cost;

    public Program() {
    }

    public Program(String code, String name, String[] technology, String teacherId, double cost) {

        this.code = code;
        this.name = name;
        this.technology = technology;
        this.teacherId = teacherId;
        this.cost = cost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTechnology() {
        return technology;
    }

    public void setTechnology(String[] technology) {
        this.technology = technology;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Program{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", technology=" + Arrays.toString(technology) +
                ", teacherId='" + teacherId + '\'' +
                ", cost=" + cost +
                '}';
    }
}
