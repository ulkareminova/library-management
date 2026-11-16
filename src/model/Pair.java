package model;

public class Pair<K,V> {
    public K key;
    public V value;
    public Pair(K Key, V Value){
        this.key = Key;
        this.value = Value;
    }
    public K getKey(){
        return this.key;
    }
    public V getValue(){
        return this.value;
    }
    public void setKey(K key){
        this.key = key;
    }
    public void setValue(V value){
        this.value = value;
    }

}
