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

import net.sf.jacclog.api.domain.http.HttpResponseHeader;
import net.sf.jacclog.api.domain.http.ReadableHttpResponseHeader;
import net.sf.jacclog.service.repository.domain.PersistableHttpResponseHeaderField;

@Entity
@Table(name = "response_headers", uniqueConstraints = @UniqueConstraint(columnNames = { "type_id", "value" }))
public class HttpResponseHeaderField implements PersistableHttpResponseHeaderField {

	/**
	 * The primary key of the entity
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private HttpResponseHeaderType type;

	private String value;

	/**
	 * The version number of the entity
	 */
	@Version
	private Integer version;

	HttpResponseHeaderField() {
	}

	public HttpResponseHeaderField(final ReadableHttpResponseHeader type, final String value) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}

		if (value == null) {
			throw new IllegalArgumentException("Argument 'value' can not be null.");
		}

		this.type = new HttpResponseHeaderType(type);
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
		final HttpResponseHeaderField other = (HttpResponseHeaderField) obj;
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

	public HttpResponseHeaderType getHttpResponseHeaderType() {
		return type;
	}

	public Long getId() {
		return id;
	}

	@Override
	public ReadableHttpResponseHeader getType() {
		return HttpResponseHeader.evaluate(type.getName());
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

	public void setHttpResponseHeaderType(final HttpResponseHeaderType type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}
		this.type = type;
	}

	@Override
	public void setType(final ReadableHttpResponseHeader type) {
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' can not be null.");
		}
		this.type = new HttpResponseHeaderType(type);
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
		builder.append("HttpResponseHeaderField [type=");
		builder.append(type);
		builder.append(", value=");
		builder.append(value);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

}
