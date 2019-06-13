package br.ufc.quixada.concorrencia;

import android.util.Log;

public class Consumidor extends Thread {

    private static final String TAG = "Concorrencia-Consumidor";
    private final PutNewTextListener listener;

    private int id;
    private Buffer pilha;
    private int totalConsurmir;


    public Consumidor(int id, Buffer pilha, int totalConsumir, PutNewTextListener listener){
        this.id = id;
        this.pilha = pilha;
        this.totalConsurmir = totalConsumir;
        this.listener = listener;
    }

    @Override
    public void run() {
        for(int i=0; i < this.totalConsurmir; i++){
            pilha.get(id);
        }

        listener.onNewText("Consumidor #" + id + " concluído!");
        Log.i(TAG, "Consumidor #" + id + " concluído!");
    }

}
