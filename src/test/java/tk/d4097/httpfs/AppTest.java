package tk.d4097.httpfs;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import tk.d4097.httpfs.controller.StoreController;

@SpringBootTest
@AutoConfigureMockMvc
public class AppTest {
  @Autowired
  StoreController storeController;

  @Test
  public void contextLoads() throws Exception {
    assertThat(storeController).isNotNull();
  }
}