package com.job;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import com.util.DataUtil;
import com.util.SolrConstants;

public class SolrBuildIndex {

	public static Logger log = Logger.getLogger(SolrBuildIndex.class.getName());

	public boolean solrBuildIndex() throws MalformedURLException, SolrServerException,IOException{

		
		 HttpSolrServer server = new HttpSolrServer("http://192.168.22.148:8080/solr/collection1");
		 // 清空之前建立的索引數據 // delete all doc
	     server.deleteByQuery( "*:*" );
		
		 //提升性能
		 server.setRequestWriter(new BinaryRequestWriter());
		 
		 String datFilePath =  "C:/Users/1409035/Desktop/FTP_server_backup/candelete/";
		 //String datFilePath = PropertyLoader.getInstance().getValue(DAT_FILE_PATH_KEY);
		 Map zinioMap= (HashMap) DataUtil.DeSerialization(datFilePath + "Zinio" + "-" + "getItemListByCategoryCode" + "-using" + ".dat");
		 Map ylibMap =(HashMap) DataUtil.DeSerialization(datFilePath + "Ylib" + "-" + "getItemListByCategoryCode" + "-using" + ".dat");
		 Map ibobarMap =(HashMap) DataUtil.DeSerialization(datFilePath + "Ibobar" + "-" + "getItemListByCategoryCode" + "-using" + ".dat");
		 Map linkingMap =(HashMap) DataUtil.DeSerialization(datFilePath + "Linking" + "-" + "getItemListByCategoryCode" + "-using" + ".dat");
		 
	     Collection<SolrInputDocument> docs2 = new ArrayList<SolrInputDocument>();
	     
	     int k=0;
	     Iterator linkingIter = linkingMap.entrySet().iterator();
		 while (linkingIter.hasNext()) {
			Map.Entry entry = (Map.Entry) linkingIter.next();
			ArrayList<HashMap<String, Object>> bookAL = (ArrayList<HashMap<String, Object>>) entry
					.getValue();
			for (HashMap hm : bookAL) {
				k++;

				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", k);
				doc.addField("channel", "Linking");
				doc.addField("name", hm.get("name"));
				doc.addField("description", hm.get("description"));

				docs2.add(doc);
			}

		 }

	     Iterator zinioIter = zinioMap.entrySet().iterator();
	     
	     
	     while(zinioIter.hasNext()) { 
	    	 Map.Entry entry = (Map.Entry) zinioIter.next(); 
	    	 //下述不能用ALHM 去接，否則會拋出ArrayList can't be cast to ALHM的錯誤
	    	 ArrayList<HashMap<String,Object>> bookAL=(ArrayList<HashMap<String,Object>>)entry.getValue();
	    	 for(HashMap hm:bookAL){
	    		 k++;
	    		 System.out.println(entry.getKey()+" "+hm.get("Title"));
	    		 
	    		 SolrInputDocument doc = new SolrInputDocument();
				 doc.addField("id", k);
				 doc.addField("channel", "Zinio");
				 doc.addField("name", hm.get("name"));
				 doc.addField("description", hm.get("description"));
				 
				 
				 docs2.add(doc);
	    	 }
	     }
	     
	     Iterator ylibIter = ylibMap.entrySet().iterator(); 
	     while(ylibIter.hasNext()) { 
	    	 Map.Entry entry = (Map.Entry) ylibIter.next(); 
	    	 ArrayList<HashMap<String,Object>> bookAL=(ArrayList<HashMap<String,Object>>)entry.getValue();
	    	 
	    	 for(HashMap hm:bookAL){
	    		 k++;
	    		 
	    		 SolrInputDocument doc = new SolrInputDocument();
				 doc.addField("id", k);
				 doc.addField("channel", "Ylib");
				 doc.addField("name", hm.get("name"));
				 doc.addField("description", hm.get("description"));
				 
				 docs2.add(doc);
	    	 }
	     }
	     
	     Iterator ibobarIter = ibobarMap.entrySet().iterator(); 
	     while(ibobarIter.hasNext()) { 
	    	 Map.Entry entry = (Map.Entry) ibobarIter.next(); 
	    	 ArrayList<HashMap<String,Object>> bookAL=(ArrayList<HashMap<String,Object>>)entry.getValue();
	    	 //System.out.println(entry.getKey()+" size="+bookAL.size());
	    	 
	    	 for(HashMap hm:bookAL){
	    		 k++;
	    		System.out.println(entry.getKey()+" "+hm.get("Title"));
	    		 
	    		 SolrInputDocument doc = new SolrInputDocument();
				 doc.addField("id", k);
				 doc.addField("channel", "Ibobar");
				 doc.addField("name", hm.get("name"));
				 doc.addField("description", hm.get("description"));
				 
				 docs2.add(doc);
	    	 }
	     }
		 
		 //將ArrayList轉為XML格式
		 //String resultList=GeneralXmlPullParser.reverse(contentAL);
	     System.out.println("=======");
	     System.out.println(docs2.toString());
	     server.add(docs2);
		
		 server.commit();
		 server.optimize(true, true);
		 
		 System.out.println("finish");
		
		
		
		return true;
	}

}
