package com.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import com.util.DataUtil;

public class SolrInitMap {

	public static SolrInitMap instance;

	@SuppressWarnings("rawtypes")
	public HashMap allBookMap;

	@SuppressWarnings("unchecked")
	public HashMap<Object, Object> getAllBookMap() {
		if (allBookMap == null) {
			this.initAllBookMap();
		}
		return this.allBookMap;
	}

	  @SuppressWarnings("rawtypes")
	public void initAllBookMap() {

		String datFilePath = "C:/Users/1409035/Desktop/FTP_server_backup/candelete/";
		Map zinioMap = (HashMap) DataUtil.DeSerialization(datFilePath + "Zinio"+ "-" + "getItemListByCategoryCode" + "-using" + ".dat");
		Map ylibMap = (HashMap) DataUtil.DeSerialization(datFilePath + "Ylib"+ "-" + "getItemListByCategoryCode" + "-using" + ".dat");
		Map IbobarMap = (HashMap) DataUtil.DeSerialization(datFilePath+ "Ibobar" + "-" + "getItemListByCategoryCode" + "-using"+ ".dat");
		Map linkingMap = (HashMap) DataUtil.DeSerialization(datFilePath+ "Linking" + "-" + "getItemListByCategoryCode" + "-using"+ ".dat");

		Iterator linkingIter = linkingMap.entrySet().iterator();
		Iterator ylibgIter = ylibMap.entrySet().iterator();
		Iterator iboarIter = IbobarMap.entrySet().iterator();
		Iterator zinioIter = zinioMap.entrySet().iterator();
		
		allBookMap = new HashMap();
	        
		allBookMap.putAll(addBookMap2AllMap("linking",allBookMap.size(),linkingIter));
		allBookMap.putAll(addBookMap2AllMap("zinio",allBookMap.size(),zinioIter));
		allBookMap.putAll(addBookMap2AllMap("ylib",allBookMap.size(),ylibgIter));
		allBookMap.putAll(addBookMap2AllMap("ibobar",allBookMap.size(),iboarIter));
		System.out.println(allBookMap.toString());
	        
	  }
	  public HashMap addBookMap2AllMap(String channel, int allMapSize,
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
				map.put(String.valueOf(index), hm);
				index++;
			}
		}
		return map;

	}

	public static synchronized SolrInitMap getInstance() {
		if (instance == null)
			instance = new SolrInitMap();
		return instance;
	}

}
