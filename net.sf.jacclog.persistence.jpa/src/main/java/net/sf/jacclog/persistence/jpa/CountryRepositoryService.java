package net.sf.jacclog.persistence.jpa;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import net.sf.jacclog.persistence.jpa.entity.Country;
import net.sf.jacclog.persistence.jpa.internal.CountryMapper;
import net.sf.jacclog.persistence.jpa.internal.CountryRepository;

public class CountryRepositoryService implements
		net.sf.jacclog.service.repository.CountryRepositoryService<net.sf.jacclog.service.repository.domain.Country> {

	private final CountryRepository repository;

	public CountryRepositoryService(final CountryRepository repository) {
		if (repository == null) {
			throw new IllegalArgumentException("Argument 'repository' can not be null.");
		}

		this.repository = repository;
	}

	@Override
	public long countAll() {
		return repository.countAll();
	}

	@Override
	public void create(final List<net.sf.jacclog.service.repository.domain.Country> countries) {
		final List<Country> list = new ArrayList<Country>();
		for (final net.sf.jacclog.service.repository.domain.Country country : countries) {
			list.add(CountryMapper.map(country));
		}
		repository.persist(list);
	}

	@Override
	public void create(final net.sf.jacclog.service.repository.domain.Country country) {
		repository.persist(CountryMapper.map(country));
	}

	@Override
	public void delete(final net.sf.jacclog.service.repository.domain.Country country) {
		repository.remove(CountryMapper.map(country));
	}

	@Override
	public net.sf.jacclog.service.repository.domain.Country find(final Inet4Address ipAddress) {
		return repository.find(ipAddress);
	}

	@Override
	public List<net.sf.jacclog.service.repository.domain.Country> find(final int startPosition, final int maxResults) {
		return CountryMapper.map(repository.find(startPosition, maxResults));
	}

	public CountryRepository getRepository() {
		return repository;
	}

	@Override
	public net.sf.jacclog.service.repository.domain.Country read(final Long id) {
		return repository.find(id);
	}

	@Override
	public List<net.sf.jacclog.service.repository.domain.Country> readAll() {
		return CountryMapper.map(repository.findAll());
	}

	@Override
	public net.sf.jacclog.service.repository.domain.Country update(
			final net.sf.jacclog.service.repository.domain.Country country) {
		return repository.merge(CountryMapper.map(country));
	}

}
