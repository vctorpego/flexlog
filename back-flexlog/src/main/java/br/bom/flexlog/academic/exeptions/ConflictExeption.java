package br.bom.flexlog.academic.exeptions;

public class ConflictExeption  extends RuntimeException{
    public ConflictExeption(String message){
        super(message);
    }
}
