package de.bennypi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.BrowserWebDriverContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoControllerTest {

  static BrowserWebDriverContainer<?> container = new BrowserWebDriverContainer<>().withCapabilities(new ChromeOptions());

  @LocalServerPort
  private int port;

  @BeforeAll
  static void beforeAll(@Autowired Environment environment) {
    Testcontainers.exposeHostPorts(environment.getProperty("local.server.port", Integer.class));
    container.start();
  }

  @Test
  void shouldDisplayMessage() throws InterruptedException {
    Thread.sleep(10000);
    RemoteWebDriver webDriver = container.getWebDriver();
    webDriver.get(String.format("http://host.testcontainers.internal:%d/hello", port));

    WebElement greetingElement = webDriver.findElement(By.id("greeting"));
    assertEquals("Hello from the otter side", greetingElement.getText());

    By byReturnedMessage = By.id("returnedMessage");
    WebElement returnedMessageElement = webDriver.findElement(byReturnedMessage);
    assertEquals("No message", returnedMessageElement.getText());

    webDriver.findElement(By.id("inputfield")).sendKeys("I like fish");
    webDriver.findElement(By.id("submit")).click();

    returnedMessageElement = webDriver.findElement(byReturnedMessage);
    assertEquals("I like fish", returnedMessageElement.getText());
  }

}
