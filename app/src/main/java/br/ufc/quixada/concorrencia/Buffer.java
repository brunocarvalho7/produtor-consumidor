package br.ufc.quixada.concorrencia;

import android.util.Log;

public class Buffer {

    private static final String TAG = "Concorrencia-Buffer";

    private int conteudo;
    private boolean disponivel;
    private PutNewTextListener listener;

    public Buffer(PutNewTextListener listener){
        this.listener = listener;
    }

    public synchronized void set(int idProdutor, int valor){
        while(disponivel){
            try{
                listener.onNewText("Produtor #" + idProdutor + " esperando...");
                Log.i(TAG, "Produtor #" + idProdutor + " esperando...");
                wait();
            } catch (InterruptedException e) {
                Log.e(TAG, "Ocorreu uma interrupção enquanto o produtor " + idProdutor + " esperava. " +
                        "Erro: " + e.getMessage() );
            }
        }

        this.conteudo = valor;
        listener.onNewText("Produtor #"+idProdutor+" colocou " + conteudo);
        Log.i(TAG, "Produtor #"+idProdutor+" colocou " + conteudo);
        disponivel = true;
        notifyAll();
    }

    public synchronized int get(int idConsumidor){
        while(!disponivel){
            try{
                listener.onNewText("Consumidor #" + idConsumidor + " esperando...");
                Log.i(TAG, "Consumidor #" + idConsumidor + " esperando...");
                wait();
            } catch (InterruptedException e) {
                Log.e(TAG, "Ocorreu uma interrupção enquanto o consumidor " + idConsumidor + " esperava. " +
                        "Erro: " + e.getMessage() );
            }
        }

        listener.onNewText("Consumidor #"+idConsumidor+" consumiu " + conteudo);
        Log.i(TAG, "Consumidor #"+idConsumidor+" consumiu " + conteudo);
        disponivel = false;
        notifyAll();

        return conteudo;
    }

}
