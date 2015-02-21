package com.nhscoding.safe2tell.API;

/**
 * Created by david_000 on 2/17/2015.
 */
public class LogoObject {

    int ID = -1;
    int type = -1;
    String Resource = "";

    public LogoObject(int id, int _type, String resource) {
        ID = id;
        type = _type;
        Resource = resource;
    }
}
