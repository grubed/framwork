package com.zongs365.util.resource;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;



/**
 * 资源工具类
 *
 */
@Slf4j
public class ResourceUtil {


    public static URL getResource(String path) {
        return getResource(ResourceUtil.class, path);
    }

    public static URL getResource(Class<?> cls, String path) {
        if (cls == null) {
            cls = ResourceUtil.class;
        }
        ClassLoader loader = cls.getClassLoader();
        URL url = loader.getResource(path);
        if (url == null) {
            loader = Thread.currentThread().getContextClassLoader();
            url = loader.getResource(path);
        }
        if (url == null) {
            url = cls.getResource(path);
        }
        return url;
    }

    public static URL[] getResources(String path) {
        return getResources(ResourceUtil.class, path);
    }

    public static URL[] getResources(Class<?> cls, String path) {
        if (cls == null) {
            cls = ResourceUtil.class;
        }
        Set<URL> urlSet = new HashSet<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> enumeration = loader.getResources(path);
            if (enumeration != null) {
                while (enumeration.hasMoreElements()) {
                    urlSet.add(enumeration.nextElement());
                }
            }
        } catch (IOException e) {

            log.warn("fail to load resources from " + path + ",using loader " + loader, e);
        }
        if (cls != null) {
            ClassLoader loader2 = ResourceUtil.class.getClassLoader();
            if (loader2 != loader) {
                try {
                    Enumeration<URL> enumeration = loader2.getResources(path);
                    if (enumeration != null) {
                        while (enumeration.hasMoreElements()) {
                            urlSet.add(enumeration.nextElement());
                        }
                    }
                } catch (IOException e) {
                    log.warn("fail to load resources from " + path + ",using loader " + loader, e);
                }
            }
        }
        return urlSet.toArray(new URL[0]);
    }

    public static InputStream getResourceAsStream(String path) {
        return getResourceAsStream(ResourceUtil.class, path);
    }

    /**
     * 获取classpath下的资源
     *
     * @param cls  class类
     * @param path 资源文件路径
     * @return 资源路径的输入流,若不存在,则返回null
     */
    @SuppressWarnings("rawtypes")
    public static InputStream getResourceAsStream(Class cls, String path) {
        URL url = getResource(cls, path);
        if (url == null) {
            return null;
        }
        if (log.isInfoEnabled()) {
            log.info("found com.zongs365.util.resource of " + path + ":" + url);
        }
        try {
            return url.openStream();
        } catch (IOException e) {
            log.warn("fail to open " + url, e);
            return null;
        }
    }

    public static byte[] readStream(InputStream is) throws IOException {
        int bufSize = 1024;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bufSize);
        byte[] buf = new byte[bufSize];
        int c = 0;
        while ((c = is.read(buf)) > 0) {
            bos.write(buf, 0, c);
        }
        return bos.toByteArray();
    }
}

