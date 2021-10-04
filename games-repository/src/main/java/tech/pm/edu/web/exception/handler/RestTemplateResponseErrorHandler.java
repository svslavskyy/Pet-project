package tech.pm.edu.web.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import tech.pm.edu.web.exception.NonExistentSessionException;
import tech.pm.edu.web.exception.SessionExpiredException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

    return httpResponse.getStatusCode().series() == CLIENT_ERROR;
  }

  @Override
  public void handleError(ClientHttpResponse httpResponse) throws IOException {
    if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new NonExistentSessionException("Session does not exist!");
    }
    if (httpResponse.getStatusCode() == HttpStatus.FORBIDDEN) {
      throw new SessionExpiredException("Session expired!");
    }
  }


}
