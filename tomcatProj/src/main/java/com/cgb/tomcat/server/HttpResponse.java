package com.cgb.tomcat.server;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author AceWong
 * @create 2024/9/17 15:29
 */
public class HttpResponse implements HttpServletResponse {
    /**
     * 输入输出相关
     */
    OutputStream output;
    private PrintWriter writer;
    HttpRequest request;

    /**
     * 中间变量
     */
    Map<String, String> headers = new ConcurrentHashMap<>();
    String message = getStatusMessage(HttpServletResponse.SC_OK);
    int status = HttpServletResponse.SC_OK;

    /**
     * 协议相关
     */
    String contentType = null;
    long contentLength = -1;
    String charset = null;
    String characterEncoding = null;
    String protocol = "HTTP/1.1";

    public HttpResponse(OutputStream output) {
        this.output = output;
    }

    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public boolean containsHeader(String s) {
        return false;
    }

    @Override
    public String encodeURL(String s) {
        return null;
    }

    @Override
    public String encodeRedirectURL(String s) {
        return null;
    }

    @Override
    public String encodeUrl(String s) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(String s) {
        return null;
    }

    @Override
    public void sendError(int i, String s) throws IOException {

    }

    @Override
    public void sendError(int i) throws IOException {

    }

    @Override
    public void sendRedirect(String s) throws IOException {

    }

    @Override
    public void setDateHeader(String s, long l) {

    }

    @Override
    public void addDateHeader(String s, long l) {

    }

    @Override
    public void setHeader(String name, String value) {
        headers.put(name, value);
        if (name.toLowerCase() == DefaultHeaders.CONTENT_LENGTH_NAME) {
            setContentLength(Integer.parseInt(value));
        }
        if (name.toLowerCase() == DefaultHeaders.CONTENT_TYPE_NAME) {
            setContentType(value);
        }
    }

    @Override
    public void addHeader(String name, String value) {
        headers.put(name, value);
        if (name.toLowerCase() == DefaultHeaders.CONTENT_LENGTH_NAME) {
            setContentLength(Integer.parseInt(value));
        }
        if (name.toLowerCase() == DefaultHeaders.CONTENT_TYPE_NAME) {
            setContentType(value);
        }
    }

    @Override
    public void setIntHeader(String s, int i) {

    }

    @Override
    public void addIntHeader(String s, int i) {

    }

    @Override
    public void setStatus(int i) {

    }

    @Override
    public void setStatus(int i, String s) {

    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public String getHeader(String s) {
        return null;
    }

    @Override
    public Collection<String> getHeaders(String s) {
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return "UTF-8";
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        writer = new PrintWriter(new OutputStreamWriter(output, getCharacterEncoding()), true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public OutputStream getOutput() {
        return output;
    }

    public void setOutput(OutputStream output) {
        this.output = output;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    protected String getStatusMessage(int status) {
        switch (status) {
            case SC_OK:
                return ("OK");
            case SC_ACCEPTED:
                return ("Accepted");
            case SC_BAD_GATEWAY:
                return ("Bad Gateway");
            case SC_BAD_REQUEST:
                return ("Bad Request");
            case SC_CONTINUE:
                return ("Continue");
            case SC_FORBIDDEN:
                return ("Forbidden");
            case SC_INTERNAL_SERVER_ERROR:
                return ("Internal Server Error");
            case SC_METHOD_NOT_ALLOWED:
                return ("Method Not Allowed");
            case SC_NOT_FOUND:
                return ("Not Found");
            case SC_NOT_IMPLEMENTED:
                return ("Not Implemented");
            case SC_REQUEST_URI_TOO_LONG:
                return ("Request URI Too Long");
            case SC_SERVICE_UNAVAILABLE:
                return ("Service Unavailable");
            case SC_UNAUTHORIZED:
                return ("Unauthorized");
            default:
                return ("HTTP Response Status " + status);
        }
    }

    private String getProtocol() {
        return this.protocol;
    }

    public void sendHeaders() throws IOException {
        PrintWriter outputWriter = getWriter();
        //下面这一端是输出状态行
        outputWriter.print(this.getProtocol());
        outputWriter.print(" ");
        outputWriter.print(status);
        if (message != null) {
            outputWriter.print(" ");
            outputWriter.print(message);
        }
        outputWriter.print("\r\n");

        if (getContentType() != null) {
            outputWriter.print("Content-Type: " + getContentType() + "\r\n");
        }
        if (getContentLength() >= 0) {
            outputWriter.print("Content-Length: " + getContentLength() + "\r\n");
        }

        //输出头信息
        for (String name : headers.keySet()) {
            String value = headers.get(name);
            outputWriter.print(name);
            outputWriter.print(": ");
            outputWriter.print(value);
            outputWriter.print("\r\n");
        }

        //最后输出空行
        outputWriter.print("\r\n");
        outputWriter.flush();
    }
}
