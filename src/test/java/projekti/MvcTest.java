package projekti;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import projekti.repositories.AccountRepository;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MvcTest {
    
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void indexGetOk() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk());
  }
  
  private String buildUrlEncodedFormEntity(String... params) {
    if ((params.length % 2) > 0) {
      throw new IllegalArgumentException("Need to give an even number of parameters");
    }
    var builder = new StringBuilder();
    for (int i = 0; i < params.length; i += 2) {
      if (i > 0) {
        builder.append('&');
      }
      try {
        builder
          .append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name()))
          .append('=')
          .append(URLEncoder.encode(params[i + 1], StandardCharsets.UTF_8.name()));
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }
    return builder.toString();
  }

  @Test
  public void postAccountWorks() throws Exception {
    var json = new StringBuilder();
    json.append("{");
    json.append("\"username\": \"nimi\",\n");
    json.append("\"password\": \"salasana\",\n");
    json.append("\"firstName\": \"etunimi\",\n");
    json.append("\"lastName\": \"sukunimi\"\n");
    json.append("}");
    
    mockMvc.perform(post("/accounts")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json.toString())
    ).andExpect(status().isOk());
    final var a = accountRepository.findByUsername("nimi");
    assertNotNull(a);
  }

}
