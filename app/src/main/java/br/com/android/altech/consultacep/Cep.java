package br.com.android.altech.consultacep;


public class Cep {

    private String mCep;
    private String mLogradouro;
    private String mBairro;
    private String mCidade;
    private String mEstado;

    public Cep(String cep, String logradouro, String bairro,
               String cidade, String estado) {
        this.mCep = cep;
        this.mLogradouro = logradouro;
        this.mBairro = bairro;
        this.mCidade = cidade;
        this.mEstado = estado;
    }

    public String getmCep() {
        return mCep;
    }

    public void setmCep(String mCep) {
        this.mCep = mCep;
    }

    public String getmLogradouro() {
        return mLogradouro;
    }

    public void setmLogradouro(String mLogradouro) {
        this.mLogradouro = mLogradouro;
    }

    public String getmBairro() {
        return mBairro;
    }

    public void setmBairro(String mBairro) {
        this.mBairro = mBairro;
    }

    public String getmCidade() {
        return mCidade;
    }

    public void setmCidade(String mCidade) {
        this.mCidade = mCidade;
    }

    public String getmEstado() {
        return mEstado;
    }

    public void setmEstado(String mEstado) {
        this.mEstado = mEstado;
    }

    @Override
    public String toString() {
        return "CEP: " + getmCep()
                + "\nLogradouro: " + getmLogradouro()
                + "\nBairro: " + getmBairro()
                + "\nCidade: " + getmCidade()
                + "\nEstado: " + getmEstado();
    }
}
