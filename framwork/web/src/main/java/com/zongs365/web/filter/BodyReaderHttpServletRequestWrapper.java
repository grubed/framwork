package com.zongs365.web.filter;

import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
@Slf4j
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] bytes;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        // 读取输入流里的请求参数，并保存到bytes里
        bytes = IOUtils.toByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new BufferedServletInputStream(this.bytes);
    }

    class BufferedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream inputStream;
        public BufferedServletInputStream(byte[] buffer) {
            //此处即赋能，可以详细查看ByteArrayInputStream的该构造函数；
            this.inputStream = new ByteArrayInputStream( buffer );
        }
        @Override
        public int available() throws IOException {
            return inputStream.available();
        }
        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return inputStream.read( b, off, len );
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
        public void setReadListener(ReadListener readListener) {

        }
    }


}
