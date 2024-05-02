package de.hixdev.mailserverapp.constants;

public class Constants {
  private Constants() {
    throw new IllegalStateException("Utility class");
  }

  public static final String MS_API_USER = "MS_API_USER";
  public static final String NOT_FOUND = "NOT_FOUND";
  public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
  public static final String HTTP_STATUS_CODE_200 = "200";
  public static final String HTTP_STATUS_CODE_200_MESSAGE = "Request was successfully processed";
  public static final String HTTP_STATUS_CODE_201 = "201";
  public static final String HTTP_STATUS_CODE_201_MESSAGE = "Eamil was successfully created";

  public static final String HTTP_STATUS_CODE_420 = "420";
  public static final String HTTP_STATUS_CODE_420_MESSAGE_UPDATE = "Update failed. Please contact your Support-Team.";
  public static final String HTTP_STATUS_CODE_420_MESSAGE_DELETE = "Delete failed. Please contact your Support-Team.";

  //LOG Messages
  public static final String LOG_MESSAGE_START = "START: {} - {}";
  public static final String LOG_MESSAGE_END = "END : {} - {}";

  public static final String LOG_MESSAGE = "Operation result: {}";

  public static final String EMAIL = "EMAIL";

  public static final String EMAIL_ID = "email-id";

  public static final String PATHVARIABLE_EMAIL_ID = "{" + EMAIL_ID + "}";

  public static final String SPAM_EMAIL = "carl@gbtec.com";

  // API Endpoints
  public static final String API_REQUESTMAPPING_PATH = "api/v1/emails";
  public static final String API_PATH_CREATE = "/create";
  public static final String API_PATH_UPDATE = "/update";
  public static final String API_PATH_DELETE = "/delete";
  public static final String API_PATH_GET = "/get";
  public static final String API_PATH_GET_ALL = "/getAll";

  public static final String API_PATH_CREATE_ALL = "/createAll";
  public static final String API_PATH_UPDATE_ALL = "/updateAll";
  public static final String API_PATH_DELETE_ALL = "/deleteAll";

}
