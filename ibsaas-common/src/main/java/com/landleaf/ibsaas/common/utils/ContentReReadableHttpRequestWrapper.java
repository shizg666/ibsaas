package com.landleaf.ibsaas.common.utils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
* @Title: ContentReReadableHttpRequestWrapper 
* @Description: 请求体可多次读取的HttpRequest请求包装类
*/
public class ContentReReadableHttpRequestWrapper extends HttpServletRequestWrapper {

    private ServletInputStream inputStream;

    /**
     * Create a new ContentReReadableHttpRequestWrapper for the given servlet request.
     * @param request the original servlet request
     */
    public ContentReReadableHttpRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 对inputStream进行包装
     * @return
     * @throws IOException
     * @author 陈宇霖
     * @date 2017年08月23日08:42:22
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (this.inputStream == null) {
            this.inputStream = new ContentReReadableInputStream(getRequest().getInputStream());
        }
        return this.inputStream;
    }

    /**
     * 将读取过的内容重新写入到流内
     * @param content
     * @throws IOException
     * @author 陈宇霖
     */
    public void reWriteContentIntoInputStream(byte[] content) throws IOException {
        ((ContentReReadableInputStream)getInputStream()).setIs(new ByteArrayInputStream(content));
    }

    /**
     * 可以重复设置InputStream流的输入流实现
     */
    private class ContentReReadableInputStream extends ServletInputStream {

        private InputStream is;

        public ContentReReadableInputStream(InputStream is) {
            this.is = is;
        }

        public void setIs(InputStream is) {
            this.is = is;
        }

        @Override
        public int read() throws IOException {
            return is.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }
    }

}
