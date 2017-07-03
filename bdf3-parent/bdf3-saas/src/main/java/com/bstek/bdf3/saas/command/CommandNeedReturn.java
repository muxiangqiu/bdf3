package com.bstek.bdf3.saas.command;
/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月3日
 */
@FunctionalInterface
public interface CommandNeedReturn<T> {
	T execute();
}
