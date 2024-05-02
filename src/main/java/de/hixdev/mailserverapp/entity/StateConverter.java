package de.hixdev.mailserverapp.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class StateConverter implements AttributeConverter<State, String> {

  @Override
  public String convertToDatabaseColumn(State state) {
    if (state == null) {
      return null;
    }
    return state.getCode();
  }

  @Override
  public State convertToEntityAttribute(final String emailState) {
    if (emailState == null) {
      return null;
    }

    return Stream.of(State.values())
        .filter(c -> c.getCode().equals(emailState))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
