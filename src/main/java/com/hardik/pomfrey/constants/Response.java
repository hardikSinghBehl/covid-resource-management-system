package com.hardik.pomfrey.constants;

public class Response {

	public static Key KEY;
	public static Value VALUE;

	public class Key {

		public static final String CREDIBILITY = "credibility";
		public static final String STATUS = "status";
		public static final String MESSAGE = "message";
		public static final String TIMESTAMP = "timestamp";

	}

	public class Value {

		public static final String ACCOUNT_CREATION_SUCCESS = "Account Created Sucessfully, Use The Credentials To Login";
		public static final String ACCOUNT_UPDATION_SUCCESS = "Account Deatils Updated Sucessfully";
		public static final String PASSWORD_CHANGED = "Password Changed Successfully";
		public static final String SAME_PASSWORDS = "New Password Has To Be Differ From Current Password For Updation";
		public static final String WRONG_OLD_PASSWORD = "Your Entered Password Does Not Match Your Current Password";
		public static final String COMMENT_CREATED = "Comment Created Successfully";
		public static final String COMMENT_DELETED = "Comment Deleted Successfully";
		public static final String UNAUTHORIZED = "Unauthorized To Perform This Task";
		public static final String FOLLOW_TOGGLED = "Follow Successfully Toggled";
		public static final String REPORT_CREATED = "Your Report Has Been Registred Successfully";
		public static final String RESOURCE_CREATED = "Your Resource Has Been Registred Successfully And Is Visible To Others";
		public static final String REQUEST_CREATED = "Your Request Has Been Registred Successfully And Is Visible To Others";
		public static final String RESOURCE_UPDATED = "Your Resource Has Been Updated Successfully And Is Visible To Others";
		public static final String REQUEST_UPDATED = "Your Request Has Been Updated Successfully And Is Visible To Others";

	}

}
