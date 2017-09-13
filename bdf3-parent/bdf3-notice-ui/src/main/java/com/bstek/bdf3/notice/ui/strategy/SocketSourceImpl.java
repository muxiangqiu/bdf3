package com.bstek.bdf3.notice.ui.strategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.notice.strategy.CategoryProvider;
import com.bstek.bdf3.notice.strategy.SocketSource;
import com.bstek.dorado.view.socket.Socket;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
@Component
public class SocketSourceImpl implements SocketSource<Socket> {
	
	private Map<String, Map<String, Socket>> socketMap = new ConcurrentHashMap<String, Map<String,Socket>>();
	private Map<String, String> indexMap = new ConcurrentHashMap<>();
	
	@Autowired
	private CategoryProvider categoryProvider;

	@Override
	public void register(String key, Socket socket) {
		String category = categoryProvider.provide();
		Map<String, Socket> map = socketMap.get(category);
		if (map == null) {
			map = new ConcurrentHashMap<>();
			socketMap.put(category, map);
		}
		if (map.containsKey(key)) {
			indexMap.remove(map.get(key).getId());
		}
		map.put(key, socket);
		indexMap.put(socket.getId(), key);
	}

	@Override
	public void remove(Socket socket) {
		String key = indexMap.get(socket.getId());
		if (key != null) {
			String category = categoryProvider.provide();
			Map<String, Socket> map = socketMap.get(category);
			if (map != null) {
				map.remove(key);
				indexMap.remove(socket.getId());
				if (map.isEmpty()) {
					socketMap.remove(category);
				}
			}
		}
	}

	@Override
	public Socket getSocket(String key) {
		String category = categoryProvider.provide();
		Map<String, Socket> map = socketMap.get(category);
		if (map != null) {
			return map.get(key);
		}
		return null;
	}

	@Override
	public Map<String, Socket> getCurrentSockets() {
		String category = categoryProvider.provide();
		return socketMap.get(category);
	}

	@Override
	public Map<String, Socket> getSockets(String category) {
		return socketMap.get(category);
	}

}
