package com.dfocus;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

/**
 * Author: qfwang
 * Date: 2017-11-02 下午3:21
 */
public class A extends B {
    public int a = 100;

    public A() {
        super();
        System.out.println(a);
        a = 200;
    }

    public static void main(String[] args) {
        System.out.println(new A().a);
    }
}

class B {
    public int num=120;
    public B() {
        System.out.println(num);
        System.out.println(((A) this).a);
    }
}
