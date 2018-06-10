package com.kodilla.testing2.crudapp;

import com.kodilla.testing2.config.WebDriverConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class CrudAppTestSuite {
    public static final String BASE_URL = "https://kajadubiel.github.io/";
    public static final String XPATH_TASK_NAME = "//form[contains(@action, \"createTask\")]/fieldset[1]/input";
    public static final String XPATH_TASK_CONTENT = "//form[contains(@action, \"createTask\")]/fieldset[2]/textarea";
    public static final String XPATH_ADD_BUTTON = "//form[contains(@action, \"createTask\")]/fieldset[3]/button";


    private WebDriver webDriver;
    private Random generator;

    @Before
    public void initTests() {
        webDriver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        webDriver.get(BASE_URL);
        generator = new Random();
    }


    @After
    public void cleanAfterTest() {
        webDriver.close();
    }


    private String createCrudAppTestTask() throws InterruptedException {
        String taskName = "Task number" + generator.nextInt(100000);
        String taskContent = taskName + " content";

        WebElement name = webDriver.findElement(By.xpath(XPATH_TASK_NAME));
        name.sendKeys(taskName);

        Thread.sleep(2000);

        WebElement content = webDriver.findElement(By.xpath(XPATH_TASK_CONTENT));
        content.sendKeys(taskContent);

        Thread.sleep(2000);

        WebElement addButton = webDriver.findElement(By.xpath(XPATH_ADD_BUTTON));
        addButton.click();
        Thread.sleep(3000);


        return taskName;
    }

    private void sendTestTaskToTrello(String taskName) throws InterruptedException {
        webDriver.navigate().refresh();

        while (!webDriver.findElement(By.xpath("//select[1]")).isDisplayed()) ;

        webDriver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm -> anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                        .getText().equals(taskName))
                .forEach(theForm -> {
                    System.out.println("found: " + taskName);
                    WebElement selectElement = theForm.findElement(By.xpath(".//select[1]"));
                    Select select = new Select(selectElement);
                    select.selectByIndex(1);

                    WebElement buttonCreated = theForm.findElement(By.xpath(".//button[contains(@class, \"card-creation\")]"));
                    buttonCreated.click();
                });
        Thread.sleep(4000);
    }

    private boolean checkTaskExistsInTrello(String taskName) throws InterruptedException {
        final String TRELLO_URL = "https://trello.com/login";
        boolean result = false;

        WebDriver driverTrello = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driverTrello.get(TRELLO_URL);

        driverTrello.findElement(By.id("user")).sendKeys("xxx");
        driverTrello.findElement(By.id("password")).sendKeys("xxx");///!!!!!
        driverTrello.findElement(By.id("login")).submit();

        Thread.sleep(4000);

        driverTrello.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div/div/div/div/div/div[1]/nav/div[1]/ul/li[2]/a")).click();


        Thread.sleep(4000);
        driverTrello.findElements(By.xpath("//a[@class=\"board-tile\"]")).stream()
                .filter(aHref -> aHref.findElements(By.xpath(".//span[@title=\"Kodilla Aplication\"]")).size() > 0)
                .forEach(aHref -> aHref.click());

        Thread.sleep(6000);

        result = driverTrello.findElements(By.xpath("//*[@id=\"board\"]/div[1]/div/div[2]/a")).stream()
                .filter(el -> el.getText().contains(taskName))
                .collect(Collectors.toList())
                .size() > 0;

        driverTrello.close();
        return result;
    }

    public void cleanCrudAppAfterTest(String taskName) throws InterruptedException {
        try {
            Alert alert = webDriver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            System.out.println(e);
        }

        webDriver.navigate().refresh();
        Thread.sleep(4000);

        List<WebElement> elements = webDriver.findElements(By.xpath("//form//p[1]")).stream()
                .filter(el -> el.getText().equals(taskName))
                .collect(Collectors.toList());

        for (WebElement el : elements) {
            WebElement propablyForm = el.findElement(By.xpath("//p/parent::fieldset/parent::form"));
            propablyForm.findElement(By.xpath(".//button[4]")).click();
        }

        Thread.sleep(3000);
    }

    @Test
    public void shouldCreateTrelloCard() throws InterruptedException {
        String taskName = createCrudAppTestTask();
        sendTestTaskToTrello(taskName);
        boolean isInTrello = checkTaskExistsInTrello(taskName);

        cleanCrudAppAfterTest(taskName);
        assertTrue(isInTrello);
    }
}
