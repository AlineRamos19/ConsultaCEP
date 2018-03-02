package br.com.android.altech.consultacep;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private Button btnBuscarCEP;
    private EditText cepInformado;
    String cep;
    TextView respostaCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout linearLayout = findViewById(R.id.linear_layout_main);

        respostaCep = findViewById(R.id.respostaCEP);
        cepInformado = findViewById(R.id.cep_informado);

        btnBuscarCEP = findViewById(R.id.buscar_cep);
        btnBuscarCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cep = cepInformado.getText().toString();

                if (cep == null || cep.isEmpty()) {
                    Snackbar.make(linearLayout, "Favor informar um CEP válido",
                            Snackbar.LENGTH_SHORT).show();
                } else {

                    try {
                        Cep retorno = new CepAsyncTask(cep).execute().get();
                        respostaCep.setText(retorno.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
