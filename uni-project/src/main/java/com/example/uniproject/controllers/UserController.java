package com.example.uniproject.controllers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.uniproject.entities.UserEntity;
import com.example.uniproject.repositories.UserRepository;

@RestController
public class UserController {

	private UserRepository _userRepository;

	public UserController(UserRepository userRepository) {
		_userRepository = userRepository;
	}

	@PostMapping(path = "/register")
	public ResponseEntity<UserEntity> register(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "repeatPassword") String repeatPassword) {

		if (password.equals(repeatPassword)) {

			String hashedPassword = hashAlgorithm(password);

			UserEntity user = new UserEntity(username, hashedPassword);

			UserEntity responseEntity = _userRepository.saveAndFlush(user);

			if (Objects.isNull(responseEntity)) {
				return new ResponseEntity<UserEntity>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<UserEntity>(responseEntity, HttpStatus.OK);
		}

		return new ResponseEntity<UserEntity>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping(path = "/login")
	public ResponseEntity<Integer> login(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, HttpSession session) {

		String hashedPassword = hashAlgorithm(password);
		UserEntity user = _userRepository.findUserByUsernameAndPassword(username, hashedPassword);

		if (user != null) {
			session.setAttribute("user", user);

			return new ResponseEntity<Integer>(user.getId(), HttpStatus.OK);
		}

		return new ResponseEntity<Integer>(-1, HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(path = "/logout")
	public ResponseEntity<Boolean> logout(HttpSession session) {

		UserEntity user = (UserEntity) session.getAttribute("user");

		if (user != null) {
			session.invalidate();
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.I_AM_A_TEAPOT);
		}
	}

	private String hashAlgorithm(String password) {

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

			StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
			for (int i = 0; i < encodedhash.length; i++) {
				String hex = Integer.toHexString(0xff & encodedhash[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
