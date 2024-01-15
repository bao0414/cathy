package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.CoinEntity;

public interface CoinDao {
	List<CoinEntity> selectByCode(List<String> codeList);
	
	int insert(List<CoinEntity> coinList);

	int update(List<CoinEntity> coinList);

	int delete(List<String> codeList);
}
