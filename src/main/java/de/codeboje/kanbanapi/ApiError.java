package de.codeboje.kanbanapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiError {

	@JsonIgnore
	private MessageCode msgCode;
	
	public ApiError(MessageCode msgCode) {
		super();
		this.msgCode = msgCode;
	}
	
	@JsonProperty("code")
	public String getMsgCode() {
		return msgCode.getCode();
	}
	
	@JsonProperty("msg")
	public String getMsg() {
		return msgCode.getMsg();
	}

}
