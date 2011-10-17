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
import java.util.List;

import net.sf.jacclog.api.domain.ReadonlyLogEntry;
import net.sf.jacclog.persistence.jpa.entity.LogEntry;
import net.sf.jacclog.persistence.jpa.internal.LogEntryMapper;
import net.sf.jacclog.persistence.jpa.internal.LogEntryRepository;

import org.joda.time.Interval;

public class LogEntryRepositoryService implements net.sf.jacclog.service.repository.LogEntryRepositoryService<LogEntry> {

	private final LogEntryRepository repository;

	public LogEntryRepositoryService(final LogEntryRepository repository) {
		if (repository == null) {
			throw new IllegalArgumentException("Argument 'repository' can not be null.");
		}
		this.repository = repository;
	}

	@Override
	public long count(final Interval interval) {
		if (interval == null) {
			throw new IllegalArgumentException("Argument 'interval' can not be null.");
		}
		return repository.count(interval);
	}

	@Override
	public long countAll() {
		return repository.countAll();
	}

	@Override
	public void create(final Collection<LogEntry> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("Argument 'entries' can not be null.");
		}
		repository.persist(entries);
	}

	@Override
	public void create(final LogEntry entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' can not be null.");
		}
		repository.persist(entry);
	}

	@Override
	public void create(final ReadonlyLogEntry entry) {
		create(LogEntryMapper.map(entry));
	}

	@Override
	public void create(final ReadonlyLogEntry[] entries) {
		create(LogEntryMapper.map(entries));
	}

	@Override
	public void delete(final LogEntry entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' can not be null.");
		}
		repository.remove(entry);
	}

	@Override
	public List<LogEntry> find(final int startPosition, final int maxResults) {
		if (startPosition < 0) {
			throw new IllegalArgumentException("Argument 'startPosition' can not be smaller than zero.");
		}
		if (maxResults < 1) {
			throw new IllegalArgumentException("Argument 'maxResults' can not be smaller than zero.");
		}
		return repository.find(startPosition, maxResults);
	}

	@Override
	public List<LogEntry> find(final Interval interval) {
		if (interval == null) {
			throw new IllegalArgumentException("Argument 'interval' can not be null.");
		}
		return repository.find(interval);
	}

	@Override
	public List<LogEntry> find(final Interval interval, final int startPosition, final int maxResults) {
		if (interval == null) {
			throw new IllegalArgumentException("Argument 'interval' can not be null.");
		}
		if (startPosition < 0) {
			throw new IllegalArgumentException("Argument 'startPosition' can not be smaller than zero.");
		}
		if (maxResults < 1) {
			throw new IllegalArgumentException("Argument 'maxResults' can not be smaller than zero.");
		}
		return repository.find(interval, startPosition, maxResults);
	}

	@Override
	public LogEntry find(final Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Argument 'id' can not be null.");
		}
		return repository.find(id);
	}

	public LogEntryRepository getRepository() {
		return repository;
	}

	@Override
	public List<LogEntry> readAll() {
		return repository.findAll();
	}

	@Override
	public LogEntry update(final LogEntry entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Argument 'entry' can not be null.");
		}
		return repository.merge(entry);
	}

}
