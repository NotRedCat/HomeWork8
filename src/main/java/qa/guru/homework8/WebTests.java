package qa.guru.homework8;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WebTests {

    static Stream<Arguments> dataMenuTest() {
        return Stream.of(
                Arguments.of(Lang.POLSKA,
                        List.of("WYBIERZ KRAJ", " ", "KULTOWY STYL", "HISTORIA", "UNIKALNE CECHY", "PRODUKTY")),
                Arguments.of(Lang.DEUTSCHLAND,
                        List.of("REGION WECHSELN", " ", "IKONISCHER STIL", "GESCHICHTE", "EINZIGARTIGKEIT", "PRODUKTE"))
        );
    }

    @MethodSource("dataMenuTest")
    @ParameterizedTest(name = "Для локали {0} отображаются кнопки {1}")
    void selenideSiteMenuTest(Lang lang, List<String> expectedButtons) {
        open("https://live-legend.borjomi.com/location");
        $$(".t396__elem a").find(text(lang.name())).click();
        $$(".tn-atom__sticky-wrapper a")
                .filter(visible)
                .shouldHave(CollectionCondition.texts(expectedButtons));
    }

    @CsvSource(value = {
            "rte@gf.jg,  Email not found.",
            "link@ran.ua,  Email with instructions has been sent to you.",
    })
    @ParameterizedTest(name = "При вводе емайла \"{0}\" сообщение: \"{1}\"")
    void commonComplexSearchTest(String testData, String expectedResult) {
        open("https://demowebshop.tricentis.com/passwordrecovery");
        $("#Email").setValue(testData);
        $("input[name='send-email']").click();
        $(".result").
                shouldHave(text(expectedResult));
    }

    @EnumSource(MenuItem.class)
    @ParameterizedTest
    void selenideSiteMenuEnumTest(MenuItem item) {
        open("https://demowebshop.tricentis.com/");
        $$(".top-menu a").find(text(item.name())).click();
        $(".breadcrumb a").shouldHave(text("Home"));
    }
}