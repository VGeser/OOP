package ru.nsu.fit.lab3;

import java.util.Arrays;

public class Stack <T> {
    private T[] containArray;
    private int size=0;
    private int capacity;
    public void stackCreate(){
        this.capacity=50;
        containArray=(T[]) new Object[capacity];
    }
    public byte stackCreate(int cap){
        this.capacity=cap;
        try{
            containArray=(T[]) new Object[cap];
            return 1;
        }catch (OutOfMemoryError out){
            return 0;
        }
    }
    public int count(){
        return size;
    }
    public byte resize(){
        int newSize=(containArray.length)*2;
        try{
            //????
            containArray = Arrays.copyOf(containArray, newSize);
        }catch (OutOfMemoryError|NullPointerException out){
            return 0;
        }
        return 1;
    }
    public byte push(T elem) {
        if (size < capacity) {
            containArray[size] = elem;
        } else {
            byte success = resize();
            if (success == 0) {
                return 0;
            }
        }
        size++;
        return 1;
    }
    public byte pushStack(T[]entry){
        int success,entryLen;
        entryLen=entry.length;
        if(entryLen+size<capacity){
            System.arraycopy(entry,0,containArray,size,entryLen);
        }else {
            success=resize();
            if(success==0)return 0;
            return pushStack(entry); //to make sure array is resized enough times to fit
        }
        return 1;
    }

    public T pop()throws NullPointerException{
        return containArray[size];
    }

    public T[] popStack(int num)throws NullPointerException{
        T[] current = (T[]) new Object[num];
        int j=num;
        for (int i = 0; i < num; i++) {
            current[j-1]=containArray[size-1];
            j--;
        }
        return current;
    }

}
