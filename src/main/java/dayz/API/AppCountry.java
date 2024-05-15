package dayz.API;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class AppCountry {
    public static void main(String[] args) throws IOException {
        final Gson gson = new Gson();
        URL country = new URL("https://restcountries.com/v3.1/name/spain?fields=name,flags");
        BufferedReader in = new BufferedReader(new InputStreamReader(country.openStream(), StandardCharsets.UTF_8));
        Country[] g = gson.fromJson(in, Country[].class);


/*        for (Country.flagsName fg: g[0].flagsNames) {
            System.out.println(fg);
        }*/
        System.out.println(g[0].flags.png);

        in.close();
    }

}
