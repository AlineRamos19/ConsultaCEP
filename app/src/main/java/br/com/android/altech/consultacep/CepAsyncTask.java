package br.com.android.altech.consultacep;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;


public class CepAsyncTask extends AsyncTask<Void, Void, Cep> {

    private static final String LOG_TAG = CepAsyncTask.class.getSimpleName();
    private String mCep;
    private String mRespostaJson = "";

    public CepAsyncTask(String cep) {
        this.mCep = cep;
    }

    @Override
    protected Cep doInBackground(Void... voids) {

        URL url = criarUrl(mCep);

        try {
            mRespostaJson = fazerPedidoHttp(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Erro ao criar pedido http" + e);
        }

        Cep dadosCep = null;
        try {
            dadosCep = extrairJSON(mRespostaJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dadosCep;

    }


    private URL criarUrl(String mCep) {

        URL url = null;

        try {
            url = new URL("https://viacep.com.br/ws/" + mCep + "/json/");
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Erro na criação da URL" + e);
        }

        return url;
    }

    private String fazerPedidoHttp(URL url) throws IOException {

        if (url == null) {
            return mRespostaJson;
        }

        HttpURLConnection conexao = null;
        InputStream inputStream = null;

        try {
            conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(10000);
            conexao.setRequestMethod("GET");
            conexao.connect();

            if (conexao.getResponseCode() == 200) {
                inputStream = conexao.getInputStream();
                mRespostaJson = converterInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Erro de resposta do servidor " + conexao.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Erro de conexão" + e);
        } finally {
            if (conexao != null) {
                conexao.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return mRespostaJson;
    }

    //método responsavel por ler o inputStream e converter para String
    private String converterInputStream(InputStream inputStream) throws IOException {

        StringBuilder saida = new StringBuilder();
        String linha = "";

        if (inputStream != null) {
            InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(reader);

            linha = bufferedReader.readLine();
            while (linha != null) {
                saida.append(linha);
                linha = bufferedReader.readLine();
            }
        }
        return saida.toString();

    }

    private Cep extrairJSON(String mRespostaJson) throws JSONException {

        Cep cepLista = null;

        if (mRespostaJson == null) {
            return null;
        }

        try {
            JSONObject valoresJson = new JSONObject(mRespostaJson);
            String cepAtual = valoresJson.optString("cep");
            String logradouro = valoresJson.optString("logradouro");
            String bairro = valoresJson.optString("bairro");
            String localidade = valoresJson.optString("localidade");
            String uf = valoresJson.optString("uf");

            cepLista = new Cep(cepAtual, logradouro, bairro, localidade, uf);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Erro em extrair JSON" + e);
        }
        return cepLista;
    }
}
