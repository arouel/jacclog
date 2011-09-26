package net.sf.jacclog.persistence.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Country implements net.sf.jacclog.service.repository.domain.Country {

	private String beginIpAddress;

	private Long beginIpAddressAsNumber;

	private String code;

	private String endIpAddress;

	private Long endIpAddressAsNumber;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private String name;

	@Version
	@Column(name = "version")
	private Integer version;

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
		final Country other = (Country) obj;
		if (beginIpAddress == null) {
			if (other.beginIpAddress != null) {
				return false;
			}
		} else if (!beginIpAddress.equals(other.beginIpAddress)) {
			return false;
		}
		if (beginIpAddressAsNumber == null) {
			if (other.beginIpAddressAsNumber != null) {
				return false;
			}
		} else if (!beginIpAddressAsNumber.equals(other.beginIpAddressAsNumber)) {
			return false;
		}
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (endIpAddress == null) {
			if (other.endIpAddress != null) {
				return false;
			}
		} else if (!endIpAddress.equals(other.endIpAddress)) {
			return false;
		}
		if (endIpAddressAsNumber == null) {
			if (other.endIpAddressAsNumber != null) {
				return false;
			}
		} else if (!endIpAddressAsNumber.equals(other.endIpAddressAsNumber)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
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

	@Override
	public String getBeginIpAddress() {
		return beginIpAddress;
	}

	@Override
	public Long getBeginIpAddressAsNumber() {
		return beginIpAddressAsNumber;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getEndIpAddress() {
		return endIpAddress;
	}

	@Override
	public Long getEndIpAddressAsNumber() {
		return endIpAddressAsNumber;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public Integer getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beginIpAddress == null) ? 0 : beginIpAddress.hashCode());
		result = prime * result + ((beginIpAddressAsNumber == null) ? 0 : beginIpAddressAsNumber.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((endIpAddress == null) ? 0 : endIpAddress.hashCode());
		result = prime * result + ((endIpAddressAsNumber == null) ? 0 : endIpAddressAsNumber.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public void setBeginIpAddress(final String beginIpAddress) {
		this.beginIpAddress = beginIpAddress;
	}

	@Override
	public void setBeginIpAddressAsNumber(final Long beginIpAddressAsNumber) {
		this.beginIpAddressAsNumber = beginIpAddressAsNumber;
	}

	@Override
	public void setCode(final String code) {
		this.code = code;
	}

	@Override
	public void setEndIpAddress(final String endIpAddress) {
		this.endIpAddress = endIpAddress;
	}

	@Override
	public void setEndIpAddressAsNumber(final Long endIpAddressAsNumber) {
		this.endIpAddressAsNumber = endIpAddressAsNumber;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Country [beginIpAddress=");
		builder.append(beginIpAddress);
		builder.append(", beginIpAddressAsNumber=");
		builder.append(beginIpAddressAsNumber);
		builder.append(", code=");
		builder.append(code);
		builder.append(", endIpAddress=");
		builder.append(endIpAddress);
		builder.append(", endIpAddressAsNumber=");
		builder.append(endIpAddressAsNumber);
		builder.append(", id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

}
