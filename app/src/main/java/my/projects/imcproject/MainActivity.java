
package my.projects.imcproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Button btnCalcular;
    private EditText edtAltura, edtPeso;
    private TextView txtResult;
    private float imc;
    private String mensagem;
    private StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();

        edtAltura.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                sb = new StringBuilder(edtAltura.getText().toString());

                if (keyCode == 67) { //Backspace's code returned from App
                    if (sb.length() == 3) {
                        sb.deleteCharAt(sb.indexOf(","));
                        edtAltura.setText("");
                        edtAltura.getText().append(sb.toString());
                    }
                    return false;
                }

                if (keyCode == 55) return true; //Comma's code returned from App

                if (sb.length() == 3) {
                    sb.insert(sb.length() - 2, ",");
                    edtAltura.setText("");
                    edtAltura.getText().append(sb.toString());
                }

                return false;
            }
        });

        edtPeso.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                sb = new StringBuilder(edtPeso.getText().toString());

                if (keyCode == 67 ){ //Backspace's code returned from App
                    if (sb.length() <= 3) {
                        if (edtPeso.getText().toString().contains(",")) {
                            sb.deleteCharAt(sb.indexOf(","));
                        }
                    } else {
                        sb.deleteCharAt(sb.indexOf(","));
                        sb.insert(sb.length() - 2, ",");
                    }
                    edtPeso.setText("");
                    edtPeso.getText().append(sb.toString());
                    return false;
                }

                if (keyCode == 55) return true; //Comma's code returned from App

                if (sb.length() >= 3) {
                    if (edtPeso.getText().toString().contains(",")){
                        sb.deleteCharAt(sb.indexOf(","));
                    }

                    sb.insert(sb.length() - 2, ",");
                    edtPeso.setText("");
                    edtPeso.getText().append(sb.toString());
                }

                return false;
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                imc = -1;

                calcularIMC();

                if (imc != -1) {
                    estadoPaciente(imc);

                    DecimalFormat df = new DecimalFormat("0.00");
                    txtResult.setText(getString(R.string.seu_imc) + ": " + df.format(imc) + "\n" +
                            getString(R.string.sua_classificacao) + ": " + mensagem);
                }
            }
        });
    }

    private void estadoPaciente(float imc) {
        mensagem = getString(R.string.indefinido);
        if (imc < 18.5)
            mensagem = getString(R.string.magreza);
        else if (imc <= 24.9)
            mensagem = getString(R.string.normal);
        else if (imc <= 29.9)
            mensagem = getString(R.string.obesidade_i);
        else if (imc <= 39.9)
            mensagem = getString(R.string.obesidade_ii);
        else if (imc >= 40)
            mensagem = getString(R.string.obesidade_iii);
    }

    private void calcularIMC() {
        String alturaS = edtAltura.getText().toString().replace(',', '.');
        String pesoS = edtPeso.getText().toString().replace(',', '.');

        if (alturaS.isEmpty() || pesoS.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_preencha_valores), Toast.LENGTH_LONG).show();
            txtResult.setText("");
        } else {
            try {
                float altura = Float.parseFloat(alturaS);
                float peso = Float.parseFloat(pesoS);
                imc = peso / (altura * altura);
            } catch (Exception e){
                Toast.makeText(this, getString(R.string.msg_valores_invalos), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void inicializarComponentes() {
        btnCalcular = findViewById(R.id.btnCalcular);

        edtAltura = findViewById(R.id.edtAltura);
        edtPeso = findViewById(R.id.edtPeso);

        txtResult = findViewById(R.id.txtResult);
    }
}