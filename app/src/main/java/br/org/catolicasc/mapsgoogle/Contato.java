package br.org.catolicasc.mapsgoogle;

public class Contato {
    private String nome;
    private String email;
    private float latitude;
    private float longitude;

    public Contato(String nome, String email, float latitude, float longitude) {
        this.nome = nome;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
