package com.hardik.pomfrey.utility;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hardik.pomfrey.constants.Response;
import com.hardik.pomfrey.entity.User;
import com.hardik.pomfrey.security.utility.JwtUtils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntityUtils {

	@Autowired
	private JwtUtils jwtUtils;

	public ResponseEntity<?> generateUserAccountCreationResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.OK.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.ACCOUNT_CREATION_SUCCESS);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> generateUserAccountDetailUpdationResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.OK.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.ACCOUNT_UPDATION_SUCCESS);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> generateWrongOldPasswordResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.EXPECTATION_FAILED.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.ACCOUNT_UPDATION_SUCCESS);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response.toString());
	}

	public ResponseEntity<?> generateUserPasswordUpdationResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.OK.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.PASSWORD_CHANGED);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> generateCommentCreationResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.OK.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.COMMENT_CREATED);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> generateCommentDeletionResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.OK.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.COMMENT_DELETED);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> generateUnauthorizedResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.UNAUTHORIZED.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.UNAUTHORIZED);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString());
	}

	public ResponseEntity<?> generateFollowToggleResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.OK.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.FOLLOW_TOGGLED);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> generateReportCreationResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.OK.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.REPORT_CREATED);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> generateResourceCreationResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.OK.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.RESOURCE_CREATED);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> generateResourceUpdationResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.OK.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.RESOURCE_UPDATED);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> generateRequestCreationResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.OK.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.REQUEST_CREATED);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> generateRequestUpdationResponse() {
		final var response = new JSONObject();
		try {
			response.put(Response.KEY.STATUS, HttpStatus.OK.value());
			response.put(Response.KEY.MESSAGE, Response.VALUE.REQUEST_UPDATED);
			response.put(Response.KEY.TIMESTAMP, LocalDateTime.now().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

	public ResponseEntity<?> generateSuccessLoginResponse(User user) {
		final var response = new JSONObject();
		try {
			response.put("status", HttpStatus.OK.value());
			response.put("jwt", jwtUtils.generateToken(user));
		} catch (JSONException exception) {
			exception.printStackTrace();
		}
		return ResponseEntity.ok(response.toString());
	}

}
