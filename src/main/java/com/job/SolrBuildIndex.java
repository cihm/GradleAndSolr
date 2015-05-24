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

		// int k = 0;

		Iterator linkingIter = linkingMap.entrySet().iterator();
		Iterator zinioIter = zinioMap.entrySet().iterator();
		Iterator ylibIter = ylibMap.entrySet().iterator();
		Iterator ibobarIter = ibobarMap.entrySet().iterator();

		docs2.addAll(addBook2SolerDoc("linking", docs2.size(), linkingIter));
		docs2.addAll(addBook2SolerDoc("zinio", docs2.size(), zinioIter));
		docs2.addAll(addBook2SolerDoc("ylib", docs2.size(), ylibIter));
		docs2.addAll(addBook2SolerDoc("ibobar", docs2.size(), ibobarIter));

		// 將ArrayList轉為XML格式
		// String resultList=GeneralXmlPullParser.reverse(contentAL);
		log.info("=======");
		log.info(docs2.toString());
		server.add(docs2);

		server.commit();
		server.optimize(true, true);

		log.info("finish");

		
		
		return true;
	}
	
	private Collection<SolrInputDocument> addBook2SolerDoc(String channel,
			int docs2Size, Iterator bookIter) {

		Collection<SolrInputDocument> docArr = new ArrayList<SolrInputDocument>();

		int index = docs2Size;

		while (bookIter.hasNext()) {
			Map.Entry entry = (Map.Entry) bookIter.next();
			ArrayList<HashMap<String, Object>> bookAL = (ArrayList<HashMap<String, Object>>) entry
					.getValue();
			for (HashMap hm : bookAL) {

				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", index);
				doc.addField("channel", channel);
				doc.addField("name", hm.get("name"));
				doc.addField("description",hm.get("description"));

				docArr.add(doc);
				index++;
			}
		}
		return docArr;
	}

	private HashMap addBookMap2AllMap(String channel, int allMapSize,
			Iterator bookIter) {

		HashMap map = new HashMap();
		int index = allMapSize;

		while (bookIter.hasNext()) {
			Map.Entry entry = (Map.Entry) bookIter.next();
			String categoryCode = (String) entry.getKey();

			ArrayList<HashMap<String, Object>> bookAL = (ArrayList<HashMap<String, Object>>) entry
					.getValue();
			for (HashMap hm : bookAL) {
				hm.put("channel", channel);// for 小7 , he need to know which
				hm.put("categoryCode", categoryCode);
				map.put(index, hm);
				index++;
			}
		}
		return map;

	}

}
