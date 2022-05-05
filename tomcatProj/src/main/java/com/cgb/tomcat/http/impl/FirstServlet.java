package com.cgb.tomcat.http.impl;

import com.cgb.tomcat.http.GPRequest;
import com.cgb.tomcat.http.GPResponse;
import com.cgb.tomcat.http.GPServlet;

public class FirstServlet extends GPServlet {
    @Override
    public void doGet(GPRequest request, GPResponse response) throws Exception {
        this.doPost(request,response);
    }

    @Override
    public void doPost(GPRequest request, GPResponse response) throws Exception {
        response.write("TESTING");
    }
}
