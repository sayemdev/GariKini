package com.app.garikini.Model;

public class ChooseSellOptionModel {
    String name;
    int resourceId;

    public ChooseSellOptionModel(String name, int resourceId) {
        this.name = name;
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "ChooseSellOptionModel{" +
                "name='" + name + '\'' +
                ", resourceId=" + resourceId +
                '}';
    }
}
