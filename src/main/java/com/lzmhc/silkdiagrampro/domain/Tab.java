package com.lzmhc.silkdiagrampro.domain;

public class Tab {
    private Integer id;
    private String name;
    private String icon;
    public Tab(){}
    public Tab(Integer id, String name,String icon) {
        this.id = id;
        this.name = name;
        this.icon=icon;
    }

    @Override
    public String toString() {
        return "Tab{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
