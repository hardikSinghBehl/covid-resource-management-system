package com.hardik.pomfrey.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Response {

	public final Key KEY = new Key();
	public final Value VALUE = new Value();

	public class Key {

		public final String STATUS = "status";
		public final String MESSAGE = "message";
		public final String TIMESTAMP = "timestamp";

	}

	public class Value {

		public final String ACCOUNT_CREATION_SUCCESS = "Account Created Sucessfully, Use The Credentials To Login";
		public final String ACCOUNT_UPDATION_SUCCESS = "Account Deatils Updated Sucessfully";
		public final String PASSWORD_CHANGED = "Password Changed Successfully";
		public final String SAME_PASSWORDS = "New Password Has To Be Differ From Current Password For Updation";
		public final String WRONG_OLD_PASSWORD = "Your Entered Password Does Not Match Your Current Password";
		public final String COMMENT_CREATED = "Comment Created Successfully";
		public final String COMMENT_DELETED = "Comment Deleted Successfully";
		public final String UNAUTHORIZED = "Unauthorized To Perform This Task";
		public final String FOLLOW_TOGGLED = "Follow Successfully Toggled";
		public final String REPORT_CREATED = "Your Report Has Been Registred Successfully";
		public final String RESOURCE_CREATED = "Your Resource Has Been Registred Successfully And Is Visible To Others";
		public final String REQUEST_CREATED = "Your Request Has Been Registred Successfully And Is Visible To Others";
		public final String RESOURCE_UPDATED = "Your Resource Has Been Updated Successfully And Is Visible To Others";
		public final String REQUEST_UPDATED = "Your Request Has Been Updated Successfully And Is Visible To Others";

	}

}
