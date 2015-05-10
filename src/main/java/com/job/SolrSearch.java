package com.job;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import com.vo.SolrArgVo;

public class SolrSearch {

	public static Logger log = Logger.getLogger(SolrSearch.class.getName());

	public boolean solrSearch(SolrArgVo solrArgVo) throws MalformedURLException, SolrServerException{

		//HttpSolrServer solr = new HttpSolrServer("http://10.24.100.237:8080/solr/collection1");
		HttpSolrServer solr = new HttpSolrServer("http://192.168.22.148:8080/solr/collection1");
		SolrQuery query = new SolrQuery();
		
		//英文是精確比對，中文是模糊比對
		//查詢條件
	
		
		query.setQuery( "*:*" );
		//query.setFilterQueries("name:" + "登基", "description:" + "登基", "channel:" + "ylib");
		String keyword = "武學";
		query.addFilterQuery("channel:" + "Ylib");
		query.setQuery("name:"+keyword+" OR description:"+keyword); 
		//query.setQuery("name:"+"王道  AND currency:"+"NTD");
		
		
		
		
		//can use to be get book by cat 
		query.setRows(100); //get row of query result//default is 10
		//query.setQuery("title:國王");
		//query.setQuery("title: art");
	    
	    QueryResponse response = solr.query(query);
	    SolrDocumentList results = response.getResults();
	    System.out.println("NumFound="+results.getNumFound());
	    System.out.println("SIZE="+results.size());
	    //System.out.println(results);
	    //System.out.println(results.get(0).get("title"));
	      for (int i = 0; i < results.size(); ++i) {
	          System.out.println("result "+i+"= "+results.get(i).get("id"));
	    	  System.out.println("result "+i+"="+ results.get(i));
	      }
		
		
		
		return true;
	}
	
}
