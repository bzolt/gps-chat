package io.realm;


public interface ChatRealmProxyInterface {
    public long realmGet$id();
    public void realmSet$id(long value);
    public String realmGet$name();
    public void realmSet$name(String value);
    public RealmList<hu.szoftarch.hf.gpschat.model.Message> realmGet$messageList();
    public void realmSet$messageList(RealmList<hu.szoftarch.hf.gpschat.model.Message> value);
    public String realmGet$serverId();
    public void realmSet$serverId(String value);
    public String realmGet$type();
    public void realmSet$type(String value);
}
