package com.bstek.bdf3.swfviewer.handler;

import java.io.File;
import java.util.Map;

public interface ISwfFileHandler {

	public String getHandlerName();

	public String getHandlerDesc();

	public File execute(Map<String, Object> parameter) throws Exception;
}
