package com.andreidadushko.tomography2017.dao.db.cache;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class QueryCache {
	  private volatile HashMap<Key, Object> globalMap = new HashMap<Key, Object>();
	    private long default_timeout;
	    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
	        @Override
	            public Thread newThread(Runnable r) {
	                Thread th = new Thread(r);
	                th.setDaemon(true);
	                return th;
	            }
	        });

	    /**
	     * @param default_timeout  количество милисекунд - время которое обьект будет кранится в кеше.
	     */
	    public QueryCache(long default_timeout) throws Exception {
	        if (default_timeout < 10) {
	            throw new Exception("Too short interval for storage in the cache. Interval should be more than 10 ms");
	        }
	        this.default_timeout = default_timeout;
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
	        }, 1, default_timeout/5, TimeUnit.MILLISECONDS);
	    }

	    /**
	     * @param default_timeout количество милисекунд - время которое обьект будет кранится в кеше
	     */
	    public void setDefault_timeout(long default_timeout) throws Exception {
	        if (default_timeout < 100) {
	            throw new Exception("Too short interval for storage in the cache. Interval should be more than 10 ms");
	        }
	        this.default_timeout = default_timeout;
	    }

	    /**
	     * Метод для вставки обьекта в кеш
	     * Время зранения берётся по умолчанию
	     * @param <K>
	     * @param <V>
	     * @param key ключ в кеше
	     * @param data данные
	     */
	    public void put(String key,Object  data) {
	        globalMap.put(new Key(key, default_timeout), data);
	    }

	    /**
	     * Метод для вставки обьекта в кеш
	     * @param <K>
	     * @param <V>
	     * @param key ключ в кеше
	     * @param data данные
	     * @param timeout время хранения обьекта в кеше в милисекундах
	     */
	    public void put(String key,Object  data, long timeout) {
	        globalMap.put(new Key(key, timeout), data);
	    }

	    /**
	     * получение значения по ключу
	     * @param <K>
	     * @param <V>
	     * @param key ключ для поиска с кеша
	     * @return Обьект данных храняшийся в кеше
	     */
	    public Object get(String key) {
	        return globalMap.get(new Key(key));
	    }

	    /**
	     * удаляет все значения по ключу из кеша
	     * @param <K>
	     * @param key - ключ
	     */
	    public void remove(String key) {
	        globalMap.remove(new Key(key));
	    }

	    /**
	     * Удаляет все значения из кеша
	     */
	    public void removeAll() {
	        globalMap.clear();
	    }

	    /**
	     * Полностью заменяет весь существующий кеш.
	     * Время хранения по умолчанию.
	     * @param <K>
	     * @param <V>
	     * @param map Карта с данными
	     */
	    public void setAll(Map<K, V> map) {
	        ConcurrentHashMap tempmap = new ConcurrentHashMap<Key, V>();
	        for (Entry<K, V> entry : map.entrySet()) {
	            tempmap.put(new Key(entry.getKey(), default_timeout), entry.getValue());
	        }
	        globalMap = tempmap;
	    }

	    /**
	     * Добавляет к сущесвуещему кешу переданую карту
	     * Время хранения по умолчанию.
	     * @param <K>
	     * @param <V>
	     * @param map Карта с данными
	     */
	    public void addAll(Map<K, V> map) {
	        for (Entry<K, V> entry : map.entrySet()) {
	            put(entry.getKey(), entry.getValue());
	        }
	    }

	    private static class Key {

	        private final String key;
	        private final long timelife;

	        public Key(String key, long timeout) {
	            this.key = key;
	            this.timelife = System.currentTimeMillis() + timeout;
	        }

	        public Key(String key) {
	            this.key = key;
	        }

	        public String getKey() {
	            return key;
	        }

	        public boolean isLive(long currentTimeMillis) {
	            return currentTimeMillis < timelife;
	        }

	        @Override
	        public String toString() {
	            return "Key{" + "key=" + key + '}';
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
