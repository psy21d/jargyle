package com.github.jh3nd3rs0n.jargyle.server.socks5.userpassmethod.internal.userrepo.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jh3nd3rs0n.jargyle.server.internal.io.FileMonitor;
import com.github.jh3nd3rs0n.jargyle.server.internal.io.FileStatusListener;
import com.github.jh3nd3rs0n.jargyle.server.socks5.userpassmethod.User;
import com.github.jh3nd3rs0n.jargyle.server.socks5.userpassmethod.UserRepository;
import com.github.jh3nd3rs0n.jargyle.server.socks5.userpassmethod.UserRepositorySpec;
import com.github.jh3nd3rs0n.jargyle.server.socks5.userpassmethod.Users;
import com.github.jh3nd3rs0n.jargyle.server.socks5.userpassmethod.internal.userrepo.impl.users.file.bind.UsersFileConversionHelper;

public final class FileSourceUserRepository extends UserRepository {
	
	private static final class UsersFileStatusListener 
		implements FileStatusListener {

		private final Logger logger;
		private final FileSourceUserRepository userRepository;

		public UsersFileStatusListener(
				final FileSourceUserRepository repository) {
			this.logger = LoggerFactory.getLogger(
					UsersFileStatusListener.class);
			this.userRepository = repository;
		}
		
		@Override
		public void onFileCreated(final File file) {
			this.logger.info(String.format(
					"Created file: %s",
					file));
			this.updateUserRepositoryFrom(file);
		}
		
		@Override
		public void onFileDeleted(final File file) {
			this.logger.info(String.format(
					"Relying on in-memory copy of deleted file: %s",
					file));
		}

		@Override
		public void onFileModified(final File file) {
			this.logger.info(String.format(
					"Modified file: %s",
					file));
			this.updateUserRepositoryFrom(file);
		}
		
		private void updateUserRepositoryFrom(final File file) {
			try {
				this.userRepository.updateUsersFromFile();
				this.logger.info("In-memory copy is up to date");
			} catch (UncheckedIOException e) {
				this.logger.error( 
						String.format(
								"Error in reading file: %s", 
								file), 
						e);
			}
		}
		
	}

	public static FileSourceUserRepository newInstance(
			final UserRepositorySpec userRepositorySpec,
			final String initializationStr) {
		FileSourceUserRepository fileSourceUserRepository =
				new FileSourceUserRepository(
						userRepositorySpec, initializationStr);
		fileSourceUserRepository.startMonitoringFile();
		return fileSourceUserRepository;
	}
	
	private static Users readUsersFrom(final File file) {
		if (!file.exists()) {
			return Users.of();
		}
		Reader reader = null;
		Users users = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file));
			users = UsersFileConversionHelper.newUsersFrom(reader);
		} catch (FileNotFoundException e) {
			throw new UncheckedIOException(e);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (IllegalArgumentException e) {
			throw new UncheckedIOException(new IOException(e));
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}
		}
		return users;
	}
	
	private static void writeUsersTo(final File file, final Users users) {
		File tempFile = new File(file.toString().concat(".tmp"));
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(tempFile));
			UsersFileConversionHelper.toFile(users, writer);
		} catch (FileNotFoundException e) {
			throw new UncheckedIOException(e);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (IllegalArgumentException e) {
			throw new UncheckedIOException(new IOException(e));
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}
		}
		try {
			Files.move(
					tempFile.toPath(), 
					file.toPath(),
					StandardCopyOption.ATOMIC_MOVE,
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	private ExecutorService executor;
	private final File file;
	private final AtomicLong lastUpdated;
	private final ReentrantLock lock;	
	private volatile Users users;
	
	private FileSourceUserRepository(
			final UserRepositorySpec userRepositorySpec, 
			final String initializationStr) {
		super(userRepositorySpec, initializationStr);
		File file = new File(initializationStr);
		Users usrs = readUsersFrom(file);
		this.executor = null;
		this.file = file;		
		this.lastUpdated = new AtomicLong(System.currentTimeMillis());
		this.lock = new ReentrantLock();
		this.users = usrs;
	}
	
	@Override
	public User get(final String name) {
		User user = null;
		this.lock.lock();
		try {
			user = this.users.get(name);
		} finally {
			this.lock.unlock();
		}
		return user;
	}
	
	@Override
	public Users getAll() {
		Users usrs = null;
		this.lock.lock();
		try {
			usrs = Users.of(this.users);
		} finally {
			this.lock.unlock();
		}
		return usrs;
	}

	@Override
	public void put(final User user) {
		this.lock.lock();
		try {
			Users usrs = Users.of(this.users);
			usrs.put(user);
			this.updateFileFrom(usrs);
			this.updateUsersFrom(usrs);			
		} finally {
			this.lock.unlock();
		}
	}
	
	@Override
	public void putAll(final Users users) {
		this.lock.lock();
		try {
			Users usrs = Users.of(this.users);
			usrs.putAll(users);
			this.updateFileFrom(usrs);
			this.updateUsersFrom(usrs);			
		} finally {
			this.lock.unlock();
		}
	}

	@Override
	public void remove(final String name) {
		this.lock.lock();
		try {
			Users usrs = Users.of(this.users);
			usrs.remove(name);
			this.updateFileFrom(usrs);
			this.updateUsersFrom(usrs);			
		} finally {
			this.lock.unlock();
		}
	}

	private void startMonitoringFile() {
		this.executor = Executors.newSingleThreadExecutor();
		this.executor.execute(FileMonitor.newInstance(
				this.file, 
				new UsersFileStatusListener(this)));
	}
	
	private void updateFileFrom(final Users usrs) {
		writeUsersTo(this.file, usrs);
	}
	
	private void updateUsersFrom(final Users usrs) {
		this.users = usrs;
		this.lastUpdated.set(System.currentTimeMillis());
	}
	
	private void updateUsersFromFile() {
		this.lock.lock();
		try {
			if (this.file.exists() 
					&& this.file.lastModified() > this.lastUpdated.longValue()) {
				Users usrs = readUsersFrom(this.file);
				this.updateUsersFrom(usrs);
			}
		} finally {
			this.lock.unlock();
		}
	}

}
