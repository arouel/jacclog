package net.sf.jacclog.persistence.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import net.sf.jacclog.api.domain.http.HttpRequestHeader;
import net.sf.jacclog.api.domain.http.ReadableHttpRequestHeader;

@Entity
@Table(name = "request_header_types", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class HttpRequestHeaderType {

	/**
	 * The primary key of the entity
	 */
	@Id
	@GeneratedValue(generator = "REQUEST_HEADER_TYPE_SEQ")
	@TableGenerator(name = "REQUEST_HEADER_TYPE_SEQ", allocationSize = 1)
	private Long id;

	@Column(name = "name", unique = true)
	private String name;

	HttpRequestHeaderType() {
	}

	public HttpRequestHeaderType(final ReadableHttpRequestHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}
		this.name = type.getName();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final HttpRequestHeaderType other = (HttpRequestHeaderType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ReadableHttpRequestHeader getType() {
		return HttpRequestHeader.evaluate(name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("HttpRequestHeaderType [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
