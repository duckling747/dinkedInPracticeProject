package projekti;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LeniumTest extends org.fluentlenium.adapter.junit.FluentTest {
    
  @LocalServerPort
  private Integer port;

  private final String localhost = "http://localhost:";

  @Before
  public void useTestingInitRoute() {
    goTo(localhost + port + "/accounts/test");
  }

  @Test
  public void canLogIn() {
    goTo(localhost + port);
    find("#loginbutton").click();
    find("#unameinput").fill().with("Arnold");
    find("#pwinput").fill().with("pw");
    find("form").first().submit();
    assertTrue(pageSource().contains("Logout"));
  }

  @Test
  public void canLogOut() {
    goTo(localhost + port + "/login");
    find("#unameinput").fill().with("Arnold");
    find("#pwinput").fill().with("pw");
    find("form").first().submit();
    find("#logoutbutton").click();
    assertTrue(pageSource().contains("You've been logged out"));
  }

}
