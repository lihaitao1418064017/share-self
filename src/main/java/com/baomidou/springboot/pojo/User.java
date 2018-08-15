package com.baomidou.springboot.pojo;

import java.io.Serializable;
import java.util.Set;


public class User implements Serializable {
    private String username;
    private Set<String> group;
    /**
     * 所在节点，只是为了方便后期的操作
     */
    private String node;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getGroup() {
        return group;
    }

    public void setGroup(Set<String> group) {
        this.group = group;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }
}
