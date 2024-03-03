package auth;

import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import tools.WaitTools;

public class HeadlessTest {
    private Logger logger = (Logger) LogManager.getLogger("Headless");
    private WebDriver driver;
    private String baseUrl = System.getProperty("base.urlDuck");
    private WaitTools waitTools;

    @BeforeEach
    private void driverInstallHeadless() {
        driver = new DriverFactory("--headless").create();
        waitTools = new WaitTools(driver);
        driver.get(baseUrl);
        logger.info("Open browser in headless");
    }

    @AfterEach
    public void driverStop() {
        if  (driver != null) {
            logger.info("Close browser");
            driver.close();
            driver.quit();
        }
    }

    @Test
    public void openHeadless() {
        WebElement elEnter = driver.findElement(By.cssSelector("#searchbox_input"));
        elEnter.click();

        WebElement elInput = driver.findElement(By.cssSelector("#searchbox_input"));
        elInput.sendKeys("ОТУС");
        elEnter.submit();

        WebElement elResult = driver.findElement(By.xpath("//span[contains(text(),'Онлайн‑курсы для профессионалов, дистанционное обучение современным ...')]"));
        Assertions.assertTrue(elResult.getText().contains("Онлайн‑курсы для профессионалов, дистанционное обучение"),
                "Error: This text is not available in search results");
        logger.info("Test passed");
    }
}
