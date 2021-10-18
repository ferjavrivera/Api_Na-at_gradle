package mx.com.naat.activityrecord.domain.data;

import java.io.Serializable;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String lastName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
