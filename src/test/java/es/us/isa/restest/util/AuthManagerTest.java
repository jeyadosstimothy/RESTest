package es.us.isa.restest.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class AuthManagerTest {

  final String AUTH_KEYS = "Sample/apikeys.json";
  final String OAUTH_DATA = "Sample/oauth.json";
  final String OAUTH_DATA_WRONG = "Sample/oauth_wrong.json";
  final String OAUTH_DATA_SHORT_EXPIRATION = "Sample/oauth_short_expiration.json";

  @Test
  public void testGetAuthKeys() {
    Map<String, String> authProperties = new HashMap<>();
    AuthManager authManager = new AuthManager(AUTH_KEYS);
    for (String authProperty : authManager.getAuthPropertyNames())
      authProperties.put(authProperty, authManager.getAuthProperty(authProperty));

    assertEquals("hello", "abc", authProperties.get("apikey_1"));
    assertEquals("hello2", "ghi", authProperties.get("apikey_2"));

    for (String authProperty : authManager.getAuthPropertyNames())
      authProperties.put(authProperty, authManager.getAuthProperty(authProperty));

    assertEquals("hello3", "def", authProperties.get("apikey_1"));
    assertEquals("hello4", "jkl", authProperties.get("apikey_2"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOauthBadJson() {
    AuthManager authManager = new AuthManager(AUTH_KEYS, true);
  }

  @Test
  public void testOauthBadCredentials() {
    AuthManager authManager = new AuthManager(OAUTH_DATA_WRONG, true);
    String oauthHeader = authManager.getUpdatedOauthHeader();
    assertNull(oauthHeader);
  }
}
