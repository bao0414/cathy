package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CoinEntity;
import com.example.demo.entity.CurrentpriceVO;
import com.example.demo.serivce.TestSerivce;

@RestController
public class TestController {
	
	@Autowired
	private TestSerivce testSerivce;
	
	@GetMapping(value ="/getData")
	public CurrentpriceVO getData() throws Exception{
		return testSerivce.getData();
	}
	
	@GetMapping(value ="/insertApiData")
	public int insertApiData() throws Exception{
		return testSerivce.insertApiData();
	}
	
	@PostMapping(value ="/insert")
	public String insert(@RequestBody List<CoinEntity> coinList) throws Exception{
		return testSerivce.insert(coinList);
	}
	
	@PostMapping(value ="/update")
	public String update(@RequestBody List<CoinEntity> coinList) throws Exception{
		return testSerivce.update(coinList);
	}
	
	@DeleteMapping(value ="/delete")
	public String delete(@RequestBody List<String> codeList) throws Exception{
		return testSerivce.delete(codeList);
	}
}
