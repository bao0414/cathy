package com.example.demo.serivce.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dao.CoinDao;
import com.example.demo.entity.BpiEntity;
import com.example.demo.entity.CoinEntity;
import com.example.demo.entity.CurrentpriceVO;
import com.example.demo.serivce.TestSerivce;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TestSerivceImpl implements TestSerivce {

	@Autowired
	private CoinDao coinDao;

	@Value("${testUrl}")
	private String testUrl;

	@Override
	public CurrentpriceVO getData() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(testUrl, String.class);
		String apiResponse = response.getBody();

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(apiResponse, CurrentpriceVO.class);
	}

	@Override
	@Transactional
	public int insertApiData() throws Exception {
		CurrentpriceVO getData = this.getData();
		List<CoinEntity> bpiList = new ArrayList<>();
		CoinEntity eur = this.getCoinEntiry(getData.getBpi().getEur());
		eur.setCodeName("歐元");
		CoinEntity gbp = this.getCoinEntiry(getData.getBpi().getGbp());
		gbp.setCodeName("英鎊");
		CoinEntity usd = this.getCoinEntiry(getData.getBpi().getUsd());
		usd.setCodeName("美元");
		bpiList.add(eur);
		bpiList.add(gbp);
		bpiList.add(usd);
		return coinDao.insert(bpiList);
	}

	@Override
	@Transactional
	public String insert(List<CoinEntity> coinList) throws Exception {
		List<String> codeList = coinList.stream().map(CoinEntity::getCode).collect(Collectors.toList());
		List<CoinEntity> selectBpiList = coinDao.selectByCode(codeList);

		if (ObjectUtils.isEmpty(selectBpiList)) {
			return coinDao.insert(coinList) > 0 ? "新增成功" : "新增失敗";
		} else {
			return "資料已存在請使用update功能";
		}
	}

	@Override
	@Transactional
	public String update(List<CoinEntity> coinList) throws Exception {
		List<String> codeList = coinList.stream().map(CoinEntity::getCode).collect(Collectors.toList());
		List<CoinEntity> selectCoinEntity = coinDao.selectByCode(codeList);

		if (ObjectUtils.isEmpty(selectCoinEntity)) {
			return "資料已存在請使用insert功能";
		} else {
			return coinDao.update(coinList) > 0 ? "更新成功" : "更新失敗";
		}
	}

	@Override
	@Transactional
	public String delete(List<String> codeList) throws Exception {
		List<CoinEntity> selectBpiList = coinDao.selectByCode(codeList);
		if (CollectionUtils.isEmpty(selectBpiList)) {
			return "刪除失敗";
		} else {
			int deleteInt = coinDao.delete(codeList);
			if (codeList.size() == deleteInt) {
				return "刪除成功";
			} else {
				List<CoinEntity> errorCode = selectBpiList.stream()
						.filter(bpiEntity -> !codeList.contains(bpiEntity.getCode())).collect(Collectors.toList());

				StringBuilder message = new StringBuilder();
				message.append("以下為資料庫不存在的資料");
				errorCode.forEach(data -> {
					message.append(data.getCode());
					message.append(",");
				});
				message.deleteCharAt(message.length() - 1);
				message.append("其餘刪除成功");
				return message.toString();
			}
		}
	}
	
	private CoinEntity getCoinEntiry(BpiEntity bpiEntity) {
		CoinEntity coinEntiry = new CoinEntity();
		coinEntiry.setCode(bpiEntity.getCode());
		coinEntiry.setRate(new BigDecimal(bpiEntity.getRate_float()));
		return coinEntiry;
	}
}
