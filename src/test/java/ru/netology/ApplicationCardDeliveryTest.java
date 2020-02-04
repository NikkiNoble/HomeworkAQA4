package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class ApplicationCardDeliveryTest {
    private SelenideElement cityName = $("[data-test-id=city] input");
    private SelenideElement date = $("[data-test-id=date] input");
    private SelenideElement personName = $("[data-test-id=name] input");
    private SelenideElement phoneNumber = $("[data-test-id=phone] input");
    private SelenideElement agreement = $("[data-test-id=agreement]");

    @Test
    void ShouldSubmitRequest () {
        open("http://localhost:8888/");
        LocalDate dt = LocalDate.now();
        LocalDate value = dt.plus(Period.ofDays(7));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        cityName.setValue("Сыктывкар");
        date.doubleClick().sendKeys(BACK_SPACE);
        date.setValue(formatter.format(value));
        personName.setValue("Иван Иванов");
        phoneNumber.setValue("+79111111111");
        agreement.click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно")).waitUntil(visible, 15000);
    }
    @Test
    void ShouldSubmitRequestWithSetDate () {
        open("http://localhost:8888/");
        cityName.setValue("Си");
        $(withText("Симферополь")).click();
        personName.setValue("Иван-Иванов");
        phoneNumber.setValue("+79111111111");
        agreement.click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно")).waitUntil(visible, 15000);
    }
    @Test
    void ShouldNotSubmitRequestWrongCity() {
        open("http://localhost:8888/");
        cityName.setValue("New York");
        personName.setValue("Иван Иванов");
        phoneNumber.setValue("+79111111111");
        agreement.click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Доставка в выбранный город недоступна")).waitUntil(visible, 5000);
    }
    @Test
    void ShouldNotSubmitRequestWrongNumber() {
        open("http://localhost:8888/");
        cityName.setValue("Москва");
        personName.setValue("Иван Иванов");
        phoneNumber.setValue("790-222-3335");
        agreement.click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Телефон указан неверно.")).waitUntil(visible, 5000);
    }
    @Test
    void ShouldNotSubmitRequestEmptyField() {
        open("http://localhost:8888/");
        cityName.setValue("Москва");
        phoneNumber.setValue("+79111111111");
        agreement.click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения")).waitUntil(visible, 5000);
    }
    @Test
    void ShouldNotSubmitRequestWrongDate() {
        open("http://localhost:8888/");
        cityName.setValue("Москва");
        date.doubleClick().sendKeys(BACK_SPACE);
        date.setValue("20.20.5000");
        personName.setValue("Иван Иванов");
        phoneNumber.setValue("+79222222222");
        agreement.click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Неверно введена дата")).waitUntil(visible, 5000);
    }
}
