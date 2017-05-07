package com.andreidadushko.tomography2017.webapp.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractRepoImpl<T> implements IAbstractRepo<T> {

	@Inject
	private RedisTemplate<String, T> redisTemplate;

	protected abstract String getKey();

	@Override
	public void save(String login, T object) {
		redisTemplate.opsForHash().put(getKey(), login, object);
		if (redisTemplate.getExpire(getKey()) < 0)
			redisTemplate.expire(getKey(), 1, TimeUnit.HOURS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T find(String login) {
		return (T) redisTemplate.opsForHash().get(getKey(), login);
	}

	@Override
	public Map<Object, Object> findAll() {
		return redisTemplate.opsForHash().entries(getKey());
	}

	@Override
	public void delete(String login) {
		redisTemplate.opsForHash().delete(getKey(), login);
	}

	@Override
	public void cleanCache() {
		redisTemplate.delete(getKey());
	}

	@Override
	public void saveCache(String path) {
		byte[] cache = redisTemplate.dump(getKey());
		File file = new File(path);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(cache, 0, cache.length);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void loadCache(String path) {
		try (FileInputStream fin = new FileInputStream(path)) {
			byte[] cache = new byte[fin.available()];
			fin.read(cache, 0, cache.length);
			redisTemplate.delete(getKey());
			redisTemplate.restore(getKey(), cache, 1, TimeUnit.HOURS);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
