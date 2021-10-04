package tech.pm.edu.web.service;

import tech.pm.edu.web.model.LaunchUrlDto;

public interface LaunchUrlService {

  LaunchUrlDto generateLaunchUrl(String lobbyGameId, String sessionKey);


}
