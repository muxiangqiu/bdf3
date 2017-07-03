package com.bstek.bdf3.saas.service;

import com.bstek.bdf3.saas.command.Command;
import com.bstek.bdf3.saas.command.CommandNeedReturn;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月3日
 */
public interface CommandService {

	<T> T executeQueryCommand(CommandNeedReturn<T> command);

	void executeQueryCommand(Command command);

	<T> T executeNonQueryCommand(CommandNeedReturn<T> command);

	void executeNonQueryCommand(Command command);


}
