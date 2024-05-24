package service;

import java.io.Serializable;

public class Question implements Serializable {
    private String q,a,b,c,good;

    public Question(String q, String a, String b, String c, String good) {
        this.q = q;
        this.a = a;
        this.b = b;
        this.c = c;
        this.good = good;
    }

    public String getQ() {
        return q;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getGood() {
        return good;
    }
}
