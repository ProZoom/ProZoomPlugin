package com.zoom.thirdlevelmenu;

import java.util.ArrayList;
import java.util.List;



public class Node {

    private   int pId; // 父母id
    private   String name; // 我的名字 （可以做成自定对象、或者图片）
    private   int id; // 本节点id
    private int level; // 菜单级别

    private Node parent; // 父节点
    private List<Node> children = new ArrayList<>();

    public Node(int id, int pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
    }


    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /* 获取本菜单的级别*/
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel()+1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
}
