package testws;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author D.Arantes
 */
public class TestWS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Cep cep;
        try {
            cep = getCep("87703270");
            if (cep.getLocalidade() != null) {
                System.out.println(cep.getLogradouro());
                System.out.println(cep.getBairro());
                System.out.println(cep.getLocalidade());
                System.out.println(cep.getUf());
                // System.out.println(cep.getComplemento());
            } else {
                System.out.println("CEP Não Encontrado!");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static Cep getCep(final String cep) throws MalformedURLException, IOException {
        URL url = new URL("http://viacep.com.br/ws/" + cep + "/json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "");
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();

        InputStream content = connection.getInputStream();
        Reader reader = new InputStreamReader(content);

        Gson gson = new Gson();

        // Criamos nosso objeto de retorno        
        Cep retorno = gson.fromJson(reader, Cep.class);
        content.close();

        return retorno;
    }

}
