package net.sf.jacclog.persistence.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import net.sf.jacclog.api.domain.http.HttpRequestHeader;
import net.sf.jacclog.api.domain.http.ReadableHttpRequestHeader;
import net.sf.jacclog.service.repository.domain.PersistableHttpRequestHeaderField;

@Entity
@Table(name = "request_headers", uniqueConstraints = @UniqueConstraint(columnNames = { "type_id", "value" }))
public class HttpRequestHeaderField implements PersistableHttpRequestHeaderField {

	/**
	 * The primary key of the entity
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private HttpRequestHeaderType type;

	private String value;

	/**
	 * The version number of the entity
	 */
	@Version
	private Integer version;

	HttpRequestHeaderField() {
	}

	public HttpRequestHeaderField(final ReadableHttpRequestHeader type, final String value) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		if (value == null) {
			throw new IllegalArgumentException("Argument 'value' can not be null.");
		}

		this.type = new HttpRequestHeaderType(type);
		this.value = value;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final HttpRequestHeaderField other = (HttpRequestHeaderField) obj;
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

	public HttpRequestHeaderType getHttpRequestHeaderType() {
		return type;
	}

	public Long getId() {
		return id;
	}

	@Override
	public ReadableHttpRequestHeader getType() {
		return HttpRequestHeader.evaluate(type.getName());
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	public void setHttpRequestHeaderType(final HttpRequestHeaderType type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}
		this.type = type;
	}

	@Override
	public void setType(final ReadableHttpRequestHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}
		this.type = new HttpRequestHeaderType(type);
	}

	@Override
	public void setValue(final String value) {
		if (value == null) {
			throw new IllegalArgumentException("Argument 'value' can not be null.");
		}
		this.value = value;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("HttpRequestHeaderField [type=");
		builder.append(type);
		builder.append(", value=");
		builder.append(value);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

}
