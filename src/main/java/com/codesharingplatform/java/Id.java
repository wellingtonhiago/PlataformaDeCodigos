package com.codesharingplatform.java;

public class Id {
    public static int id = 0;

    public Id (){
        id++;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Id.id = id;
    }
}
