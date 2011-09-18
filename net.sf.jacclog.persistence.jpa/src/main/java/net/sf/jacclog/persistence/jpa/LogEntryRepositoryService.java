/*******************************************************************************
 * Copyright 2011 André Rouél
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.jacclog.persistence.jpa;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.sf.jacclog.persistence.jpa.internal.LogEntryMapper;
import net.sf.jacclog.persistence.jpa.internal.LogEntryRepository;

import org.joda.time.Interval;

public class LogEntryRepositoryService implements
		net.sf.jacclog.service.repository.LogEntryRepositoryService<net.sf.jacclog.service.repository.LogEntry> {

	private final LogEntryRepository repository;

	public LogEntryRepositoryService(final LogEntryRepository repository) {
		if (repository == null) {
			throw new IllegalArgumentException("Argument 'repository' can not be null.");
		}
		this.repository = repository;
	}

	@Override
	public long count(final Interval interval) {
		return repository.count(interval);
	}

	@Override
	public long countAll() {
		return repository.countAll();
	}

	@Override
	public void create(final Collection<net.sf.jacclog.service.repository.LogEntry> entries) {
		final List<LogEntry> list = new LinkedList<LogEntry>();
		for (final net.sf.jacclog.service.repository.LogEntry entry : entries) {
			list.add(LogEntryMapper.translateLogEntry(entry));
		}
		repository.persist(list);
	}

	@Override
	public void create(final net.sf.jacclog.service.repository.LogEntry entry) {
		repository.persist(LogEntryMapper.translateLogEntry(entry));
	}

	@Override
	public void delete(final net.sf.jacclog.service.repository.LogEntry entry) {
		repository.remove(LogEntryMapper.translateLogEntry(entry));
	}

	public LogEntryRepository getRepository() {
		return repository;
	}

	@Override
	public List<net.sf.jacclog.service.repository.LogEntry> read(final int startPosition, final int maxResults) {
		return LogEntryMapper.translateList(repository.find(startPosition, maxResults));
	}

	@Override
	public List<net.sf.jacclog.service.repository.LogEntry> read(final Interval interval) {
		return LogEntryMapper.translateList(repository.find(interval));
	}

	@Override
	public List<net.sf.jacclog.service.repository.LogEntry> read(final Interval interval, final int startPosition,
			final int maxResults) {
		return LogEntryMapper.translateList(repository.find(interval, startPosition, maxResults));
	}

	@Override
	public net.sf.jacclog.service.repository.LogEntry read(final Long id) {
		return repository.find(id);
	}

	@Override
	public List<net.sf.jacclog.service.repository.LogEntry> readAll() {
		return LogEntryMapper.translateList(repository.findAll());
	}

	@Override
	public net.sf.jacclog.service.repository.LogEntry update(final net.sf.jacclog.service.repository.LogEntry entry) {
		return repository.merge(LogEntryMapper.translateLogEntry(entry));
	}

}
