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


    private static List<String> readAllLines(String resource) throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try (InputStream inputStream = classLoader.getResourceAsStream(resource);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }

    public final Random randomNum = new Random();

    @GetMapping("/")
    public ModelAndView vypisCitat() throws IOException {

        List<String> citaty = readAllLines("citaty.txt");
        List<String> imgLinks = readAllLines("imgLinks.txt");
        int citatySize = citaty.size();
        int imgLinksSize = imgLinks.size();
        String citat = citaty.get(randomNum.nextInt(1, citatySize));
        String imgLink = imgLinks.get(randomNum.nextInt(1, imgLinksSize));

        ModelAndView result = new ModelAndView("citaty")
                .addObject("citat", citat)
                .addObject("imgLink", imgLink);
        return result;
    }
}
