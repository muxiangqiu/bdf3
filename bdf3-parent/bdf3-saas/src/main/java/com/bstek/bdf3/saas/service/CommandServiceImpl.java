package com.bstek.bdf3.saas.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.saas.command.Command;
import com.bstek.bdf3.saas.command.CommandNeedReturn;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月3日
 */
@Service
@Transactional(readOnly = true)
public class CommandServiceImpl implements CommandService {
	
	@Override
	public <T> T executeQueryCommand(CommandNeedReturn<T> command) {
		return command.execute();
	}
	
	@Override
	public void executeQueryCommand(Command command) {
		command.execute();
	}
	
	@Override
	@Transactional
	public <T> T executeNonQueryCommand(CommandNeedReturn<T> command) {
		return command.execute();
	}
	
	@Override
	@Transactional
	public void executeNonQueryCommand(Command command) {
		command.execute();
	}

}
