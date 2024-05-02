package de.hixdev.mailserverapp.entity;


import java.util.Arrays;

public enum State {
  EMAIL_DRAFT("0"),
  EMAIL_SENT("1"),
  EMAIL_DELETED("2"),
  EMAIL_SPAM("3");

  private final String code;

  State(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public static State fromCode(String code) {
    return Arrays.stream(State.values())
        .filter(s -> s.getCode().compareTo(code) == 0)
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

}
