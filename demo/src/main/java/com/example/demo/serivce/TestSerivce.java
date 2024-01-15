package com.example.demo.serivce;

import java.util.List;

import com.example.demo.entity.CoinEntity;
import com.example.demo.entity.CurrentpriceVO;

public interface TestSerivce {
	
	public CurrentpriceVO getData() throws Exception;
	
	public int insertApiData() throws Exception;

	public String insert(List<CoinEntity> coinList) throws Exception;

	public String update(List<CoinEntity> coinEntiry) throws Exception;
	
	public String delete(List<String> codeList) throws Exception;
}
