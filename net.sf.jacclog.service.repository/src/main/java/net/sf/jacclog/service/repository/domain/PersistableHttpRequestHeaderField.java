package net.sf.jacclog.service.repository.domain;

import net.sf.jacclog.api.domain.http.ReadWritableHttpRequestHeaderField;

/**
 * Defines an storable Hypertext Transfer Protocol (HTTP) request header field consisting of a type, value and version.
 * 
 * @author André Rouél
 */
public interface PersistableHttpRequestHeaderField extends ReadWritableHttpRequestHeaderField, Persistable {
}
