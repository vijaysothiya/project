package com.login.entity;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



public class Logindata {
	    
	    @NotBlank(message ="User Name can not be blank")
	    @Size(min=3,max=12,message="Maintaint between 3 to 12")
        private String userName;
	    
	    @NotBlank(message = "not null")
	    @Size(message = "should be more")
        private String mail;
        
        
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getMail() {
			return mail;
		}
		public void setMail(String mail) {
			this.mail = mail;
		}
		@Override
		public String toString() {
			return "Logindata [userName=" + userName + ", mail=" + mail + "]";
		}
        

}
