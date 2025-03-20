/*package com.kulushev.app.security;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CachedBodyHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream cachedBody = new ByteArrayOutputStream();
    private final ServletOutputStream originalOutputStream;
    private final PrintWriter printWriter;

    public CachedBodyHttpServletResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        this.originalOutputStream = response.getOutputStream();
        this.printWriter = new PrintWriter(new OutputStreamWriter(cachedBody, StandardCharsets.UTF_8));
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                cachedBody.write(b);
                originalOutputStream.write(b);
            }

            @Override
            public void flush() throws IOException {
                cachedBody.flush();
                originalOutputStream.flush();
            }

            @Override
            public void close() throws IOException {
                cachedBody.close();
                originalOutputStream.close();
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {}
        };
    }

    @Override
    public PrintWriter getWriter() {
        return printWriter;
    }

    @Override
    public void flushBuffer() throws IOException {
        printWriter.flush();
        cachedBody.writeTo(originalOutputStream);
        super.flushBuffer();
    }

    public String getResponseBody() {
        printWriter.flush();
        return cachedBody.toString(StandardCharsets.UTF_8);
    }

    public void copyBodyToResponse() throws IOException {
        flushBuffer();
    }
}*/

