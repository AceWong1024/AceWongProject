package com.cgb.tomcat.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author AceWong
 */
public class GPTomcat {
    private int port = 8880;
    private ServerSocket serverSocket;
    private Map<String, GPServlet> servletMap = new HashMap<>();
    private Properties webxml = new Properties();

    public static void main(String[] args) {
        new GPTomcat().start();
    }

    private void init() {
        String WEB_INF = this.getClass().getResource("/").getPath();
        try {
            FileInputStream fileInputStream = new FileInputStream(WEB_INF + "web.properties");

            webxml.load(fileInputStream);

            for (Object k : webxml.keySet()) {
                String key = k.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");
                    GPServlet gpServlet = (GPServlet) Class.forName(className).newInstance();
                    servletMap.put(url, gpServlet);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        init();

        try {
            this.serverSocket = new ServerSocket(this.port);

            while (true){
                Socket client =  serverSocket.accept();
                process(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(Socket client) throws Exception {
        InputStream is = client.getInputStream();
        OutputStream os = client.getOutputStream();

        GPRequest request = new GPRequest(is);
        GPResponse response = new GPResponse(os);

        String url  = request.getUrl();

        if(servletMap.containsKey(url)){
            servletMap.get(url).service(request,response);
        }else{
            response.write("404 - NOT FOUND");
        }

        os.flush();
        os.close();

        is.close();
        client.close();
    }
}
