package com.persin.weatherlist.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persin.weatherlist.services.UserService;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserService userService;

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		UserDetails user = (UserDetails) authentication.getPrincipal();
		response.getOutputStream().print(objectMapper.writeValueAsString(user));
	}
}
