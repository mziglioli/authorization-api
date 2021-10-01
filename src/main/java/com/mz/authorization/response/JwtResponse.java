package com.mz.authorization.response;

import lombok.*;

@Getter
@Setter
public class JwtResponse extends UserResponse {

  private String token;

  public JwtResponse() {
    super("", "", "", "",false, 0);
    this.token = "";
  }
  public JwtResponse(int loginAttempt) {
    super("", "", "", "", false, loginAttempt);
    this.token = "";
  }
  public JwtResponse(UserResponse user, String token) {
    super(user.getId(), user.getName(), user.getInitials(), user.getName(), user.isActive(), user.getLoginAttempt());
    this.token = token;
  }
}