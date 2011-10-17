package net.sf.jacclog.service.repository.domain;

import net.sf.jacclog.api.domain.http.ReadWritableHttpResponseHeaderField;

/**
 * Defines an storable Hypertext Transfer Protocol (HTTP) response header field consisting of a type, value and version.
 * 
 * @author André Rouél
 */
public interface PersistableHttpResponseHeaderField extends ReadWritableHttpResponseHeaderField, Persistable {
}
