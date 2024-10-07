import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceVeiculo {
    private List<Veiculo> frota = new ArrayList<>();

    public Veiculo save(Veiculo veiculo) {
        frota.add(veiculo);
        return veiculo;
    }

    public Veiculo findByPlaca(String placa) throws Exception {
        Veiculo veiculoRet = null;
        for (Veiculo veiculo : frota) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                veiculoRet = veiculo;
                break;
            }
        }
        if (veiculoRet == null)
            throw new Exception("Veículo não encontrado com a placa informada");
        return veiculoRet;
    }


    public void cadastrar(Veiculo veiculo) throws Exception{
        if (veiculo.getMarca() == null || veiculo.getMarca().isEmpty()){
            throw new Exception("A marca do veiculo não pode ser vazia.");
        }
        
        if (veiculo.getModelo() == null || veiculo.getModelo().isEmpty()){
            throw new Exception("O modelo do veiculo não pode ser vazio.");
        }

        if (veiculo.getPlaca() == null || veiculo.getPlaca().isEmpty()){
            throw new Exception("A placa do veiculo não pode ser vazia.");
        }

        if (veiculo.getAno() <= 1800 || veiculo.getAno() > LocalDate.now().getYear()){
            throw new  Exception("Informe um ano válido... (Maior que 1800 e menor ou igual ao ano atual)");
        }

        for (Veiculo v : frota) {
            if (v.getPlaca().equalsIgnoreCase(veiculo.getPlaca())) {
                throw new Exception("Já existe um veículo cadastrado com esta placa: " + veiculo.getPlaca());
            }
        }

        frota.add(veiculo);
    }
    public void removerPorPlaca(String placa) throws Exception {
        boolean placaRemovida = false;
        for (Veiculo veiculo : frota) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                frota.remove(veiculo);
                placaRemovida = true;
                break;
            }
        }
        if (!placaRemovida) {
            throw new Exception("Nenhum veiculo encontrado com essa placa" + placa);
        }
    }

    public List<Veiculo> findAll() {
        return frota;
    }
}