public class Encomenda {
    private String nomeCliente;
    private String telefone;
    private String produto;
    private String modelo;
    private String cor;
    private int quantidade;
    private String status; // <<< adicionando status

    // Construtor atualizado
    public Encomenda(String nomeCliente, String telefone, String produto, String modelo, String cor, int quantidade,
            String status) {
        this.nomeCliente = nomeCliente;
        this.telefone = telefone;
        this.produto = produto;
        this.modelo = modelo;
        this.cor = cor;
        this.quantidade = quantidade;
        this.status = status; // <<< inicializa o status também
    }

    // Getters e Setters
    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getStatus() { // <<< getter do status
        return status;
    }

    public void setStatus(String status) { // <<< setter do status
        this.status = status;
    }
}
