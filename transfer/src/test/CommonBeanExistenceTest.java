import com.bank.transfer.util.ApplicationContextProvider;
import com.bank.transfer.TransferApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest(classes = TransferApplication.class)
public class CommonBeanExistenceTest {

    @Test
    public void mainTest() {
        Assertions.assertNotEquals(0,
                Arrays.stream(ApplicationContextProvider.getApplicationContext().getBeanDefinitionNames()).filter(
                        name -> name.toLowerCase().contains("GlobalRestExceptionHandler".toLowerCase())).count());
    }
}
