package br.ufc.quixada.concorrencia;

import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PutNewTextListener, View.OnClickListener {

    private static final String TAG = "Concorrencia-MainActivity";
    private static int SEQUENCE_ID_CONSUMIDOR = 1;
    private static int SEQUENCE_ID_PRODUTOR = 1;

    private TextInputEditText txtConsumidor, txtProdutor;
    private TextView txtLogs;
    private Handler handler;
    private Buffer bufferCompartilhado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.handler = new Handler();
        this.bufferCompartilhado = new Buffer(this);

        findViewById(R.id.btnIniciarDefault).setOnClickListener(this);
        findViewById(R.id.btnIniciarProdutor).setOnClickListener(this);
        findViewById(R.id.btnIniciarConsumidor).setOnClickListener(this);

        txtConsumidor = findViewById(R.id.txtConsumidor);
        txtProdutor = findViewById(R.id.txtProdutor);
        txtLogs = findViewById(R.id.logs);
        txtLogs.setMovementMethod(new ScrollingMovementMethod());
    }

    public void addText(String s){
        txtLogs.setText( txtLogs.getText() + "\n" + s );
    }


    @Override
    public void onNewText(final String s) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                addText(s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btnIniciarConsumidor){
            int qtdConsumir = -1;

            try{
                qtdConsumir = Integer.parseInt(txtConsumidor.getText().toString());
                Consumidor consumidor = new Consumidor(SEQUENCE_ID_CONSUMIDOR++, bufferCompartilhado, qtdConsumir, this);
                consumidor.start();
            }catch (ClassCastException e){
                Log.e(TAG, "Erro ao tentar converter " + e.getMessage() );
                Toast.makeText(this, "Informe um valor valor a ser consumido", Toast.LENGTH_SHORT).show();
            }

        }else if(id == R.id.btnIniciarProdutor){
            int qtdProduzir = -1;

            try{
                qtdProduzir = Integer.parseInt(txtProdutor.getText().toString());
                Produtor produtor = new Produtor(SEQUENCE_ID_PRODUTOR++, bufferCompartilhado, qtdProduzir, this);
                produtor.start();
            }catch (ClassCastException e){
                Log.e(TAG, "Erro ao tentar converter " + e.getMessage() );
                Toast.makeText(this, "Informe um valor valor a ser produzido", Toast.LENGTH_SHORT).show();
            }

        }else if(id == R.id.btnIniciarDefault){
            Produtor produtor1 = new Produtor(SEQUENCE_ID_PRODUTOR++, bufferCompartilhado, 5, this);
            Produtor produtor2 = new Produtor(SEQUENCE_ID_PRODUTOR++, bufferCompartilhado, 5, this);

            Consumidor consumidor1 = new Consumidor(SEQUENCE_ID_CONSUMIDOR++, bufferCompartilhado, 2, this);
            Consumidor consumidor2 = new Consumidor(SEQUENCE_ID_CONSUMIDOR++, bufferCompartilhado, 8, this);

            produtor1.start();
            consumidor1.start();

            produtor2.start();
            consumidor2.start();
        }
    }
}
