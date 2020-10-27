package com.mz.authorization.response;

import lombok.*;

@Getter
@Setter
public class JwtResponse extends UserResponse {

  private String token;

  public JwtResponse() {
    super("", "", "", false);
    this.token = "";
  }
  public JwtResponse(UserResponse user, String token) {
    super(user.getId(), user.getName(), user.getName(), user.isActive());
    this.token = token;
  }
}