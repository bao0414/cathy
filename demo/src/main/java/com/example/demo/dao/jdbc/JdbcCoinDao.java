package com.example.demo.dao.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.example.demo.dao.CoinDao;
import com.example.demo.entity.CoinEntity;

@Repository
public class JdbcCoinDao implements CoinDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<CoinEntity> selectByCode(List<String> codeList) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CODE, CODENAME, RATE, TO_TIMESTAMP(UPDATETIME,'YYYY/MM/DD HH24:MI:SS') AS NEWUPDATETIME FROM COIN WHERE CODE IN (");
		for (String code : codeList) {
			sql.append("'");
			sql.append(code);
			sql.append("',");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return jdbcTemplate.query(sql.toString(), (resultSet, rowNum) -> {
			CoinEntity coin = new CoinEntity();
			coin.setCode(resultSet.getString("CODE"));
			coin.setCodeName(resultSet.getString("CODENAME"));
			coin.setRate(resultSet.getBigDecimal("RATE"));
			coin.setUpdatetime(resultSet.getString("NEWUPDATETIME"));
			return coin;
		});
	}

	@Override
	public int insert(List<CoinEntity> coinList) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO COIN (CODE,CODENAME,RATE,UPDATETIME) VALUES ");
		for (CoinEntity coin : coinList) {
			sql.append("('");
			sql.append(coin.getCode());
			sql.append("','");
			sql.append(coin.getCodeName());
			sql.append("',");
			sql.append(coin.getRate());
			sql.append(",");
			sql.append("CURRENT_TIMESTAMP");
			sql.append("),");
		}
		return jdbcTemplate.update(sql.deleteCharAt(sql.length() - 1).toString());
	}

	@Override
	public int update(List<CoinEntity> coinList) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE COIN ");
		sql.append(" SET ");
		sql.append(" RATE = CASE ");
		for (CoinEntity coin : coinList) {
			sql.append(" WHEN CODE = '");
			sql.append(coin.getCode());
			sql.append("' THEN ");
			sql.append(coin.getRate());
		}
		sql.append(" ELSE RATE END, ");
		sql.append(" UPDATETIME = CURRENT_TIMESTAMP ");
		sql.append(" WHERE CODE IN (");
		for (CoinEntity coin : coinList) {
			sql.append("'");
			sql.append(coin.getCode());
			sql.append("',");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return jdbcTemplate.update(sql.toString());
	}

	@Override
	public int delete(List<String> codeList) {
		if (CollectionUtils.isEmpty(codeList)) {
			return 0;
		} else {
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE FROM COIN ");
			sql.append(" WHERE CODE IN ( ");
			for (String code : codeList) {
				sql.append("'");
				sql.append(code);
				sql.append("',");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ) ");
			return jdbcTemplate.update(sql.toString());
		}

	}

}
