import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class Utils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String getUrl(String uri) throws IOException {
        var response = httpClient.execute(new HttpGet(uri));
        var nasaObject = mapper.readValue(response.getEntity().getContent(), NasaObject.class);
        return nasaObject.getUrl();
    }
    public static String getHDUrl(String uri) throws IOException {
        var response = httpClient.execute(new HttpGet(uri));
        var nasaObject = mapper.readValue(response.getEntity().getContent(), NasaObject.class);
        return nasaObject.getHdurl();
    }

    public static String getExplanation(String uri) throws IOException {
        var response = httpClient.execute(new HttpGet(uri));
        var nasaObject = mapper.readValue(response.getEntity().getContent(), NasaObject.class);
        return nasaObject.getExplanation();
    }

    public static String translate(String explanation){
        return"";
    }
}
