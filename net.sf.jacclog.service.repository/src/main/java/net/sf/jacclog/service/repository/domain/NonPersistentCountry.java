package net.sf.jacclog.service.repository.domain;

public class NonPersistentCountry extends AbstractCountry implements UnsavedCountry {

	@Override
	public Long getId() {
		return Long.MIN_VALUE;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("NonPersistentCountry [\ngetId()=");
		builder.append(getId());
		builder.append(",\ngetBeginIpAddress()=");
		builder.append(getBeginIpAddress());
		builder.append(",\ngetBeginIpAddressAsNumber()=");
		builder.append(getBeginIpAddressAsNumber());
		builder.append(",\ngetCode()=");
		builder.append(getCode());
		builder.append(",\ngetEndIpAddress()=");
		builder.append(getEndIpAddress());
		builder.append(",\ngetEndIpAddressAsNumber()=");
		builder.append(getEndIpAddressAsNumber());
		builder.append(",\ngetName()=");
		builder.append(getName());
		builder.append(",\ngetClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}
