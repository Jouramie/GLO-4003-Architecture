package ca.ulaval.glo4003.ws.api.validation;

import java.util.List;

public class InputErrorResponseModel {

  public final List<String> inputErrors;

  public InputErrorResponseModel(List<String> inputErrors) {
    this.inputErrors = inputErrors;
  }
}
