package com.kodilla.testing2.facebook;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FacebookTestingApp {

    public static final String XPATH_INPUT_NAME = "//input[contains(@name, \"firstname\")]";
    public static final String XPATH_INPUT_LASTNAME = "//input[contains(@name, \"lastname\")]";
    public static final String XPATH_INPUT_PHONE = "//input[contains(@name, \"reg_email__\")]";
    public static final String XPATH_INPUT_PASSWORD = "//input[contains(@name, \"reg_passwd__\")]";

    public static final String XPATX_WAIT_FOR = "//select";
    public static final String XPATH_SELECT_DAY = "//div[contains(@id, \"reg_form_box\")]/div/div/span/span/select[contains(@id, \"day\")]";
    public static final String XPATH_SELECT_MONTH = "//div[contains(@id, \"reg_form_box\")]/div/div/span/span/select[contains(@id, \"month\")]";
    public static final String XPATH_SELECT_YEAR = "//div[contains(@id, \"reg_form_box\")]/div/div/span/span/select[contains(@id, \"year\")]";
    public static final String XPATH_BUTTON_SUBMIT = "//div[@class=\"clearfix\"]/button[contains(@name, \"websubmit\")]";
    public static final String XPATH_INPUT_SEX = "//input[@name=\"sex\" and @value=\"2\"]";

    public static void main(String[] args) {
        WebDriver webDriver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        webDriver.get("https://www.facebook.com/");

        WebElement nameField = webDriver.findElement(By.xpath(XPATH_INPUT_NAME));
        nameField.sendKeys("Jan");

        WebElement lastnameField = webDriver.findElement(By.xpath(XPATH_INPUT_LASTNAME));
        lastnameField.sendKeys("Kowalski");

        WebElement phoneNum = webDriver.findElement(By.xpath(XPATH_INPUT_PHONE));
        phoneNum.sendKeys("12345678");

        WebElement password = webDriver.findElement(By.xpath(XPATH_INPUT_PASSWORD));
        password.sendKeys("VeryComplicated122");

        while (!webDriver.findElement(By.xpath(XPATX_WAIT_FOR)).isDisplayed()) ;

        WebElement daySelectDropdown = webDriver.findElement(By.xpath(XPATH_SELECT_DAY));
        Select selectDay = new Select(daySelectDropdown);
        selectDay.selectByValue("17");

        WebElement monthSelectDropdown = webDriver.findElement(By.xpath(XPATH_SELECT_MONTH));
        Select selectMonth = new Select(monthSelectDropdown);
        selectMonth.selectByValue("10");

        WebElement yearSelectDropdown = webDriver.findElement(By.xpath(XPATH_SELECT_YEAR));
        Select selectYear = new Select(yearSelectDropdown);
        selectYear.selectByValue("2000");

        WebElement sex = webDriver.findElement(By.xpath(XPATH_INPUT_SEX));
        sex.click();

        WebElement buttonSubmit = webDriver.findElement(By.xpath(XPATH_BUTTON_SUBMIT));
        buttonSubmit.click();
    }
}
