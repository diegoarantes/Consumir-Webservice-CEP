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
        long tempoInicio = System.currentTimeMillis();

        Cep cep;
        try {
            cep = getCep("87720140");
            if (cep.getCidade() != null) {
                System.out.println(cep.getLogradouro());
                System.out.println(cep.getBairro());
                System.out.println(cep.getCidade());
                System.out.println(cep.getEstado());

            } else {
                System.out.println("CEP Não Encontrado!");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //Código do programa...
        System.out.println("Tempo Total: " + (System.currentTimeMillis() - tempoInicio));

    }

    private static Cep getCep(final String cep) throws MalformedURLException, IOException {
        URL url = new URL("http://api.postmon.com.br/v1/cep/" + cep);
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
