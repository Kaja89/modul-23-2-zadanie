package com.kodilla.testing2.crudapp;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CrudAppTestingApp {
    public static final String XPATH_INPUT = "//html/body/main/section/form/fieldset/input";
    public static final String XPATH_TEXTAREA = "//html/body/main/section/form/fieldset[2]/textarea";
    public static final String XPATH_WAIT_FOR = "//select[1]";
    public static final String XPATH_SELECT = "//div[contains(@class, \"tasks-container\")]/form/div/fieldset/select[1]";

    //
    public static final String XPATH_BUTTON = "//button[1]";
    public static void main(String[] args) {
        WebDriver webDriver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        webDriver.get("https://kajadubiel.github.io/");

        WebElement searchField = webDriver.findElement(By.xpath(XPATH_INPUT));
        searchField.sendKeys("New robotic task");

        WebElement textAreaField = webDriver.findElement(By.xpath(XPATH_TEXTAREA));
        textAreaField.sendKeys("The robotic content");

        WebElement buttonAddTask = webDriver.findElement(By.xpath(XPATH_BUTTON));
        buttonAddTask.submit();


        while(!webDriver.findElement(By.xpath(XPATH_WAIT_FOR)).isDisplayed());

        WebElement selectCombo = webDriver.findElement(By.xpath(XPATH_SELECT));
        Select selectBoard = new Select(selectCombo);
        selectBoard.selectByIndex(2);

    }
}
