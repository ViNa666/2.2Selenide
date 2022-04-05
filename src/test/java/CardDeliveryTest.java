import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {



    @Test
    public void shouldOrderCardDelivery () {

        //Настройка актуальной валидной даты
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        long todayTime = today.getTime();                  // текущая дата в милисекундах с 01.01.1970
        long validDayTime = todayTime + (3*24*60*60*1000); // прибавляю к текущей дате 3 дня в милисекундах
        String validDate = format.format(validDayTime);    // форматирование из милисекунд в дату

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").sendKeys("Санкт-Петербург");
        $("[data-test-id='date'] input").sendKeys(validDate);

        $("[data-test-id='name'] input").sendKeys("Кузьма Петров-Водкин");
        $("[data-test-id='phone'] input").sendKeys("+79000000000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(withText("Забронировать")).click();

        $x("//*[contains(text(), 'Успешно!')]").should(Condition.appear, Duration.ofSeconds(20));
        $x("//*[contains(text(), 'Встреча успешно забронирована на')]").should(Condition.appear, Duration.ofSeconds(20));



    }
}
