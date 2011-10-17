package net.sf.jacclog.service.repository.domain;

/**
 * Defines a storable object that must have a version.
 * 
 * @author André Rouél
 */
public interface Persistable {

	/**
	 * Gets the version number of the entity.<br>
	 * <br>
	 * The version is used to ensure integrity when performing the merge operation and for optimistic concurrency
	 * control.
	 * 
	 * @return the version number
	 */
	Integer getVersion();

}
