import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.text.SimpleDateFormat;
import java.time.Duration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {


    @Test
    public void shouldOrderCardDelivery () {


        String planningDate = generateDate(5);

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").sendKeys("Санкт-Петербург");

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);

        $("[data-test-id='name'] input").sendKeys("Кузьма Петров-Водкин");
        $("[data-test-id='phone'] input").sendKeys("+79000000000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(withText("Забронировать")).click();

        $x("//*[contains(text(), 'Успешно!')]").should(Condition.appear, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));



    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
