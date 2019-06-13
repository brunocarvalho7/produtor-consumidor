package br.ufc.quixada.concorrencia;

import android.util.Log;

public class Produtor extends Thread {

    private static final String TAG = "Concorrencia-Produtor";
    private final PutNewTextListener listener;

    private int id;
    private Buffer pilha;
    private int producaoTotal;


    public Produtor(int id, Buffer pilha, int producaoTotal, PutNewTextListener listener){
        this.id = id;
        this.pilha = pilha;
        this.producaoTotal = producaoTotal;
        this.listener = listener;
    }

    @Override
    public void run() {
        for(int i=0; i < this.producaoTotal; i++){
            pilha.set(id, i);
        }

        listener.onNewText("Produtor #" + id + " concluído!");
        Log.i(TAG, "Produtor #" + id + " concluído!");
    }
}
