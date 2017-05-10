package com.andreidadushko.tomography2017.dao.db.cache;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryCache.class);

	private volatile HashMap<Key, Object> globalMap = new HashMap<Key, Object>();

	private static long timeout;

	private String savePath;

	private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread th = new Thread(r);
			th.setDaemon(true);
			return th;
		}
	});

	public QueryCache(long time) throws Exception {
		if (time < 100) {
			throw new IllegalArgumentException(
					"Too short interval for storage in the cache. Interval should be more than 100 ms");
		}
		timeout = time;
		scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				long current = System.currentTimeMillis();
				for (Key k : globalMap.keySet()) {
					if (!k.isLive(current)) {
						globalMap.remove(k);
					}
				}
			}
		}, 1, timeout / 5, TimeUnit.MILLISECONDS);
	}

	public void setDefaultTimeout(long time) throws Exception {
		if (time < 100) {
			throw new IllegalArgumentException(
					"Too short interval for storage in the cache. Interval should be more than 100 ms");
		}
		timeout = time;
	}

	public void put(String key, Object data) {
		globalMap.put(new Key(key), data);
	}

	public Object get(String key) {
		return globalMap.get(new Key(key));
	}

	public void remove(String key) {
		globalMap.remove(new Key(key));
	}

	public void removeAll() {
		globalMap.clear();
	}

	public void saveCache() {
		try (FileOutputStream fos = new FileOutputStream(savePath);
				ObjectOutputStream outStream = new ObjectOutputStream(fos)) {
			outStream.writeObject(globalMap);
			outStream.flush();
		} catch (IOException ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public void loadCache() {
		try (FileInputStream fis = new FileInputStream(savePath);
				ObjectInputStream inStream = new ObjectInputStream(fis)) {
			globalMap = (HashMap<Key, Object>) inStream.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	private static class Key {

		private String key;
		private long timeLife;

		public Key(String key) {
			this.key = key;
			this.timeLife = System.currentTimeMillis() + timeout;
		}

		public boolean isLive(long currentTimeMillis) {
			return currentTimeMillis < timeLife;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}
	}
}
