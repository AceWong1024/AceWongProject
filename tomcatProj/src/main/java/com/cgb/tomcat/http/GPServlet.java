package com.cgb.tomcat.http;

import com.cgb.tomcat.constant.StringConstant;

/**
 * @author AceWong
 */
public abstract class GPServlet {
    public void service(GPRequest request, GPResponse response) throws Exception {
        if (StringConstant.HTTP_TYPE_GET.equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    /**
     * @param request
     * @param response
     * @throws Exception
     */
    public abstract void doGet(GPRequest request, GPResponse response) throws Exception;

    /**
     * @param request
     * @param response
     * @throws Exception
     */
    public abstract void doPost(GPRequest request, GPResponse response) throws Exception;
}
