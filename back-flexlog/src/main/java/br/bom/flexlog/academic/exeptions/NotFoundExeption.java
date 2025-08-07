package br.bom.flexlog.academic.exeptions;



public class NotFoundExeption  extends RuntimeException{
    public NotFoundExeption(String message){
        super(message);
    }
}
