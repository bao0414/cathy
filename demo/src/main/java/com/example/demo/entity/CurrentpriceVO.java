package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentpriceVO {

	@JsonProperty("time")
	private Time time;

	@JsonProperty("disclaimer")
	private String disclaimer;

	@JsonProperty("chartName")
	private String chartName;

	@JsonProperty("bpi")
	private Bpi bpi;

	public class Time {
		
		@JsonProperty("updated")
		private String updated;
		
		@JsonProperty("updatedISO")
		private String updatedISO;

		@JsonProperty("updateduk")
		private String updateduk;

		public String getUpdated() {
			return updated;
		}

		public void setUpdated(String updated) {
			this.updated = updated;
		}

		public String getUpdatedISO() {
			return updatedISO;
		}

		public void setUpdatedISO(String updatedISO) {
			this.updatedISO = updatedISO;
		}

		public String getUpdateduk() {
			return updateduk;
		}

		public void setUpdateduk(String updateduk) {
			this.updateduk = updateduk;
		}


	}

	public class Bpi {
		
		// 美元
		@JsonProperty("USD")
		private BpiEntity usd;

		// 英鎊
		@JsonProperty("GBP")
		private BpiEntity gbp;
		
		// 歐元
		@JsonProperty("EUR")
		private BpiEntity eur;

		public BpiEntity getUsd() {
			return usd;
		}

		public void setUsd(BpiEntity usd) {
			this.usd = usd;
		}

		public BpiEntity getGbp() {
			return gbp;
		}

		public void setGbp(BpiEntity gbp) {
			this.gbp = gbp;
		}

		public BpiEntity getEur() {
			return eur;
		}

		public void setEur(BpiEntity eur) {
			this.eur = eur;
		}


	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public Bpi getBpi() {
		return bpi;
	}

	public void setBpi(Bpi bpi) {
		this.bpi = bpi;
	}

}
