package com.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SolrHandler {
 
    static Log logger = LogFactory.getLog(SolrHandler.class);
 
    public static void main(String[] args) {
		String[] arg = {"search:王道:Ylib"};
		//String[] arg = {"build"};
		//String[] arg ={"h"};
		(new SolrFlowHandler()).main(arg);
	}
 
}