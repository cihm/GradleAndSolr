package com.handler;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.job.SolrBuildIndex;
import com.job.SolrSearch;
import com.util.SolrConstants;
import com.vo.SolrArgVo;

public class SolrFlowHandler {

	public static Logger log = Logger.getLogger(SolrFlowHandler.class.getName());
	
	public static void main(String[] args) {
		
		for (int i = 0; i < args.length; i++) {
			log.info("args:" + i + " is : " + args[i] + " type   is "
					+ args[i].getClass() + "  length is " + args[i].length());
		}
		
		if (null != args && args.length ==0 ) {
			log.info("arg error");
			return;
		} else {
			
			SolrArgVo solrArgVo;
			String[] argArr = args[0].split(":");
			if(argArr.length>1){
			   solrArgVo = new SolrArgVo(argArr[0], argArr[1], argArr[2]);
			}else{
			   solrArgVo = new SolrArgVo(argArr[0], "", "");
			}

			SolrBuildIndex solrBuildIndex = new SolrBuildIndex();
			SolrSearch solrSearch = new SolrSearch();
			
			switch (solrArgVo.getCondition()) {
            case SolrConstants.BUILD : 
            	
				try {
					SolrInitMap.getInstance().initAllBookMap();
					solrBuildIndex.solrBuildIndex();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            	break;
            case SolrConstants.SEARCH: 
            
            	try {
            		HashMap allMap = SolrInitMap.getInstance().getAllBookMap();
					solrSearch.solrSearch(solrArgVo,allMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	break;
            default: 
            	log.info("args is wrong");
                break;
			}

		}
		
		log.info("done");
	}
}
