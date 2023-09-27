package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private final Random random = new Random();

    private static List<String> readAllLines(String resource) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(resource);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader
                    .lines()
                    .collect(Collectors.toList());

            //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
            //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
            //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            // příklad volání: readAllLines("citaty.txt")

        }
    }

    @GetMapping("/")
    public ModelAndView text() throws IOException {
        List<String> radky = readAllLines("citaty.txt");
        List<String> odkazy = readAllLines("odkazy.txt");
        int nahodneCisloCitatu = random.nextInt(radky.size());
        int nahodneCisloPozadi = random.nextInt(odkazy.size());
        //tato varianta počítá s možnými změnami rozsahu souboru a nezávislosti na pozadí
        //je možné počítat s tím, že každý citát by měl vlastní pevně určené pozadí a písma z důvodu optimální čitelnosti
        ModelAndView result = new ModelAndView("index");
        result.addObject("pozadi", odkazy.get(nahodneCisloPozadi));
        result.addObject("text", radky.get(nahodneCisloCitatu));
        return result;
    }
}

    //řešení číslo jedna - nahrát obrázky a stejně jako v hodu kostkou je nahrát dle příslušného čísla
    // result.addObject("pozadi", String.format("/images/pozadi-%d.jpg",nahodneCisloCitatu));
        //výhoda - snadný zápis, stránka spoléhá jen sama na sebe
        //nevýhoda - zabírá hodně prostoru


    //řešení číslo dva - pevně sepsat list obsahující odkazy do kódu a vybírat, který bude vybrán dle příslušného čísla
        //výhoda - relativně snadné, nevýhoda - zapleveluje kód, závislost na provozu jiného zdroje

    //řešení číslo tři - udělat texťák jen pro část zdroje a využít shodnou metodu jako v případě citace
        //výhoda - zcela oddělený obsah, možnost snadného rozšiřování souboru, nevýhoda - závislost na provozu jiného zdroje






