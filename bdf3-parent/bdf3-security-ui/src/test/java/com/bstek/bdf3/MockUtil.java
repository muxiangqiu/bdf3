package com.bstek.bdf3;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.UnsupportedEncodingException;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月4日
 */
public class MockUtil {
	
	 public static ResultActions mock(MockMvc mvc, String uri, String json)
		      throws UnsupportedEncodingException, Exception {
		    return mvc
		        .perform(
		            post(uri).characterEncoding("UTF-8")
		                .contentType(MediaType.APPLICATION_JSON)
		                .content(json.getBytes()));
	}
	
}
