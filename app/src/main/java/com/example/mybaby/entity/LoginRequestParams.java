package com.example.mybaby.entity;


public class LoginRequestParams extends BaseRequestParams {

	private String agentNum;

	private String password;

	private String sources;

	private String system;

	private String telephone;


	public String getAgentNum() {
		return agentNum;
	}

	public void setAgentNum(String agentNum) {
		this.agentNum = agentNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "{" +
				"\"agentNum\":\"" + agentNum + '\"' +
				", \"channel\":\"" + channel + '\"' +
				", \"password\":\"" + password + '\"' +
				", \"sources\":\"" + sources + '\"' +
				", \"system\":\"" + system + '\"' +
				", \"telephone\":\"" + telephone + '\"' +
				", \"timestamp\":" + timestamp +
				", \"code\":\"" + code + '\"' +
				'}';
	}
}
